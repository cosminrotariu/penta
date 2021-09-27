package com.p5.penta1.api.service;

import com.p5.penta1.api.repository.cats.Cat;
import com.p5.penta1.api.repository.dogs.Dog;
import com.p5.penta1.api.repository.shelters.AnimalShelter;
import com.p5.penta1.api.repository.shelters.AnimalShelterRepo;
import com.p5.penta1.api.service.DTO.CatDTO;
import com.p5.penta1.api.service.DTO.ListDTO;
import com.p5.penta1.api.service.DTO.ShelterDTO;
import com.p5.penta1.api.service.adapters.CatAdapter;
import com.p5.penta1.api.service.adapters.ShelterAdapter;
import com.p5.penta1.api.service.exceptions.ApiError;
import com.p5.penta1.api.service.exceptions.ShelterLocationException;
import com.p5.penta1.api.service.exceptions.Violation;
import com.p5.penta1.api.service.validations.OnCreate;
import com.p5.penta1.api.service.validations.OnUpdate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Validated
public class AnimalShelterService {
    private final AnimalShelterRepo animalShelterRepo;

    public AnimalShelterService(AnimalShelterRepo animalShelterRepo) {
        this.animalShelterRepo = animalShelterRepo;
    }

    public ListDTO<ShelterDTO> findAll() {
        ListDTO<ShelterDTO> listDTO = new ListDTO<>();

        List<AnimalShelter> allShelters = animalShelterRepo.findAll();

        listDTO.setData(ShelterAdapter.toDTOList(allShelters));
        listDTO.setTotalCount(animalShelterRepo.count());

        return listDTO;
    }

    @Validated(OnCreate.class)
    public ShelterDTO createShelter(@Valid ShelterDTO animalShelter) {
        validateShelterLocation(animalShelter);

        AnimalShelter shelter = ShelterAdapter.fromDTO(animalShelter);
        return ShelterAdapter.toDTO(animalShelterRepo.save(shelter));
    }

    // public for Test visibility
    public void validateShelterLocation(ShelterDTO animalShelter) {
        String location = animalShelter.getLocation().toLowerCase(Locale.ROOT);
        if (!location.contains("brasov") && !location.contains("iasi")) {
            throw new ShelterLocationException("Brasov or Iasi is required");
        }
    }

    private void validateShelter(ShelterDTO shelterDTO) {
        ApiError error = new ApiError(HttpStatus.CONFLICT, "Shelter validation failed");

        if (shelterDTO.getDogs().isEmpty()) {
            error.getViolations().add(new Violation("dogs", "Minimum 1 dog pls"));
        }
        if (shelterDTO.getName().contains("_")) {
            error.getViolations().add(new Violation("name", "No underscore('_') in name "));
        }

        if (!error.getViolations().isEmpty()) {
            throw new ValidationException(String.valueOf(error));
        }
    }

    @Validated(OnUpdate.class)
    public ShelterDTO updateShelter(Integer id, @Valid ShelterDTO animalShelter) {
        validateShelterLocation(animalShelter);
        validateShelter(animalShelter);


        AnimalShelter shelter = getShelterById(id);
        if (!shelter.getId().equals(animalShelter.getId())) {
            throw new RuntimeException("Id of entity not the same with path id");
        }

        return ShelterAdapter.toDTO(animalShelterRepo.save(ShelterAdapter.fromDTO(animalShelter)));
    }

    public ShelterDTO findById(Integer id) {
        return ShelterAdapter.toDTO(getShelterById(id));
    }

    public void deleteShelter(Integer id) {
        animalShelterRepo.deleteById(id);
    }

    public List<CatDTO> findAllCatsByShelter(Integer shelterId) {
        AnimalShelter shelter = getShelterById(shelterId);

        return CatAdapter.toDTOList(shelter.getCats());
    }

    public List<CatDTO> addNewCatToShelter(Integer shelterId, CatDTO cat) {
        AnimalShelter shelter = getShelterById(shelterId);

        shelter.getCats().add(CatAdapter.fromDTO(cat));

        animalShelterRepo.save(shelter);

        return CatAdapter.toDTOList(shelter.getCats());
    }

    public Cat updateCatInShelter(Integer shelterId, Integer catId, Cat cat) {
        AnimalShelter shelter = getShelterById(shelterId);

        List<Cat> newCats = shelter.getCats().stream().map(c -> {
            if (c.getId().equals(catId)) {
                cat.setId(catId);
                return cat;
            }
            return c;
        }).collect(Collectors.toList());

        shelter.setCats(newCats);

        animalShelterRepo.save(shelter);

        return cat;
    }

    public void deleteCatInShelter(Integer shelterId, Integer catId) {
        AnimalShelter shelter = getShelterById(shelterId);
        List<Cat> newCats = shelter.getCats()
                .stream()
                .filter(c -> !c.getId().equals(catId))
                .collect(Collectors.toList());
        shelter.setCats(newCats);
        animalShelterRepo.save(shelter);
    }

    public List<Dog> findAllDogsByShelter(Integer shelterId) {
        AnimalShelter shelter = getShelterById(shelterId);
        return shelter.getDogs();
    }

    private AnimalShelter getShelterById(Integer id) {
        Optional<AnimalShelter> optional = animalShelterRepo.findById(id);
        return optional.orElseThrow(() -> new EntityNotFoundException("Shelter with id " + id + " not found"));
    }

    public List<Dog> addNewDogToShelter(Integer shelterId, Dog dog) {
        AnimalShelter shelter = getShelterById(shelterId);

        shelter.getDogs().add(dog);

        animalShelterRepo.save(shelter);

        return shelter.getDogs();
    }

    public Dog updateDogInShelter(Integer shelterId, Integer dogId, Dog dog) {
        AnimalShelter shelter = getShelterById(shelterId);

        List<Dog> newDogs = shelter.getDogs().stream().map(d -> {
            if (d.getId().equals(dogId)) {
                dog.setId(dogId);
                return dog;
            }
            return d;
        }).collect(Collectors.toList());

        shelter.setDogs(newDogs);

        animalShelterRepo.save(shelter);

        return dog;
    }

    public void deleteDogInShelter(Integer shelterId, Integer dogId) {
        AnimalShelter shelter = getShelterById(shelterId);

        boolean removed = shelter.getDogs().removeIf(d -> d.getId().equals(dogId));

        animalShelterRepo.save(shelter);

        if (!removed) {
            throw new RuntimeException("Already deleted or entity missing");
        }
    }
}