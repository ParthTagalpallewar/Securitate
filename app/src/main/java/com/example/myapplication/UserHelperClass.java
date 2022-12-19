package com.example.myapplication;

public class UserHelperClass {
    String name1,usermail,username,userpass,usergender,userbloodgroup,userprofession,userdob,userphoneno;

    public UserHelperClass() {

    }

    public UserHelperClass(String name1, String usermail, String username, String userpass, String usergender, String userbloodgroup, String userprofession, String userdob, String userphoneno) {
        this.name1 = name1;
        this.usermail = usermail;
        this.username = username;
        this.userpass = userpass;
        this.usergender = usergender;
        this.userbloodgroup = userbloodgroup;
        this.userprofession = userprofession;
        this.userdob = userdob;
        this.userphoneno = userphoneno;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getUsermail() {
        return usermail;
    }

    public void setUsermail(String usermail) {
        this.usermail = usermail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getUserpass() {
        return userpass;
    }

    public void setUserpass(String userpass) {
        this.userpass = userpass;
    }

    public String getUsergender() {
        return usergender;
    }

    public void setUsergender(String usergender) {
        this.usergender = usergender;
    }

    public String getUserbloodgroup() {
        return userbloodgroup;
    }

    public void setUserbloodgroup(String userbloodgroup) {
        this.userbloodgroup = userbloodgroup;
    }

    public String getUserprofession() {
        return userprofession;
    }

    public void setUserprofession(String userprofession) {
        this.userprofession = userprofession;
    }

    public String getUserdob() {
        return userdob;
    }

    public void setUserdob(String userdob) {
        this.userdob = userdob;
    }

    public String getUserphoneno() {
        return userphoneno;
    }

    public void setUserphoneno(String userphoneno) {
        this.userphoneno = userphoneno;
    }
}
