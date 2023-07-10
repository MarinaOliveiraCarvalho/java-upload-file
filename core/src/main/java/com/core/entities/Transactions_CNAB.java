package com.core.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(schema = "todo", name = "TRANSACTIONS_CNAB")
public class Transactions_CNAB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(nullable = false)
    private String description;
    @NotNull
    @Column(nullable = false)
    private String nature;
    @NotNull
    @Column(nullable = false)
    private String signal;
}
