package ru.nsu.anisimov;

public class ParallelThreadPrimeNumberCheck implements PrimeNumberCheck {
    private static class ThreadPrimeNumberCheck implements Runnable {
        private final int[] array;
        private final int start, end;
        public boolean hasNonPrime = false;

        public ThreadPrimeNumberCheck(int[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            for (int i = start; i < end; ++i) {
                if (!SequentialPrimeNumberCheck.isPrime(array[i])) {
                    hasNonPrime = true;
                    break;
                }
            }
        }

        public boolean hasNonPrime() {
            return hasNonPrime;
        }
    }

    @Override
    public boolean hasNonPrime(int[] array) throws InterruptedException {
        int threadCount = Runtime.getRuntime().availableProcessors();
        int length = array.length;
        ThreadPrimeNumberCheck[] tasks = new ThreadPrimeNumberCheck[threadCount];
        Thread[] threads = new Thread[threadCount];
        int chunkSize = (int) Math.ceil((double) length / threadCount); // round up

        for (int i = 0; i < threadCount; ++i) {
            int start = i * chunkSize;
            int end = Math.min(length, start + chunkSize);
            tasks[i] = new ThreadPrimeNumberCheck(array, start, end);
            threads[i] = new Thread(tasks[i]);
            threads[i].start();
        }

        boolean prime = false;

        for (int i = 0; i < threadCount; ++i) {
            threads[i].join();
            if (tasks[i].hasNonPrime()) {
                prime = true;
            }
        }
        return prime;
    }


}
