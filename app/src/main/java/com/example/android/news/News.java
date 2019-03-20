package com.example.android.news;

public class News {
    private String mTitle, mContent, mAuthor, mDate, mUrl, mImageUrl;

    public News(String imageUrl, String title, String content, String author, String date, String url) {
        mImageUrl = imageUrl;
        mTitle = title;
        mContent = content;
        mAuthor = author;
        mDate = date;
        mUrl = url;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getContent() {
        return mContent;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getDate() {
        return mDate;
    }

    public String getUrl() {
        return mUrl;
    }
}
