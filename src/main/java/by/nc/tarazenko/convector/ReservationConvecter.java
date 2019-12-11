package by.nc.tarazenko.convector;

import by.nc.tarazenko.dtos.ReservationDTO;
import by.nc.tarazenko.entity.Guest;
import by.nc.tarazenko.entity.Reservation;
import by.nc.tarazenko.entity.Room;

public class ReservationConvecter implements Convector<ReservationDTO, Reservation> {

    @Override
    public ReservationDTO toDTO(Reservation reservation) {
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setId(reservation.getId());
        reservationDTO.setCheckInDate(reservation.getCheckInDate());
        reservationDTO.setCheckOutDate(reservation.getCheckOutDate());
        reservationDTO.setGuestId(reservation.getGuest().getId());
        reservationDTO.setRoomId(reservation.getRoom().getId());
        return reservationDTO;

    }

    @Override
    public Reservation fromDTO(ReservationDTO reservationDTO) {
        Reservation reservation = new Reservation();
        reservation.setId(reservationDTO.getId());
        Room room = new Room();
        room.setId(reservationDTO.getRoomId());
        reservation.setRoom(room);
        Guest guest = new Guest();
        guest.setId(reservationDTO.getGuestId());
        reservation.setGuest(guest);
        reservation.setCheckInDate(reservationDTO.getCheckInDate());
        reservation.setCheckOutDate(reservationDTO.getCheckOutDate());
        return reservation;
    }
}
