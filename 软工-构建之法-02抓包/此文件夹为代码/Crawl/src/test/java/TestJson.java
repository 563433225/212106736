import org.json.JSONArray;
import org.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


import java.io.IOException;

public class TestJson {
    private static CloseableHttpResponse response = null;;
    public static String geturl(String url) throws Exception {
        String str = "";
        String s = extracted(url);
        JSONObject jsonResult  = new JSONObject(s);
        JSONArray json1 = jsonResult.getJSONArray("data");
        for (int i = 0; i < json1.length(); i++) {
            JSONObject jo1 = json1.getJSONObject(i);
            JSONObject jo2 = jo1.getJSONObject("content");
            JSONObject jo3 =jo2.getJSONObject("question");
            String title = jo3.getString("title");
            String utl = jo3.getString("url");
            System.out.println(title);
            System.out.println(utl);
            str= str+"{\"title\":\""+title+"\",\"url\":\""+url+"\"},";
        }
        return str;
    }

    //访问朴朴指定页面
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