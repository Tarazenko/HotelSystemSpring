package by.nc.tarazenko.repository;

import by.nc.tarazenko.entity.Reservation;
import by.nc.tarazenko.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation,Integer> {
    List<Reservation> getReservationByRoom(Room room);
}
