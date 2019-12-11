package by.nc.tarazenko.service.implementations;

import by.nc.tarazenko.convector.ReservationConvecter;
import by.nc.tarazenko.convector.RoomConvector;
import by.nc.tarazenko.dtos.ReservationDTO;
import by.nc.tarazenko.entity.Guest;
import by.nc.tarazenko.entity.Reservation;
import by.nc.tarazenko.entity.Room;
import by.nc.tarazenko.repository.GuestRepository;
import by.nc.tarazenko.repository.ReservationRepository;
import by.nc.tarazenko.repository.RoomRepositoy;
import by.nc.tarazenko.service.ReservationService;
import by.nc.tarazenko.service.exceptions.GuestNotFoundException;
import by.nc.tarazenko.service.exceptions.ReservationNotFoundException;
import by.nc.tarazenko.service.exceptions.RoomAlreadyBookException;
import by.nc.tarazenko.service.exceptions.RoomNotFoundException;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


@Service
public class ReservationServiceImpl implements ReservationService {

    private Logger logger = Logger.getLogger(ReservationServiceImpl.class);

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    RoomRepositoy roomRepositoy;

    @Autowired
    GuestRepository guestRepository;

    @Autowired
    RoomServiceImpl roomService;

    private ReservationConvecter reservationConvecter = new ReservationConvecter();
    private RoomConvector roomConvector = new RoomConvector();

    @Override
    public ReservationDTO getById(int id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() ->
                new ReservationNotFoundException("There is no such reservation."));
        return reservationConvecter.toDTO(reservation);
    }

    @Override
    public List<ReservationDTO> getAll() {
        List<Reservation> reservations = reservationRepository.findAll();
        List<ReservationDTO> reservationDTOs = new ArrayList<>();
        for (Reservation reservation : reservations) {
            reservationDTOs.add(reservationConvecter.toDTO(reservation));
        }
        return reservationDTOs;
    }

    @Override
    public ReservationDTO create(ReservationDTO reservationDTO) {
        Reservation reservation = reservationConvecter.fromDTO(reservationDTO);
        Guest guest = guestRepository.findById(reservation.getGuest().getId()).orElseThrow(() ->
                new GuestNotFoundException("There is no such guest."));
        Room room = roomRepositoy.findById(reservation.getRoom().getId()).orElseThrow(() ->
                new RoomNotFoundException("There is no such room."));
        if(roomService.isBook(room, reservation.getCheckInDate(),reservation.getCheckOutDate()))
            throw new RoomAlreadyBookException("Room is already book.");
        guest.setBill(guest.getBill() + roomConvector.toDTO(room).getCost()*
                reservation.getCheckInDate().until(
                        reservation.getCheckOutDate(), ChronoUnit.DAYS));
        reservation = reservationRepository.saveAndFlush(reservation);
        return reservationConvecter.toDTO(reservation);
    }

    @Override
    public ReservationDTO update(ReservationDTO reservationDTO) {
        Reservation reservation = reservationConvecter.fromDTO(reservationDTO);
        reservationRepository.findById(reservation.getId()).orElseThrow(() ->
                new ReservationNotFoundException("There is no such reservation."));
        reservation = reservationRepository.saveAndFlush(reservation);
        return reservationConvecter.toDTO(reservation);
    }

    @Override
    public void deleteById(int id) {
        reservationRepository.findById(id).orElseThrow(() ->
                new ReservationNotFoundException("There is no such reservation."));
        reservationRepository.deleteById(id);
    }
}
