package edu.brooklyn.cpusim.service;

import edu.brooklyn.cpusim.algorithm.AlgorithmStrategy;
import edu.brooklyn.cpusim.dto.ScheduleRequest;
import edu.brooklyn.cpusim.dto.ScheduleResult;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SchedulerService {
    private final Map<String, AlgorithmStrategy> algorithms = new HashMap<>();

    public SchedulerService(List<AlgorithmStrategy> strategies) {
        for(AlgorithmStrategy strategy : strategies) {
            String key = getAlgorithmKey(strategy);
            algorithms.put(key, strategy);
        }
    }

    private String getAlgorithmKey(AlgorithmStrategy strategy) {
        if (strategy.getClass().getSimpleName().contains("FCFS")) {
            return "FCFS";
        } else {
            throw new IllegalArgumentException();
        }
    }

    public ScheduleResult runSimulation(ScheduleRequest request) {
        String algorithm = request.getAlgorithm();
        AlgorithmStrategy strategy = algorithms.get(algorithm);
        return strategy.runAlgorithm(request.getProcesses());
    }
}
