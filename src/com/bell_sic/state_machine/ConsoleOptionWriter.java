package com.bell_sic.state_machine;

public class ConsoleOptionWriter {
    public static class Pair<T,K> {
        private final T first;
        private final K second;

        public Pair(T first, K second) {
            this.first = first;
            this.second = second;
        }

        public T first() {
            return first;
        }

        public K second() {
            return second;
        }
    }
}
