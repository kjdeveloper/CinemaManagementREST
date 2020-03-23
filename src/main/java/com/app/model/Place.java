/*
package com.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "places")
public class Place {

    @Id
    @GeneratedValue
    private Long id;

    private Boolean available;
    private Integer row;
    private Integer number;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cinemaHall_id")
    private CinemaHall cinemaHall;
}
*/
