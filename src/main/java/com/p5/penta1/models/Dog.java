package com.p5.penta1.models;

public class Dog extends Animal {
    @Override
    public String makeSound() {
        return "Ham." + secondSound();
    }

    public Dog(String name, String photo) {
        super(name, photo);
    }
}
