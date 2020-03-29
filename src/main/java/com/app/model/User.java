package com.app.model;

import com.app.exception.AppException;
import com.app.model.base.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    private String name;
    private String surname;
    private Integer age;
    private String username;
    private String email;
    private String password;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<Ticket> tickets;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<Movie> favouriteMovies;

    public Long addFavouriteMovie(Movie movie) {
        if (Objects.isNull(movie)) {
            throw new AppException("Movie is null");
        }
        favouriteMovies.add(movie);
        return movie.getId();
    }

    public void deleteFavouriteMovie(Movie movie) {
        if (Objects.isNull(movie)) {
            throw new AppException("Movie is null");
        }
        favouriteMovies.remove(movie);
    }


}
