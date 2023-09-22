package database;

import fileInput.MovieInput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class Movie {
    private String name;
    private int year;
    private int duration;
    private ArrayList<String> genres;
    private ArrayList<String> actors;
    private ArrayList<String> countriesBanned;
    private int numLikes;
    private double rating;
    private int numRatings;
    private Map<User, Integer> ratingsMap;

    /* Constructor */
    public Movie(MovieInput movieInput) {
        this.name = movieInput.getName();
        this.year = movieInput.getYear();
        this.duration = movieInput.getDuration();
        this.genres = movieInput.getGenres();
        this.actors = movieInput.getActors();
        this.countriesBanned = movieInput.getCountriesBanned();
        numLikes = 0;
        rating = 0;
        numRatings = 0;
        ratingsMap = new HashMap<>();
    }

    /**
     * Computes the rating for the movie, by averaging the values from ratingsMap.
     */
    public void computeRating() {
        if (ratingsMap.isEmpty()) {
            rating = 0;
            return;
        }

        rating = ratingsMap.values().stream()
                .mapToDouble(Integer::doubleValue)
                .average()
                .orElse(0);
    }

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
    public int getNumLikes() {
        return numLikes;
    }
    public void setNumLikes(int numLikes) {
        this.numLikes = numLikes;
    }
    public double getRating() {
        return rating;
    }
    public void setRating(double rating) {
        this.rating = rating;
    }
    public int getNumRatings() {
        return numRatings;
    }
    public void setNumRatings(int numRatings) {
        this.numRatings = numRatings;
    }
    public Map<User, Integer> getRatingsMap() {
        return ratingsMap;
    }
}
