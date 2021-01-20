package com.bell_sic.entity.wards.rooms;

import com.bell_sic.entity.wards.Ward;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Room {
    private String roomCode;
    private int capacity;
    private final List<Bed> beds = new ArrayList<>();

    public Room(String roomCode, int capacity) throws IllegalArgumentException, NullPointerException {
        if (roomCode.isBlank()) {
            throw new IllegalArgumentException("Roomcode cannot be blank");
        }
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity cannot be 0 or negative!");
        }
        this.roomCode = roomCode;
        this.capacity = capacity;
    }

    public Room(String roomCode) throws IllegalArgumentException, NullPointerException {
        this(roomCode, 10);
    }

    public List<Bed> getBeds() {
        return Collections.unmodifiableList(beds);
    }

    public void addBed(Bed bed) throws IllegalStateException {
        if (beds.size() == capacity) {
            throw new IllegalStateException("Maximum capacity reached!");
        }
        beds.add(Objects.requireNonNull(bed, "Bed cannot be null!"));
    }

    public boolean removeBed(Bed bed) {
        return beds.remove(Objects.requireNonNull(bed, "Bed cannot be null!"));
    }

    public Ward getWard() {
        throw new UnsupportedOperationException();
        // TODO to implement
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) throws IllegalArgumentException, NullPointerException {
        if (roomCode.isBlank()) {
            throw new IllegalArgumentException("Roomcode cannot be blank!");
        }
        this.roomCode = roomCode;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) throws IllegalArgumentException {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity cannot be 0 or negative!");
        }
        this.capacity = capacity;
    }
}
