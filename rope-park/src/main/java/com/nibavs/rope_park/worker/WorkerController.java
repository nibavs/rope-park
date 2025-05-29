package com.nibavs.rope_park.worker;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public record WorkerController(WorkerService workerService) {

    @PostMapping("/registerWorker")
    public String addWorker(@RequestBody WorkerRequest workerRequest) {
        workerService.registerWorker(workerRequest);
        return "success";
    }

    @GetMapping("/getWorkers")
    public List<Worker> getWorkers() {
        return workerService.getWorkers();
    }
}
