package org.codeforiraq.myvolley.Model;

public class Book {
    private int id;
    private String name;
    private String author;
    private String created_at;
    private String updated_at;

    public Book() {
    }

    public Book(String name, String author) {
        this.name = name;
        this.author = author;
    }

    public Book(String name, String author, String created_at) {
        this.name = name;
        this.author = author;
        this.created_at = created_at;
    }

    public Book(int id, String name, String author, String created_at) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.created_at = created_at;
    }

    public Book(int id, String name, String author, String created_at, String updated_at) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.created_at = created_at;
        this.updated_at = updated_at;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
