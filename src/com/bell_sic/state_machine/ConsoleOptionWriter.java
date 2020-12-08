package com.bell_sic.state_machine;

public class ConsoleOptionWriter {
    public static class Pair<T, K> {
        private T first;
        private K second;

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

        public void setFirst(T first) {
            this.first = first;
        }

        public void setSecond(K second) {
            this.second = second;
        }
    }
}
