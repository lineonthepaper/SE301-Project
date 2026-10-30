package org.svfrms.runnable;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;

import org.svfrms.request.ControlRequest;

public class ControlQueue implements Runnable {
    public static final int CAPACITY = 5;
    private static final ControlRequest STOP = new ControlRequest("STOP", null);
    private final Consumer<ControlRequest> executor;
    private final BlockingQueue<ControlRequest> queue = new ArrayBlockingQueue<>(CAPACITY);

    public ControlQueue(Consumer<ControlRequest> executor) {
        this.executor = executor;
    }

    public void addControlRequest(ControlRequest controlRequest) throws InterruptedException {
        System.out.println(controlRequest);
        for (ControlRequest r : queue) {
            System.out.println(r);
        }
        queue.put(controlRequest);
    }

    public void shutdown() throws InterruptedException {
        queue.put(STOP);
    }

    @Override
    public void run() {
        try {
            ControlRequest request;
            while ((request = queue.take()) != STOP) {
                executeSafely(request);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void executeSafely(ControlRequest request) {
        try {
            executor.accept(request);
        } catch (RuntimeException e) {
            System.out.println("Control request failed for " + request + ": " + e.getMessage());
        }
    }
}
