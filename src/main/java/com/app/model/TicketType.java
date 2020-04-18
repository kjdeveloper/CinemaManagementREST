package com.app.model;

import com.app.model.base.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder

@Entity
@Table(name = "ticket_types")
public class TicketType extends BaseEntity {

    private String name;
    private BigDecimal price;

    @OneToMany(mappedBy = "ticketType")
    private Set<Ticket> tickets;

}
