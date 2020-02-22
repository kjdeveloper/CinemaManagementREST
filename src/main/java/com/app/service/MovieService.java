package com.app.service;

import com.app.dto.createDto.CreateMovieDto;
import com.app.dto.getDto.GetMovieDto;
import com.app.dto.getDto.GetUserDto;
import com.app.exception.AppException;
import com.app.model.FilmShow;
import com.app.model.Movie;
import com.app.model.User;
import com.app.model.enums.Genre;
import com.app.repository.MovieRepository;
import com.app.repository.RepertoireRepository;
import com.app.repository.UserRepository;
import com.app.service.mappers.CreateMappers;
import com.app.service.mappers.GetMappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MovieService {

    private final MovieRepository movieRepository;
    private final RepertoireRepository repertoireRepository;
    private final UserRepository userRepository;

    public List<GetMovieDto> findAll() {
        return movieRepository.findAll()
                .stream()
                .map(GetMappers::fromMovieToGetMovieDto)
                .collect(Collectors.toList());
    }

    public GetMovieDto findById(Long id) {
        return movieRepository.findById(id)
                .stream()
                .map(GetMappers::fromMovieToGetMovieDto)
                .findFirst()
                .orElseThrow(() -> new AppException("Movie with id " + id + " doesn't exist"));
    }

    public List<GetMovieDto> findByTitle(String title) {
        return movieRepository
                .findByTitle(title)
                .stream()
                .map(GetMappers::fromMovieToGetMovieDto)
                .collect(Collectors.toList());
    }

    public List<GetMovieDto> findByGenre(Genre genre) {
        return movieRepository
                .findByGenre(genre)
                .stream()
                .map(GetMappers::fromMovieToGetMovieDto)
                .collect(Collectors.toList());
    }

    public List<GetMovieDto> findByDateBetweenGiven(LocalDate from, LocalDate to) {
        return repertoireRepository
                .findByDateBetween(from, to)
                .stream()
                .flatMap(re -> re.getFilmShows()
                        .stream())
                .collect(Collectors.toList())
                .stream()
                .map(FilmShow::getMovie)
                .map(GetMappers::fromMovieToGetMovieDto)
                .collect(Collectors.toList());
    }

    public Long addOne(CreateMovieDto createMovieDto) {
        if (createMovieDto == null) {
            throw new AppException("Movie is null");
        }
        if (movieRepository.findByTitleAndDirector(createMovieDto.getTitle(), createMovieDto.getDirector()).isPresent()) {
            throw new AppException("Movie with title " + createMovieDto.getTitle()
                    + " and director " + createMovieDto.getDirector()
                    + " already exist");
        }

        Movie movie = CreateMappers.fromCreateMovieDtoToMovie(createMovieDto);
        movieRepository.save(movie);
        return movie.getId();
    }

    public Long update(Long id, CreateMovieDto createMovieDto) {
        if (id == null) {
            throw new AppException("id is null");
        }
        if (createMovieDto == null) {
            throw new AppException("Movie is null");
        }

        Movie movie = movieRepository
                .findById(id)
                .orElseThrow(() -> new AppException("Movie with id " + id + " doesn't exist"));

        movie.setTitle(createMovieDto.getTitle() != null ? createMovieDto.getTitle() : movie.getTitle());
        movie.setDirector(createMovieDto.getDirector() != null ? createMovieDto.getDirector() : movie.getDirector());
        movie.setDuration(createMovieDto.getDuration() != null ? createMovieDto.getDuration() : movie.getDuration());
        movie.setGenre(createMovieDto.getGenre()!= null ? createMovieDto.getGenre() : movie.getGenre());
        movie.setDescription(createMovieDto.getDescription()!= null ? createMovieDto.getDescription() : movie.getDescription());

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

    public Long deleteAll() {
        long rows = movieRepository.count();

        movieRepository.deleteAll();
        return rows;
    }

    public Long addToFavourites(Long userId, Long movieId) {
        if (userId == null) {
            throw new AppException("User id is null");
        }
        if (movieId == null) {
            throw new AppException("Movie id is null");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException("Cannot find user with id " + userId + "."));

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new AppException("Cannot find movie with id " + movieId + "."));

        user.addFavouriteMovie(movie);
        return movie.getId();
    }

    public Long deleteFromFavourites(Long userId, Long movieId) {
        if (userId == null) {
            throw new AppException("User id is null");
        }
        if (movieId == null) {
            throw new AppException("Movie id is null");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException("Cannot find user with id " + userId + "."));

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new AppException("Cannot find movie with id " + movieId + "."));

        user.deleteFavouriteMovie(movie);
        return movie.getId();
    }
}
