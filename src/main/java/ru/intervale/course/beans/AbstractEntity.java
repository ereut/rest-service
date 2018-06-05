package ru.intervale.course.beans;

public abstract class AbstractEntity {

    private int id;

    public AbstractEntity(int id) {
        this.id = id;
    }
    public AbstractEntity() {
        this(0);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
