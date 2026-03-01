package edu.brooklyn.cpusim.sorter;

import edu.brooklyn.cpusim.model.Process;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProcessSorter {
    public static void sortByArrivalTime(List<Process> processes) {
        Collections.sort(processes, Comparator.comparing(Process::getArrivalTime));
    }
}
