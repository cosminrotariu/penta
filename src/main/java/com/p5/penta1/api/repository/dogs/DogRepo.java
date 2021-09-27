package com.p5.penta1.api.repository.dogs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DogRepo extends JpaRepository<Dog, Integer> {
}
