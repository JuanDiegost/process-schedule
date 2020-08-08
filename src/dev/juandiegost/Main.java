package dev.juandiegost;

import dev.juandiegost.dao.*;
import dev.juandiegost.models.Process;
import dev.juandiegost.utils.ColouredSystemOutPrintln;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        List<Process> processes = new ArrayList<>();
        Scanner sn = new Scanner(System.in);
        boolean salir = false;
        int opcion; //Guardaremos la opcion del usuario

        while (!salir) {

            System.out.println("1. Agregar un procceso");
            System.out.println("2. Simular FCFS");
            System.out.println("3. SJF");
            System.out.println("4. Prioridad");
            System.out.println("5. Round Robin");
            System.out.println(ColouredSystemOutPrintln.ANSI_RED + "0. Salir" + ColouredSystemOutPrintln.ANSI_RESET);

            try {

                System.out.println("Escribe una de las opciones");
                opcion = sn.nextInt();

                switch (opcion) {
                    case 1:
                        System.out.println("Nombre: ");
                        String name = sn.next();
                        System.out.println("Número de ticks de CPU a atender: ");
                        int ticks = sn.nextInt();
                        System.out.println("Tiempo de llegada: ");
                        int arrival = sn.nextInt();
                        System.out.println("Prioridad: ");
                        int priority = sn.nextInt();
                        processes.add(new Process(name, ticks, arrival, priority));
                        System.out.println("Proceso agregado ");
                        break;
                    case 2:
                        ProcessManager fcs = new FCFS();
                        fcs.addProcessList(processes);
                        fcs.run();
                        break;
                    case 3:
                        ProcessManager sjf = new ShortestJobFirst();
                        sjf.addProcessList(processes);
                        sjf.run();
                        break;
                    case 4:
                        ProcessManager ip = new IterationPriority();
                        ip.addProcessList(processes);
                        ip.run();
                        break;
                    case 5:
                        System.out.println("Ingrese el Quantum: ");
                        int quantum = sn.nextInt();
                        ProcessManager rr = new RoundRobin(quantum);
                        rr.addProcessList(processes);
                        rr.run();
                        break;
                    case 0:
                        salir = true;
                        break;
                    default:
                        System.out.println("Solo números entre 1 y 4");
                }
            } catch (InputMismatchException e) {
                System.out.println("Debes insertar un número");
                sn.next();
            }
        }
    }


}
