package com.raccoon.city.survivors.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import com.raccoon.city.survivors.dto.LocationDTO;
import com.raccoon.city.survivors.dto.SurvivorDTO;
import com.raccoon.city.survivors.dto.SurvivorListItemDTO;
import com.raccoon.city.survivors.exception.NotFoundException;
import com.raccoon.city.survivors.service.InfectionReportService;
import com.raccoon.city.survivors.service.SurvivorService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/umbrella/survivors")
@Api(value = "Survivors", description = "Survivors API", tags = {"Survivors"})
public class SurvivorController {

	private final SurvivorService survivorService;
	private final InfectionReportService infectionReportService;

	@GetMapping
	@ApiOperation(value = "Retrieves survivors list", tags = {"Survivors"})
	public List<SurvivorListItemDTO> list() {
		log.info("Retrieving survivor list");

		return survivorService.findAll();
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Return survivor with given id", tags = {"Survivors"})
	public SurvivorDTO findById(
		@ApiParam(value = "Survivor ID", required = true, example = "123") @PathVariable("id") Integer id
	) {
		log.info("Searching survivor with id #{}", id);

		return survivorService.findById(id).orElseThrow(NotFoundException::new);
	}

	@PostMapping
	@ApiOperation(value = "Add a new survivor to the database", tags = {"Survivors"})
	public ResponseEntity<Void> create(@RequestBody SurvivorDTO survivorDTO) {
		log.info("Persisting survivor with: {}", survivorDTO.toString());

		SurvivorDTO survivor = survivorService.save(survivorDTO);

		return ResponseEntity.created(URI.create("/api/umbrella/survivors/" + survivor.getId())).build();
	}

	@PutMapping("/{id}/location")
	@ApiOperation(value = "Update the last known location of the survivor with the given ID", tags = {"Survivors"})
	public ResponseEntity<Void> create(
		@ApiParam(value = "Survivor ID", required = true, example = "123") @PathVariable("id") Integer id,
		@RequestBody LocationDTO locationDTO
	) {

		log.info("Updating survivor #{} location to: {}", id, locationDTO.toString());

		survivorService.updateLocation(id, locationDTO);

		return ResponseEntity.noContent().build();
	}

	@PostMapping("/{id}/infected")
	@ApiOperation(value = "Flag the survivor with given ID as infected", tags = {"Survivors"})
	public ResponseEntity<Void> reportInfection(
		@ApiParam(value = "Target Survivor ID", required = true, example = "1") @PathVariable("id") Integer targetId,
		@ApiParam(value = "Current Session User ID", required = true, example = "123") @RequestHeader(value = "User-Id", required = false) Integer reporterId
	) {
			if (reporterId == null) {
				throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
			}

			infectionReportService.reportInfection(targetId, reporterId);

			return ResponseEntity.noContent().build();
	}
}