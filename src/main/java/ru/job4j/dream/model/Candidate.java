package ru.job4j.dream.model;

import java.util.Objects;

public class Candidate {
    private int id;
    private String name;
    private int cityID;
    private int photoID;

    public Candidate(int id, String name, int cityID) {
        this(id, name, cityID, 0);
    }

    public Candidate(int id, String name, int cityID, int photoID) {
        this.id = id;
        this.name = name;
        this.cityID = cityID;
        this.photoID = photoID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhotoID() {
        return photoID;
    }

    public void setPhotoID(int photoID) {
        this.photoID = photoID;
    }

    public void setCityID(int cityID) {
        this.cityID = cityID;
    }

    public int getCityID() {
        return cityID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Candidate candidate = (Candidate) o;
        return id == candidate.id &&
                Objects.equals(name, candidate.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}