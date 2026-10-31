package org.svfrms.runnable;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.svfrms.request.Command;
import org.svfrms.util.constants.ControlConstants;

public class ControlQueue implements Runnable {
    private static final Command STOP = () -> {
    };
    private final BlockingQueue<Command> queue = new ArrayBlockingQueue<>(ControlConstants.controlQueueSize);

    public void addCommand(Command command) throws InterruptedException {
        queue.put(command);
    }

    public void shutdown() throws InterruptedException {
        queue.put(STOP);
    }

    @Override
    public void run() {
        try {
            Command command;
            while ((command = queue.take()) != STOP) {
                executeSafely(command);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void executeSafely(Command command) {
        try {
            command.execute();
        } catch (RuntimeException e) {
            System.out.println("Control request failed for " + command + ": " + e.getMessage());
        }
    }
}
