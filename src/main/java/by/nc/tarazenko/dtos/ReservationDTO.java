package by.nc.tarazenko.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservationDTO {
        private int id;
        private int roomId;
        private int guestId;
        private LocalDate checkInDate;
        private LocalDate checkOutDate;
}
