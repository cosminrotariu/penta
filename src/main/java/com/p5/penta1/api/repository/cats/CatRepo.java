package com.p5.penta1.api.repository.cats;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatRepo extends JpaRepository<Cat, Integer> {
}
