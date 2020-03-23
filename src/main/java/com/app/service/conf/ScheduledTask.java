/*
package com.app.service.conf;

import com.app.model.FilmShow;
import com.app.repository.FilmShowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@EnableScheduling
public class ScheduledTask {

    private final FilmShowRepository filmShowRepository;

    @Scheduled(fixedRate = 2000)
    public void scheduleReleaseOfSeatsWithFixedRate() {

       List<FilmShow> filmShowList = filmShowRepository
                .findAll()
                .stream()
                .filter(f -> f.getStartTime().plusMinutes(f.getMovie().getDuration()).isAfter(LocalDateTime.now()))
                .collect(Collectors.toList());

        if (!filmShowList.isEmpty()) {
            filmShowList
                    .forEach(fi -> fi.getCinemaHall()
                            .getPlaces()
                            .forEach(p -> p.setAvailable(true)));
        }
    }
}
*/
