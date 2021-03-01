package com.example.gapoclone.Model;

public class TypePost {
    private String title;
    private int drawable_post;


    public TypePost(String title, int drawable_post) {
        this.title = title;
        this.drawable_post = drawable_post;
    }

    public TypePost() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDrawable_post() {
        return drawable_post;
    }

    public void setDrawable_post(int drawable_post) {
        this.drawable_post = drawable_post;
    }

    @Override
    public String toString() {
        return "TypePost{" +
                "title='" + title + '\'' +
                ", drawable_post=" + drawable_post +
                '}';
    }
}
