package com.example.cab302week4.model;

public class User {
    public int UserID;
    private String Username;
    private  String Password;
    public int CurrentUser;

    public User(){}

    public User(int UserID, String Username, String Password) {
        this.UserID = UserID;
        this.Username = Username;
        this.Password = Password;
    }

    public int getUserID() {
        return UserID;
    }
    public String getUsername() {
        return Username;
    }
    public String getPassword() {
        return Password;
    }

    public void LogedinUser(int EnteredID){
        this.CurrentUser = EnteredID;
    }

    //Currently no logout function but can use this when added later if desire
    public void LogoutUser(){
        this.UserID = 0;
    }

}
