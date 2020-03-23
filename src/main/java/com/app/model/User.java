package com.app.model;

import com.app.exception.AppException;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private Long id;

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
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Ticket> tickets;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Movie> favouriteMovies = new HashSet<>();

    public Long addFavouriteMovie(Movie movie) {
        if (movie == null) {
            throw new AppException("Movie is null");
        }
        favouriteMovies.add(movie);
        return movie.getId();
    }

    public void deleteFavouriteMovie(Movie movie) {
        if (movie == null) {
            throw new AppException("Movie is null");
        }
        favouriteMovies.remove(movie);
    }


}
