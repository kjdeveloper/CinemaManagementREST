package com.app.service;

import com.app.dto.createDto.CreateRoleDto;
import com.app.dto.getDto.GetRoleDto;
import com.app.exception.AppException;
import com.app.model.Role;
import com.app.repository.RoleRepository;
import com.app.service.mappers.CreateMappers;
import com.app.service.mappers.GetMappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public List<GetRoleDto> findAll() {
        return roleRepository.findAll()
                .stream()
                .map(GetMappers::fromRoleToGetRoleDto)
                .collect(Collectors.toList());
    }

    public GetRoleDto getOneByName(String name) {

        if (Objects.isNull(name)) {
            throw new AppException("Name is null");
        }

        return roleRepository.findByName(name)
                .map(GetMappers::fromRoleToGetRoleDto)
                .orElseThrow(() -> new AppException("Role with given name doesn't exist"));
    }

    public Long add(CreateRoleDto roleDto) {
        if (Objects.isNull(roleDto)) {
            throw new AppException("Role is null");
        }

        if (roleRepository.findByName(roleDto.getName()).isPresent()) {
            throw new AppException("Role with given name already exist");
        }

        Role role = CreateMappers.fromCreateRoleDtoToRole(roleDto);

        return roleRepository.save(role).getId();
    }

    public Long update(Long id, CreateRoleDto roleDto) {
        if (Objects.isNull(roleDto)) {
            throw new AppException("Role is null");
        }
        if (Objects.isNull(id)) {
            throw new AppException("Id is null");
        }

        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new AppException("Role with given id doesn't exist"));

        role.setName(roleDto.getName());

        return role.getId();
    }

    public Long deleteById(Long id) {

        if (Objects.isNull(id)) {
            throw new AppException("Id is null");
        }

        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new AppException("Cannot find role with given id"));

        roleRepository.delete(role);
        return role.getId();
    }

    public Long deleteAll() {

        long rows = roleRepository.count();
        roleRepository.deleteAll();
        return rows;
    }

}
