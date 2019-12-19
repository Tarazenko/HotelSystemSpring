package by.nc.tarazenko.service.implementations;

import by.nc.tarazenko.convector.RoomConvector;
import by.nc.tarazenko.dtos.RoomDTO;
import by.nc.tarazenko.entity.Reservation;
import by.nc.tarazenko.entity.Room;
import by.nc.tarazenko.repository.ReservationRepository;
import by.nc.tarazenko.repository.RoomRepository;
import by.nc.tarazenko.service.RoomService;
import by.nc.tarazenko.service.exceptions.InvalidOrderException;
import by.nc.tarazenko.service.exceptions.RoomAlreadyExistException;
import by.nc.tarazenko.service.exceptions.RoomNotFoundException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {
    private Logger logger = Logger.getLogger(RoomServiceImpl.class);

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    ReservationRepository reservationRepository;

    private RoomConvector roomConvector = new RoomConvector();

    @Override
    public RoomDTO getById(int id) {
        Room room = roomRepository.findById(id).orElseThrow(() ->
                new RoomNotFoundException("There is no such room."));
        return roomConvector.toDTO(room);
    }

    @Override
    public List<RoomDTO> getAll() {
        List<Room> rooms = roomRepository.findAll();
        List<RoomDTO> roomDTOs = new ArrayList<>();
        for (Room room : rooms) {
            roomDTOs.add(roomConvector.toDTO(room));
        }
        return roomDTOs;
    }

    @Override
    public RoomDTO create(RoomDTO roomDTO) {
        Room room = roomConvector.fromDTO(roomDTO);
        if(roomRepository.getRoomByNumber(room.getNumber()) != null){
            throw new RoomAlreadyExistException("Room with such number already exist.");
        }
        room = roomRepository.saveAndFlush(room);
        return roomConvector.toDTO(room);
    }

    @Override
    public RoomDTO update(RoomDTO roomDTO) {
        Room room = roomConvector.fromDTO(roomDTO);
        roomRepository.findById(room.getId()).orElseThrow(() ->
                new RoomNotFoundException("There is no such room."));
        room = roomRepository.saveAndFlush(room);
        return roomConvector.toDTO(room);
    }

    @Override
    public void deleteById(int id) {
        Room room = roomRepository.findById(id).orElseThrow(() ->
                new RoomNotFoundException("There is no such room."));
        roomRepository.deleteById(id);
    }

    public boolean isBook(Room room, LocalDate checkin, LocalDate checkout) {
        List<Reservation> reservations = reservationRepository.getReservationByRoom(room);
        boolean isBook = false;
        for (Reservation reservation : reservations) {
            logger.debug(reservation);
            if ((reservation.getCheckOutDate().isAfter(checkin) &&
                    reservation.getCheckInDate().isBefore(checkin)) ||
                    (reservation.getCheckInDate().isAfter(checkin) &&
                            reservation.getCheckOutDate().isBefore(checkout)) ||
                    (reservation.getCheckInDate().isBefore(checkout) &&
                            reservation.getCheckOutDate().isAfter(checkout))) {
                isBook = true;
                break;
            }
        }
        logger.debug(isBook);
        return isBook;
    }

    @Override
    public List<RoomDTO> getFree(LocalDate checkin, LocalDate checkout) {
        if (checkin.isAfter(checkout))
            throw new InvalidOrderException("Checkin date should be before checkout.");
        List<Room> rooms = roomRepository.findAll();
        List<RoomDTO> freeRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (!isBook(room,checkin,checkout)) {
                freeRooms.add(roomConvector.toDTO(room));
            }
        }
        return freeRooms;
    }
}
