package com.p5.penta1.api.repository.animal;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String photo;


    public String getName() {
        return name;
    }

    public Animal setName(String name) {
        this.name = name;
        return this;
    }

    public String getPhoto() {
        return photo;
    }

    public Animal setPhoto(String photo) {
        this.photo = photo;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public Animal setId(Integer id) {
        this.id = id;
        return this;
    }
}
