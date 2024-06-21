package com.example.edupay;

public class ReadwriteuserDetails {
    private String Email,fullname,department,hallname,fathernaem,mothername,session,registrationNo,religion,nationality,mobileNo,dob,gender,address;

    public ReadwriteuserDetails(String email, String fullname, String department, String hallname, String fathernaem, String mothername, String session, String registrationNo, String religion, String nationality, String mobileNo, String dob, String gender,String address) {
        Email = email;
        this.fullname = fullname;
        this.department = department;
        this.hallname = hallname;
        this.fathernaem = fathernaem;
        this.mothername = mothername;
        this.session = session;
        this.registrationNo = registrationNo;
        this.religion = religion;
        this.nationality = nationality;
        this.mobileNo = mobileNo;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
    }

    public ReadwriteuserDetails() {

    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getHallname() {
        return hallname;
    }

    public void setHallname(String hallname) {
        this.hallname = hallname;
    }

    public String getFathernaem() {
        return fathernaem;
    }

    public void setFathernaem(String fathernaem) {
        this.fathernaem = fathernaem;
    }

    public String getMothername() {
        return mothername;
    }

    public void setMothername(String mothername) {
        this.mothername = mothername;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
