package com.nibavs.rope_park.salary;

import com.nibavs.rope_park.Park;
import com.nibavs.rope_park.worker.Worker;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
public class SalaryService {
    SalaryHistoryRepository salaryHistoryRepository;

    public SalaryService(SalaryHistoryRepository salaryHistoryRepository) {
        this.salaryHistoryRepository = salaryHistoryRepository;
    }

    public ResponseEntity<String> calculateSalaries(ShiftDataRequest request) {
        List<WorkerShiftInfo> shifts = request.shiftInfos();
        shifts.forEach(shift -> System.out.println(shift.worker()));
        Park park = request.park();

        int fixedRate = (park == Park.PERVOM || park == Park.ZVEZDA || park == Park.BUGRINKA) ? 1000 : 700;

        List<Integer> profitPoints = getProfitPoints(shifts);

        List<ProfitInterval> intervals = getIntervals(profitPoints);

        Map<Worker, Double> workerBonuses = calculateWorkerBonuses(shifts, intervals);

        workerBonuses.forEach((worker, bonus) -> {
            System.out.println(worker);
            int seniorBonus = worker.isSenior() ? 500 : 0;
            int finalSalary = (int) Math.round(bonus + fixedRate + seniorBonus);

            salaryHistoryRepository.save(SalaryHistory.builder()
                            .salary(finalSalary)
                            .earningDate(LocalDate.now())
                            .workerId(worker.getId())
                            .park(park)
                            .build());
        });


        return ResponseEntity.ok("Salaries are calculated and added to history");
    }

    private List<Integer> getProfitPoints(List<WorkerShiftInfo> shifts) {
        return shifts.stream()
                .flatMap(shift -> Stream.of(shift.startTotal(), shift.endTotal()))
                .distinct()
                .sorted()
                .toList();
    }

    private List<ProfitInterval> getIntervals(List<Integer> profitPoints) {
        List<ProfitInterval> intervals = new ArrayList<>();
        for (int i = 0; i < profitPoints.size() - 1; i++) {
            int start = profitPoints.get(i);
            int end = profitPoints.get(i + 1);
            intervals.add(new ProfitInterval(start, end));
        }
        return intervals;
    }

    private Map<Worker, Double> calculateWorkerBonuses(List<WorkerShiftInfo> shifts, List<ProfitInterval> intervals) {
        Map<Worker, Double> workerBonuses = new HashMap<>();
        for (ProfitInterval interval : intervals) {
            List<Worker> activeWorkers = shifts.stream()
                    .filter(shift -> shift.startTotal() <= interval.start
                            && shift.endTotal() >= interval.end)
                    .map(WorkerShiftInfo::worker)
                    .toList();

            if (activeWorkers.isEmpty()) continue;

            int intervalProfit = interval.end - interval.start;
            double intervalBonus = intervalProfit / 10.0;
            double intervalBonusPerWorker = intervalBonus / activeWorkers.size();
            activeWorkers.forEach(worker ->
                    workerBonuses.merge(worker, intervalBonusPerWorker, Double::sum));
        }
        return workerBonuses;
    }

    private record ProfitInterval(int start, int end) {}
}


