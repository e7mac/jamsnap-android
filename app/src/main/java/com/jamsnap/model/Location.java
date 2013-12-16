package com.jamsnap.model;


import java.util.ArrayList;

public class Location {
    public int id;
    public float latitude;
    public float longitude;
    public String locality;
    public String city;

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
        Location other = (Location) obj;
        return id == other.id;
    }

    @SuppressWarnings("serial")
    public static class List extends ArrayList<Location> {
    }
}
