package edu.bu.met.cs665.repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

public class MongoRepository {
    private static MongoCollection collection;

    /**
     * @param collectionName
     */
    public MongoRepository(String collectionName) {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("SDPProject");
        this.collection = database.getCollection(collectionName);
    }

    /**
     *
     * @param attribute
     * @param key
     * @return Document
     */
    public <T> Document search(String attribute,T key) {
        return (Document) collection.find(new Document(attribute, key)).first();
    }

    /**
     * insert one record
     * @param document
     */
    public void insertOne(Document document) {
        collection.insertOne(document);
    }

    /**
     * update one record
     * @param id
     * @param document
     */
    public void updateOne(String id, Document document) {
        collection.replaceOne(new Document("_id", new ObjectId(id)), document);
    }

    /**
     * delete one record
     * @param attribute
     * @param key
     */
    public <T> void deleteOne(String attribute,T key) {
        collection.deleteOne(new Document(attribute, key));
//        System.out.println(key.getClass().getName());
//        System.out.println("deleting");
    }
}

