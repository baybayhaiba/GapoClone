package com.example.gapoclone.Model;

import com.google.firebase.Timestamp;

import java.util.ArrayList;

public class Content {

    private String contentText;
    private String typePost;
    private int backgroundPost;

    public Content() {
    }

    public Content(String contentText, String typePost, int backgroundPost) {
        this.contentText = contentText;
        this.typePost = typePost;
        this.backgroundPost = backgroundPost;
    }



    public String getTypePost() {
        return typePost;
    }

    public void setTypePost(String typePost) {
        this.typePost = typePost;
    }

    public int getBackgroundPost() {
        return backgroundPost;
    }

    public void setBackgroundPost(int backgroundPost) {
        this.backgroundPost = backgroundPost;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }
}
