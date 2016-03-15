package model;

/**
 * Created by Leon on 3/7/2016.
 */
public class Quote {
    private long id;
    private String content;

    public Quote() {

    }

    public Quote(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
