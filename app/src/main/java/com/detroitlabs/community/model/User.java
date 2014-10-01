package com.detroitlabs.community.model;

public class User{

    private int id;
    private String username;
    private String name;
    private String password;

    private User(Builder builder) {
        id = builder.id;
        username = builder.username;
        name = builder.name;
        password = builder.password;
    }

    public int getId(){
        return id;
    }

    public String getUsername(){
        return username;
    }

    public String getName(){
        return name;
    }

    public String getPassword(){
        return password;
    }


    public static final class Builder {
        private int id;
        private String username;
        private String name;
        private String password;

        public Builder() {
        }

        public Builder(User copy) {
            id = copy.id;
            username = copy.username;
            name = copy.name;
            password = copy.password;
        }

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
