package com.stefane.tasklist.model;

import java.io.Serializable;

public class Task implements Serializable {
    private Long id;
    private String taskTitle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }
}
