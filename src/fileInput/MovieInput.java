package fileInput;

import java.util.ArrayList;

public final class MovieInput {
    private String name;
    private int year;
    private int duration;
    private ArrayList<String> genres;
    private ArrayList<String> actors;
    private ArrayList<String> countriesBanned;

    /* Getters and Setters */
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public ArrayList<String> getGenres() {
        return genres;
    }
    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }
    public ArrayList<String> getActors() {
        return actors;
    }
    public void setActors(ArrayList<String> actors) {
        this.actors = actors;
    }
    public ArrayList<String> getCountriesBanned() {
        return countriesBanned;
    }
    public void setCountriesBanned(ArrayList<String> countriesBanned) {
        this.countriesBanned = countriesBanned;
    }
}
