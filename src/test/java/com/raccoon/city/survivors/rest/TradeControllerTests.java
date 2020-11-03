package com.raccoon.city.survivors.rest;

import com.raccoon.city.survivors.TestUtils;
import com.raccoon.city.survivors.dto.SurvivorDTO;
import com.raccoon.city.survivors.service.SurvivorService;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TradeControllerTests {

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
    public void shouldTradeSuppliesBetweenHealthySurvivors() throws Exception {
        final String tradeDTO = TestUtils.getPayload("trade_proposal_okay");

        this.mockMvc.perform(post("/api/umbrella/trade-supplies")
            .content(tradeDTO).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldNotTradeSuppliesWithInfectedSurvivor() throws Exception {
        final String tradeDTO = TestUtils.getPayload("trade_proposal_infected");

        this.mockMvc.perform(post("/api/umbrella/trade-supplies")
            .content(tradeDTO).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldNotTradeSuppliesWithUnbalancedTrade() throws Exception {
        final String tradeDTO = TestUtils.getPayload("trade_proposal_unbalanced");

        this.mockMvc.perform(post("/api/umbrella/trade-supplies")
            .content(tradeDTO).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

}
