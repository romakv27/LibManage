package ru.study.library.model;

import ru.study.library.enums.Status;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

public class HistoryContent {
    private UUID id;
    private String className;
    private String createData;
    private String actor;
    private String methodName;
    private Object object;
    private Status status;

    public HistoryContent() {
    }

    public HistoryContent(UUID id, String className, long timeMilliseconds, String actor, String methodName, Object object, Status status) {
        this.id = id;
        this.className = className;
        this.createData = formatTime(timeMilliseconds);
        this.actor = actor;
        this.methodName = methodName;
        this.object = object;
        this.status = status;
    }

    private String formatTime(long time) {
        Date date = new Date(time);
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(date);
    }

    public UUID getId() {return id;}
    public void setId(UUID id) {this.id = id;}

    public String getClassName() {return className;}
    public void setClassName(String className) {this.className = className;}

    public String getCreateData() {return createData;}
    public void setCreateData(String createData) {this.createData = createData;}

    public String getActor() {return actor;}
    public void setActor(String actor) {this.actor = actor;}

    public String getMethodName() {return methodName;}
    public void setMethodName(String methodName) {this.methodName = methodName;}

    public Object getObject() {return object;}
    public void setObject(Object object) {this.object = object;}

    public Status getStatus() {return status;}
    public void setStatus(Status status) {this.status = status;}
}
