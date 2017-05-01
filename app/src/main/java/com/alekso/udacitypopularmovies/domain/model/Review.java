package com.alekso.udacitypopularmovies.domain.model;

/**
 * Created by alekso on 01/05/2017.
 */

public class Review {
    private String mId;
    private String mAuthor;
    private String mContent;


    /**
     * Initialization constructor
     *
     * @param id
     * @param author
     * @param content
     */
    public Review(String id, String author, String content) {
        setId(id);
        setAuthor(author);
        setContent(content);
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    @Override
    public String toString() {
        return "{" + mId + ", " + mAuthor + ", " + mContent.substring(0, 10) + " } ";
    }
}
