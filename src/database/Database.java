package database;

import java.util.ArrayList;

public final class Database {
    private ArrayList<User> registeredUsers;
    private ArrayList<Movie> availableMovies;

    /* Constructor */
    public Database() {
        registeredUsers = new ArrayList<>();
        availableMovies = new ArrayList<>();
    }

    /* Getters and Setters */
    public ArrayList<User> getRegisteredUsers() {
        return registeredUsers;
    }
    public void setRegisteredUsers(ArrayList<User> registeredUsers) {
        this.registeredUsers = registeredUsers;
    }
    public ArrayList<Movie> getAvailableMovies() {
        return availableMovies;
    }
    public void setAvailableMovies(ArrayList<Movie> availableMovies) {
        this.availableMovies = availableMovies;
    }
}
