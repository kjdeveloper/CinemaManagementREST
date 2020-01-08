package com.app.model;

import com.app.model.enums.City;
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
@Table(name = "cinemas")
public class Cinema {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @Enumerated(EnumType.STRING)
    private City city;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "cinema")
    private Set<Repertoire> repertoires = new HashSet<>();

}
