package by.nc.tarazenko.service.implementations;

import by.nc.tarazenko.convector.ReservationConvecter;
import by.nc.tarazenko.convector.RoomConvector;
import by.nc.tarazenko.dtos.ReservationDTO;
import by.nc.tarazenko.entity.Guest;
import by.nc.tarazenko.entity.Reservation;
import by.nc.tarazenko.entity.Room;
import by.nc.tarazenko.repository.GuestRepository;
import by.nc.tarazenko.repository.ReservationRepository;
import by.nc.tarazenko.repository.RoomRepository;
import by.nc.tarazenko.service.ReservationService;
import by.nc.tarazenko.service.exceptions.GuestNotFoundException;
import by.nc.tarazenko.service.exceptions.ReservationNotFoundException;
import by.nc.tarazenko.service.exceptions.RoomAlreadyBookException;
import by.nc.tarazenko.service.exceptions.RoomNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    private final RoomRepository roomRepository;

    private final GuestRepository guestRepository;

    private final RoomServiceImpl roomService;

    private ReservationConvecter reservationConvecter = new ReservationConvecter();

    private RoomConvector roomConvector = new RoomConvector();

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository, RoomRepository roomRepository, GuestRepository guestRepository, RoomServiceImpl roomService) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
        this.roomService = roomService;
    }

    @Override
    public ReservationDTO getById(int id) {
        log.debug("Getting reservation(id - {})", id);
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() ->
                new ReservationNotFoundException(String.format("There is no reservation with id - %d", id)));
        log.debug("Returning reservation {}", reservation);
        return reservationConvecter.toDTO(reservation);
    }

    @Override
    public List<ReservationDTO> getAll() {
        log.debug("Getting all reservations");
        List<Reservation> reservations = reservationRepository.findAll();
        List<ReservationDTO> reservationDTOs = new ArrayList<>();
        for (Reservation reservation : reservations) {
            reservationDTOs.add(reservationConvecter.toDTO(reservation));
        }
        log.debug("Returning - {}", reservations);
        return reservationDTOs;
    }

    @Override
    public ReservationDTO create(ReservationDTO reservationDTO) {
        log.debug("Creating {}", reservationDTO);
        Reservation reservation = reservationConvecter.fromDTO(reservationDTO);
        int guestId = reservation.getGuest().getId();
        int roomId = reservation.getRoom().getId();
        Guest guest = guestRepository.findById(reservation.getGuest().getId()).orElseThrow(() ->
                new GuestNotFoundException(String.format("There is no guest with id - %d", guestId)));
        Room room = roomRepository.findById(reservation.getRoom().getId()).orElseThrow(() ->
                new RoomNotFoundException(String.format("There is no room with id - %d", roomId)));
        if (roomService.isBook(room, reservation.getCheckInDate(), reservation.getCheckOutDate()))
            throw new RoomAlreadyBookException(String.format("Room(id - %d) is already book", roomId));
        guest.setBill(guest.getBill() + roomConvector.toDTO(room).getCost() *
                reservation.getCheckInDate().until(
                        reservation.getCheckOutDate(), ChronoUnit.DAYS));
        reservation = reservationRepository.saveAndFlush(reservation);
        log.debug("Created {}", reservation);
        return reservationConvecter.toDTO(reservation);
    }

    @Override
    public ReservationDTO update(ReservationDTO reservationDTO) {
        log.debug("Updating {}", reservationDTO);
        Reservation reservation = reservationConvecter.fromDTO(reservationDTO);
        int reservationId = reservation.getId();
        reservationRepository.findById(reservation.getId()).orElseThrow(() ->
                new ReservationNotFoundException(String.format("There is no reservation with id - %d",
                        reservationId)));
        reservation = reservationRepository.saveAndFlush(reservation);
        log.debug("Update {}", reservation);
        return reservationConvecter.toDTO(reservation);
    }

    @Override
    public void deleteById(int id) {
        log.debug("Deleting reservation id - ", id);
        reservationRepository.findById(id).orElseThrow(() ->
                new ReservationNotFoundException(String.format("There is no reservation with id - %d", id)));
        reservationRepository.deleteById(id);
        log.debug("Deleted reservation id - {}", id);
    }
}
