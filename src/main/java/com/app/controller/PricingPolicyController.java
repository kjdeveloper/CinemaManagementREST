package com.app.controller;

import com.app.dto.createDto.CreateReducePricesDto;
import com.app.dto.data.Info;
import com.app.service.PricingPolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pricePolicy")
public class PricingPolicyController {

    private final PricingPolicyService pricingPolicy;

    @PostMapping("/backToBasic")
    public ResponseEntity<Info<String>> backToBasicPrice(@RequestBody Map<String, String> prices) {
        return new ResponseEntity<>(Info.<String>builder()
                .data(pricingPolicy.backToBasicPrices(prices))
                .build(),
                HttpStatus.CREATED);
    }

    @PostMapping("/discount")
    public ResponseEntity<Info<String>> reducedPrices(@RequestBody CreateReducePricesDto createReducePricesDto) {
        return new ResponseEntity<>(Info.<String>builder()
                .data(pricingPolicy.reducePrices(createReducePricesDto))
                .build(),
                HttpStatus.CREATED);
    }
}
