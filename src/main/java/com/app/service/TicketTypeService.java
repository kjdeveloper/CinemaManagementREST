package com.app.service;

import com.app.dto.createDto.CreateTicketTypeDto;
import com.app.dto.getDto.GetTicketTypeDto;
import com.app.exception.AppException;
import com.app.model.TicketType;
import com.app.repository.TicketTypeRepository;
import com.app.service.mappers.CreateMappers;
import com.app.service.mappers.GetMappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TicketTypeService {

    private final TicketTypeRepository ticketTypeRepository;

    public List<GetTicketTypeDto> findAll() {
        return ticketTypeRepository.findAll()
                .stream()
                .map(GetMappers::fromTicketTypeToGetTicketTypeDto)
                .collect(Collectors.toList());
    }

    public GetTicketTypeDto findByName(String name) {
        if (Objects.isNull(name)) {
            throw new AppException("Id is null");
        }

        return ticketTypeRepository
                .findByName(name)
                .map(GetMappers::fromTicketTypeToGetTicketTypeDto)
                .orElseThrow(() -> new AppException("Ticket type with given id doesn't exist"));
    }

    public GetTicketTypeDto findById(Long id) {
        if (Objects.isNull(id)) {
            throw new AppException("Id is null");
        }

        return ticketTypeRepository
                .findById(id)
                .map(GetMappers::fromTicketTypeToGetTicketTypeDto)
                .orElseThrow(() -> new AppException("Ticket type with given id doesn't exist"));
    }

    public Long addTicketType(CreateTicketTypeDto ticketTypeDto) {
        if (Objects.isNull(ticketTypeDto)) {
            throw new AppException("Ticket type is null");
        }
        if (ticketTypeRepository.findByName(ticketTypeDto.getName()).isPresent()) {
            throw new AppException("Ticket type with given name already exist");
        }

        TicketType ticketType = CreateMappers.fromCreateTicketTypeDtoToTicketType(ticketTypeDto);

        return ticketTypeRepository.save(ticketType).getId();
    }

    public Long updateTicket(Long id, CreateTicketTypeDto createTicketTypeDto) {
        if (Objects.isNull(createTicketTypeDto)) {
            throw new AppException("Ticket dto is null");
        }
        if (Objects.isNull(id)) {
            throw new AppException("id is null");
        }

        TicketType ticketType = ticketTypeRepository.findById(id)
                .orElseThrow(() -> new AppException("id is null"));

        ticketType.setName(createTicketTypeDto.getName() != null ? createTicketTypeDto.getName() : ticketType.getName());
        ticketType.setPrice(createTicketTypeDto.getPrice() != null ? createTicketTypeDto.getPrice() : ticketType.getPrice());

        return ticketTypeRepository.save(ticketType).getId();
    }

    public Long deleteById(Long id) {
        if (Objects.isNull(id)) {
            throw new AppException("id is null");
        }

        TicketType ticketType = ticketTypeRepository.findById(id)
                .orElseThrow(() -> new AppException("Ticket type with given id doesn't exist"));

        ticketTypeRepository.delete(ticketType);
        return ticketType.getId();
    }

    public Long deleteAll() {
        long rows = ticketTypeRepository.count();
        ticketTypeRepository.deleteAll();
        return rows;
    }
}
