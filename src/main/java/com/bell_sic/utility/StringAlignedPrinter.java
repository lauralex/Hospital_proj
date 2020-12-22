package com.bell_sic.utility;

public final class StringAlignedPrinter {
    public static void print(int alignment, Object... args) {
        StringBuilder format = new StringBuilder();
        for (var ignored :
                args) {
            format.append("%-").append(alignment).append("s");
        }
        System.out.printf(format.toString(), args);
    }
}
