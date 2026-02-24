package edu.brooklyn.cpusim.dto;

import edu.brooklyn.cpusim.model.Process;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ScheduleRequest {
    private String algorithm;
    private int quantum;
    private List<Process> processes;
}
