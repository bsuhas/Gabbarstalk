package com.gabbarstalk.models;

/**
 * Created by SUHAS on 13/03/2017.
 */

public class SurnameCountModel {
    private String surname;
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return "SurnameCountModel{" +
                "surname='" + surname + '\'' +
                ", count=" + count +
                '}';
    }
}
