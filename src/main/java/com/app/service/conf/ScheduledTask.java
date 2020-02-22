package com.app.service.conf;

import com.app.model.FilmShow;
import com.app.repository.FilmShowRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ScheduledTask {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTask.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    private final FilmShowRepository filmShowRepository;

    //change time
/*    @Scheduled(fixedRate = 20000)
    public void scheduleReleaseOfSeatsWithFixedRate() {

        List<FilmShow> filmShowList = filmShowRepository.findAll()
                .stream()
                .filter(f -> f.getStartTime().plusMinutes(f.getMovie().getDuration()).isAfter(LocalDateTime.now()))
                .collect(Collectors.toList());

        if (filmShowList.size() > 0) {
            filmShowList
                    .stream()
                    .forEach(fi -> fi.getCinemaHall()
                            .getPlaces()
                            .stream()
                            .forEach(p -> p.setAvailable(true)));

            for (FilmShow film : filmShowList) {
                logger.info("Cinema hall " + film.getCinemaHall().getName() + " have places available");
            }
        }

        logger.info("Nothing to clean");
    }*/

}
