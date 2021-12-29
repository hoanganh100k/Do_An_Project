package com.example.lib.Model;

public class CommentModel {
    private String name;
    private String content;

    public CommentModel(String name, String content) {
        this.name = name;
        this.content = content;
    }
    public CommentModel() {
        this.name = "";
        this.content = "";
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }


}
