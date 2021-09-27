package com.p5.penta1.api.repository.shelters;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalShelterRepo extends JpaRepository<AnimalShelter, Integer> {
}
