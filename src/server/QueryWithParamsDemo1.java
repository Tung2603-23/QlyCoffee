/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 *
 * @author ADMIN
 */
import java.net.UnknownHostException;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class QueryWithParamsDemo1 {

    // Xây dựng JSON:
    // { "dept_name" : "ACCOUNTING"}
    public static DBObject getWhereClause_1() {
        BasicDBObjectBuilder whereBuilder = BasicDBObjectBuilder.start();

        // Sử dụng method append (cũng giống với dùng add)
        whereBuilder.append("username", "lexuanhuynh");
        whereBuilder.append("password", "huynhzip3");

        //
        DBObject where = whereBuilder.get();
        System.out.println("Where: " + where.toString());
        return where;
    }

    public static void main(String args[]) throws UnknownHostException {

        // Kết nối tới MongoDB.
        MongoClient mongoClient = MongoUtils.getMongoClient();

        DB db = mongoClient.getDB(MyConstants.DB_NAME);

        DBCollection dept = db.getCollection("Employee");

        DBObject where = getWhereClause_1();

        // Truy vấn theo điều kiện.
        DBCursor cursor = dept.find(where);
        int i = 1;
        while (cursor.hasNext()) {
            System.out.println("Document: " + i);
            System.out.println(cursor.next());
            i++;
        }

        System.out.println("Done!");
    }

    public QueryWithParamsDemo1() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   

    
}
