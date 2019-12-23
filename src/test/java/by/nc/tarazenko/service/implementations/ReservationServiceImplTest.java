package by.nc.tarazenko.service.implementations;

import by.nc.tarazenko.convector.ReservationConvector;
import by.nc.tarazenko.dtos.ReservationDTO;
import by.nc.tarazenko.entity.Guest;
import by.nc.tarazenko.entity.Reservation;
import by.nc.tarazenko.entity.Room;
import by.nc.tarazenko.repository.GuestRepository;
import by.nc.tarazenko.repository.ReservationRepository;
import by.nc.tarazenko.repository.RoomRepository;
import by.nc.tarazenko.service.exceptions.ReservationNotFoundException;
import by.nc.tarazenko.service.exceptions.RoomAlreadyBookException;
import by.nc.tarazenko.service.exceptions.RoomNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ReservationServiceImplTest {

    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private RoomRepository roomRepository;
    @Mock
    private GuestRepository guestRepository;
    @Mock
    private RoomServiceImpl roomService;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    private Reservation reservation;

    private final ReservationConvector reservationConvector = new ReservationConvector();

    @Before
    public void init() {
        reservation = new Reservation();
        reservation.setId(1);
        reservation.setGuest(new Guest());
        reservation.setRoom(new Room());
        reservation.setCheckInDate(LocalDate.parse("2019-12-10"));
        reservation.setCheckOutDate(LocalDate.parse("2019-12-22"));
    }

    @Test
    public void getByIdIdExpectGuestById() {
        doReturn(Optional.of(reservation)).when(reservationRepository).findById(anyInt());
        ReservationDTO reservationDTOExpect = reservationConvector.toDTO(reservation);
        ReservationDTO reservationDTO = reservationService.getById(1);
        assertEquals(reservationDTOExpect, reservationDTO);
        verify(reservationRepository).findById(1);
    }

    @Test(expected = ReservationNotFoundException.class)
    public void getByIdReservationNotExistThrowNotFoundException() {
        when(reservationRepository.findById(anyInt())).thenReturn(Optional.empty());
        reservationService.getById(1);
    }

    @Test
    public void getAllReturnsListOfReservations() {
        doReturn(Collections.singletonList(reservation)).when(reservationRepository).findAll();
        List<ReservationDTO> reservationDTOsExpected = Collections.singletonList(reservationConvector.toDTO(reservation));
        List<ReservationDTO> reservationDTOs = reservationService.getAll();
        verify(reservationRepository).findAll();
        assertEquals(reservationDTOsExpected, reservationDTOs);
    }

    @Test
    public void createReturnedCreatedReservation() {
        doReturn(reservation).when(reservationRepository).saveAndFlush(any());
        doReturn(Optional.of(new Guest())).when(guestRepository).findById(any());
        doReturn(Optional.of(new Room())).when(roomRepository).findById(any());
        when(roomService.isBook(any(), any(), any())).thenReturn(false);
        ReservationDTO reservationDTOExpected = reservationConvector.toDTO(reservation);
        ReservationDTO reservationDTO = reservationService.create(reservationDTOExpected);
        assertEquals(reservationDTOExpected, reservationDTO);
        verify(reservationRepository).saveAndFlush(any());
        verify(roomRepository).findById(any());
        verify(guestRepository).findById(any());
        verify(roomService).isBook(any(), any(), any());
    }

    @Test(expected = RoomNotFoundException.class)
    public void createRoomNotExistThrowNotFoundException() {
        doReturn(Optional.of(new Guest())).when(guestRepository).findById(any());
        doReturn(Optional.empty()).when(roomRepository).findById(any());
        reservationService.create(new ReservationDTO());
    }

    @Test(expected = RoomAlreadyBookException.class)
    public void createRoomAlreadyReservedThrowAlreadyBookException() {
        doReturn(Optional.of(new Guest())).when(guestRepository).findById(any());
        doReturn(Optional.of(new Room())).when(roomRepository).findById(any());
        when(roomService.isBook(any(), any(), any())).thenReturn(true);
        reservationService.create(new ReservationDTO());
    }

    @Test
    public void updateReservationReturnedReservationWhichWasUpdated() {
        doReturn(reservation).when(reservationRepository).saveAndFlush(any());
        doReturn(Optional.of(reservation)).when(reservationRepository).findById(any());
        ReservationDTO reservationDTOexpect = reservationConvector.toDTO(reservation);
        ReservationDTO reservationDTO = reservationService.update(reservationDTOexpect);
        assertEquals(reservationDTOexpect, reservationDTO);
        verify(reservationRepository).findById(anyInt());
        verify(reservationRepository).saveAndFlush(any());
    }

    @Test(expected = ReservationNotFoundException.class)
    public void updateWhenReservationNotFoundAndThrowNotFoundException() {
        when(reservationRepository.findById(anyInt())).thenReturn(Optional.empty());
        reservationService.update(reservationConvector.toDTO(reservation));
    }

    @Test
    public void deleteByIdCheckCallDeletingMethod() {
        doReturn(Optional.of(reservation)).when(reservationRepository).findById(anyInt());
        reservationService.deleteById(1);
        verify(reservationRepository).deleteById(anyInt());
    }
}