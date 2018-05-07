package ru.intervale.course.beans;

public abstract class AbstractEntity {

    private final int id;

    public AbstractEntity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}
