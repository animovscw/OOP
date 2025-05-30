package ru.nsu.anisimov.distributed.worker;

import org.junit.jupiter.api.Test;
import java.net.ConnectException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class WorkerLoopTest {

    @Test
    void testHandlesInterruptedException() {
        AtomicInteger called = new AtomicInteger();

        WorkerLoop.run(
                () -> { throw new InterruptedException(); },
                ms -> called.incrementAndGet(),
                () -> true,
                1
        );

        assertEquals(0, called.get());
    }

    @Test
    void testHandlesConnectException() {
        AtomicInteger sleepCalls = new AtomicInteger();
        AtomicInteger tries = new AtomicInteger();

        WorkerLoop.run(
                () -> {
                    if (tries.incrementAndGet() == 1)
                        throw new ConnectException();
                    else
                        throw new InterruptedException();
                },
                ms -> sleepCalls.incrementAndGet(),
                () -> true,
                1
        );

        assertEquals(1, sleepCalls.get());
    }

    @Test
    void testHandlesGenericException() {
        AtomicInteger sleepCalls = new AtomicInteger();
        AtomicInteger tries = new AtomicInteger();

        WorkerLoop.run(
                () -> {
                    if (tries.incrementAndGet() == 1)
                        throw new RuntimeException("Failure");
                    else
                        throw new InterruptedException();
                },
                ms -> sleepCalls.incrementAndGet(),
                () -> true,
                1
        );

        assertEquals(1, sleepCalls.get());
    }

    @Test
    void testLoopRunsAndStops() {
        AtomicInteger connectCalls = new AtomicInteger();
        AtomicBoolean done = new AtomicBoolean(false);

        WorkerLoop.run(
                connectCalls::incrementAndGet,
                ms -> done.set(true),
                () -> !done.get(),
                1
        );

        assertEquals(1, connectCalls.get());
    }
}
