package com.p5.penta1.models;

public class Animal {
    private String name;
    private String photo;

    public Animal() {
    }

    public Animal(String name, String photo) {
        this.name = name;
        this.photo = photo;
    }

    public String makeSound() {
        return "Nothing.";
    }

    public String getName() {
        return name;
    }

    public Animal setName(String name) {
        this.name = name;
        return this;
    }

    protected String secondSound(){
        return "Nothing2";
    }

    public String getPhoto() {
        return photo;
    }

    public Animal setPhoto(String photo) {
        this.photo = photo;
        return this;
    }
}
