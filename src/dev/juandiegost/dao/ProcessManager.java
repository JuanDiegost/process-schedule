package dev.juandiegost.dao;

import dev.juandiegost.models.Process;
import dev.juandiegost.utils.ColouredSystemOutPrintln;

import java.util.*;

public abstract class ProcessManager {

    /**
     * Lista de procesos
     */
    protected HashMap<Integer, Process> processList;
    /**
     * Lista de procesos
     */
    protected Stack<Integer> history;
    /**
     * Fila que tiene los proceso que esperan su ejecución
     */
    protected List<Process> processesQueue;

    /**
     * Lista de todos los procesos que se deven ejecutar
     */
    protected List<Process> processestoExecute;

    /**
     * Obtiene el proximo proceso que deve ejecutarse
     *
     * @return Proceso que deve ejecutarse
     */
    abstract Process getNextProcess(int tick);

    /**
     * Calcula cuanto tiepo debe ejecutarse determinado proceso
     *
     * @param process el procceso con el tiempo a calcular
     */
    private void executeProcess(Process process) {
        process.setTicks(process.getTicks() - 1);
    }

    ProcessManager() {
        this.processList = new HashMap<>();
        this.processesQueue = new ArrayList<>();
        this.history = new Stack<>();
    }

    /**
     * Ejecuta la simulación
     */
    public void run() {
        this.preRun();
        int tick = 0;
        while (!this.isEndProcess()) {
            this.updateQueue(tick);
            Process process = this.getNextProcess(tick);
            if (process == null) {
                System.out.println("Sin procesos " + tick);
                this.history.push(0);
            } else {
                System.out.println("Tick " + tick + " -> " + process.toString());
                this.executeProcess(process);
                this.history.push(process.getId());
            }
            tick++;
        }
        this.printList();
        this.printTable();
    }

    /**
     * Asigna los procesos a la lista de simulación
     */
    private void preRun() {
        this.processestoExecute = new ArrayList<>();
        for (Process process :
                processList.values()) {
            try {
                this.processestoExecute.add((Process) process.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Actualiza la lista de espera para ejecutar
     *
     * @param tick en el cual se esta
     */
    private void updateQueue(int tick) {
        for (Process process : processestoExecute) {
            if (process.getArrivalTime() == tick) {
                this.processesQueue.add(process);
            }
        }
        //Elimina de la cola los procesos finalizados
        this.processesQueue.removeIf(process -> process.getTicks() == 0);
    }

    /**
     * Avalua si todos los procesos han terminado
     *
     * @return true si todos los proceso terminaro y false si no
     */
    private boolean isEndProcess() {
        for (Process process : processestoExecute) {
            if (process.getTicks() > 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Agrega un proceso a la fila de ejecución
     *
     * @param process proceso a se agrgado
     */
    private void addQuequeProcces(Process process) {
        this.processesQueue.add(process);
    }

    /**
     * Agrega un proceso a la simulación
     *
     * @param process Proceso a agregar
     */
    public void addProcess(Process process) {
        this.processList.put(process.getId(), process);
    }

    /**
     * Agrega un proceso a la simulación
     *
     * @param process Proceso a agregar
     */
    public void addProcessList(List<Process> process) {
        for (Process pr:process
             ) {
            this.processList.put(pr.getId(),pr);
        }
    }

    /**
     * Obtiene un proceso por ID
     *
     * @param id del proceso que se quiere obtener
     * @return el proceso buscado
     */
    private Process getProcessById(int id) {
        try {
            return this.processList.get(id);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Elimina un proceso de la simulación
     *
     * @param id id del proceso a eliminar
     * @return el proceso eliminado, 'por si las mosacas XD'
     */
    public Process deleteProcess(int id) {
        return this.processList.remove(id);
    }

    private void printList() {
        System.out.println(ColouredSystemOutPrintln.ANSI_BRIGHT_GREEN + "----------------------------------------" + ColouredSystemOutPrintln.ANSI_RESET);
        System.out.println(ColouredSystemOutPrintln.ANSI_BRIGHT_BLUE + "Simulación" + ColouredSystemOutPrintln.ANSI_RESET);
        System.out.println();
        System.out.println("Leyenda");

        for (Process p : processList.values()) {
            System.out.print(ColouredSystemOutPrintln.ANSI_BLACK + ColouredSystemOutPrintln.BACKGROUNDS[p.getId()] + " " + p.getName() + "  " + ColouredSystemOutPrintln.ANSI_RESET + System.getProperty("line.separator"));
        }
        System.out.println("Resultado");
        int i = 0;
        String text = "";
        for (
                Integer integer :
                history) {
            Process p = this.getProcessById(integer);
            i++;
            if (p == null) {
                text += "   ";
            } else {
                text += ColouredSystemOutPrintln.ANSI_BLACK + ColouredSystemOutPrintln.BACKGROUNDS[p.getId()] + " " + i + " ";
            }
            text += ColouredSystemOutPrintln.ANSI_RESET;
        }
        System.out.println(text);
    }

    private void printTable() {
        double pT = 0;
        double pE = 0;
        double pP = 0;
        int i = 0;
        for (Process process :
                processList.values()) {
            int start = this.getStartProcesses(process.getId());
            int end = this.getEndProcesses(process.getId());
            int arrival = process.getArrivalTime();
            double t = (end - arrival);
            double e = (end - arrival - process.getTicks());
            double p = t / process.getTicks();
            pT += t;
            pE += e;
            pP += p;

            System.out.println("Proceso: " + process.getName() + " Inicio " + start + " Fin " + end + " T. llegada  " + arrival + " Tiempo de respuesta  " + t + " Tiempo en espera " + e + " Proporción de penalización " + p);
            i++;
        }
        System.out.println();
        System.out.println("Promedio Tiempo de respuesta: " + (pT / i) + " Promedio Tiempo en espera " + (pE / i) + " Promedio Proporción de penalización " + (pP / i));
    }

    private int getStartProcesses(int id) {
        for (int i = 0; i < history.size(); i++) {
            if (history.get(i) == id) {
                return i;
            }
        }
        return -1;
    }

    private int getEndProcesses(int id) {
        for (int i = history.size() - 1; i > 0; i--) {
            if (history.get(i) == id) {
                return i + 1;
            }
        }
        return -1;
    }
}
