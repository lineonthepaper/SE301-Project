package org.svfrms.runnable;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;

import org.svfrms.request.ControlRequest;
import org.svfrms.util.constants.ControlConstants;

public class ControlQueue implements Runnable {
    private static final ControlRequest STOP = new ControlRequest("STOP", null);
    private final Consumer<ControlRequest> executor;
    private final BlockingQueue<ControlRequest> queue = new ArrayBlockingQueue<>(ControlConstants.controlQueueSize);

    public ControlQueue(Consumer<ControlRequest> executor) {
        this.executor = executor;
    }

    public void addControlRequest(ControlRequest controlRequest) throws InterruptedException {
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
