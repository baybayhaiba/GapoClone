package com.example.gapoclone.Model;

import com.google.firebase.Timestamp;

public class Comment {
    private String idComment;
    private String idPost;
    private String idPerson;
    private String text;
    private Timestamp lastComment;


    public Comment() {
    }

    public Comment(String idComment, String idPost, String idPerson, String text, Timestamp lastComment) {
        this.idComment = idComment;
        this.idPost = idPost;
        this.idPerson = idPerson;
        this.text = text;
        this.lastComment = lastComment;
    }

    public String getIdComment() {
        return idComment;
    }

    public void setIdComment(String idComment) {
        this.idComment = idComment;
    }

    public String getIdPost() {
        return idPost;
    }

    public void setIdPost(String idPost) {
        this.idPost = idPost;
    }

    public String getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(String idPerson) {
        this.idPerson = idPerson;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getLastComment() {
        return lastComment;
    }

    public void setLastComment(Timestamp lastComment) {
        this.lastComment = lastComment;
    }
}
