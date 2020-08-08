package dev.juandiegost.models;

public class Process implements Cloneable {
    public static int ID_COUNT;
    private int id;
    private String name;
    private int ticks;
    private int arrivalTime;
    private int priority;

    public Process(String name, int ticks, int arrivalTime, int priority) {
        this.id = ++ID_COUNT;
        this.name = name;
        this.ticks = ticks;
        this.arrivalTime = arrivalTime;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTicks() {
        return ticks;
    }

    public void setTicks(int ticks) {
        this.ticks = ticks;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return this.ticks > 1 ? "Ejecuta Proceso " +
                name : "Ejecuta Proceso " +
                name + " y Termina";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
