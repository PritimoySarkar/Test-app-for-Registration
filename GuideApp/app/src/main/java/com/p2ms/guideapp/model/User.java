package com.p2ms.guideapp.model;

public class User {
    private String userId, userName, userEmail, userContact, userPass, userDp;

    public User(String userId, String userName, String userEmail, String userContact, String userPass) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userContact = userContact;
        this.userPass = userPass;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserContact() {
        return userContact;
    }

    public String getUserPass() {
        return userPass;
    }

    public String getUserDp() {
        return userDp;
    }
}
