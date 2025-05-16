package ru.nsu.anisimov.distributed.common;

import java.io.Serializable;

public class Result implements Serializable {
    public boolean hasNonPrime;

    public Result(boolean hasNonPrime) {
        this.hasNonPrime = hasNonPrime;
    }
}
