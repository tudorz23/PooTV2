package database;

import database.observer.IObservable;
import database.observer.IObserver;
import fileInput.ActionInput;

import java.util.ArrayList;
import java.util.Iterator;

public final class Database implements IObservable {
    private ArrayList<User> registeredUsers;
    private ArrayList<Movie> availableMovies;

    // For the sake of the Observer design pattern, will store the observers separately,
    // although they are the same with the registered users.
    private ArrayList<IObserver> observers;

    /* Constructor */
    public Database() {
        registeredUsers = new ArrayList<>();
        availableMovies = new ArrayList<>();
        observers = new ArrayList<>();
    }


    @Override
    public void addObserver(IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(IObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(ActionInput news) {
        for (IObserver observer : observers) {
            observer.update(news);
        }
    }

    /**
     * Adds the given movie to the movie list.
     * @param movie movie to add.
     */
    public void addMovie(Movie movie) {
        availableMovies.add(movie);
    }

    /**
     * Removes the given movie from the movie list.
     * @param movie movie to remove.
     */
    public void removeMovie(String movieName) {
        availableMovies.removeIf(movie -> movie.getName().equals(movieName));
    }

    /* Getters and Setters */
    public ArrayList<User> getRegisteredUsers() {
        return registeredUsers;
    }
    public ArrayList<Movie> getAvailableMovies() {
        return availableMovies;
    }
}
