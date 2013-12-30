package com.pixaura.model;

import java.util.ArrayList;

public class User {

    public int id;
    public String username;
    public String profilePicture;

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
        User other = (User) obj;
        return id == other.id;
    }

    @SuppressWarnings("serial")
    public static class List extends ArrayList<User> {
    }
}
