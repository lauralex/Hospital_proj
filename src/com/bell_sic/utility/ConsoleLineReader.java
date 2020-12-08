package com.bell_sic.utility;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public final class ConsoleLineReader {
    private static final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public static BufferedReader getBufferedReader() {
        return bufferedReader;
    }
}
