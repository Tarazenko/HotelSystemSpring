package by.nc.tarazenko.repository;

import by.nc.tarazenko.entity.AttendancesGuestsConnect;
import by.nc.tarazenko.util.GuestAttendancesKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendancesGuestsConnectRepository extends
        JpaRepository<AttendancesGuestsConnect, GuestAttendancesKey> {
}
