package edu.brooklyn.cpusim;

import edu.brooklyn.cpusim.algorithm.FCFSAlgorithm;
import edu.brooklyn.cpusim.dto.ScheduleResult;
import org.junit.jupiter.api.Test;
import edu.brooklyn.cpusim.model.Process;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Basic unit tests for the FCFSAlgorithm class.
 * These tests check that the algorithm produces the correct
 * Gantt chart order and correct waiting/turnaround times.
 *
 * This is written in a simple beginner‑friendly style.
 */
public class FCFSAlgorithmTest {

    @Test
    public void testFCFSSimpleCase() {
        // Create example processes
        List<Process> processes = new ArrayList<>();
        processes.add(new Process("P1", 0, 5, 0));
        processes.add(new Process("P2", 2, 5, 0));
        processes.add(new Process("P3", 1, 8, 0));
        processes.add(new Process("P4", 8, 3, 0));

        // Run FCFS
        FCFSAlgorithm fcfs = new FCFSAlgorithm();
        ScheduleResult result = fcfs.runAlgorithm(processes);

        // Check Gantt chart order
        assertEquals("P1", result.getGanttChart().get(0).getProcessId());
        assertEquals("P3", result.getGanttChart().get(1).getProcessId());
        assertEquals("P2", result.getGanttChart().get(2).getProcessId());
        assertEquals("P4", result.getGanttChart().get(3).getProcessId());

        // Check start/end times
        assertEquals(0, result.getGanttChart().get(0).getStart());
        assertEquals(5, result.getGanttChart().get(0).getEnd());

        assertEquals(5, result.getGanttChart().get(1).getStart());
        assertEquals(13, result.getGanttChart().get(1).getEnd());

        assertEquals(13, result.getGanttChart().get(2).getStart());
        assertEquals(18, result.getGanttChart().get(2).getEnd());

        assertEquals(18, result.getGanttChart().get(3).getStart());
        assertEquals(21, result.getGanttChart().get(3).getEnd());

        // Check average waiting time
        assertEquals(6.25, result.getAvgWaitingTime(), 0.01);

        // Check average turnaround time
        assertEquals(11.5, result.getAvgTurnaroundTime(), 0.01);

        // Check throughput
        assertEquals(0.190, result.getThroughput(), 0.01);
    }
}
