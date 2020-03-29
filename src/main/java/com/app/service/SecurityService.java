package com.app.service;

import com.app.dto.createDto.CreateUserDto;
import com.app.dto.getDto.GetUserDto;
import com.app.exception.AppException;
import com.app.model.Role;
import com.app.model.User;
import com.app.repository.RoleRepository;
import com.app.repository.UserRepository;
import com.app.service.mappers.CreateMappers;
import com.app.service.mappers.GetMappers;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SecurityService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public List<GetUserDto> findAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(GetMappers::fromUserToGetUserDto)
                .collect(Collectors.toList());
    }

    public GetUserDto findUserById(Long id) {
        if (Objects.isNull(id)) {
            throw new AppException("id is null");
        }
        return userRepository.
                findById(id)
                .map(GetMappers::fromUserToGetUserDto)
                .orElseThrow(() -> new AppException("User with given id doesn't exist"));
    }

    public Long register(CreateUserDto userDto) {

        if (Objects.isNull(userDto)) {
            throw new AppException("register is null");
        }

        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new AppException("user with given username already exist");
        }
        if (!Objects.equals(userDto.getPassword(), userDto.getPasswordConfirmation())) {
            throw new AppException("passwords are not equal");
        }

        Set<Role> roles = userDto.getRoles()
                .stream()
                .map(roleStr -> roleRepository.findByName(roleStr).orElseThrow(() -> new AppException("role not found")))
                .collect(Collectors.toSet());

        User user = CreateMappers.fromCreateUserDtoToUser(userDto);
        String password = passwordEncoder.encode(userDto.getPassword());
        user.setPassword(password);

        user.setRoles(roles);
        return userRepository.save(user).getId();
    }

    public Long update(Long id, CreateUserDto userDto) {
        if (Objects.isNull(id)) {
            throw new AppException("id is null");
        }

        if (Objects.isNull(userDto)) {
            throw new AppException("user is null");
        }

        if (!Objects.equals(userDto.getPassword(), userDto.getPasswordConfirmation())) {
            throw new AppException("registration - passwords don't match");
        }

        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new AppException("User with given id is not exist"));

        Set<Role> roles = userDto
                .getRoles()
                .stream()
                .map(name -> roleRepository.findByName(name).orElseThrow(() -> new AppException("no role with name " + name)))
                .collect(Collectors.toSet());

        user.setName(userDto.getName() != null ? userDto.getName() : user.getName());
        user.setSurname(userDto.getSurname() != null ? userDto.getSurname() : user.getSurname());
        user.setUsername(userDto.getUsername() != null ? userDto.getUsername() : user.getUsername());
        user.setAge(userDto.getAge() != null ? userDto.getAge() : user.getAge());
        user.setEmail(userDto.getEmail() != null ? userDto.getEmail() : user.getEmail());
        user.setPassword(userDto.getPassword() != null ? userDto.getPassword() : user.getPassword());
        user.setRoles(roles);
        return userRepository.save(user).getId();
    }

    public Long changeParam(Long id, Map<String, String> params) {
        if (Objects.isNull(id)) {
            throw new AppException("id is null");
        }
        if (Objects.isNull(params)) {
            throw new AppException("params are null");
        }

        String password = params.get("password");
        String passwordConfirmation = params.get("passwordConfirmation");
        if (!Objects.equals(password, passwordConfirmation)) {
            throw new AppException("passwords are not equal");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException("User with given id doesn't exist"));

        user.setName(params.containsKey("name") ? params.get("name") : user.getName());
        user.setSurname(params.containsKey("surname") ? params.get("surname") : user.getSurname());
        user.setAge(params.containsKey("age") ? Integer.parseInt(params.get("age")) : user.getAge());
        user.setEmail(params.containsKey("email") ? params.get("email") : user.getEmail());
        user.setUsername(params.containsKey("username") ? params.get("username") : user.getUsername());
        user.setPassword(params.containsKey("password") ? params.get("password") : user.getPassword());

        userRepository.save(user);
        return user.getId();
    }

    public Long remove(Long id) {
        if (Objects.isNull(id)) {
            throw new AppException("id is null");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException("User with given id doesn't exist"));
        user.setRoles(null);

        userRepository.delete(user);

        return user.getId();
    }

    public Long removeAll() {
        long rows = userRepository.count();
        userRepository.deleteAll();
        return rows;
    }
}
