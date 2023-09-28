package fileInput;

public final class ActionInput {
    private String type;
    private String page;
    private String movie;
    private String feature;
    private CredentialsInput credentials;
    private String startsWith;
    private FiltersInput filters;
    private String count;
    private int rate;
    private String subscribedGenre;
    private MovieInput addedMovie;
    private String deletedMovie;

    /* Getters and Setters*/
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getPage() {
        return page;
    }
    public void setPage(String page) {
        this.page = page;
    }
    public String getMovie() {
        return movie;
    }
    public void setMovie(String movie) {
        this.movie = movie;
    }
    public String getFeature() {
        return feature;
    }
    public void setFeature(String feature) {
        this.feature = feature;
    }
    public CredentialsInput getCredentials() {
        return credentials;
    }
    public void setCredentials(CredentialsInput credentials) {
        this.credentials = credentials;
    }
    public String getStartsWith() {
        return startsWith;
    }
    public void setStartsWith(String startsWith) {
        this.startsWith = startsWith;
    }
    public FiltersInput getFilters() {
        return filters;
    }
    public void setFilters(FiltersInput filters) {
        this.filters = filters;
    }
    public String getCount() {
        return count;
    }
    public void setCount(String count) {
        this.count = count;
    }
    public int getRate() {
        return rate;
    }
    public void setRate(int rate) {
        this.rate = rate;
    }
    public String getSubscribedGenre() {
        return subscribedGenre;
    }
    public void setSubscribedGenre(String subscribedGenre) {
        this.subscribedGenre = subscribedGenre;
    }
    public MovieInput getAddedMovie() {
        return addedMovie;
    }
    public void setAddedMovie(MovieInput addedMovie) {
        this.addedMovie = addedMovie;
    }
    public String getDeletedMovie() {
        return deletedMovie;
    }
    public void setDeletedMovie(String deletedMovie) {
        this.deletedMovie = deletedMovie;
    }
}
