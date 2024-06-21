package com.example.edupay;

public class TransectionReadWrite {

    private String rollid,department,session,transectionId,mobile,semester;

    public TransectionReadWrite(String rollid, String department, String session, String transectionId, String mobile,String semester) {
        this.rollid = rollid;
        this.department = department;
        this.session = session;
        this.transectionId = transectionId;
        this.mobile = mobile;
        this.semester=semester;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public TransectionReadWrite() {
    }

    public String getRollid() {
        return rollid;
    }

    public void setRollid(String rollid) {
        this.rollid = rollid;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getTransectionId() {
        return transectionId;
    }

    public void setTransectionId(String transectionId) {
        this.transectionId = transectionId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
