package com.example.edpprojekt2.couriermail;

public class DataDTO {
    private String recipientName;

    public DataDTO(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }
}
