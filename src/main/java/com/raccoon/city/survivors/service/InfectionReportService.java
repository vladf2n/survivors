package com.raccoon.city.survivors.service;

import com.raccoon.city.survivors.config.Constants;
import com.raccoon.city.survivors.exception.InfectionReportException;
import com.raccoon.city.survivors.exception.NotFoundException;
import com.raccoon.city.survivors.model.InfectionReport;
import com.raccoon.city.survivors.model.Survivor;
import com.raccoon.city.survivors.repository.SurvivorRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InfectionReportService {

    private final SurvivorRepository survivorRepository;

    public void reportInfection(Integer targetId, Integer reporterId) {
        Survivor reporterSurvivor = new Survivor().withId(reporterId);
        Survivor targetSurvivor = loadSurvivor(targetId);

        validateTargetSurvivor(targetSurvivor);

        if (targetSurvivor.wasAlreadyReportedBy(reporterSurvivor)) {
            throw new InfectionReportException(Constants.FLAG_MORE_THAN_ONCE_ERROR);
        }

        targetSurvivor.getInfectionReports().add(
            InfectionReport.builder()
                .target(targetSurvivor)
                .reporter(reporterSurvivor)
                .build()
        );

        if (targetSurvivor.timesReported(Constants.INFECTION_CONFIRMATION_NUMBER)) {
            targetSurvivor.setInfected(true);
        }

        survivorRepository.save(targetSurvivor);
    }

    private void validateTargetSurvivor(Survivor targetSurvivor) {

        if (targetSurvivor == null) {
            throw new NotFoundException();
        }

        if (targetSurvivor.isInfected()) {
            throw new InfectionReportException(Constants.SURVIVOR_ALREADY_INFECTED_ERROR);
        }
    }

    public Survivor loadSurvivor(int survivorId) {
        return survivorRepository.findById(survivorId)
            .orElseThrow(NotFoundException::new);
    }

    public Survivor findById(Integer id) {
        return survivorRepository.findByIdWithInfectionReports(id).get();
    }
}
