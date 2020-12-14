package com.bell_sic.entity.wards;

import java.util.*;

public class WardView {
    private static final Set<Ward> wards = new HashSet<>();

    public static void addWard(Ward ward) {
        wards.add(ward);
    }

    public static void clearWards() {
        wards.clear();
    }

    public static boolean removeWard(Ward ward) {
        return wards.remove(ward);
    }

    public static Set<Ward> getWards() {
        return wards;
    }

    public static Optional<Ward> getWardByType(Class<? extends Ward> wardType) {
        return wards.stream().filter(wardType::isInstance).findAny();
    }

    public static Ward getAnyWard() {
        var defaultWard = WardView.getWards().stream().findAny();
        if (defaultWard.isEmpty()) throw new NoSuchElementException("There is no ward!");
        return Objects.requireNonNull(defaultWard.get(), "Ward cannot be null!");
    }
}
