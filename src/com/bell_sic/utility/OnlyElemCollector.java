package com.bell_sic.utility;

import java.util.NoSuchElementException;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class OnlyElemCollector {

    public static <T> Collector<T, ?, T> getOnly() {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    if (list.size() > 1) {
                        throw new IllegalArgumentException("Element is not unique");
                    }
                    if (list.size() < 1) {
                        throw new NoSuchElementException("Element not found!");
                    }
                    return list.get(0);
                }
        );
    }
}
