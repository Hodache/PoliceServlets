package dataclasses;

public class Fine {
    private String id;
    private String date;
    private String description;
    private int size;

    public Fine(String id, String date, String description, int size){
        this.id = id;
        this.date = date;
        this.description = description;
        this.size = size;
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public int getSize() {
        return size;
    }
}
