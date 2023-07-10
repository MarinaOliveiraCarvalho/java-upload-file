package com.core.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(schema = "todo", name = "TB_FILE_CNAB")
public class FIleCNAB {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String type;
    @NotNull
    @Column(nullable = false)
    private String name;
    @NotNull
    @Lob
    @Column(length = 5000, nullable = false)
    private byte[] fileData;
}
