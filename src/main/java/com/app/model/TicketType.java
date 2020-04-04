package com.app.model;

import com.app.model.base.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

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

    @OneToOne(cascade = CascadeType.PERSIST, mappedBy = "ticketType")
    private Ticket ticket;

}
