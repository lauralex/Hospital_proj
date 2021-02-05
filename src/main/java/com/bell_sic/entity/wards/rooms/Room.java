package com.bell_sic.entity.wards.rooms;

import com.bell_sic.entity.wards.Ward;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Room {
    private final Set<Bed> beds = new HashSet<>();
    private final Ward parentWard;
    private String roomCode;
    private int capacity;

    public Room(String roomCode, int capacity, Ward ward) throws IllegalArgumentException, NullPointerException {
        if (roomCode.isBlank()) {
            throw new IllegalArgumentException("Roomcode cannot be blank");
        }
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity cannot be 0 or negative!");
        }

        this.parentWard = Objects.requireNonNull(ward);
        this.roomCode = roomCode;
        this.capacity = capacity;
        for (int i = 0; i < capacity; i++) {
            beds.add(new Bed(String.valueOf(beds.size()), this));
        }
    }

    public Room(String roomCode, Ward ward) throws IllegalArgumentException, NullPointerException {
        this(roomCode, 10, ward);
    }

    public Set<Bed> getBeds() {
        return Collections.unmodifiableSet(beds);
    }

    public void addBed(Bed bed) throws IllegalStateException {
        if (beds.size() == capacity) {
            throw new IllegalStateException("Maximum capacity reached!");
        }
        beds.add(Objects.requireNonNull(bed, "Bed cannot be null!"));
    }

    public void addBed() throws IllegalStateException {
        if (beds.size() == capacity) {
            throw new IllegalStateException("Maximum capacity reached!");
        }
        beds.add(new Bed(String.valueOf(beds.size()), this));
    }

    public boolean removeBed(Bed bed) {
        return beds.remove(Objects.requireNonNull(bed, "Bed cannot be null!"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return roomCode.equals(room.roomCode) && parentWard.equals(room.parentWard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomCode, parentWard);
    }


    @Override
    public String toString() {
        return "Room{" +
                "parentWard=" + parentWard +
                ", roomCode='" + roomCode + '\'' +
                ", capacity=" + capacity +
                '}';
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

    public Ward getParentWard() {
        return parentWard;
    }
}
