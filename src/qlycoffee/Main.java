/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qlycoffee;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import java.io.IOException;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static java.time.temporal.TemporalQueries.localDate;
import java.util.Arrays;
import java.util.List;
import javax.swing.text.Document;

/**
 *
 * @author HUYNH
 */
public class Main {

    public static void main(String[] args) throws UnknownHostException, IOException {
        new Login_Form().setVisible(true);
//        Mongo mongo = new Mongo();
//        DB db = mongo.getDB("QlyCofee");
//        DBCollection history = db.getCollection("Product&ProductType");
//        DBCursor cursor = history.find(new BasicDBObject().append("Size", "Nhỏ").append("ProductName", "Đen đá"));
//                    DBObject dbo2 = (DBObject) history.findOne(new BasicDBObject().append("Size", "Nhỏ").append("ProductName", "Đen đá"));

//        BasicDBObject projection = new BasicDBObject("Size", (String) cbSize.getSelectedItem()).append("ProductName", (String) cbproduct.getSelectedItem());
//                    DBCursor cursor = history.find(new BasicDBObject().append("STT", 2));
//                    DBCursor cursor = history.find(projection);
//
//                    long cc = history.count();
//                                DBObject ht=(DBObject) history.find("count":"1");
//   for(int i=0;i<cursor.size();i++){
//        if(cursor.hasNext())
//    {
//        System.out.println(dbo2);
//    }
// 
//   }
//                    FindIterable<Document> iterDoc = collection.find();
//      Iterator it = iterDoc.iterator();
//      while (it.hasNext()) {
//         System.out.println(it.next());
//      }
//DBCursor x=history.find();
////                    long x=employees.count();
//                    System.out.println(x.next());
//Mongo mongo = new Mongo();
//DB db = mongo.getDB("QlyCofee");
//DBCollection employees = db.getCollection("Employee");
//DBCursor cursor = employees.find(new BasicDBObject().append("NameEmp", "Dương Phương Thảo"));
//DBObject dbo = (DBObject) history.findOne(new BasicDBObject().append("NameEmp", "Dương Phương Thảo"));
//System.out.println(dbo);
//System.out.println(dbo.get("Gender"));

//System.out.println(cursor.next().get("Gender"));
//                    employees.insert(new BasicDBObject().append("name", "uj Hightower").append("gender", "f").append("phone", "123456789").append("age", 30));
//                    DBCursor cursor = employees.find(new BasicDBObject(new BasicDBObject("statusLog",
//                new BasicDBObject("$elemMatch", new BasicDBObject("status", "Submitted")))););
//BasicDBObject cursor  = new BasicDBObject(new BasicDBObject("Size",
//                new BasicDBObject("$elemMatch", new BasicDBObject("Size", "Nhỏ"))));
//                    DBObject cursor = (DBObject) employees.find();
//                                            DBCursor cursor = employees.find(new BasicDBObject().append("","").append("username",1));
//                      System.out.println(cursor.next());
//        List searchResults = employees.distinct("username");
//        int u=searchResults.size();
////int i=1;
//int i=0;
//
//                    while (i<u) {
//                        System.out.println(searchResults.get(i));
//                        i++;
//    }
//                         AggregateIterable<Document> iterable = themalComfortProfileCollection.aggregate(Arrays.asList(
//            new Document("$group", new Document("_id", new Document("date", "$Date").append("time", "$Time") ).append("ThermalComfortList", new Document("$push", "$ThermalComfort"))),
//            new Document("$sort", new Document("_id", 1))));
//                    }
//                    cursor=
//                    System.out.println(employees.find(new BasicDBObject(),cursor);
//MongoClient mongoClient = new MongoClient();
//DB database = mongoClient.getDB("QlyCofee");
//
//DBCollection collection = database.getCollection("Product&Type");
//BasicDBObject projection = new BasicDBObject("Size",new BasicDBObject("$elemMatch", new BasicDBObject("Size", "Lớn"))).append("ProductName","Sinh tố cam"); 
//DBCursor cursor=collection.find(projection);    
//  while (cursor.hasNext()) {
////                        DBObject object = cursor.next();
////                        System.out.println(cursor.next());
////                        String x=(String) cursor.next().get("IDType");
////                        System.out.println(x);
//
//                    }
//        System.out.println(java.time.LocalDate.now().toString());
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/LLLL/yyyy");
//        String x = java.time.LocalDate.now().format(formatter);
//                System.out.println(x);
//                
//DateTimeFormatter uuu = DateTimeFormatter.ofPattern("HH:mm:ss");
//        String y = java.time.LocalTime.now().format(uuu);
//        System.out.println(y);
//
//        Mongo mongo = new Mongo();
//        DB db = mongo.getDB("QlyCofee");
//        DBCollection orders = db.getCollection("OrderDetail");
//        BasicDBObject document = new BasicDBObject();
//        document.put("IDHĐ", "mkyongDB");
//        document.put("DateHĐ", java.time.LocalDate.now().toString());
//        document.put("TimeHĐ", java.time.LocalTime.now().toString());
//        document.put("userName", "hosting");
//
//        orders.insert(document);
//Mongo mongo = new Mongo();
//DB db = mongo.getDB("QlyCofee");
//DBCollection orders = db.getCollection("Employee");
//DBCursor cursor = orders.find(new BasicDBObject().append("","").append("username",1));
//while (cursor.hasNext()) {
//    System.out.println(cursor.next());
////    vec.add(cursor.next().get("username"));
//}
//        DB db = mongo.getDB("QlyCofee");
//
//        DBCollection coll = db.getCollection("OrderDetail");
        // create the pipeline operations, first with the $match
//        DBObject match = new BasicDBObject("$match",
//            new BasicDBObject("_id", employeeId)
//        );
        // build the $lookup operations
//        DBObject lookupFields = new BasicDBObject("from", "[Order]");
//        lookupFields.put("localField", "IDHĐ");
//        lookupFields.put("foreignField", "IDHĐ");
//        lookupFields.put("as", "[Order]");      
//        DBObject lookup = new BasicDBObject("$lookup", lookupFields);
//        
//
//
//        // build the $project operations
//        DBObject projectFields = new BasicDBObject("IDHĐ", 1);
//        projectFields.put("IDProduct", 1);
//        projectFields.put("Quantity", 1);
//        projectFields.put("TimeHĐ", "$[Order].TimeHĐ");       
//        DBObject project = new BasicDBObject("$project", projectFields);
//
//        List<DBObject> pipeline = Arrays.asList( lookup, project);
//
//        AggregationOutput output = coll.aggregate(pipeline);
//
//        for (DBObject result : output.results()) {
//            System.out.println(result);
//        }
//            BasicDBObject projection = new BasicDBObject( new BasicDBObject("$elemMatch", new BasicDBObject("Size", (String) cbSize.getSelectedItem()))).append("ProductName", (String) cbproduct.getSelectedItem());
//DBCursor cursor=coll.find();
//        Mongo mongo = new Mongo();
//        DB db = mongo.getDB("QlyCofee");
//        DBCollection history = db.getCollection("History");
//        DBCursor cursor = history.find(new BasicDBObject().append("DateHĐ", "12/6/2020").append("username", "12345"));
//        long u = (long) cursor.next().get("STT");
//            if (cursor.hasNext()) {

//        System.out.println(u);

//        }
    }
}
