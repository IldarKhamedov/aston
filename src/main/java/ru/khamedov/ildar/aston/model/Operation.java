package ru.khamedov.ildar.aston.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Date;

@Entity
@Getter
@Setter
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long amount;

    @Basic(optional = false)
    private Date date= Date.from(Instant.now());

    @ManyToOne(optional = false)
    private Account from;

    @ManyToOne
    private Account to;

    @Enumerated(EnumType.STRING)
    @Basic(optional = false)
    private OperationType operationType;


}
