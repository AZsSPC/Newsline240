package com.azspc.newsline240.post;

import android.content.res.Resources;

import com.azspc.newsline240.R;

public class Post {
    private String title, text, author;
    private int color;

    int getColor() {
        return color;
    }

    String getAuthor() {
        return author + "  ";
    }

    String getTitle() {
        return title;
    }

    String getText() {
        return text;
    }


    public Post(Resources r, String title, String text, String type, String author) {
        this.title = title;
        this.text = text;
        this.author = author;
        this.color = r.getColor(initColor(type));
    }

    private int initColor(String c) {
        switch (c) {
            case "0":
                return R.color.t_system;
            default:
                return R.color.t_normal;
            case "2":
                return R.color.t_spy;
            case "3":
                return R.color.t_alarm;
        }
    }
}
