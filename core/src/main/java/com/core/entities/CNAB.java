package com.core.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(schema = "todo", name = "TB_CNAB")
public class CNAB {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private LocalDate date;
    private double value;
    private String cpf;
    private String card;
    private LocalTime time;
    private String storeOwner;
    private String storeName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "transactions_cnab", nullable = false)
    private Transactions_CNAB Transactions_CNAB;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filecnab_id", nullable = false)
    private FIleCNAB fIleCNAB;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
