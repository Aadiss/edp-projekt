package com.example.edpprojekt2.mongodb;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class MongoAdapter {
    private static final String MONGODB_URL = "mongodb+srv://root:root@cluster0.befft.mongodb.net/?retryWrites=true&w=majority";
    private MongoCollection<Document> edpCollection;

    public MongoAdapter() {

        MongoDatabase db = MongoClients.create(MONGODB_URL).getDatabase("edp");
        this.edpCollection = db.getCollection("edp");
    }

    public void insert(Document game) {
        this.edpCollection.insertOne(game);
    }

    private GameDTO toGameDTO(Document doc) {
        return new GameDTO(new ObjectId(doc.get("_id").toString()), doc.get("date").toString(), doc.get("prize").toString(), doc.get("result").toString(), doc.get("time").toString());
    }

    public List<GameDTO> getALlGames() {
        List<GameDTO> result = new ArrayList<>();
        this.edpCollection.find().map(this::toGameDTO).forEach(result::add);
        return result;
    }
}
