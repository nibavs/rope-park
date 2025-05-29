package com.nibavs.rope_park.salary;

import com.nibavs.rope_park.Park;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SalaryHistory {
    @Id
    @SequenceGenerator(
            name = "salary_history_id_sequence",
            sequenceName = "salary_history_id_sequence"
            )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "salary_history_id_sequence"
    )
    private Integer id;
    private Integer salary;
    private LocalDate earningDate;
    private Integer workerId;
    private Park park;
}
