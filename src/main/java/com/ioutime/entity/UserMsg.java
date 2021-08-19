package com.ioutime.entity;

/**
 * @author ioutime
 * @version 1.0
 * @date 2021/8/17 11:00
 */

public class UserMsg {

    private Integer id;
    private Integer uid;



    private String notes;
    private String msg;


    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
