SDE III : Logger Library

Description:
You have to design and implement a logger library that applications can use to log messages.

Requirements:
Driver Application should be able to Initialize the Library and log messages to the desired sink.
Logger
Accepts messages from client(s)
A logger would have one or more sinks associated with it.
Supports defined message levels.
enriches message with current timestamp while directing message to a sink
Logger is initialised with a configuration eg:logger name, sink(s), buffer size.
Logger should support both sync and async logging.
For Async logger buffer size would determine the maximum inflight messages.
Messages must be ordered. Messages should reach the sink in the order they were sent.
Should support writes from multiple-threads.
There shouldn’t be any data loss.

Sink:
There can be various types of sink (file, stdout, database).  
Sink has a destination.
For this round you may create STDOUT sink, which would print the message to console.
Sink has an associate log level. Any messages level is lower than the sink level should be discarded.

Message
has content which is of type string
has a level associated with it

Log Level
DEBUG, INFO, WARN, ERROR, FATAL ; in order of priority. ERROR has higher priority than INFO.

Sending messages
Sink need not be mentioned while sending a message to the logger library.
You specify message content and level while sending a message
Logger configuration (see sample below)

Specifies all the details required to use the logger library.
Library can accept one or more configuration for an application
Example:
time format
logging level
sink type
Logger type sync/async
details required for sink (eg file location)); this depends on sink type.

Sample Config:
ts_format:dd-mm-yyyy-hh-mm-ss
log_level:INFO
logger_type:ASYNC
buffer_size:25
sink_type:STDOUT

Sample Output Log Entry
27-06-2019-09-30-00 [INFO] This is a sample log message.

Guidelines

Please use structures provided by the language of your choice.
Do not use any external database, queue or NoSQL store, use in-memory data-structures only.
Usage of any logging library as a part of implementation is prohibited.
Write a driver class for the demo purpose. This will execute all the commands at one place in the code and test cases. Do not take any input from console/file/etc
Please prioritize code compilation, execution and completion.
Work on the expected output first and then add good-to-have features of your own.


Expectations

Make sure that you have a working and demonstrable code
Make sure that code is functionally correct
Make sure concurrent requests are handled appropriately
Code should be modular and readable
Separation of concern should be addressed
Code should easily accommodate new requirements with minimal changes
Code should be easily testable



