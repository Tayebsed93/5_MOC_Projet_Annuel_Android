package com.esgi.iaitmansour.myfoot.Models;

/**
 * Created by iaitmansour on 08/07/2018.
 */

public class News {

    public String title;
    public String content;
    public String imgUrl;
    public String created;
    public  int id;

    public News() {
    }

    public News(String title, String content, String imgUrl, int id) {
        this.title = title;
        this.content = content;
        this.imgUrl = imgUrl;
        this.id = id;
    }

    public News(String title, String content, String imgUrl, String created, int id) {
        this.title = title;
        this.content = content;
        this.imgUrl = imgUrl;
        this.created = created;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
