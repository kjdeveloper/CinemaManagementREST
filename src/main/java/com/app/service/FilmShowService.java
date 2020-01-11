package com.app.service;

import com.app.dto.FilmShowDto;
import com.app.exception.AppException;
import com.app.model.FilmShow;
import com.app.repository.FilmShowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FilmShowService {

    private final FilmShowRepository filmShowRepository;

    public List<FilmShowDto> findAll() {
        return filmShowRepository.findAll()
                .stream()
                .map(Mappers::fromFilmShowToFilmShowDto)
                .collect(Collectors.toList());
    }

    public FilmShowDto findById(Long id) {
        if (id == null) {
            throw new AppException("id is null");
        }

        return filmShowRepository
                .findById(id)
                .map(Mappers::fromFilmShowToFilmShowDto)
                .orElseThrow(() -> new AppException("Film show with id " + id + " doesn't exist"));
    }

    public Long add(FilmShowDto filmShowDto) {
        if (filmShowDto == null) {
            throw new AppException("Film show is null");
        }
       /* if (filmShowRepository.findByStartTime_DateAndMovie_Title(filmShowDto.getStartTime().toLocalDate(), filmShowDto.getMovieDto().getTitle()).isPresent()) {
            throw new AppException("Film show on the day " + filmShowDto.getStartTime().toLocalDate() + " and with given movie title: " + filmShowDto.getMovieDto().getTitle() + "already exist");
        }
*/
        FilmShow filmShow = Mappers.fromFilmShowDtoToFilmShow(filmShowDto);
        filmShowRepository.save(filmShow);
        return filmShow.getId();
    }

    public Long edit(Long id, FilmShowDto filmShowDto) {
        if (id == null) {
            throw new AppException("id is null");
        }
        if (filmShowDto == null) {
            throw new AppException("Film show is null");
        }

        FilmShow filmShow = filmShowRepository
                .findById(id)
                .orElseThrow(() -> new AppException("Film show with id " + id + " doesn't exist"));

        filmShow.setMovie(filmShowDto.getMovieDto() == null ? null : Mappers.fromMovieDtoToMovie(filmShowDto.getMovieDto()));
        filmShow.setCinemaHall(filmShowDto.getCinemaHall());
        filmShow.setDuration(filmShowDto.getDuration());
        filmShow.setStartTime(filmShowDto.getStartTime());
        filmShow.setTicketsAvailable(filmShowDto.getTicketsAvailable());

        filmShowRepository.save(filmShow);
        return filmShow.getId();
    }

    public Long deleteById(Long id){
        if (id == null){
            throw new AppException("id is null");
        }
        FilmShow filmShow = filmShowRepository
                .findById(id)
                .orElseThrow(() -> new AppException("Film show with id " + id + " doesn't exist"));

        filmShowRepository.delete(filmShow);
        return filmShow.getId();
    }

    public Long deleteAll(Long id){
        long rows = filmShowRepository.count();
        filmShowRepository.deleteAll();
        return rows;
    }

}
