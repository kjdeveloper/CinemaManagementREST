package com.app.service;

import com.app.dto.MovieDto;
import com.app.exception.AppException;
import com.app.model.Movie;
import com.app.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MovieService {

    private final MovieRepository movieRepository;

    public List<MovieDto> findAll() {
        return movieRepository.findAll()
                .stream()
                .map(Mappers::fromMovieToMovieDto)
                .collect(Collectors.toList());
    }

    public MovieDto getById(Long id) {
        return movieRepository.findById(id)
                .stream()
                .map(Mappers::fromMovieToMovieDto)
                .findFirst()
                .orElseThrow(() -> new AppException("Movie with id " + id + " doesn't exist"));
    }

    public Long addOne(MovieDto movieDto) {
        if (movieDto == null) {
            throw new AppException("Movie is null");
        }
        if (movieRepository.findByTitleAndDirector(movieDto.getTitle(), movieDto.getDirector()).isPresent()) {
            throw new AppException("Movie with title " + movieDto.getTitle()
                    + " and director " + movieDto.getDirector()
                    + " already exist");
        }

        Movie movie = Mappers.fromMovieDtoToMovie(movieDto);
        movieRepository.save(movie);
        return movie.getId();
    }

    public Long update(Long id, MovieDto movieDto) {
        if (id == null) {
            throw new AppException("id is null");
        }
        if (movieDto == null) {
            throw new AppException("Movie is null");
        }

        Movie movie = movieRepository
                .findById(id)
                .orElseThrow(() -> new AppException("Movie with id " + id + " doesn't exist"));

        movie.setTitle(movieDto.getTitle());
        movie.setDirector(movieDto.getDirector());
        movie.setDuration(movieDto.getDuration());
        movie.setGenre(movieDto.getGenre());
        movie.setDescription(movieDto.getDescription());

        movieRepository.save(movie);
        return movie.getId();
    }

    public Long deleteById(Long id) {
        if (id == null) {
            throw new AppException("id is null");
        }

        Movie movie = movieRepository
                .findById(id)
                .orElseThrow(() -> new AppException("Movie with id " + id + " doesn't exist"));

        movieRepository.delete(movie);
        return movie.getId();
    }

    public Long deleteAll(){
        long rows = movieRepository.count();

        movieRepository.deleteAll();
        return rows;
    }


}
