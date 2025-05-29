package com.nibavs.rope_park.worker;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Worker {
    @Id
    @SequenceGenerator(
            name = "worker_id_sequence",
            sequenceName = "worker_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "worker_id_sequence"
    )
    private Integer id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private boolean senior;

}
