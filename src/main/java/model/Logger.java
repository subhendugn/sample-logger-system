package model;

import enums.LoggerType;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Logger {
    String loggerName;
    List<Sink> sinks;
    int bufferSize;
    LoggerType loggerType;
    String tsFormat;

    public LoggerType getLoggerType() {
        return loggerType;
    }

    public void setLoggerType(LoggerType loggerType) {
        this.loggerType = loggerType;
    }

    public String getLoggerName() {
        return loggerName;
    }

    public void setLoggerName(String loggerName) {
        this.loggerName = loggerName;
    }

    public List<Sink> getSinks() {
        return sinks;
    }

    public void setSinks(List<Sink> sinks) {
        this.sinks = sinks;
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public String getTsFormat() {
        return tsFormat;
    }

    public void setTsFormat(String tsFormat) {
        this.tsFormat = tsFormat;
    }

    public Logger(String loggerName, List<Sink> sinks, int bufferSize, LoggerType loggerType, String tsFormat) {
        this.loggerName = loggerName;
        this.sinks = sinks;
        this.bufferSize = bufferSize;
        this.loggerType = loggerType;
        this.tsFormat = tsFormat;
    }

    public String getCurrentTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(tsFormat);
        return LocalDateTime.now().format(formatter);
    }
}