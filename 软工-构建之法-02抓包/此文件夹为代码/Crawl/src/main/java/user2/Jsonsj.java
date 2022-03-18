package user2;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class Jsonsj {
    private static CloseableHttpResponse response = null;;
    //通过JSON获取所需要的信息
    public static String geturl(String url) throws Exception {
        String str = "";
        String s = extracted(url);
    //将获取的字符串存储到json中
    JSONObject jsonResult  = new JSONObject(s);
       // System.out.println(jsonResult);
    //这是json数组，所以用到JSONArray
    JSONArray json1 = jsonResult.getJSONArray("data");
        //System.out.println(json1);
        //System.out.println(json1);
        for (int i = 0; i < json1.length(); i++) {
            //按节点查找
        JSONObject jo1 = json1.getJSONObject(i);
        JSONObject jo2 = jo1.getJSONObject("content");
        JSONObject jo3 =jo2.getJSONObject("question");
        String title = jo3.getString("title");
        String utl = jo3.getString("url");
        System.out.println(title);
        System.out.println(utl);
        str= str+"{\"title\":\""+title+"\",\"url\":\""+url+"\"},";
        }
        str = str.substring(0,str.length() - 1);
        str = "\"count\":["+str+"]";
        return str;
}
    //连接方法
    private static  String extracted( String us) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //输入网址，发起get请求，创建HttpGet对象
        HttpGet httpGet = new HttpGet(us);
        response = httpClient.execute(httpGet);
        try{
            //响应代码为200，则链接成功
            if (response.getStatusLine().getStatusCode() == 200) {
                //解析响应，获取数据
                HttpEntity httpEntity = response.getEntity();
                String s = EntityUtils.toString(httpEntity);
                return s;
            }else {
                System.out.println("连接失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }finally {
            //关闭连接
            response.close();
        }
        return null;
    }
}
