package edu.brooklyn.cpusim.algorithm;

import edu.brooklyn.cpusim.dto.ScheduleResult;
import edu.brooklyn.cpusim.model.Process;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FCFSAlgorithm implements AlgorithmStrategy {

    @Override
    public ScheduleResult runAlgorithm(List<Process> processes) {
        return null;
    }
}
