
package com.app.service;

import com.app.dto.createDto.CreatePlaceDto;
import com.app.dto.getDto.GetPlaceDto;
import com.app.model.CinemaHall;
import com.app.model.Place;
import com.app.repository.PlaceRepository;
import com.app.service.mappers.CreateMappers;
import com.app.service.mappers.GetMappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;

    public Set<Place> createPlacesForHalls(CinemaHall cinemaHall, int row, int number) {
        Set<Place> placeDtoSet = new HashSet<>();

        CreatePlaceDto placeDto = new CreatePlaceDto();

        for (int i = 1; i <= row; i++) {
            placeDto.setRow(i);
            for (int j = 1; j <= number; j++) {
                placeDto.setNumber(j);
                placeDto.setAvailable(true);
                Place pl = CreateMappers.fromCreatePlaceDtoToPlace(placeDto);
                pl.setCinemaHall(cinemaHall);
                placeDtoSet.add(pl);
                placeRepository.save(pl);
            }
        }
        return placeDtoSet;
    }

    public List<GetPlaceDto> findAll() {
        return placeRepository
                .findAll()
                .stream()
                .map(GetMappers::fromPlaceToGetPlaceDto)
                .collect(Collectors.toList());
    }
}

