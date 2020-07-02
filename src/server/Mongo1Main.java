/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

/**
 *
 * @author ADMIN
 */
public class Mongo1Main {

    public static void main(String[] args) throws Exception {
        Mongo mongo = new Mongo();
        DB db = mongo.getDB("QlyCoffee");
        DBCollection employees = db.getCollection("TEST");
//                    employees.insert(new BasicDBObject().append("name", "nam Hightower").append("gender", "f").append("phone", "123456789").append("age", 30));

        DBCursor cursor = employees.find(new BasicDBObject().append("name", "c Hightower"));
        if (cursor.hasNext()) {
            DBObject object = cursor.next();
            System.out.println(object);

        } else {
            System.out.println("ko co");

        }
        

    }

}
