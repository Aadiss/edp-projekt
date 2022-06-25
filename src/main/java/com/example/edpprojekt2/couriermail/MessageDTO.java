package com.example.edpprojekt2.couriermail;

public class MessageDTO {
    private String template;
    private ToDTO to;
    private DataDTO data;

    public MessageDTO(String template, ToDTO to, DataDTO data) {
        this.template = template;
        this.to = to;
        this.data = data;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public void setTo(ToDTO to) {
        this.to = to;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public String getTemplate() {
        return template;
    }

    public ToDTO getTo() {
        return to;
    }

    public DataDTO getData() {
        return data;
    }
}
