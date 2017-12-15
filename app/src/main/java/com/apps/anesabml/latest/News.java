package com.apps.anesabml.latest;

/**
 * An instance of {@link News} custom class contains information related to a single news.
 */

public class News {

    /** Image of the news */
    private String mImageLink;

    /** Title of the news */
    private String mTitle;

    /** Section of the news */
    private String mSection;

    /** Time & Date of the news */
    private String mDate;

    /**
     * Construct a new {@link News} object
     * @param imageLink is the image link of the news
     * @param title is the title of the news
     * @param section is the section of the news
     * @param date is the time and date of the news
     */
    public News(String imageLink, String title, String section, String date) {
        this.mImageLink = imageLink;
        this.mTitle = title;
        this.mSection = section;
        this.mDate = date;
    }

    /** Return the image of the news  */
    public String getImageLink() {
        return mImageLink;
    }

    /** Return the title of the news */
    public String getTitle() {
        return mTitle;
    }

    /** Return the section of the news */
    public String getSection() {
        return mSection;
    }

    /** Return the time and date of the news */
    public String getDate() {
        return mDate;
    }
}
