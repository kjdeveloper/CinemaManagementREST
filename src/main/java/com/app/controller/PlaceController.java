package com.app.controller;

import com.app.dto.data.Info;
import com.app.dto.getDto.GetPlaceDto;
import com.app.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/place")
public class PlaceController {

    private final PlaceService placeService;

    @GetMapping("/findAll")
    public ResponseEntity<Info<List<GetPlaceDto>>> getAll(){
        return ResponseEntity.ok(Info.<List<GetPlaceDto>>builder()
                .data(placeService.findAll())
                .build());
    }
}
