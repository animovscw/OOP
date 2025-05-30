package ru.nsu.anisimov.distributed.worker;

import java.net.ConnectException;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for WorkerLoop resilience.
 */
class WorkerLoopTest {

    @Test
    void testInterruptedDuringSleepStopsLoop() {
        AtomicInteger connectCalls = new AtomicInteger();

        WorkerLoop.run(
                () -> {
                    connectCalls.incrementAndGet();
                    return true;
                },
                ms -> {
                    throw new InterruptedException();
                },
                () -> true,
                1
        );

        Assertions.assertEquals(1, connectCalls.get());
    }

    @Test
    void testExceptionInConnectorTriggersSleepThenStops() {
        AtomicInteger sleepCalls = new AtomicInteger();

        WorkerLoop.run(
                () -> {
                    throw new RuntimeException("failure");
                },
                ms -> {
                    if (sleepCalls.get() == 0) {
                        sleepCalls.incrementAndGet();
                    } else {
                        throw new InterruptedException();
                    }
                },
                () -> true,
                1
        );

        Assertions.assertEquals(1, sleepCalls.get());
    }

    @Test
    void testLoopRunsOnceThenStopsViaContinuator() {
        AtomicInteger connectCalls = new AtomicInteger();

        WorkerLoop.run(
                () -> {
                    connectCalls.incrementAndGet();
                    return true;
                },
                ms -> {
                    /* no-op */
                },
                () -> connectCalls.get() == 0,
                1
        );

        Assertions.assertEquals(1, connectCalls.get());
    }

    @Test
    public void testWorkerLoopHandlesConnectExceptionAndContinues() {
        final int[] counter = {0};

        WorkerLoop.Connector mockConnector = () -> {
            counter[0]++;
            throw new ConnectException("Mock connection error");
        };

        WorkerLoop.Sleeper mockSleeper = ms -> {
            if (counter[0] >= 2) {
                throw new InterruptedException();
            }
        };

        WorkerLoop.Continuator mockContinuator = () -> true;

        WorkerLoop.run(mockConnector, mockSleeper, mockContinuator, 100);

        Assertions.assertEquals(2, counter[0]);
    }

    @Test
    public void testConnectAndProcess_ClassNotFoundHandled() throws Exception {
        boolean result = PrimeWorker.connectAndProcess();
        Assertions.assertFalse(result);
    }
}