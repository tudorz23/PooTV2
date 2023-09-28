package commands.databaseStrategy;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Movie;
import fileInput.ActionInput;
import fileOutput.PrinterJson;

public class DatabaseDeleteStrategy implements IDatabaseStrategy {
    private Session session;
    private ActionInput actionInput;
    private ArrayNode output;

    /* Constructor */
    public DatabaseDeleteStrategy(Session session, ActionInput actionInput, ArrayNode output) {
        this.session = session;
        this.actionInput = actionInput;
        this.output = output;
    }

    @Override
    public void modifyDatabase() {
        String deletedMovie = actionInput.getDeletedMovie();

        if (!checkMovieExistenceInDatabase(deletedMovie)) {
            PrinterJson printerJson = new PrinterJson();
            printerJson.printError(output);
            return;
        }

        session.getDatabase().removeMovie(deletedMovie);

        session.getDatabase().notifyObservers(actionInput);
    }

    /**
     * Checks if the movie is registered in the database.
     * @return true if the movie is in the database, false otherwise.
     */
    private boolean checkMovieExistenceInDatabase(String movieName) {
        for (Movie movie : session.getDatabase().getAvailableMovies()) {
            if (movie.getName().equals(movieName))
                return true;
        }
        return false;
    }
}
