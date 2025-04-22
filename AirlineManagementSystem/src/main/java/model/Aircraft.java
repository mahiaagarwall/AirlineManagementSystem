package model;

public class Aircraft {
    private int id;
    private String model;
    private int capacity;

    // Constructor, Getters & Setters
    public Aircraft(int id, String model, int capacity) {
        this.id = id;
        this.model = model;
        this.capacity = capacity;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public int getCapacity() {
        return capacity;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}

