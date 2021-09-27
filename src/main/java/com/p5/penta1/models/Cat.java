package com.p5.penta1.models;

public class Cat extends Animal {
    public Cat(String name, String photo) {
        super(name, photo);
    }

    @Override
    public String makeSound() {
        return "Miau." + secondSound();
    }
}
