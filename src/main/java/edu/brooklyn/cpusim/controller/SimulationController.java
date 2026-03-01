package edu.brooklyn.cpusim.controller;

import edu.brooklyn.cpusim.dto.ScheduleRequest;
import edu.brooklyn.cpusim.dto.ScheduleResult;
import edu.brooklyn.cpusim.service.SchedulerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SimulationController {

    private final SchedulerService schedulerService;

    public SimulationController(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    @PostMapping("/simulate")
    public ResponseEntity<ScheduleResult> simulate(@RequestBody ScheduleRequest request) {
        ScheduleResult result = schedulerService.runSimulation(request);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
