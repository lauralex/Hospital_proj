package com.bell_sic.entity.wards.rooms;

import com.bell_sic.entity.Hospital;
import com.bell_sic.entity.wards.Ward;

import java.util.*;

public class Room {
    private String roomCode;
    private int capacity;
    private final Set<Bed> beds = new HashSet<>();

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return roomCode.equals(room.roomCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomCode);
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

    public boolean removeBed(Bed bed) {
        return beds.remove(Objects.requireNonNull(bed, "Bed cannot be null!"));
    }

    public Ward getWard() throws NoSuchElementException {
        return Hospital.WardView.getWards().stream().filter(ward -> ward.getRooms().contains(this)).findAny()
                .orElseThrow(() -> new NoSuchElementException("No ward found"));
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
