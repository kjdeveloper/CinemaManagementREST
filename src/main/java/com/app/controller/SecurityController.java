package com.app.controller;

import com.app.dto.createDto.CreateUserDto;
import com.app.dto.data.Info;
import com.app.dto.data.RefreshTokenDto;
import com.app.dto.data.Tokens;
import com.app.dto.getDto.GetUserDto;
import com.app.security.token.JwtTokenManager;
import com.app.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/security")
public class SecurityController {

    private final SecurityService securityService;
    private final JwtTokenManager jwtTokenManager;

    @GetMapping
    public ResponseEntity<Info<List<GetUserDto>>> findAllUsers() {
        return ResponseEntity.ok(Info.<List<GetUserDto>>builder()
                .data(securityService.findAllUsers())
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Info<GetUserDto>> findUserById(@PathVariable Long id) {
        return ResponseEntity.ok(Info.<GetUserDto>builder()
                .data(securityService.findUserById(id))
                .build());
    }

    @PostMapping("/refreshTokens")
    public ResponseEntity<Info<Tokens>> refreshToken(@RequestBody RefreshTokenDto refreshTokenDto) {
        return new ResponseEntity<>(Info.<Tokens>builder()
                .data(jwtTokenManager.generateTokens(refreshTokenDto))
                .build(),
                HttpStatus.CREATED);
    }

    @PostMapping("/register")
    public ResponseEntity<Info<Long>> register(@RequestBody CreateUserDto createUserDto) {
        return new ResponseEntity<>(Info.<Long>builder()
                .data(securityService.register(createUserDto))
                .build(),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Info<Long>> update(@PathVariable Long id, @RequestBody CreateUserDto userDto) {
        return new ResponseEntity<>(Info.<Long>builder()
                .data(securityService.update(id, userDto))
                .build(),
                HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<Info<Long>> updateParams(@PathVariable Long id, @RequestBody Map<String, String> params) {
        return new ResponseEntity<>(Info.<Long>builder()
                .data(securityService.changeParam(id, params))
                .build(),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Info<Long>> deleteById(@PathVariable Long id) {
        return new ResponseEntity<>(Info.<Long>builder()
                .data(securityService.remove(id))
                .build(),
                HttpStatus.OK);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Info<Long>> deleteAll() {
        return new ResponseEntity<>(Info.<Long>builder()
                .data(securityService.removeAll())
                .build(),
                HttpStatus.OK);
    }

}
