package com.app.service;

import com.app.dto.createDto.CreateRepertoireDto;
import com.app.dto.getDto.GetRepertoireDto;
import com.app.exception.AppException;
import com.app.model.Repertoire;
import com.app.model.enums.City;
import com.app.repository.RepertoireRepository;
import com.app.service.mappers.CreateMappers;
import com.app.service.mappers.GetMappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RepertoireService {

    private final RepertoireRepository repertoireRepository;

    public List<GetRepertoireDto> findByDateAndCinemaCity(LocalDate date, City city) {
        if (date == null) {
            throw new AppException("date is null");
        }
        if (city == null) {
            throw new AppException("Cinema name is null");
        }

        return repertoireRepository.findByDateAndCinema_City(date, city)
                .stream()
                .flatMap(Collection::stream)
                .map(GetMappers::fromRepertoireToGetRepertoireDto)
                .collect(Collectors.toList());
    }

    public List<GetRepertoireDto> findAll() {
        return repertoireRepository.findAll()
                .stream()
                .map(GetMappers::fromRepertoireToGetRepertoireDto)
                .collect(Collectors.toList());
    }

    public Long add(CreateRepertoireDto createRepertoireDto) {
        if (createRepertoireDto == null) {
            throw new AppException("Repertoire is null");
        }
        if (repertoireRepository.findByDateAndCinema_City(createRepertoireDto.getDate(), createRepertoireDto.getCinema().getCity()).isPresent()) {
            throw new AppException("Repertoire on the day " + createRepertoireDto.getDate() + " in cinema " + createRepertoireDto.getCinema().getName() + " already exist");
        }

        Repertoire repertoire = CreateMappers.fromCreateRepertoireDtoToRepertoire(createRepertoireDto);
        repertoireRepository.save(repertoire);
        return repertoire.getId();
    }

    public Long update(Long id, CreateRepertoireDto repertoireDto) {
        if (id == null) {
            throw new AppException("id is null");
        }

        Repertoire repertoire = repertoireRepository.findById(id).orElseThrow(() -> new AppException("Repertoire with given id doesn't exist"));
        repertoire.setCinema(repertoireDto.getCinema() != null ? CreateMappers.fromCreateCinemaDtoToCinema(repertoireDto.getCinema()) : repertoire.getCinema());
        repertoire.setDate(repertoireDto.getDate() != null ? repertoireDto.getDate() : repertoire.getDate());
        repertoireRepository.save(repertoire);
        return repertoire.getId();
    }

    public Long deleteById(Long id){
        if (id == null){
            throw new AppException("id is null");
        }

        Repertoire repertoire = repertoireRepository.findById(id)
                .orElseThrow(() -> new AppException("Repertoire with given id doesn't exist"));

        repertoireRepository.delete(repertoire);
        return repertoire.getId();
    }

    public Long deleteAll(){
        long rows = repertoireRepository.count();
        repertoireRepository.deleteAll();
        return rows;
    }

}
