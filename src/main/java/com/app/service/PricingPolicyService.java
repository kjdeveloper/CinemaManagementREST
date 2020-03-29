package com.app.service;

import com.app.model.enums.TicketType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PricingPolicyService {

    private BigDecimal lowerPrice(BigDecimal price, Double percent){
        return price.multiply(BigDecimal.valueOf(percent).multiply(BigDecimal.valueOf(100)));
    }

    //changing price = 20% lower
    public String summerPriceForTickets(){
        List<TicketType> tickets = Arrays.asList(TicketType.values());
        System.out.println(tickets);
        tickets.stream()
                .map(TicketType::getPrice)
                .forEach(p -> lowerPrice(p, 20.00));
        System.out.println(tickets);
        return "Prices of ticket will be lower 20%";
    }
}
