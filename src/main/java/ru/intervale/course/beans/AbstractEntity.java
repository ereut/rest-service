package ru.intervale.course.beans;

import ru.intervale.course.Constants;

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

    protected static String getFieldForPrint(String field) {
        return field == null || "".equals(field) ? Constants.DASH : field;
    }

}
