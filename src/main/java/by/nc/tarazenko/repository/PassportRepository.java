package by.nc.tarazenko.repository;

import by.nc.tarazenko.entity.Passport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassportRepository extends JpaRepository<Passport,Integer> {
}
