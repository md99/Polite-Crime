package polite.crime.model;

import java.util.List;

/**
 * Created by Hulk on 11/15/16.
 */

public class BasicResponse<T> {
    private List<T> items;

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
