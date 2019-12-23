package by.nc.tarazenko.service.implementations;

import by.nc.tarazenko.convector.RoomConvector;
import by.nc.tarazenko.dtos.RoomDTO;
import by.nc.tarazenko.entity.Feature;
import by.nc.tarazenko.entity.Reservation;
import by.nc.tarazenko.entity.Room;
import by.nc.tarazenko.repository.FeatureRepository;
import by.nc.tarazenko.repository.ReservationRepository;
import by.nc.tarazenko.repository.RoomRepository;
import by.nc.tarazenko.service.RoomService;
import by.nc.tarazenko.service.exceptions.FeatureNotFoundException;
import by.nc.tarazenko.service.exceptions.InvalidOrderException;
import by.nc.tarazenko.service.exceptions.RoomAlreadyExistException;
import by.nc.tarazenko.service.exceptions.RoomNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    private final ReservationRepository reservationRepository;

    private final FeatureRepository featureRepository;

    private final RoomConvector roomConvector = new RoomConvector();

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository, ReservationRepository reservationRepository, FeatureRepository featureRepository) {
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
        this.featureRepository = featureRepository;
    }

    @Override
    public RoomDTO getById(int id) {
        log.debug("Getting room(id - {})", id);
        Room room = roomRepository.findById(id).orElseThrow(() ->
                new RoomNotFoundException(String.format("There is no room with id - %d", id)));
        log.debug("Returning room {}", room);
        return roomConvector.toDTO(room);
    }

    @Override
    public List<RoomDTO> getAll() {
        log.debug("Getting all rooms");
        List<Room> rooms = roomRepository.findAll();
        List<RoomDTO> roomDTOs = new ArrayList<>();
        for (Room room : rooms) {
            roomDTOs.add(roomConvector.toDTO(room));
        }
        log.debug("Returning - {}", rooms);
        return roomDTOs;
    }

    @Override
    public RoomDTO create(RoomDTO roomDTO) {
        log.debug("Creating {}", roomDTO);
        Room room = roomConvector.fromDTO(roomDTO);
        if (roomRepository.getRoomByNumber(room.getNumber()) != null) {
            throw new RoomAlreadyExistException(String.format("Room with number - %d - already exist",
                    room.getNumber()));
        }
        room = roomRepository.saveAndFlush(room);
        log.debug("Created {}", room);
        return roomConvector.toDTO(room);
    }

    @Override
    public RoomDTO update(RoomDTO roomDTO) {
        log.debug("Updating {}", roomDTO);
        Room room = roomConvector.fromDTO(roomDTO);
        int roomId = room.getId();
        roomRepository.findById(room.getId()).orElseThrow(() ->
                new RoomNotFoundException(String.format("There is no room with id - %d",
                        roomId)));
        room = roomRepository.saveAndFlush(room);
        log.debug("Update {}", room);
        return roomConvector.toDTO(room);
    }

    @Override
    public void deleteById(int id) {
        log.debug("Deleting id - {}", id);
        Room room = roomRepository.findById(id).orElseThrow(() ->
                new RoomNotFoundException(String.format("There is no room with id - %d", id)));
        roomRepository.deleteById(id);
        log.debug("Deleted  id - {}", id);
    }

    private boolean isReservationIncludeCheckin(Reservation reservation, LocalDate checkin) {
        return reservation.getCheckOutDate().isAfter(checkin) &&
                reservation.getCheckInDate().isBefore(checkin);
    }

    private boolean isReservationIncludeCheckout(Reservation reservation, LocalDate checkout) {
        return reservation.getCheckInDate().isBefore(checkout) &&
                reservation.getCheckOutDate().isAfter(checkout);
    }

    private boolean isReservationIncludeCheckinAndCheckout(Reservation reservation, LocalDate checkin,
                                                           LocalDate checkout) {
        return reservation.getCheckInDate().isAfter(checkin) &&
                reservation.getCheckOutDate().isBefore(checkout);
    }

    boolean isBook(Room room, LocalDate checkin, LocalDate checkout) {
        List<Reservation> reservations = reservationRepository.getReservationByRoom(room);
        boolean isBook = false;
        for (Reservation reservation : reservations) {
            if ((isReservationIncludeCheckin(reservation, checkin)) ||
                    (isReservationIncludeCheckinAndCheckout(reservation, checkin, checkout)) ||
                    (isReservationIncludeCheckout(reservation, checkout))) {
                isBook = true;
                break;
            }
        }
        return isBook;
    }

    @Override
    public List<RoomDTO> getFree(LocalDate checkin, LocalDate checkout) {
        log.debug("Getting free rooms to dates: checkin - {}, checkout - {}", checkin, checkout);
        if (checkin.isAfter(checkout))
            throw new InvalidOrderException("Checkin date should be before checkout.");
        List<Room> rooms = roomRepository.findAll();
        List<RoomDTO> freeRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (!isBook(room, checkin, checkout)) {
                freeRooms.add(roomConvector.toDTO(room));
            }
        }
        log.debug("Free rooms - {}", freeRooms);
        return freeRooms;
    }

    @Override
    public RoomDTO addFeature(int roomId, int featureId) {
        log.debug("Add feature(id - {}) to room(id - {})", featureId, roomId);
        Room room = roomRepository.findById(roomId).orElseThrow(() ->
                new RoomNotFoundException("There is no room with id - " + roomId));
        Feature feature = featureRepository.findById(featureId).orElseThrow(() ->
                new FeatureNotFoundException("There is no feature with id - " + featureId));
        room.getFeatures().add(feature);
        room = roomRepository.saveAndFlush(room);
        log.debug("Add feature out: room - {}", room);
        return roomConvector.toDTO(room);
    }
}
