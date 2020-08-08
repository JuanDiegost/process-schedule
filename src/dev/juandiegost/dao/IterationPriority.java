package dev.juandiegost.dao;

import dev.juandiegost.models.Process;

public class IterationPriority extends ProcessManager {
    @Override
    Process getNextProcess(int tick) {
        Process processRt=this.processesQueue.get(0);
        if(!this.history.isEmpty())
        if(processRt.getId()==this.history.peek()){
            return processRt;
        }
        for (Process process:this.processesQueue ) {
            if(processRt.getPriority()>process.getPriority()){
                processRt=process;
            }
        }
        return processRt;
    }
}
