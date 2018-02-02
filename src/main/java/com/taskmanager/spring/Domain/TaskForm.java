package com.taskmanager.spring.Domain;

import org.hibernate.validator.constraints.NotEmpty;

public class TaskForm
{
    @NotEmpty
    private String title;
    @NotEmpty
    private  String body;
    @NotEmpty
    private  String dateComplete;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDateComplete() {
        return dateComplete;
    }

    public void setDateComplete(String dateComplete) {
        this.dateComplete = dateComplete;
    }

}
