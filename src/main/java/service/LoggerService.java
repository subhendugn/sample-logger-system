package service;

import model.Logger;
import model.Sink;
import enums.LogLevel;
import repository.LoggerConfiguration;
import util.FileUtil;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LoggerService {
    // Logger configuration instance
    private Logger loggerConfiguration;

    // Constructor to initialize LoggerService with LoggerConfiguration
    public LoggerService(LoggerConfiguration loggerConfig) {
        this.loggerConfiguration = loggerConfig.getLogger();
    }

    // Queue to hold messages for asynchronous logging
    private Queue<String> messageQueue = new ConcurrentLinkedQueue<>();

    // Method to log a message with a specific log level
    public void logMessage(String messageContent, LogLevel logLevel) {
        List<LogLevel> logLevelList = Arrays.asList(LogLevel.values());

        // Iterate through each sink in the logger configuration
        for (Sink sink : loggerConfiguration.getSinks()) {
            // Skip logging if the log level is lower than the sink's log level
            if (logLevelList.indexOf(logLevel) < logLevelList.indexOf(sink.getLogLevel())) {
                continue;
            }

            synchronized (this) {
                // Synchronous logging
                if (loggerConfiguration.getLoggerType().equals("SYNC")) {
                    logToSink(sink, messageContent, logLevel);
                } else {
                    // Asynchronous logging
                    if (messageQueue.size() <= loggerConfiguration.getBufferSize()) {
                        messageQueue.add(messageContent);
                        logToSink(sink, messageQueue.poll(), logLevel);
                    }
                }
            }
        }
    }

    // Convenience methods for logging messages with specific log levels
    public void info(String messageContent) {
        logMessage(messageContent, LogLevel.INFO);
    }

    public void debug(String messageContent) {
        logMessage(messageContent, LogLevel.DEBUG);
    }

    public void warn(String messageContent) {
        logMessage(messageContent, LogLevel.WARN);
    }

    public void error(String messageContent) {
        logMessage(messageContent, LogLevel.ERROR);
    }

    public void fatal(String messageContent) {
        logMessage(messageContent, LogLevel.FATAL);
    }

    // Method to log a formatted message to a specific sink
    private void logToSink(Sink sink, String messageContent, LogLevel logLevel) {
        String formattedMessage = messageFormatter(messageContent, logLevel);
        switch (sink.getSinkType()) {
            case STDOUT:
                System.out.println(formattedMessage);
                break;
            case FILE:
                FileUtil.writeOutputToFile(formattedMessage);
                break;
            case DATABASE:
                // Implement database logging if needed
                break;
            default:
                System.out.println("INVALID INPUT");
        }
    }

    // Method to generate the current timestamp using the Logger's date-time utility
    private String generateDate() {
        return loggerConfiguration.getCurrentTimestamp();
    }

    // Method to format the log message with the timestamp and log level
    private String messageFormatter(String messageContent, LogLevel logLevel) {
        String formattedDate = generateDate();
        return String.format("%s\t [%s] \t%s", formattedDate, logLevel, messageContent);
    }
}