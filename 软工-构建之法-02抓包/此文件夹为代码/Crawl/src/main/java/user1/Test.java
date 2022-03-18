package user1;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;


public class Test {
    private static CloseableHttpResponse response = null;
    private static String pupu = "https://j1.pupuapi.com/client/product/storeproduct/detail/7f10e39a-57cb-41a6-94f2-d5bf4e2abbbc/ed60af11-25b0-48b8-bc5b-f9136d9f89ad";

    public static void main(String[] args) throws Exception {
        //头代码
        top();
        System.out.println();
        //第二行标题
        centile();
        monitor();
    }

    private static void monitor() {
        new Timer().schedule(new TimerTask() {
            //执行监听次数
            private int count = 10;
            @Override
            public void run() {
                try {
                    if (count>0){
                        //使用连接方法
                        String dom = getDocument(pupu);
                        //将放回的字符串转换为json
                        JSONObject jsonObject = new JSONObject(dom);
                        //按节点查找
                        JSONObject data = jsonObject.getJSONObject("data");
                        //获取name的value
                        String name =data.getString("name");
                        //当前价格
                        double price = data.getDouble("price")/100;
                        //当前系统时间
                        String date = String.format("%tF %1$tT ",LocalDateTime.now());
                        System.out.println("当前时间为："+date+"   【"+name+"】的价格为："+price);
                        //执行过后减少一次
                        count--;
                    }
                    else {
                        //停止监听
                        cancel();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },0,2000);//启动延时为0，每隔2秒执行一次
    }

    private static void centile() throws Exception {
        //使用连接方法
        String dom = getDocument(pupu);
        //将放回的字符串转换为json
        JSONObject jsonObject = new JSONObject(dom);
        //按节点查找
        JSONObject data = jsonObject.getJSONObject("data");
        //获取name的value
        String name =data.getString("name");
        System.out.println("---------------\""+name+"\"的价格波动---------------");
    }

    private static void top() throws Exception {
        //使用连接方法
        String dom = getDocument(pupu);
        //System.out.println(dom);
        //将放回的字符串转换为json
        JSONObject jsonObject = new JSONObject(dom);
        //按节点查找
        JSONObject data = jsonObject.getJSONObject("data");

        //获取name的value
        String name =data.getString("name");

        //当前价格
        double price = data.getDouble("price")/100;

        //原价
        double market_price = data.getDouble("market_price")/100;

        //规格
        String spec= data.getString("spec");

        //内容详情
        String share_content = data.getString("share_content");
        System.out.println("---------------"+name+"---------------");
        System.out.println("规格："+spec);
        System.out.println("价格："+price);
        System.out.println("原价/折扣价："+market_price+"/"+price);
        System.out.println("详细内容："+share_content);
    }

    //连接方法
    private static String getDocument( String sUrl) throws Exception {
        //创建HttpClient对象，打开浏览器
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //发起Gget请求，输入网址
        HttpGet httpGet = new HttpGet(sUrl);
        //添加协议头
        httpGet.addHeader("Host","j1.pupuapi.com\n");
        httpGet.addHeader("Connection","keep-alive");
        httpGet.addHeader("Accep","application/json");
        httpGet.addHeader("Authorization","Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIiLCJhdWQiOiJodHRwczovL3VjLnB1cHVhcGkuY29tIiwiaXNfbm90X25vdmljZSI6IjEiLCJpc3MiOiJodHRwczovL3VjLnB1cHVhcGkuY29tIiwiZ2l2ZW5fbmFtZSI6IuWNq-WNq-WTnyIsImV4cCI6MTY0NzU5NjMzMywidmVyc2lvbiI6IjIuMCIsImp0aSI6IjM2ZmM4NDRmLWI1MDctNGQwNi05MDEzLWQxZGUxYzYwYTMwYiJ9.QGA4veRF5F7vpNXtGjnFL1vg2w17KWLP90D6OyoUImI");
        httpGet.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36 MicroMessenger/7.0.9.501 NetType/WIFI MiniProgramEnv/Windows WindowsWechat");
        httpGet.addHeader("content-type",":application/json");
        httpGet.addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.51 Safari/537.36");
        httpGet.addHeader("open-id","oMwzt0N-TOFXilsZAEJo3hOq6MtM");
        httpGet.addHeader("pp-os"," 0");
        httpGet.addHeader("pp-placeid","8eb75536-f871-452a-a3a8-37a3611eed13");
        httpGet.addHeader("pp-userid","36fc844f-b507-4d06-9013-d1de1c60a30b");
        httpGet.addHeader("pp-version:","2021063100");
        httpGet.addHeader("pp_storeid","7f10e39a-57cb-41a6-94f2-d5bf4e2abbbc");
        httpGet.addHeader("Referer","https://servicewechat.com/wx122ef876a7132eb4/156/page-frame.html");
        //发其请求，返回响应星系，使用httpClient对象发其请求
        response = httpClient.execute(httpGet);
        if (response.getStatusLine().getStatusCode()==200){
            HttpEntity httpEntity = response.getEntity();
            String content = EntityUtils.toString(httpEntity);
            return  content;
        }
        return null;
    }

}
