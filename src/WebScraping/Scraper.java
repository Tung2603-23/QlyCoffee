package WebScraping;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import static java.lang.Integer.parseInt;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Scraper {

    public static void main(String[] args) throws Exception {
        final Document document = Jsoup.connect("https://www.highlandscoffee.com.vn/vn/espresso-coffee.html").get();
        for (Element row : document.select("#slideOther .item .product")) {
//                            System.out.println(document.outerHtml());

            final String name = row.select(".tend2 a").text();
            final String price = row.select(".price strong").text();
            int min = 0;
            int max = 10;
            int random_int = (int) (Math.random() * (max - min + 1) + min);

            //insert to database
            String string = price;
            String[] parts = string.split(" ");
            String part1 = parts[0];
            String[] partss = part1.split(",");
            String part11 = partss[0];
            String part22 = partss[1];
            String x = part11 + part22;
            int x1 = parseInt(x);
            int x2 = parseInt(x) + 10000;
            int x3 = parseInt(x) + 15000;
            System.out.println(name + " -> Giá: " + x);
            Mongo mongo = new Mongo();
            DB db = mongo.getDB("QlyCofee");
            DBCollection history = db.getCollection("Product");
            DBCollection history2 = db.getCollection("Product&ProductType");
            String s = "Nhỏ";
            for (int i = 1; i < 4; i++) {

                BasicDBObject document1 = new BasicDBObject();
                document1.put("IDProduct", "DF" + random_int);
                document1.put("ProductName", name);
                document1.put("IDType", "T0" + i);
                document1.put("Price", x1);
                history.insert(document1);

                BasicDBObject document2 = new BasicDBObject();
                document2.put("IDProduct", "DF" + random_int);
                document2.put("ProductName", name);
                document2.put("IDType", "T0" + i);
                document2.put("Price", x1);
                document2.put("TypeName", "Cà Phê");
                document2.put("Size", s);
                history2.insert(document2);
                if (i == 1) {
                    x1 = x2;
                    s = "Vừa";

                } else {
                    x1 = x3;
                    s = "Lớn";

                }
            }

        }
        System.out.println("Thu thập dữ liệu thành công.");

    }
}
