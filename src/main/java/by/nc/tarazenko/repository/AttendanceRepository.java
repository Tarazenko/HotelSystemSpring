package by.nc.tarazenko.repository;

import by.nc.tarazenko.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
    boolean existsByName(String name);
}
