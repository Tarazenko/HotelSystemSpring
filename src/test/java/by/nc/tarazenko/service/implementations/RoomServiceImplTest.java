package by.nc.tarazenko.service.implementations;

import by.nc.tarazenko.convector.RoomConvector;
import by.nc.tarazenko.dtos.RoomDTO;
import by.nc.tarazenko.entity.Reservation;
import by.nc.tarazenko.entity.Room;
import by.nc.tarazenko.repository.ReservationRepository;
import by.nc.tarazenko.repository.RoomRepository;
import by.nc.tarazenko.service.exceptions.InvalidOrderException;
import by.nc.tarazenko.service.exceptions.RoomAlreadyExistException;
import by.nc.tarazenko.service.exceptions.RoomNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RoomServiceImplTest {

    @Mock
    RoomRepository roomRepository;

    @Mock
    ReservationRepository reservationRepository;

    private Room room;
    private List<Reservation> reservations;

    private final RoomConvector roomConvector = new RoomConvector();

    @InjectMocks
    RoomServiceImpl roomService;

    @Before
    public void init() {
        room = new Room();
        room.setNumber(21);
        room.setId(12);

        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setCheckInDate(LocalDate.parse("2019-12-10"));
        reservation.setCheckOutDate(LocalDate.parse("2019-12-20"));

        reservations = new ArrayList<>();
        reservations.add(reservation);
    }

    @Test
    public void getByIdExpectRoomById() {
        doReturn(Optional.of(room)).when(roomRepository).findById(anyInt());
        RoomDTO roomDTOexpect = roomConvector.toDTO(room);
        RoomDTO roomDTO = roomService.getById(12);
        assertEquals(roomDTOexpect, roomDTO);
    }

    @Test(expected = RoomNotFoundException.class)
    public void getByIdWhenRoomNotFound() {
        when(roomRepository.findById(anyInt())).thenReturn(Optional.empty());
        roomService.getById(0);
    }

    @Test
    public void getAllRoomsAndReturnListOfRooms() {
        List<Room> roomsExpect = new ArrayList<>();
        roomsExpect.add(new Room(1, 217, null, null));
        roomsExpect.add(new Room(2, 218, null, null));
        doReturn(roomsExpect).when(roomRepository).findAll();
        List<RoomDTO> roomDTOExpect = new ArrayList<>();
        for (Room room : roomsExpect) {
            roomDTOExpect.add(roomConvector.toDTO(room));
        }
        List<RoomDTO> rooms = roomService.getAll();
        assertEquals(roomDTOExpect, rooms);
        verify(roomRepository).findAll();
    }

    @Test
    public void createRoomReturnedCreatedRoom() {
        doReturn(room).when(roomRepository).saveAndFlush(any());
        RoomDTO roomDTOexpect = roomConvector.toDTO(room);
        RoomDTO roomDTO = roomService.create(roomDTOexpect);
        assertEquals(roomDTOexpect, roomDTO);
        verify(roomRepository).saveAndFlush(room);
    }

    @Test(expected = RoomAlreadyExistException.class)
    public void createRoomWhenRoomAlreadyExistThrowAlreadyExistException() {
        doReturn(room).when(roomRepository).getRoomByNumber(anyInt());
        RoomDTO roomDTOexpect = roomConvector.toDTO(room);
        roomService.create(roomDTOexpect);
    }

    @Test
    public void updateRoomReturnedRoomWhichWasUpdated() {
        doReturn(room).when(roomRepository).saveAndFlush(any());
        doReturn(Optional.of(room)).when(roomRepository).findById(anyInt());
        RoomDTO roomDTOexpect = roomConvector.toDTO(room);
        RoomDTO roomDTO = roomService.update(roomDTOexpect);
        assertEquals(roomDTOexpect, roomDTO);
        verify(roomRepository).saveAndFlush(room);
        verify(roomRepository).findById(anyInt());
    }

    @Test(expected = RoomNotFoundException.class)
    public void updateWhenRoomNotFoundAndThrowNotFoundException() {
        when(roomRepository.findById(anyInt())).thenReturn(Optional.empty());
        RoomDTO roomDTOexpect = roomConvector.toDTO(room);
        roomService.update(roomDTOexpect);
    }

    @Test
    public void deleteByIdCheckMethodCall() {
        doReturn(Optional.of(room)).when(roomRepository).findById(anyInt());
        roomService.deleteById(1);
        verify(roomRepository).deleteById(anyInt());
    }

    @Test(expected = RoomNotFoundException.class)
    public void deleteByIdWhenRoomNotExistThrowRoomNotFoundException() {
        when(roomRepository.findById(anyInt())).thenReturn(Optional.empty());
        roomService.deleteById(1);
    }


    @Test
    public void getFreeWhenRoomAlreadyReservedWhenDatesInsideReservedRoom() {
        doReturn(reservations).when(reservationRepository).getReservationByRoom(any());
        doReturn(Collections.singletonList(room)).when(roomRepository).findAll();
        List<RoomDTO> roomDTOs = roomService.getFree(LocalDate.parse("2019-12-11"),
                LocalDate.parse("2019-12-15"));
        assertEquals( 0,roomDTOs.size());
    }

    @Test
    public void getFreeWhenRoomNotReservedBeforeReservedInterval() {
        doReturn(reservations).when(reservationRepository).getReservationByRoom(any());
        doReturn(Collections.singletonList(room)).when(roomRepository).findAll();
        List<RoomDTO> roomDTOs = roomService.getFree(LocalDate.parse("2019-12-01"),
                LocalDate.parse("2019-12-08"));
        assertEquals( 1,roomDTOs.size());
    }

    @Test
    public void getFreeWhenRoomAlreadyReservedWhenCheckoutDateInReservedInterval() {
        doReturn(reservations).when(reservationRepository).getReservationByRoom(any());
        doReturn(Collections.singletonList(room)).when(roomRepository).findAll();
        List<RoomDTO> roomDTOs = roomService.getFree(LocalDate.parse("2019-12-02"),
                LocalDate.parse("2019-12-11"));
        assertEquals( 0,roomDTOs.size());
    }

    @Test
    public void getFreeWhenRoomAlreadyReservedWhenCheckinDateInReservedInterval() {
        doReturn(reservations).when(reservationRepository).getReservationByRoom(any());
        doReturn(Collections.singletonList(room)).when(roomRepository).findAll();
        List<RoomDTO> roomDTOs = roomService.getFree(LocalDate.parse("2019-12-15"),
                LocalDate.parse("2019-12-25"));
        assertEquals(0,roomDTOs.size());
    }

    @Test
    public void getFreeWhenRoomNotReservedAfterReservedInterval() {
        doReturn(reservations).when(reservationRepository).getReservationByRoom(any());
        doReturn(Collections.singletonList(room)).when(roomRepository).findAll();
        List<RoomDTO> roomDTOs = roomService.getFree(LocalDate.parse("2019-12-21"),
                LocalDate.parse("2019-12-25"));
        assertEquals(1,roomDTOs.size());
    }

    @Test(expected = InvalidOrderException.class)
    public void getFreeInputDatesInInvalidOrderThrowInvalidOrderException() {
        LocalDate checkIn = LocalDate.parse("2019-12-18");
        LocalDate checkOut = LocalDate.parse("2019-12-10");
        roomService.getFree(checkIn, checkOut);
    }
}