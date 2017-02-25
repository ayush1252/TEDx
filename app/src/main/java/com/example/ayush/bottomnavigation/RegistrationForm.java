package com.example.ayush.bottomnavigation;

import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ayush on 17/2/17.
 */

public  class RegistrationForm {

    String firstname;

    public RegistrationForm() {

    }

    String secondname;
    String email;
    String phone;
    Integer gender;
    String placeofwork;
    String dob;
    String adress;
    String Locality;
    String city;
    String country;
    String Gender;
    String know;
    String attend;
    String ideas;
    String id;

    public String getId()
    {
        return id;
    }

    public void setId(String id){ this.id=id;}


    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSecondname() {
        return secondname;
    }

    public void setSecondname(String secondname) {
        this.secondname = secondname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPlaceofwork() {
        return placeofwork;
    }

    public void setPlaceofwork(String placeofwork) {
        this.placeofwork = placeofwork;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getLocality() {
        return Locality;
    }

    public void setLocality(String locality) {
        Locality = locality;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getKnow() {
        return know;
    }

    public void setKnow(String know) {
        this.know = know;
    }

    public String getAttend() {
        return attend;
    }

    public void setAttend(String attend) {
        this.attend = attend;
    }

    public String getIdeas() {
        return ideas;
    }

    public void setIdeas(String ideas) {
        this.ideas = ideas;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setgender(Integer gender) {
        this.gender = gender;
    }

    public  Integer getgender(){return gender;}

    public  JSONObject toJSON()
    {


        JSONObject jsonObject=new JSONObject();
        try {
           // jsonObject.put("user_token",getId());
            jsonObject.put("username",getEmail());
            jsonObject.put("fname",getFirstname());
            jsonObject.put("lname",getSecondname());
            jsonObject.put("contact",getPhone());
            jsonObject.put("college",getPlaceofwork());
            jsonObject.put("dob",getDob());
            jsonObject.put("address",getAdress());
            jsonObject.put("locality",getLocality());
            jsonObject.put("city",getCity());
            jsonObject.put("gender",getgender());
            jsonObject.put("how",getKnow());
            jsonObject.put("why",getAttend());
            jsonObject.put("ideas",getIdeas());
            //jsonObject.put("user_detail",user_detail);
            return jsonObject;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


}
