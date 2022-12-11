package com.example.framelibrary.data.books;

public class Book {
    private String id;
    private String title;
    private String author;
    private String category;
    private String description;
    private String coverPath;

    public Book(String id, String title, String author, String category, String description, String coverPath) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.description = description;
        String[] partsCoverPath = coverPath.split("://");
        this.coverPath = "https://" + partsCoverPath[1];
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }
}
