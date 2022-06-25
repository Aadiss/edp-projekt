package com.example.edpprojekt2.couriermail;

public class MailBodyDTO {
    private MessageDTO message;

    public MailBodyDTO(MessageDTO message) {
        this.message = message;
    }

    public MessageDTO getMessage() {
        return message;
    }

    public void setMessage(MessageDTO message) {
        this.message = message;
    }
}
