package com.example.edupay.model;

public class AuthReadWrite {
    private String department,RegistrationID,Reg_Mobile,registerUid;

    public AuthReadWrite() {
    }


    public AuthReadWrite(String department, String RegistrationID, String reg_Mobile, String registerUid) {
        this.department = department;
        this.RegistrationID = RegistrationID;
        this.Reg_Mobile = reg_Mobile;
        this.registerUid = registerUid;
    }

    public String getRegisterUid() {
        return registerUid;
    }

    public void setRegisterUid(String registerUid) {
        this.registerUid = registerUid;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getRegistrationID() {
        return RegistrationID;
    }

    public void setRegistrationID(String RegistrationID) {
        this.RegistrationID = RegistrationID;
    }

    public String getReg_Mobile() {
        return Reg_Mobile;
    }

    public void setReg_Mobile(String Reg_Mobile) {
        this.Reg_Mobile = Reg_Mobile;
    }
}
