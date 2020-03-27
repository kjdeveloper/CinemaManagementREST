
package com.app.service;

import com.app.dto.createDto.CreatePlaceDto;
import com.app.model.Place;
import com.app.repository.PlaceRepository;
import com.app.service.mappers.CreateMappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class PlaceService {

    private PlaceRepository placeRepository;

    public Set<Place> createPlacesForHalls(int row, int number) {
        Set<Place> placeDtoSet = new HashSet<>();

        for (int i = 1; i <= row; i++) {
            for (int j = 1; j <= number; j++) {
                placeDtoSet.add(CreateMappers.fromCreatePlaceDtoToPlace(CreatePlaceDto.builder()
                        .available(true)
                        .row(i)
                        .number(j)
                        .build()));
            }
        }
        return placeDtoSet;
    }

}

