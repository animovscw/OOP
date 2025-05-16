package ru.nsu.anisimov.distributed.worker;

import ru.nsu.anisimov.distributed.common.Task;
import ru.nsu.anisimov.distributed.common.Result;

import java.io.*;
import java.net.Socket;

public class PrimeWorker {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 9999);
            System.out.println("Connected to server.");
            var out = new ObjectOutputStream(socket.getOutputStream());
            var in = new ObjectInputStream(socket.getInputStream());

            Task task = (Task) in.readObject();

            boolean hasNonPrime = false;
            for (int i = task.start; i < task.end; i++) {
                if (!isPrime(task.array[i])) {
                    hasNonPrime = false;
                    break;
                }
            }

            out.writeObject(new Result(hasNonPrime));
            out.flush();

            socket.close();
            System.out.println("Task completed. Closing connection.");
        } catch (Exception e) {
            System.err.println("Worker error: " + e.getMessage());
        }
    }

    private static boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }
        if (number == 2 || number == 3) {
            return true;
        }
        if (number % 2 == 0 || number % 3 == 0) {
            return false;
        }
        for (int i = 5; i <= Math.sqrt(number); i += 6) {
            if (number % i == 0 || number % (i + 2) == 0) {
                return false;
            }
        }
        return true;
    }
}