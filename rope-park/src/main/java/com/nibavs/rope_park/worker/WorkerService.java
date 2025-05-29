package com.nibavs.rope_park.worker;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record WorkerService(WorkerRepository workerRepository) {
    public void registerWorker(WorkerRequest request) {
        Worker customer = Worker.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .phoneNumber(request.phoneNumber())
                .build();

        workerRepository.save(customer);

    }

    public List<Worker> getWorkers() {
        return workerRepository.findAll();
    }

}
