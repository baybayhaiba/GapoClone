package com.example.gapoclone.Model;


import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Date;

public class Post {
    private String idPost;
    private String idPerson;
    private ArrayList<String> imgPost;
    private ArrayList<Comment> comments;
    private ArrayList<React> reacts;
    private Content content;
    private long postAgo;


    public Post() {
    }

    public Post(String idPost, String idPerson, ArrayList<String> imgPost,
                ArrayList<Comment> comments, ArrayList<React> reacts,
                Content content, Timestamp postAgo) {
        this.idPost = idPost;
        this.idPerson = idPerson;
        this.imgPost = imgPost;
        this.comments = comments;
        this.reacts = reacts;
        this.content = content;
        this.postAgo = postAgo.getSeconds();
    }


    public ArrayList<React> getReacts() {
        return reacts;
    }

    public void setReacts(ArrayList<React> reacts) {
        this.reacts = reacts;
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

    public ArrayList<String> getImgPost() {
        return imgPost;
    }

    public void setImgPost(ArrayList<String> imgPost) {
        this.imgPost = imgPost;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

//    public Timestamp getPostAgo() {
//        return postAgo;
//    }
//
//    public void setPostAgo(Timestamp postAgo) {
//        this.postAgo = postAgo;
//    }


    public long getPostAgo() {
        return postAgo;
    }

    public void setPostAgo(long postAgo) {
        this.postAgo = postAgo;
    }

    @Override
    public String toString() {
        return "Post{" +
                "idPost='" + idPost + '\'' +
                ", idPerson='" + idPerson + '\'' +
                ", imgPost=" + imgPost +
                ", comments=" + comments +
                ", content=" + content +
                // ", postAgo=" + postAgo +
                '}';
    }
}
