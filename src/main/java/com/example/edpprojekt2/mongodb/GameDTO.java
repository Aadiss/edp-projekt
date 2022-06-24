package com.example.edpprojekt2.mongodb;

import org.bson.Document;
import org.bson.types.ObjectId;

public class GameDTO {
    private ObjectId id;
    private String date;
    private String prize;
    private String result;
    private String time;
    private String userId;

    public GameDTO(String date, String prize, String result, String time, String userId) {
        this.date = date;
        this.prize = prize;
        this.result = result;
        this.time = time;
        this.userId = userId;
    }

    public GameDTO(ObjectId id, String date, String prize, String result, String time, String userId) {
        this.id = id;
        this.date = date;
        this.prize = prize;
        this.result = result;
        this.time = time;
        this.userId = userId;
    }

    public ObjectId getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getPrize() {
        return prize;
    }

    public String getResult() {
        return result;
    }

    public String getTime() {
        return time;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Game{"
                + "id='" + id + "'"
                + ", date='" + date + "'"
                + ", prize='" + prize + "'"
                + ", result='" + result + "'"
                + ", time='" + time + "'"
                + ", userId='" + userId + "'"
                + "}";
    }

    public Document toDocument() {
        return new Document("date", date).append("prize", prize).append("result", result).append("time", time).append("userId", userId);
    }
}
