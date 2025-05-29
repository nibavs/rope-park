package com.nibavs.rope_park.salary;

import com.nibavs.rope_park.Park;

import java.util.List;

public record ShiftDataRequest(List<WorkerShiftInfo> shiftInfos, Integer totalProfit, Park park) {
}
