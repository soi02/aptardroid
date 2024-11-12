package com.inhatc.android_fianl;

public class BoardItem {
    private String uid;
    private String title;
    private String content;
    private String writer;

    public BoardItem(String uid, String title, String content, String writer) {
        this.uid = uid;
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

    public String getWriter(){
        return writer;
    }

    public String getUid(){
        return uid;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}

