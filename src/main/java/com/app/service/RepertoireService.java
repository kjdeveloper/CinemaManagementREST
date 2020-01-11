package com.app.service;

import com.app.dto.RepertoireDto;
import com.app.exception.AppException;
import com.app.model.Repertoire;
import com.app.repository.RepertoireRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RepertoireService {

    private final RepertoireRepository repertoireRepository;

    public RepertoireDto findByDateAndCinemaName(LocalDate date, String cinemaName) {
        if (date == null) {
            throw new AppException("date is null");
        }
        if (cinemaName == null) {
            throw new AppException("Cinema name is null");
        }

        return repertoireRepository.findByDateAndCinema_Name(date, cinemaName)
                .map(Mappers::fromRepertoireToRepertoireDto)
                .orElseThrow(() -> new AppException("Repertoire one the day " + date + " in " + cinemaName + " doesn't exist"));
    }

    public List<RepertoireDto> findAll() {
        return repertoireRepository.findAll()
                .stream()
                .map(Mappers::fromRepertoireToRepertoireDto)
                .collect(Collectors.toList());
    }

    public Long add(RepertoireDto repertoireDto) {
        if (repertoireDto == null) {
            throw new AppException("Repertoire is null");
        }
        if (repertoireRepository.findByDateAndCinema_Name(repertoireDto.getDate(), repertoireDto.getCinemaDto().getName()).isPresent()) {
            throw new AppException("Repertoire on the day " + repertoireDto.getDate() + " in cinema " + repertoireDto.getCinemaDto().getName() + " already exist");
        }

        Repertoire repertoire = Mappers.fromRepertoireDtoToRepertoire(repertoireDto);
        repertoireRepository.save(repertoire);
        return repertoire.getId();
    }
}
