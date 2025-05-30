package ru.nsu.anisimov.distributed.common;

import java.io.Serializable;

/**
 * Represents the result of checking a subarray for non-prime numbers.
 * Sent from worker nodes back to the server.
 */
public class Result implements Serializable {
    private boolean hasNonPrime;

    public Result(boolean hasNonPrime) {
        this.hasNonPrime = hasNonPrime;
    }

    public boolean getHasNonPrime() {
        return hasNonPrime;
    }
}
