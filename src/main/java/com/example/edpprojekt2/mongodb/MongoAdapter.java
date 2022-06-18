package com.example.edpprojekt2.mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MongoAdapter {
    private static final String MONGODB_URL = "mongodb+srv://root:root@cluster0.befft.mongodb.net/?retryWrites=true&w=majority";
    private MongoCollection<Document> edpCollection;

    public MongoAdapter() {

        MongoDatabase db = MongoClients.create(MONGODB_URL).getDatabase("edp");
        this.edpCollection = db.getCollection("edp");
    }

    public static void getCollection(){
        MongoClient client =  MongoClients.create(MONGODB_URL);
        client.listDatabaseNames().forEach(System.out::println);
    }

    public void insert(Document game){
        this.edpCollection.insertOne(game);
    }

    private GameDTO toGameDTO(Document doc){
        return new GameDTO(doc.get("date").toString(), doc.get("prize").toString(), doc.get("result").toString(), doc.get("time").toString());
    }

    public GameDTO getLastGame(){
        return toGameDTO(Objects.requireNonNull(this.edpCollection.find().first()));
    }

    public List<GameDTO> getALlGames(){
        List<GameDTO> result = new ArrayList<>();
        this.edpCollection.find().map(this::toGameDTO).forEach(result::add);
        return result;
    }
}
