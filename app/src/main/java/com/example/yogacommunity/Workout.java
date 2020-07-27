package com.example.yogacommunity;

public class Workout {

    private String title, titleExpress;
    private String description,descriptionExpress;
    //***************************
    private String link, linkExpress;
    //***************************
    public Workout(String title, String description, String link, String titleExpress, String descriptionExpress, String linkExpress) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.titleExpress = titleExpress;
        this.descriptionExpress = descriptionExpress;
        this.linkExpress = linkExpress;
    }

    public Workout() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    //****************************
    public String getLink() {return link;}

    public void setLink(String link){ this.link = link;}

    public String getTitleExpress() {return titleExpress;}
    public void setTitleExpress(String titleExpress) {this.titleExpress = titleExpress;}

    public String getDescriptionExpress() {
        return descriptionExpress;
    }

    public void setDescriptionExpress(String descriptionExpress) {
        this.descriptionExpress = descriptionExpress;
    }

    public String getLinkExpress() {
        return linkExpress;
    }

    public void setLinkExpress(String linkExpress) {
        this.linkExpress = linkExpress;
    }
}

