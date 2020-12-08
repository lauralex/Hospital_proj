package com.bell_sic.utility;

public final class ConsoleColoredPrinter {

    public static void print(String color, CharSequence text) {
        if (checkColorCapability()) {
            System.out.print(color + text + Color.RESET);
        } else {
            System.out.print(text);
        }
    }

    public static void print(CharSequence text) {
        print(Color.RED, text);
    }

    public static void println(String color, CharSequence text) {
        if (checkColorCapability()) {
            System.out.println(color + text + Color.RESET);
        } else {
            System.out.println(text);
        }
    }

    public static void println(CharSequence text) {
        println(Color.RED, text);
    }

    private static boolean checkColorCapability() {
        var enclosingProcess = ProcessHandle.current().parent();
        if (enclosingProcess.isEmpty()) return false;

        var command = enclosingProcess.get().info().command();
        return command.isPresent() && !command.get().contains("cmd.exe");
    }

    public static final class Color {
        public static final String RED = "\u001B[31m";
        public static final String BLACK = "\u001B[30m";
        public static final String GREEN = "\u001B[32m";
        private static final String RESET = "\u001B[0m";
    }
}
