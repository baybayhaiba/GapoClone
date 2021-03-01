package com.example.gapoclone.Model;

public class NewsFeed {
    private String image_news_feed;
    private String image_owner;
    private String name_owner;


    public static class Builder {
        private String image_news_feed;
        private String image_owner;
        private String name_owner;

        public Builder() {
        }

        public Builder image_news_feed(String image_news_feed) {
            this.image_news_feed = image_news_feed;
            return this;
        }

        public Builder image_owner(String image_owner) {
            this.image_owner = image_owner;
            return this;
        }

        public Builder name_owner(String name_owner) {
            this.name_owner = name_owner;
            return this;
        }

        public NewsFeed build() {
            return new NewsFeed(this);
        }
    }

    public NewsFeed(Builder builder) {
        this.image_news_feed = builder.image_news_feed;
        this.image_owner = builder.image_owner;
        this.name_owner = builder.name_owner;
    }

    public NewsFeed(String imgage_news_feed, String image_owner, String owner) {
        this.image_news_feed = imgage_news_feed;
        this.image_owner = image_owner;
        this.name_owner = owner;
    }

    public NewsFeed() {
    }

    public String getImage_news_feed() {
        return image_news_feed;
    }

    public void setImage_news_feed(String image_news_feed) {
        this.image_news_feed = image_news_feed;
    }

    public String getImage_owner() {
        return image_owner;
    }

    public void setImage_owner(String image_owner) {
        this.image_owner = image_owner;
    }

    public String getName_owner() {
        return name_owner;
    }

    public void setName_owner(String name_owner) {
        this.name_owner = name_owner;
    }

    @Override
    public String toString() {
        return "Newfeed{" +
                "imgage_news_feed='" + image_news_feed + '\'' +
                ", image_owner='" + image_owner + '\'' +
                ", owner='" + name_owner + '\'' +
                '}';
    }
}
