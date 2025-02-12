package ru.nsu.anisimov;

public class ParallelThreadPrimeNumberCheck {
    private static class ThreadPrimeNumberCheck extends Thread {
        private final int[] array;
        private final int start, end;
        public boolean NonPrime = false;

        public ThreadPrimeNumberCheck(int[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            for (int i = start; i < end; ++i) {
                if (!SequentialPrimeNumberCheck.isPrime(array[i])) {
                    NonPrime = true;
                    break;
                }
            }
        }

        public boolean hasNonPrime() {
            return NonPrime;
        }
    }

    public static boolean NonPrimeParallelThread(int[] array, int threadCount) throws InterruptedException {
        int length = array.length;
        ThreadPrimeNumberCheck[] threads = new ThreadPrimeNumberCheck[threadCount];
        int chunkSize = (int) Math.ceil((double) length / threadCount); // round up

        for (int i = 0; i < threadCount; ++i) {
            int start = i * chunkSize;
            int end = Math.min(length, start + chunkSize);
            threads[i] = new ThreadPrimeNumberCheck(array, start, end);
            threads[i].start();
        }

        for (ThreadPrimeNumberCheck thread : threads) {
            thread.join();
            if (thread.hasNonPrime()) {
                return true;
            }
        }
        return false;
    }


}
