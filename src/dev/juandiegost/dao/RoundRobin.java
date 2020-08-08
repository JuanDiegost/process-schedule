package dev.juandiegost.dao;

import dev.juandiegost.models.Process;

public class RoundRobin extends ProcessManager {

    private final int tickMax;

    public RoundRobin(int tick) {
        this.tickMax = tick;
    }

    @Override
    Process getNextProcess(int tick) {
        if (this.processesQueue.isEmpty())
            return null;
        Process processRt = this.processesQueue.get(0);
        if (this.history.toArray().length > this.tickMax) {
            for (int i = this.history.toArray().length - this.tickMax; i < this.history.toArray().length; i++) {
                if ((int) this.history.toArray()[i] != processRt.getId()) {
                    return processRt;
                }
            }

            if (this.processesQueue.size() > 1) {
                Process removeProcesse = this.processesQueue.remove(0);
                this.processesQueue.add(removeProcesse);
                processRt = this.processesQueue.get(0);
                for (Process process : this.processesQueue) {
                    if (processRt.getArrivalTime() > process.getArrivalTime() && removeProcesse.getId() != process.getId()) {
                        processRt = process;
                    }
                }
            }
        }
        return processRt;
    }
}
