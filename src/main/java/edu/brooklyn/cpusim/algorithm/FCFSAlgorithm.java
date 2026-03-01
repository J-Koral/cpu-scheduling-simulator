package edu.brooklyn.cpusim.algorithm;

import edu.brooklyn.cpusim.dto.ScheduleResult;
import edu.brooklyn.cpusim.model.GanttEntry;
import edu.brooklyn.cpusim.model.Process;
import edu.brooklyn.cpusim.model.ProcessResult;
import edu.brooklyn.cpusim.sorter.ProcessSorter;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FCFSAlgorithm implements AlgorithmStrategy {

    @Override
    public ScheduleResult runAlgorithm(List<Process> processes) {

        // We sort the processes my arrival time with my custom class.
        ProcessSorter.sortByArrivalTime(processes);

        List<GanttEntry> ganttChart = new ArrayList<>();
        List<ProcessResult> processTable = new ArrayList<>();

        int curr = 0;
        int end = 0;
        for(Process process : processes) {
            String processId = process.getProcessId();
            int arrivalTime = process.getArrivalTime();
            int burstTime = process.getBurstTime();

            if(curr < arrivalTime) {
                curr = arrivalTime;
            }
            end = curr + burstTime;

            GanttEntry ganttEntry = new GanttEntry(processId, curr, end);
            ganttChart.add(ganttEntry);

            curr = end;

            int turnaroundTime = end - arrivalTime;
            int waitingTime = turnaroundTime - burstTime;

            ProcessResult processResult = new ProcessResult(processId, arrivalTime, burstTime, waitingTime, turnaroundTime);
            processTable.add(processResult);
        }

        double avgWaitingTime = processTable.stream()
                .mapToInt(process -> process.getWaitingTime())
                .average()
                .orElse(0);

        double averageTurnaroundTime = processTable.stream()
                .mapToInt(process -> process.getTurnaroundTime())
                .average()
                .orElse(0);

        double throughput = (double) processes.size() / end;

        return new ScheduleResult(ganttChart, processTable, avgWaitingTime, averageTurnaroundTime, throughput);
    }
}
