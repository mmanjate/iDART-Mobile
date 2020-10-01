package mz.org.fgh.idartlite.rest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorThreadProvider {

    private static final int NUMBER_OF_THREADS = 4;

    private ExecutorService executorService;

    private static ExecutorThreadProvider instance;

    private ExecutorThreadProvider() {
        executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    }

    public static ExecutorThreadProvider getInstance(){
        if (instance == null) {
            instance = new ExecutorThreadProvider();
        }
        return instance;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }
}
