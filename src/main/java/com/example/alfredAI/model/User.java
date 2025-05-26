package com.example.alfredAI.model;

import java.util.Objects;


/**
 * A class to store and track user data and login status
 */
public class User {
    public int UserID;
    private String Username;
    private  String Password;
    public int CurrentUser;

    public User(){}

    /**
     *
     * @param UserID the users identification number from the database
     * @param Username the entered username that the user enters
     * @param Password for corresponding username
     */
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

    /**
     * Function that determines if a users information is in the database
     * @param ID this is the users identification number
     * @return successfully login phrase or invalid phrase
     */
    public String Login(int ID){
        if(ID == 0){
            return ("This combination of username and password is invalid, please try again");
        }else{
            LogedinUser(ID);
            return ("You have successfully logged in");
        }
    }

    /**
     * Functions to determine if a user entered data is unique if yes they are able to create an account
     * @param ID user identification number
     * @return success or failure to register user
     */
    public String Register(int ID){
        if(ID == 0){
            LogedinUser(ID);
            return ("You have sucessfully registered an account");
        }else{
            return ("This User already exsits");
        }
    }


    /**
     * Function to register the logged in users identification number for easier access in further functions
     * @param EnteredID user identification number
     */
    public void LogedinUser(int EnteredID){
        this.CurrentUser = EnteredID;
    }

}
