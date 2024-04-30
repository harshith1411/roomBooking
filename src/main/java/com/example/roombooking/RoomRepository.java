package com.example.roombooking;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoomRepository extends CrudRepository<Room, Integer> {

    boolean existsByRoomName(String roomName);


    Room findByRoomName(String roomName);

    Room findByRoomId(int roomID);

    List<Room> findByCapacityGreaterThanEqual(int capacity);
}
