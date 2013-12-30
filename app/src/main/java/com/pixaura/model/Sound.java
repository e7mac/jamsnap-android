package com.pixaura.model;

import java.util.ArrayList;

public class Sound {

    public int id;
    public String url;
    public String aac;
    public String ogg;
    public String file;
    public String pixaura;
    public Float posX;
    public Float posY;
    public String dateCreated; // TODO: As DateTime

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
        Sound other = (Sound) obj;
        return id == other.id;
    }

    @SuppressWarnings("serial")
    public static class List extends ArrayList<Sound> {
    }
}
