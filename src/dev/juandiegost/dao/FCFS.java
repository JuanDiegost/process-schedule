package dev.juandiegost.dao;

import dev.juandiegost.models.Process;

import java.util.List;

public class FCFS extends ProcessManager{


    @Override
    Process getNextProcess(int tick) {
        if(this.processesQueue.isEmpty())
            return null;
        Process processRt=this.processesQueue.get(0);
        for (Process process:this.processesQueue ) {
            if(processRt.getArrivalTime()>process.getArrivalTime()){
                processRt=process;
            }
        }
        return processRt;
    }

}
