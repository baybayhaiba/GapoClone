package com.example.gapoclone.Model;

public class Person {
    private String personId;
    private String name;
    private String email;
    private String personImg;


    public Person() {
    }

    public Person(String personId, String name, String email, String personImg) {
        this.personId = personId;
        this.name = name;
        this.email = email;
        this.personImg = personImg;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPersonImg() {
        return personImg;
    }

    public void setPersonImg(String personImg) {
        this.personImg = personImg;
    }
}
