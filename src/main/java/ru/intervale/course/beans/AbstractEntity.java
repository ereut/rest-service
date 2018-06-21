package ru.intervale.course.beans;

import ru.intervale.course.Constants;

import java.text.ParseException;

public abstract class AbstractEntity {

    private Integer id;

    public AbstractEntity(Integer id) {
        this.id = id;
    }

    public AbstractEntity() {
        this(null);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
