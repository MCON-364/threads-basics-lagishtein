package edu.touro.mcon364.concurrency.lesson1.exercises;

import edu.touro.mcon364.concurrency.common.model.Priority;
import edu.touro.mcon364.concurrency.common.model.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Exercise 5: Creating threads with a Runnable passed as a lambda
 *
 * The lesson showed four equivalent ways to write a Runnable.
 * This exercise practises the lambda style — the most common modern form:
 *
 *   Runnable r = () -> { ... };
 *   Thread t = new Thread(r, "thread-name");
 *   t.start();
 *
 * Or inline:
 *   Thread t = new Thread(() -> { ... }, "thread-name");
 *
 * Your tasks:
 *
 * (A) launchLoggerThread(List<String> log, String message)
 *     Create a Runnable AS A LAMBDA that appends {@code message} to {@code log},
 *     wrap it in a Thread named "logger", start it, and join it before returning.
 *
 * (B) launchTwoCounterThreads(List<Task> tasks, List<String> threadNames)
 *     Launch exactly two threads using INLINE lambda syntax:
 *       - "counter-a" iterates through tasks and counts those with Priority.HIGH,
 *         storing the result in highCount.
 *       - "counter-b" iterates through tasks and counts those with Priority.LOW,
 *         storing the result in lowCount.
 *     Start both threads, then join both before returning.
 *
 * Do NOT use an anonymous class or a named class — lambdas only.
 */
public class LambdaRunnableExercise {

    // Written by (A)
    private String loggedMessage;

    // Written by (B)
    private int highCount;
    private int lowCount;

    /**
     * (A) Create a lambda Runnable that appends {@code message} to {@code log},
     * wrap it in a Thread named "logger", start it, and join it.
     */
    public void launchLoggerThread(List<String> log, String message) throws InterruptedException {
        // TODO: create a Runnable lambda, pass it to new Thread(..., "logger"),
        //       start the thread, join it, and store the message in loggedMessage.
        Runnable loggerRunnable = () -> {
            log.add(message);
        };
        Thread logger = new Thread(loggerRunnable, "logger");
        logger.start();
        logger.join();
        this.loggedMessage = message;
    }

    /**
     * (B) Launch two threads with inline lambda syntax.
     * "counter-a" counts HIGH-priority tasks → stored in highCount.
     * "counter-b" counts LOW-priority tasks  → stored in lowCount.
     * Start both, then join both before returning.
     */
    public void launchTwoCounterThreads(List<Task> tasks) throws InterruptedException {
        // TODO: create two threads using inline lambda syntax, start both,
        //       join both, and store results in highCount and lowCount.

        Runnable countHigh = () ->
            this.highCount = (int)tasks.stream().filter(task -> task.priority() == Priority.HIGH).count();

        Thread highCounter = new Thread(countHigh, "counter-a");
        highCounter.start();
        highCounter.join();
        Runnable countLow  = () ->
                this.lowCount = (int)tasks.stream().filter(task -> task.priority().equals(Priority.LOW)).count();
        Thread lowCounter = new Thread(countLow, "counter-b");
        lowCounter.start();
        lowCounter.join();
    }

    public String getLoggedMessage() { return loggedMessage; }
    public int getHighCount()        { return highCount; }
    public int getLowCount()         { return lowCount; }
}

