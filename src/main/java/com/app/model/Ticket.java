package com.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue
    private Long id;

    private Integer quantity;
    private Boolean reservation;

    @ManyToMany(mappedBy = "tickets")
    private Set<FilmShow> filmShows = new HashSet<>();

    @ManyToMany(mappedBy = "tickets")
    private Set<User> users = new HashSet<>();
}

