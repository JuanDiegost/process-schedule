package dev.juandiegost.dao;

import dev.juandiegost.models.Process;

public class ShortestJobFirst extends ProcessManager{
    @Override
    Process getNextProcess(int tick) {
        if(this.processesQueue.isEmpty())
            return null;
        Process processRt=this.processesQueue.get(0);
        if(!this.history.isEmpty())
            if(processRt.getId()==this.history.peek()){
            return processRt;
        }
        for (Process process:this.processesQueue ) {
            if(processRt.getTicks()>process.getTicks()){
                processRt=process;
            }
        }
        return processRt;
    }
}
