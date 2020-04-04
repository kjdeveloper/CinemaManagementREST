package com.app.service;

import com.app.dto.createDto.CreateReducePricesDto;
import com.app.exception.AppException;
import com.app.model.TicketType;
import com.app.repository.TicketTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class PricingPolicyService {

    private final TicketTypeRepository ticketTypeRepository;

    private BigDecimal reducePrice(BigDecimal price, String percent) {
        BigDecimal discount = price.multiply(new BigDecimal(percent).divide(new BigDecimal("100")));
        return price.subtract(discount);
    }

    public String backToBasicPrices(Map<String, String> prices) {
        TicketType normal = ticketTypeRepository
                .findByName("Normal")
                .orElseThrow(() -> new AppException("Cannot find normal ticket type"));

        TicketType reduced = ticketTypeRepository
                .findByName("Reduced")
                .orElseThrow(() -> new AppException("Cannot find reduced ticket type"));

        TicketType senior = ticketTypeRepository
                .findByName("Senior")
                .orElseThrow(() -> new AppException("Cannot find senior ticket type"));

        normal.setPrice(new BigDecimal(prices.get("Normal")));
        reduced.setPrice(new BigDecimal(prices.get("Reduced")));
        senior.setPrice(new BigDecimal(prices.get("Senior")));

        ticketTypeRepository.saveAll(List.of(normal, reduced, senior));

        return "Prices have returned to standard";
    }

    public String reducePrices(CreateReducePricesDto createReducePricesDto) {

        List<String> ticketTypes = createReducePricesDto.getTicketTypeList();
        String percent = createReducePricesDto.getPercentDiscount();
        String title = createReducePricesDto.getTitle();

        for (String tt : ticketTypes) {
            TicketType ticketType = ticketTypeRepository
                    .findByName(tt)
                    .orElseThrow(() -> new AppException("Ticket type doesn't exist"));

            ticketType.setPrice(reducePrice(ticketType.getPrice(), percent));
            ticketTypeRepository.save(ticketType);
        }

        return title +
                "The following ticket types have changed: "
                + " " +
                ticketTypes + ".";
    }


}
