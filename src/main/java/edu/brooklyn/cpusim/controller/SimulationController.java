package edu.brooklyn.cpusim.controller;

import edu.brooklyn.cpusim.dto.ScheduleRequest;
import edu.brooklyn.cpusim.dto.ScheduleResult;
import edu.brooklyn.cpusim.service.SchedulerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This controller class handles all the API requests related to running the CPU scheduling simulations.
 * It exposes the POST endpoint /api/simulate that accepts a list of processes and the name of the
 * scheduling algorithm the user selected on the frontend.
 */
@RestController
@RequestMapping("/api")
public class SimulationController {

    private final SchedulerService schedulerService;

    /**
     * Constructor for the SimulationController
     * @param schedulerService the service that contains all CPU scheduling algorithm logic
     */
    public SimulationController(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    /**
     * Runs a CPU scheduling simulation using the data sent from the frontend
     *
     * This method takes a JSON body that has a list of processes with their
     * arrival and burst times, and the name of the scheduling algorithm to run
     *
     * This method passes this information to the SchedulerService and that service
     * performs the actual scheduling and returns a ScheduleResult object.
     *
     * @param request the simulation request sent from the frontend, containing the
     *                process information and the selected algorithm
     * @return a ResponseEntity that has the ScheduleResult object and an HTTP 200 status
     */
    @PostMapping("/simulate")
    public ResponseEntity<ScheduleResult> simulate(@RequestBody ScheduleRequest request) {
        ScheduleResult result = schedulerService.runSimulation(request);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
