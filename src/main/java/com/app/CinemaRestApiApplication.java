package com.app;

import com.app.model.*;
import com.app.model.enums.CinemaHallType;
import com.app.model.enums.City;
import com.app.model.enums.Genre;
import com.app.repository.*;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@SpringBootApplication
@RequiredArgsConstructor
@EnableScheduling
public class CinemaRestApiApplication {

    private final RoleRepository roleRepository;
    private final RepertoireRepository repertoireRepository;
    private final UserRepository userRepository;
    private final CinemaRepository cinemaRepository;
    private final MovieRepository movieRepository;
    private final FilmShowRepository filmShowRepository;
    //private final ScheduledTask scheduledTask;

    public static void main(String[] args) {
        SpringApplication.run(CinemaRestApiApplication.class, args);
    }


    @Bean
    public CommandLineRunner commandLineRunner() {
        return new CommandLineRunner() {
            @Override
            @Transactional
            public void run(String... args) throws Exception {

               if (roleRepository.count() == 0) {
                    roleRepository.save(Role.builder().name("USER").build());
                    roleRepository.save(Role.builder().name("ADMIN").build());
                }

                if (cinemaRepository.count() == 0 || movieRepository.count() == 0 || filmShowRepository.count() == 0 || repertoireRepository.count() == 0) {
                    Cinema cinema1 = Cinema.builder().city(City.GDANSK).name("CINEMAGDO").repertoires(new HashSet<>()).build();

                    CinemaHall cinemaHallVIP = CinemaHall.builder().type(CinemaHallType.VIP).name("vipowska").cinema(cinema1).build();
                    CinemaHall cinemaHallBig = CinemaHall.builder().type(CinemaHallType.BIG).name("bigowska").cinema(cinema1).build();
                    CinemaHall cinemaHallSmall = CinemaHall.builder().type(CinemaHallType.SMALL).name("smallowska").cinema(cinema1).build();
                    CinemaHall cinemaHallMedium = CinemaHall.builder().type(CinemaHallType.MEDIUM).name("mediumowska").cinema(cinema1).build();


                    Movie movie1 = Movie.builder().title("Title1").genre(Genre.FAMILY).director("director 1").duration(120).description("Some description about title 1").filmShows(new HashSet<>()).build();
                    Movie movie2 = Movie.builder().title("Title2").genre(Genre.ACTION).director("director 2").duration(125).description("Some description about title 2").filmShows(new HashSet<>()).build();
                    Movie movie3 = Movie.builder().title("Title3").genre(Genre.ACTION).director("director 3").duration(125).description("Some description about title 3").filmShows(new HashSet<>()).build();
                    Repertoire repertoire1 = repertoireRepository.save(Repertoire.builder().filmShows(new HashSet<>()).date(LocalDate.now().plusDays(2)).cinema(cinema1).build());
                    filmShowRepository.save(FilmShow.builder().startTime(LocalDateTime.now().plusDays(2)).movie(movie1).ticketsAvailable(225).repertoire(repertoire1).cinemaHall(cinemaHallBig).build());
                    filmShowRepository.save(FilmShow.builder().startTime(LocalDateTime.now().plusDays(2)).movie(movie2).ticketsAvailable(150).repertoire(repertoire1).cinemaHall(cinemaHallMedium).build());
                    filmShowRepository.save(FilmShow.builder().startTime(LocalDateTime.now().plusDays(2)).movie(movie3).ticketsAvailable(150).repertoire(repertoire1).cinemaHall(cinemaHallVIP).build());

                    Cinema cinema2 = Cinema.builder().city(City.POZNAN).name("CINEMAPOZ").repertoires(new HashSet<>()).build();

                    CinemaHall cinemaHallBig2 = CinemaHall.builder().type(CinemaHallType.BIG).name("bigowska").cinema(cinema2).build();
                    CinemaHall cinemaHallSmall2 = CinemaHall.builder().type(CinemaHallType.SMALL).name("smallowska").cinema(cinema2).build();
                    CinemaHall cinemaHallMedium2 = CinemaHall.builder().type(CinemaHallType.MEDIUM).name("mediumowska").cinema(cinema2).build();
                    Movie movie4 = Movie.builder().title("Title4").genre(Genre.FANTASY).director("director 4").duration(110).description("Some description about title 4").filmShows(new HashSet<>()).build();
                    Movie movie5 = Movie.builder().title("Title5").genre(Genre.FANTASY).director("director 5").duration(100).description("Some description about title 5").filmShows(new HashSet<>()).build();
                    Movie movie6 = Movie.builder().title("Title6").genre(Genre.SCI_FI).director("director 6").duration(110).description("Some description about title 6").filmShows(new HashSet<>()).build();
                    Repertoire repertoire2 = repertoireRepository.save(Repertoire.builder().filmShows(new HashSet<>()).date(LocalDate.now().plusDays(3)).cinema(cinema2).build());
                    filmShowRepository.save(FilmShow.builder().startTime(LocalDateTime.now().plusDays(3)).movie(movie4).repertoire(repertoire2).ticketsAvailable(225).cinemaHall(cinemaHallBig2).build());
                    filmShowRepository.save(FilmShow.builder().startTime(LocalDateTime.now().plusDays(3)).movie(movie5).repertoire(repertoire2).ticketsAvailable(150).cinemaHall(cinemaHallMedium2).build());
                    filmShowRepository.save(FilmShow.builder().startTime(LocalDateTime.now().plusDays(3)).movie(movie6).repertoire(repertoire2).ticketsAvailable(100).cinemaHall(cinemaHallSmall2).build());




                    Cinema cinema3 = Cinema.builder().city(City.WARSZAWA).name("CINEMAWAW").repertoires(new HashSet<>()).build();


                    CinemaHall cinemaHallVIP3 = CinemaHall.builder().type(CinemaHallType.VIP).name("vipowska").cinema(cinema3).build();
                    CinemaHall cinemaHallSmall3 = CinemaHall.builder().type(CinemaHallType.SMALL).name("smallowska").cinema(cinema3).build();
                    CinemaHall cinemaHallMedium3 = CinemaHall.builder().type(CinemaHallType.MEDIUM).name("mediumowska").cinema(cinema3).build();
                    Movie movie7 = Movie.builder().title("Title7").genre(Genre.SCI_FI).director("director 7").duration(125).description("Some description about title 7").filmShows(new HashSet<>()).build();
                    Movie movie8 = Movie.builder().title("Title8").genre(Genre.FANTASY).director("director 8").duration(125).description("Some description about title 8").filmShows(new HashSet<>()).build();
                    Movie movie9 = Movie.builder().title("Title9").genre(Genre.BIOGRAPHY).director("director 9").duration(110).description("Some description about title 9").filmShows(new HashSet<>()).build();
                    Repertoire repertoire3 = repertoireRepository.save(Repertoire.builder().filmShows(new HashSet<>()).date(LocalDate.now().plusDays(3)).cinema(cinema3).build());
                    filmShowRepository.save(FilmShow.builder().startTime(LocalDateTime.now().plusDays(3)).movie(movie7).repertoire(repertoire3).ticketsAvailable(150).cinemaHall(cinemaHallMedium3).build());
                    filmShowRepository.save(FilmShow.builder().startTime(LocalDateTime.now().plusDays(3)).movie(movie8).repertoire(repertoire3).ticketsAvailable(100).cinemaHall(cinemaHallSmall3).build());
                    filmShowRepository.save(FilmShow.builder().startTime(LocalDateTime.now().plusDays(3)).movie(movie9).repertoire(repertoire3).ticketsAvailable(150).cinemaHall(cinemaHallVIP3).build());


                }
            }
        };
}

    @PostConstruct
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Bean
    public SecretKey secretKey() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }
}
