package com.raccoon.city.survivors.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.raccoon.city.survivors.dto.LocationDTO;
import com.raccoon.city.survivors.dto.SurvivorDTO;
import com.raccoon.city.survivors.dto.SurvivorListItemDTO;
import com.raccoon.city.survivors.exception.NotFoundException;
import com.raccoon.city.survivors.model.Survivor;
import com.raccoon.city.survivors.repository.SurvivorRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SurvivorService {

  private final SurvivorRepository survivorRepository;

  public List<SurvivorListItemDTO> findAll() {
    return survivorRepository.findAll().stream().map(SurvivorListItemDTO::from).collect(Collectors.toList());
  }

  public Optional<SurvivorDTO> findById(Integer id) {
    return survivorRepository.findByIdWithSupplies(id).map(SurvivorDTO::from);
  }

  public SurvivorDTO save(SurvivorDTO survivorDTO) {
    Survivor survivor = survivorRepository.save(survivorDTO.toEntity());

    return SurvivorDTO.from(survivor);
  }

  public void updateLocation(Integer id, LocationDTO locationDTO) {
    Survivor survivor = survivorRepository.findById(id).orElseThrow(NotFoundException::new);

    if (survivor == null) {
      throw new NotFoundException();
    }

    survivor.setLastLocation(locationDTO.toEntity());

    survivorRepository.save(survivor);
  }

}