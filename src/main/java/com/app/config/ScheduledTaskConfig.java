/*
package com.app.config;

import com.app.model.FilmShow;
import com.app.model.Place;
import com.app.model.Ticket;
import com.app.repository.FilmShowRepository;
import com.app.repository.PlaceRepository;
import com.app.repository.TicketRepository;
import com.app.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@EnableScheduling
public class ScheduledTaskConfig {

    private final FilmShowRepository filmShowRepository;
    private final PlaceRepository placeRepository;
    private final TicketRepository ticketRepository;
    private final TicketService ticketService;

    @Scheduled(fixedRate = 5000)
    public void scheduleReleaseOfSeatsWithFixedRate() {
        LocalDateTime timeNow = ZonedDateTime.now(ZoneId.of("Europe/Paris")).toLocalDateTime();
        List<FilmShow> filmShowList = filmShowRepository
                .findAll()
                .stream()
                .filter(f -> timeNow.isAfter(f.getStartTime().plusMinutes(f.getMovie().getDuration())))
                .collect(Collectors.toList());

        System.out.println(filmShowList);
        List<Place> places = new ArrayList<>();

        if (!filmShowList.isEmpty()) {
            places = filmShowList
                    .stream()
                    .map(FilmShow::getCinemaHall)
                    .flatMap(ch -> ch.getPlaces()
                            .stream())
                    .collect(Collectors.toList());
        }
        System.out.println(places);
        places.forEach(pl -> pl.setAvailable(true));
        places.forEach(pl -> pl.setTicket(null));

        placeRepository.saveAll(places);
    }

    @Scheduled(fixedRate = 5000)
    public void scheduleReleaseOfTicketsWithFixedRate() {
        LocalDateTime timeNow = ZonedDateTime.now(ZoneId.of("Europe/Paris")).toLocalDateTime();
        List<Ticket> ticketsReservation = ticketRepository.findByReservation(true)
                .stream()
                .filter(filmShowStartTime -> timeNow.isAfter(filmShowStartTime.getFilmShow().getStartTime().minusMinutes(30)))
                .collect(Collectors.toList());

        System.out.println(ticketsReservation);
        //ticketService.deleteCollection(ticketsReservation);
    }
}


*/
