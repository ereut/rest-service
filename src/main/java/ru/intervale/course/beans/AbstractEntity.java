package ru.intervale.course.beans;

import ru.intervale.course.Constants;

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

    protected static String getFieldForPrint(String field) {
        return field == null || "".equals(field) ? Constants.DASH : field;
    }

}
