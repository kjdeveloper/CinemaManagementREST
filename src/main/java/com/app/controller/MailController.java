package com.app.controller;

import com.app.dto.data.Info;
import com.app.service.mail.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mail")
public class MailController {

    private final MailService mailService;

    @PostMapping("/aboutEvent")
    public ResponseEntity<Info<Long>> sendMailAboutEvent(@RequestBody String title, @RequestBody String message) {
        return new ResponseEntity<>(Info.<Long>builder()
                .data(mailService.sendInformationAboutNewEvent(title, message))
                .build(),
                HttpStatus.CREATED);
    }

    @PostMapping("/aboutFavMovie")
    public ResponseEntity<Info<Long>> sendMailAboutFavMovie(@RequestBody String title, @RequestBody String message) {
        return new ResponseEntity<>(Info.<Long>builder()
                .data(mailService.sendInformationAboutFavouriteMovie(title, message))
                .build(),
                HttpStatus.CREATED);
    }


}
