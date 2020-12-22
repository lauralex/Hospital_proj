package com.bell_sic.utility;

public class ConsoleDelay {
    public static void addDelay(long delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void addDelay() {
        addDelay(100);
    }
}
