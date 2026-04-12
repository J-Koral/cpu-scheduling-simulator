package edu.brooklyn.cpusim.service;

import edu.brooklyn.cpusim.algorithm.AlgorithmStrategy;
import edu.brooklyn.cpusim.dto.ScheduleRequest;
import edu.brooklyn.cpusim.dto.ScheduleResult;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This service runs the CPU scheduling simulations.
 * The SchedulerService stores all the scheduling algorithms you can use.
 *
 * Each algorithm is stored in a hashmap where it can easily be looked up
 * using its name as the key and that allows the program to select the
 * appropriate strategy to run.
 *
 * So far, the only version it supports is FCFS, but this class easily be
 * updated to support the rest when the algorithms are implemented.
 */
@Service
public class SchedulerService {
    private final Map<String, AlgorithmStrategy> algorithms = new HashMap<>();

    /**
     * This is the constructor for the SchedulerService.
     * All the algorithm strategies will be assigned a key and can be looked up
     * when it's needed.
     *
     * @param strategies a list of all the scheduling algorithm implementations
     */
    public SchedulerService(List<AlgorithmStrategy> strategies) {
        for(AlgorithmStrategy strategy : strategies) {
            String key = getAlgorithmKey(strategy);
            algorithms.put(key, strategy);
        }
    }

    /**
     * Creates the appropriate string key for the algorithm passed.
     *
     * Right now, it only supports FCFS because it's the only algorithm implemented,
     * but it will be updated to support the SJF, SRTF, RR, and Priority algorithms.
     *
     * @param strategy the class of the algorithm implementation
     * @return the string key that will be used to store the algorithm strategy
     * in the hashmap.
     */
    private String getAlgorithmKey(AlgorithmStrategy strategy) {
        if (strategy.getClass().getSimpleName().contains("FCFS")) {
            return "FCFS";
        } if(strategy.getClass().getSimpleName().contains("SJF")) {
            return "SJF";
        } if(strategy.getClass().getSimpleName().contains("SRTF")) {
            return "SRTF";
        } if(strategy.getClass().getSimpleName().contains("RR")) {
            return "RR";
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Runs the scheduling simulation using the algorithm selected by the user along
     * with the list of processes.
     *
     * This method looks up the correct AlgorithmStrategy from the hashmap, runs the
     * algorithm, and returns the ScheduleResult object that has all the data to
     * create the Gantt chart and process table.
     *
     * @param request the simulation request that contains the processes with their
     *                arrival and burst times with the selected algorithm.
     * @return a ScheduleResult object that has the full simulation output.
     */
    public ScheduleResult runSimulation(ScheduleRequest request) {
        String algorithm = request.getAlgorithm();
        AlgorithmStrategy strategy = algorithms.get(algorithm);
        return strategy.runAlgorithm(request.getProcesses());
    }
}
