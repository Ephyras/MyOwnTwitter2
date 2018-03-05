package com.example.javier.mot2;

/**
 * Created by Javier on 2/5/2018.
 */

class Message {

    private String author;
    private String message;
    private Long publishedAt; //timestamp


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Long publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
