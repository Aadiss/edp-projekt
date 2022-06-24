package com.example.edpprojekt2.mongodb;

import com.mongodb.BasicDBObject;
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
    private MongoCollection<Document> usersCollection;

    public MongoAdapter() {

        MongoDatabase db = MongoClients.create(MONGODB_URL).getDatabase("edp");
        this.edpCollection = db.getCollection("edp");
        this.usersCollection = db.getCollection("users");
    }

    public void insertGame(Document game) {
        this.edpCollection.insertOne(game);
    }

    public void insertUser(Document user) {
        this.usersCollection.insertOne(user);
    }

    public UserDTO getUser(String username){
        BasicDBObject query = new BasicDBObject();
        query.put("username", username);

        return this.usersCollection.find(query).map(this::toUserDTO).first();
    }

    public boolean checkUserExistence(String username, String email) {
        BasicDBObject queryUsername = new BasicDBObject();
        queryUsername.put("username", username);

        BasicDBObject queryEmail = new BasicDBObject();
        queryEmail.put("email", email);

        UserDTO usernameDTO = this.usersCollection.find(queryUsername).map(this::toUserDTO).first();
        UserDTO emailDTO = this.usersCollection.find(queryEmail).map(this::toUserDTO).first();

        if(usernameDTO == null && emailDTO == null) return true;

        return false;
    }

    private GameDTO toGameDTO(Document doc) {
        return new GameDTO(new ObjectId(doc.get("_id").toString()), doc.get("date").toString(), doc.get("prize").toString(), doc.get("result").toString(), doc.get("time").toString(), doc.get("userId").toString());
    }

    private UserDTO toUserDTO(Document doc) {
        return new UserDTO(new ObjectId(doc.get("_id").toString()), doc.get("username").toString(), doc.get("email").toString(), doc.get("password").toString(), doc.get("lastLogged").toString());
    }

    public List<GameDTO> getUserGames(String userId){
        List<GameDTO> result = new ArrayList<>();
        BasicDBObject query = new BasicDBObject();
        query.put("userId", userId);

        this.edpCollection.find(query).map(this::toGameDTO).forEach(result::add);
        return result;
    }

    public List<GameDTO> getALlGames() {
        List<GameDTO> result = new ArrayList<>();
        this.edpCollection.find().map(this::toGameDTO).forEach(result::add);
        return result;
    }
}
