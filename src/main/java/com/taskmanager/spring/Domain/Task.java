package com.taskmanager.spring.Domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Task
{
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long taskid;
    @NotNull
    private Long userid;
    @NotEmpty
    private String title;
    @Lob
    @NotEmpty
    private String body;
    @NotEmpty
    //@Temporal(TemporalType.TIMESTAMP)
    private String dateCreated;
    @NotEmpty
    //@Temporal(TemporalType.TIMESTAMP)
    private String dateComplete;
    @NotNull
    private Integer completed;

    //Empty constructor for use in forms
    public Task()
    {

    }

    public Task(Long userid, String title, String body, String dateCreated, String dateComplete, Integer completed)
    {
        this.userid = userid;
        this.title = title;
        this.body = body;
        this.dateCreated = dateCreated;
        this.dateComplete = dateComplete;
        this.completed = completed;
    }


    public Long getTaskid() {
        return taskid;
    }

    public void setTaskid(Long taskid) {
        this.taskid = taskid;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

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

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateComplete() {
        return dateComplete;
    }

    public void setDateComplete(String dateComplete) {
        this.dateComplete = dateComplete;
    }

    public Integer getCompleted() {
        return completed;
    }

    public void setCompleted(Integer completed) {
        this.completed = completed;
    }
}
