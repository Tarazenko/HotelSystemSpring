package by.nc.tarazenko.repository;

import by.nc.tarazenko.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room,Integer> {
    Room getRoomByNumber(int number);
}
