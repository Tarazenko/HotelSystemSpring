package by.nc.tarazenko.service.implementations;

import by.nc.tarazenko.convector.GuestConvector;
import by.nc.tarazenko.dtos.GuestDTO;
import by.nc.tarazenko.entity.Attendance;
import by.nc.tarazenko.entity.Guest;
import by.nc.tarazenko.entity.Passport;
import by.nc.tarazenko.repository.AttendanceRepository;
import by.nc.tarazenko.repository.GuestRepository;
import by.nc.tarazenko.service.exceptions.AttendanceNotFoundException;
import by.nc.tarazenko.service.exceptions.GuestAlreadyExistException;
import by.nc.tarazenko.service.exceptions.GuestNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GuestServiceImplTest {

    @Mock
    GuestRepository guestRepository;

    @Mock
    AttendanceRepository attendanceRepository;

    @InjectMocks
    GuestServiceImpl guestService;

    private Guest guest;
    private Attendance attendance;

    private GuestConvector guestConvector = new GuestConvector();

    @Before
    public void init() {

        Passport passport = new Passport();
        passport.setFirstName("Ilya");
        passport.setSecondName("Tarasenko");
        passport.setThirdName("Vit");
        passport.setNumber("1234");
        passport.setId(1);

        attendance = new Attendance();
        attendance.setName("SPA");
        attendance.setId(1);
        attendance.setCost(10.0);

        guest = new Guest();
        guest.setId(1);
        guest.setBill(0.0);
        guest.setPassport(passport);
        guest.setPhoneNumber("+375333999870");
    }

    @Test
    public void getByIdExpectGuestById() {
        doReturn(Optional.of(guest)).when(guestRepository).findById(anyInt());
        GuestDTO guestDTOexp = guestConvector.toDTO(guest);
        GuestDTO guestDTO = guestService.getById(1);
        assertEquals(guestDTOexp, guestDTO);
        verify(guestRepository).findById(1);
    }

    @Test(expected = GuestNotFoundException.class)
    public void getByIdGuestNotExistThrowNotFoundException() {
        when(guestRepository.findById(anyInt())).thenReturn(Optional.empty());
        guestService.getById(1);
    }

    @Test
    public void getAllReturnsListOfGuests() {
        doReturn(Collections.singletonList(guest)).when(guestRepository).findAll();
        List<GuestDTO> guestDTOsExp = Collections.singletonList(guestConvector.toDTO(guest));
        List<GuestDTO> guestDTOs = guestService.getAll();
        verify(guestRepository).findAll();
        assertEquals(guestDTOsExp, guestDTOs);
    }

    @Test
    public void createReturnedCreatedGuest() {
        doReturn(guest).when(guestRepository).saveAndFlush(any());
        GuestDTO guestDTOexpect = guestConvector.toDTO(guest);
        GuestDTO guestDTO = guestService.create(guestDTOexpect);
        assertEquals(guestDTOexpect, guestDTO);
        verify(guestRepository).saveAndFlush(any());
    }

    @Test(expected = GuestAlreadyExistException.class)
    public void createAlreadyExistGuestThrowAlreadyExistException() {
        doReturn(guest).when(guestRepository).getGuestByPhoneNumber(anyString());
        GuestDTO guestDTOexpect = guestConvector.toDTO(guest);
        guestService.create(guestDTOexpect);
    }

    @Test
    public void updateGuestReturnedGuestWhichWasUpdated() {
        doReturn(guest).when(guestRepository).saveAndFlush(any());
        doReturn(Optional.of(guest)).when(guestRepository).findById(any());
        GuestDTO guestDTOexpect = guestConvector.toDTO(guest);
        GuestDTO guestDTO = guestService.update(guestDTOexpect);
        assertEquals(guestDTOexpect, guestDTO);
        verify(guestRepository).findById(anyInt());
    }

    @Test(expected = GuestNotFoundException.class)
    public void updateWhenGuestNotFoundAndThrowNotFoundException() {
        when(guestRepository.findById(anyInt())).thenReturn(Optional.empty());
        guestService.update(guestConvector.toDTO(guest));
    }

    @Test
    public void deleteByIdCheckCallDeletingMethod() {
        doReturn(Optional.of(guest)).when(guestRepository).findById(anyInt());
        guestService.deleteById(1);
        verify(guestRepository).deleteById(anyInt());
    }

    @Test(expected = GuestNotFoundException.class)
    public void deleteByIdWhenGuestNotExistThrowNotFoundException() {
        when(guestRepository.findById(anyInt())).thenThrow(GuestNotFoundException.class);
        guestService.deleteById(1);
    }

    @Test(expected = GuestNotFoundException.class)
    public void getAttendancesGuestNotExistThrowFoundException() {
        when(guestRepository.findById(anyInt())).thenReturn(Optional.empty());
        guestService.getAttendances(1);
    }

    @Test
    public void getAttendancesReturnedListAttendancesByGuestId() {
        guest.setAttendances(Collections.singletonList(attendance));
        doReturn(Optional.of(guest)).when(guestRepository).findById(anyInt());
        guestService.getAttendances(1);
        verify(guestRepository).findById(anyInt());
    }

    @Test(expected = GuestNotFoundException.class)
    public void addAttendanceGuestNotExistThrowNotFoundException() {
        doReturn(Optional.of(attendance)).when(attendanceRepository).findById(any());
        when(guestRepository.findById(anyInt())).thenReturn(Optional.empty());
        guestService.addAttendance(1, 1);
    }

    @Test(expected = AttendanceNotFoundException.class)
    public void addAttendanceAttendanceNotExistThrowNotFoundException() {
        when(attendanceRepository.findById(anyInt())).thenReturn(Optional.empty());
        guestService.addAttendance(1, 1);
    }

    @Test
    public void addAttendanceByGuestIdAndAttendanceIdReturnGuestWithAddingAttendance() {
        doReturn(Optional.of(attendance)).when(attendanceRepository).findById(any());
        doReturn(Optional.of(guest)).when(guestRepository).findById(any());
        doReturn(guest).when(guestRepository).saveAndFlush(any());
        GuestDTO guestDTO = guestService.addAttendance(1, 1);
        guest.setAttendances(Collections.singletonList(attendance));
        guest.setBill(10.0);
        GuestDTO guestDTOexpect = guestConvector.toDTO(guest);
        assertEquals(guestDTOexpect,guestDTO);
        verify(guestRepository).saveAndFlush(any());
        verify(guestRepository).findById(any());
        verify(attendanceRepository).findById(any());
    }
}