import model.Logger;
import model.Sink;
import enums.LogLevel;
import enums.LoggerType;
import enums.SinkType;
import repository.LoggerConfiguration;
import service.LoggerService;
import util.FileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Application {
    private static final int THREAD_POOL_SIZE = 10; // Define the size of the thread pool
    private static final int TASK_COUNT = 100; // Define the number of logging tasks

    public static void main(String[] args) {
        // Create LoggerService instances for both ASYNC and SYNC loggers
        LoggerService asyncLoggerService = getLoggerService(LoggerType.ASYNC);
        LoggerService syncLoggerService = getLoggerService(LoggerType.SYNC);

        // Create a fixed thread pool to handle concurrent logging tasks
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        try {
            System.out.println("ASYNC Logger:");
            performAsyncLogging(asyncLoggerService, executorService);

            System.out.println("SYNC Logger:");
            performSyncLogging(syncLoggerService);
        } catch (RuntimeException e) {
            System.out.println(e);
        } finally {
            executorService.shutdown(); // Shut down the ExecutorService gracefully
        }
    }

    private static void performAsyncLogging(LoggerService asyncLoggerService, ExecutorService executorService) {
        // Submit multiple logging tasks to the ExecutorService for asynchronous logging
        for (int i = 0; i < TASK_COUNT; i++) {
            int finalI = i;
            executorService.submit(() -> {
                asyncLoggerService.info("Info message " + finalI + " -- ASYNC");
                asyncLoggerService.debug("Debug message " + finalI + " -- ASYNC");
                asyncLoggerService.warn("Warning message " + finalI + " -- ASYNC");
                asyncLoggerService.error("Error message " + finalI + " -- ASYNC");
                asyncLoggerService.fatal("Fatal message " + finalI + " -- ASYNC");
                try {
                    TimeUnit.MILLISECONDS.sleep(100); // Introduce delay between log messages
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
    }

    private static void performSyncLogging(LoggerService syncLoggerService) {
        // Log messages synchronously with a delay between each message
        for (int i = 0; i < TASK_COUNT; i++) {
            syncLoggerService.info("Info message " + i);
            syncLoggerService.debug("Debug message " + i);
            syncLoggerService.warn("Warning message " + i);
            syncLoggerService.error("Error message " + i);
            syncLoggerService.fatal("Fatal message " + i);
            try {
                TimeUnit.MILLISECONDS.sleep(100); // Introduce delay between log messages
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private static LoggerService getLoggerService(LoggerType loggerType) {
        // Create Sink instances for file and console logging
        Sink fileSink = new Sink(SinkType.FILE, LogLevel.ERROR);
        Sink consoleSink = new Sink(SinkType.STDOUT, LogLevel.DEBUG);

        // Add the sinks to a list
        List<Sink> sinkList = new ArrayList<>();
        sinkList.add(fileSink);
        sinkList.add(consoleSink);

        // Read the timestamp format from the XML configuration file
        String tsFormat = FileUtil.getTimestampFormat("src/main/resources/config.xml");

        // Create a LoggerConfiguration instance with the specified logger type and timestamp format
        LoggerConfiguration loggerConfiguration = new LoggerConfiguration(new Logger("testLogger", sinkList, 2, loggerType, tsFormat));

        // Return a new LoggerService instance with the created LoggerConfiguration
        return new LoggerService(loggerConfiguration);
    }
}