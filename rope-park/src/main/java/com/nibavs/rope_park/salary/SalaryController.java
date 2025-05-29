package com.nibavs.rope_park.salary;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public record SalaryController(SalaryService salaryService) {
    @PostMapping("/salary")
    public ResponseEntity<String> salary(@RequestBody ShiftDataRequest shiftDataRequest) {
        return salaryService.calculateSalaries(shiftDataRequest);
    }
}
