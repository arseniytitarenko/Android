package com.example.exam6;

import java.io.Serializable;

public class Contact implements Serializable {
    private int id;
    private String name;
    private String phone;
    private String comment;

    public Contact(int id, String name, String phone, String comment) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.comment = comment;
    }

    public Contact(String name, String phone, String comment) {
        this.name = name;
        this.phone = phone;
        this.comment = comment;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getComment() { return comment; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setComment(String comment) { this.comment = comment; }
}
