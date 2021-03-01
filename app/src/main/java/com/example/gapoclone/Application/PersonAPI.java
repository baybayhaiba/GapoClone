package com.example.gapoclone.Application;

import android.app.Application;

import com.example.gapoclone.Model.Person;

public class PersonAPI extends Application {
    private static Person instance;


    public static Person getInstance() {
        if (instance == null)
            instance = new Person();
        return instance;
    }

}
