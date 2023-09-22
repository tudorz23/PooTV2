package fileInput;

public final class FiltersInput {
    private SortInput sort;
    private ContainsInput contains;

    /* Getters and Setters*/
    public SortInput getSort() {
        return sort;
    }
    public void setSort(SortInput sort) {
        this.sort = sort;
    }
    public ContainsInput getContains() {
        return contains;
    }
    public void setContains(ContainsInput contains) {
        this.contains = contains;
    }
}
