package com.app.controller;

import com.app.dto.createDto.CreateRoleDto;
import com.app.dto.data.Info;
import com.app.dto.getDto.GetRoleDto;
import com.app.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/findAll")
    public ResponseEntity<Info<List<GetRoleDto>>> findAll() {
        return ResponseEntity.ok(Info.<List<GetRoleDto>>builder()
                .data(roleService.findAll())
                .build());
    }

    @GetMapping("/findOne/{name}")
    public ResponseEntity<Info<GetRoleDto>> getOne(@PathVariable String name) {
        return ResponseEntity.ok(Info.<GetRoleDto>builder()
                .data(roleService.getOneByName(name))
                .build());
    }

    @PostMapping
    public ResponseEntity<Info<Long>> add(@RequestBody CreateRoleDto roleDto) {
        return new ResponseEntity<>(Info.<Long>builder()
                .data(roleService.add(roleDto))
                .build(),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Info<Long>> update(@PathVariable Long id, @RequestBody CreateRoleDto roleDto) {
        return new ResponseEntity<>(Info.<Long>builder()
                .data(roleService.update(id, roleDto))
                .build(),
                HttpStatus.OK);
    }

    @DeleteMapping("/deleteOne/{id}")
    public ResponseEntity<Info<Long>> deleteById(@PathVariable Long id) {
        return new ResponseEntity<>(Info.<Long>builder()
                .data(roleService.deleteById(id))
                .build(),
                HttpStatus.OK);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Info<Long>> deleteAll() {
        return new ResponseEntity<>(Info.<Long>builder()
                .data(roleService.deleteAll())
                .build(),
                HttpStatus.OK);
    }
}
