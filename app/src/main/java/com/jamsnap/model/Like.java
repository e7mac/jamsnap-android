package com.jamsnap.model;

import java.util.ArrayList;

public class Like {
    public int id;
    public String pixaura;
    public User user;

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
        Like other = (Like) obj;
        return id == other.id;
    }

    @SuppressWarnings("serial")
    public static class List extends ArrayList<Like> {
    }
}
