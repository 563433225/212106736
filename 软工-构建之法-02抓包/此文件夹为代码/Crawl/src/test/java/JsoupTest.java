
import org.apache.http.client.methods.CloseableHttpResponse;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import java.io.IOException;
import java.util.regex.Pattern;

public class JsoupTest {
    private static CloseableHttpResponse response = null;
    private static String zhihu = "https://www.zhihu.com";
    @Test
    public static void main(String[] args) throws Exception {
        Document doc = getDocument("https://www.zhihu.com/people/wei-wei-59-66-16/collections");
        //使用标签选择器
        String title = doc.getElementsByTag("title").first().text();
        System.out.println(title);
        //根据class获取到 页面元素内容
        Elements tables1 = doc.getElementsByClass("SelfCollectionItem-title");
        System.out.println(tables1);
        //根据  a  标签来划分
        Elements a = tables1.select("a");

        for(int j=0;j<a.size();j++) {
            //获取到标签中的内容
            String text = a.get(j).text();
            System.out.println(text);
            //获取A标签的href 网址  select 获取到当前A标签 attr href 获取到地址
            String s = a.get(j).select("a").attr("href");
            String in = Pattern.compile("[^0-9]").matcher(s).replaceAll("").trim();
            System.out.println(in.trim());
            //TestJson.geturl("https://www.zhihu.com/api/v4/collections/"+in+"/items?offset=0&limit=20");
        }
    }



    private static Document getDocument( String sUrl) throws IOException {
        //访问的url点至，第二个是超时时间
        Document doc2 = null;
        //创建网站连接和添加协议头
        Connection connect =Jsoup.connect(sUrl);
        connect.header("Host","www.zhihu.com");
        connect.header("Connection","keep-alive");
        connect.header("Cache-Control","max-age=0");
        connect.header("sec-ch-ua"," \" Not A;Brand\";v=\"99\", \"Chromium\";v=\"99\", \"Google Chrome\";v=\"99\"");
        connect.header("sec-ch-ua-mobile","?0");
        connect.header("sec-ch-ua-platform","\"Windows\"");
        connect.header("Upgrade-Insecure-Requests","1");
        connect.header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.51 Safari/537.36");
        connect.header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        connect.header("Sec-Fetch-Site","same-origin");
        connect.header("Sec-Fetch-Mode","navigate");
        connect.header("Sec-Fetch-User","?1");
        connect.header("Fetch-Dest","document");
        //连接网址
        doc2 = connect.get();
        return doc2;
    }

}
