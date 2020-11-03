package com.raccoon.city.survivors.rest;

import javax.validation.Valid;

import com.raccoon.city.survivors.dto.TradeDTO;
import com.raccoon.city.survivors.service.TradeService;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/umbrella/trade-supplies")
@Api(value = "Trade", description = "Supply trading API", tags = {"Trade"})
public class TradeController {

    private final TradeService tradeService;

    @Autowired
    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiOperation(value = "Trade supplies between two survivors", tags = {"Trade", "Survivors"})
	public void tradeSupplies(@Valid @RequestBody TradeDTO tradeDTO) {
		tradeService.trade(tradeDTO);
    }
}
