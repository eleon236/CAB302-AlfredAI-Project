package com.example.cab302week4.model;

import java.util.Objects;

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

    public String Login(int ID){
        if(ID == 0){
            return ("This combination of username and password is invalid, please try again");
        }else{
            LogedinUser(ID);
            return ("You have sucessfully logged in");
        }
    }

    public String Register(int ID){
        if(ID == 0){
            LogedinUser(ID);
            return ("You have sucessfully registered an account");
        }else{
            return ("This User already exsits");
        }
    }


    public void LogedinUser(int EnteredID){
        this.CurrentUser = EnteredID;
    }

    //Currently no logout function but can use this when added later if desire
    public void LogoutUser(){
        this.UserID = 0;
    }

}
