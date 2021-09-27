package com.p5.penta1.api.repository.shelters;

import com.p5.penta1.api.repository.cats.Cat;
import com.p5.penta1.api.repository.dogs.Dog;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class AnimalShelter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(unique = true)
    private String name;

    private String location;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "shelter_id")
    private List<Cat> cats = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "shelter_id")
    private List<Dog> dogs = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public AnimalShelter setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public AnimalShelter setName(String name) {
        this.name = name;
        return this;
    }

    public List<Cat> getCats() {
        return cats;
    }

    public AnimalShelter setCats(List<Cat> cats) {
        this.cats = cats;
        return this;
    }

    public List<Dog> getDogs() {
        return dogs;
    }

    public AnimalShelter setDogs(List<Dog> dogs) {
        this.dogs = dogs;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public AnimalShelter setLocation(String location) {
        this.location = location;
        return this;
    }
}
