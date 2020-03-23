package com.app.service;

import com.app.dto.getDto.GetUserDto;
import com.app.exception.AppException;
import com.app.repository.UserRepository;
import com.app.service.mappers.GetMappers;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MailService {

    private final UserRepository userRepository;

    private final JavaMailSender javaMailSender;

    public Long sendInformationAboutNewEvent(String title, String message) {
        if (Objects.isNull(title)) {
            throw new AppException("title is null");
        }
        if (Objects.isNull(message)) {
            throw new AppException("message is null");
        }

        List<GetUserDto> users = userRepository.findAll()
                .stream()
                .map(GetMappers::fromUserToGetUserDto)
                .collect(Collectors.toList());

        long rows = users.size();

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        for (GetUserDto u : users) {
            simpleMailMessage.setTo(u.getEmail());
            simpleMailMessage.setSubject(title);
            simpleMailMessage.setText(message);
            javaMailSender.send(simpleMailMessage);
        }
        return rows;
    }

    public Long sendInformationAboutFavouriteMovie(String title, String message) {
        if (Objects.isNull(title)) {
            throw new AppException("title is null");
        }
        if (Objects.isNull(message)) {
            throw new AppException("message is null");
        }

        List<GetUserDto> users = userRepository.findAll()
                .stream()
                .filter(u -> u.getFavouriteMovies()
                        .stream()
                        .filter(t -> t.getTitle().equals(title)).count() == 1)
                .map(GetMappers::fromUserToGetUserDto)
                .collect(Collectors.toList());

        long rows = users.size();

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        for (GetUserDto u : users) {
            simpleMailMessage.setTo(u.getEmail());
            simpleMailMessage.setSubject(title);
            simpleMailMessage.setText(message);
            javaMailSender.send(simpleMailMessage);
        }
        return rows;
    }

}
