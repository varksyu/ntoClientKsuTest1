package ru.myitschool.work.domain;

import androidx.annotation.NonNull;

public class User {
    //public Integer id;
    @NonNull
    public String login;
    @NonNull
    public String name;
    @NonNull
    public String photo;
    @NonNull
    public String position;

    @NonNull
    public String getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(@NonNull String lastVisit) {
        this.lastVisit = lastVisit;
    }

    @NonNull
    public String getLogin() {
        return login;
    }

    public void setLogin(@NonNull String login) {
        this.login = login;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(@NonNull String photo) {
        this.photo = photo;
    }

    @NonNull
    public String getPosition() {
        return position;
    }

    public void setPosition(@NonNull String position) {
        this.position = position;
    }

    @NonNull
    public String lastVisit;


}
