package edu.brooklyn.cpusim.algorithm;

import edu.brooklyn.cpusim.dto.ScheduleResult;
import edu.brooklyn.cpusim.model.Process;

import java.util.List;

public interface AlgorithmStrategy {
    ScheduleResult runAlgorithm(List<Process> processes);
}
