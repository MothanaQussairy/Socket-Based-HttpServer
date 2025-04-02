package model;

import java.util.Date;

public class User {
    private int id;
    private String name;
    private String phone;
    public User( String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public User(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String toJson() {
        return String.format("{\"id\":%d, \"name\":\"%s\", \"phone\":\"%s\"}",
                id, name, phone);
    }
}
