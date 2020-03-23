package com.app.service;

import com.app.dto.createDto.CreateRepertoireDto;
import com.app.dto.getDto.GetRepertoireDto;
import com.app.exception.AppException;
import com.app.model.Cinema;
import com.app.model.Repertoire;
import com.app.model.enums.City;
import com.app.repository.CinemaRepository;
import com.app.repository.RepertoireRepository;
import com.app.service.mappers.CreateMappers;
import com.app.service.mappers.GetMappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RepertoireService {

    private final RepertoireRepository repertoireRepository;
    private final CinemaRepository cinemaRepository;

    public List<GetRepertoireDto> findByDateAndCinemaCity(Map<String, String> params) {
        if (Objects.isNull(params)) {
            throw new AppException("params is null");
        }

        return repertoireRepository.findByDateAndCinema_Id(LocalDate.parse(params.get("date")), Long.valueOf(params.get("cinemaId")))
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
        if (Objects.isNull(createRepertoireDto)) {
            throw new AppException("Repertoire is null");
        }
        if (repertoireRepository.findByDateAndCinema_Id(createRepertoireDto.getDate(), createRepertoireDto.getCinemaId()).isPresent()) {
            throw new AppException("Repertoire on the day " + createRepertoireDto.getDate() + " in cinema id " + createRepertoireDto.getCinemaId() + " already exist");
        }
        Cinema cinema = cinemaRepository.findById(createRepertoireDto.getCinemaId())
                .orElseThrow(() -> new AppException("Cinema doesn't exist"));

        Repertoire repertoire = CreateMappers.fromCreateRepertoireDtoToRepertoire(createRepertoireDto);
        repertoire.setCinema(cinema);
        repertoireRepository.save(repertoire);

        return repertoire.getId();
    }
        //dodaje nowy cinema
    public Long update(Long id, CreateRepertoireDto repertoireDto) {
        if (Objects.isNull(id)) {
            throw new AppException("id is null");
        }

        Repertoire repertoire = repertoireRepository.findById(id)
                .orElseThrow(() -> new AppException("Repertoire with given id doesn't exist"));

        Cinema cinema = cinemaRepository.findById(repertoireDto.getCinemaId())
                .orElseThrow(() -> new AppException("Cinema doesn't exist"));


        repertoire.setCinema(repertoireDto.getCinemaId() != null ? cinema : repertoire.getCinema());
        repertoire.setDate(repertoireDto.getDate() != null ? repertoireDto.getDate() : repertoire.getDate());
        repertoireRepository.save(repertoire);
        return repertoire.getId();
    }

    public Long deleteById(Long id) {
        if (Objects.isNull(id)) {
            throw new AppException("id is null");
        }

        Repertoire repertoire = repertoireRepository.findById(id)
                .orElseThrow(() -> new AppException("Repertoire with given id doesn't exist"));

        repertoireRepository.delete(repertoire);
        return repertoire.getId();
    }

    public Long deleteAll() {
        long rows = repertoireRepository.count();
        repertoireRepository.deleteAll();
        return rows;
    }

}
