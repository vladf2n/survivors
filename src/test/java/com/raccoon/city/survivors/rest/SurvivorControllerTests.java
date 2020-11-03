package com.raccoon.city.survivors.rest;


import com.raccoon.city.survivors.TestUtils;
import com.raccoon.city.survivors.dto.SurvivorDTO;
import com.raccoon.city.survivors.service.SurvivorService;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SurvivorControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SurvivorService survivorService;

    @Autowired
    private Flyway flyway;

    @BeforeEach
    public void init(){
        flyway.clean();
        flyway.migrate();

        TestUtils.loadSurvivors(survivorService, TestUtils.getResourceList("survivors", SurvivorDTO.class));
    }

    @Test
    public void shouldRepoprtInfectedSurvivor() throws Exception {
        final int reporterSurvivorId = 1;
        final int targetSurvivorId = 2;
        
        this.mockMvc.perform(post("/api/umbrella/survivors/"+targetSurvivorId+"/infected")
        .header("User-Id", reporterSurvivorId))
        .andExpect(status().isNoContent());
    }

    @Test
    public void shouldNotReportInfectedWithAlreadyInfectedSurvivor() throws Exception {
        final int reporterSurvivorId = 3;
        final int targetSurvivorId = 1;

        this.mockMvc.perform(post("/api/umbrella/survivors/"+targetSurvivorId+"/infected")
        .header("User-Id", reporterSurvivorId))
        .andExpect(status().isBadRequest());
    }
}
