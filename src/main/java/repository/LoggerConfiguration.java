package repository;

import model.Logger;
import model.Sink;
import enums.LogLevel;

import java.util.List;

public class LoggerConfiguration {
    private Logger logger;

    public LoggerConfiguration(Logger logger) {
        this.logger = logger;
    }

    public Logger getLogger() {
        return logger;
    }
}