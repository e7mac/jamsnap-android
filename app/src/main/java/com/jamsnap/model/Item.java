package com.jamsnap.model;

import java.util.ArrayList;

public class Item {

    public int id;
    public String pictureUrl;
    public String caption;
    public String dateCreated; // TODO: As DateTime
    public String dateUploaded; // TODO: As DateTime
    public String dateModified; // TODO: As DateTime
    public Boolean isPublic;
    public String stringPrefix;
    public Boolean uploadedFiles;
    public String key;
    public Boolean featured;
    public int commentsCount;
    public Location location;
    public User user;
    public ArrayList<Tag> tags;
    public ArrayList<Like> likes;

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Item other = (Item) obj;
        return id == other.id;
    }

    @SuppressWarnings("serial")
    public static class List extends ArrayList<Item> {
    }
}
