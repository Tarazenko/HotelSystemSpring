package by.nc.tarazenko.service.implementations;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class RoomServiceImplTest {

    @Mock
    RoomRepository roomRepository;

    @Mock
    ReservationRepository reservationRepository;

    private Room room;
    private Reservation reservation;
    private List<Reservation> reservations;

    private RoomConvector roomConvector = new RoomConvector();

    @InjectMocks
    RoomServiceImpl roomService;

    @Before
    public void init() {
        room = new Room();
        room.setNumber(21);
        room.setId(12);

        reservation = new Reservation();
        reservation.setId(1);
        reservation.setCheckInDate(LocalDate.parse("2019-12-10"));
        reservation.setCheckOutDate(LocalDate.parse("2019-12-20"));

        reservations = new ArrayList<>();
        reservations.add(reservation);
    }

    @Test
    public void getByIdTest1() {
        doReturn(Optional.of(room)).when(roomRepository).findById(anyInt());
        RoomDTO roomDTOexpect = roomConvector.toDTO(room);
        RoomDTO roomDTO = roomService.getById(12);
        assertEquals(roomDTOexpect, roomDTO);
    }

    @Test(expected = RoomNotFoundException.class)
    public void getByIdExceptionTest() {
        when(roomRepository.findById(anyInt())).thenThrow(RoomNotFoundException.class);
        roomService.deleteById(0);
    }

    @Test
    public void getAllOk() {
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
    public void createOkTest() {
        doReturn(room).when(roomRepository).saveAndFlush(any());
        RoomDTO roomDTOexpect = roomConvector.toDTO(room);
        RoomDTO roomDTO = roomService.create(roomDTOexpect);
        assertEquals(roomDTOexpect, roomDTO);
        verify(roomRepository).saveAndFlush(room);
    }

    @Test(expected = RoomAlreadyExistException.class)
    public void createExceptionAlreadyExist() {
        doReturn(room).when(roomRepository).getRoomByNumber(anyInt());
        RoomDTO roomDTOexpect = roomConvector.toDTO(room);
        roomService.create(roomDTOexpect);
    }

    @Test
    public void update() {
        doReturn(room).when(roomRepository).saveAndFlush(any());
        doReturn(Optional.of(room)).when(roomRepository).findById(anyInt());
        RoomDTO roomDTOexpect = roomConvector.toDTO(room);
        RoomDTO roomDTO = roomService.update(roomDTOexpect);
        assertEquals(roomDTOexpect, roomDTO);
        verify(roomRepository).saveAndFlush(room);
        verify(roomRepository).findById(anyInt());
    }

    @Test(expected = RoomNotFoundException.class)
    public void updateNotFoundException() {
        when(roomRepository.findById(anyInt())).thenThrow(RoomNotFoundException.class);
        RoomDTO roomDTOexpect = roomConvector.toDTO(room);
        RoomDTO roomDTO = roomService.update(roomDTOexpect);
    }

    @Test
    public void deleteById() {
        doReturn(Optional.of(room)).when(roomRepository).findById(anyInt());
        roomService.deleteById(1);
        verify(roomRepository).deleteById(anyInt());
    }

    @Test(expected = RoomNotFoundException.class)
    public void deleteByIdRoomNotFoundException() {
        when(roomRepository.findById(anyInt())).thenThrow(RoomNotFoundException.class);
        roomService.deleteById(1);
    }


    @Test
    public void getFreeTest1() {
        doReturn(reservations).when(reservationRepository).getReservationByRoom(any());
        doReturn(Arrays.asList(room)).when(roomRepository).findAll();
        List<RoomDTO> roomDTOs = roomService.getFree(LocalDate.parse("2019-12-10"),
                LocalDate.parse("2019-12-15"));
        assertEquals( 0,roomDTOs.size());
    }

    @Test
    public void getFreeTest2() {
        doReturn(reservations).when(reservationRepository).getReservationByRoom(any());
        doReturn(Arrays.asList(room)).when(roomRepository).findAll();
        List<RoomDTO> roomDTOs = roomService.getFree(LocalDate.parse("2019-12-01"),
                LocalDate.parse("2019-12-08"));
        assertEquals( 1,roomDTOs.size());
    }

    @Test
    public void getFreeTest3() {
        doReturn(reservations).when(reservationRepository).getReservationByRoom(any());
        doReturn(Arrays.asList(room)).when(roomRepository).findAll();
        List<RoomDTO> roomDTOs = roomService.getFree(LocalDate.parse("2019-12-02"),
                LocalDate.parse("2019-12-11"));
        assertEquals( 0,roomDTOs.size());
    }

    @Test
    public void getFreeTest4() {
        doReturn(reservations).when(reservationRepository).getReservationByRoom(any());
        doReturn(Arrays.asList(room)).when(roomRepository).findAll();
        List<RoomDTO> roomDTOs = roomService.getFree(LocalDate.parse("2019-12-15"),
                LocalDate.parse("2019-12-25"));
        assertEquals(0,roomDTOs.size());
    }

    @Test
    public void getFreeTest5() {
        doReturn(reservations).when(reservationRepository).getReservationByRoom(any());
        doReturn(Arrays.asList(room)).when(roomRepository).findAll();
        List<RoomDTO> roomDTOs = roomService.getFree(LocalDate.parse("2019-12-21"),
                LocalDate.parse("2019-12-25"));
        assertEquals(1,roomDTOs.size());
    }

    @Test(expected = InvalidOrderException.class)
    public void getFreeInvalidOrderDate() {
        LocalDate checkIn = LocalDate.parse("2019-12-18");
        LocalDate checkOut = LocalDate.parse("2019-12-10");
        roomService.getFree(checkIn, checkOut);
    }
}