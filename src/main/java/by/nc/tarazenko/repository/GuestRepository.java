package by.nc.tarazenko.repository;

import by.nc.tarazenko.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest,Integer> {
    Guest getGuestByPhoneNumber(String number);
}
