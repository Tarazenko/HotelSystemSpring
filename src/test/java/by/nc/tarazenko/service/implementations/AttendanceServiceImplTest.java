package by.nc.tarazenko.service.implementations;

import by.nc.tarazenko.convector.AttendanceConvector;
import by.nc.tarazenko.dtos.AttendanceDTO;
import by.nc.tarazenko.entity.Attendance;
import by.nc.tarazenko.repository.AttendanceRepository;
import by.nc.tarazenko.service.exceptions.AttendanceAlreadyExistException;
import by.nc.tarazenko.service.exceptions.AttendanceNotFoundException;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AttendanceServiceImplTest {
    @Mock
    private AttendanceRepository attendanceRepository;

    @InjectMocks
    private AttendanceServiceImpl attendanceService;

    private AttendanceConvector attendanceConvector = new AttendanceConvector();

    private Attendance attendance;

    @Before
    public void init(){
        attendance = new Attendance();
        attendance.setId(1);
        attendance.setCost(123.0);
        attendance.setName("Bar");
    }

    @Test
    public void getByIdExpectAttendanceById() {
        doReturn(Optional.of(attendance)).when(attendanceRepository).findById(anyInt());
        AttendanceDTO attendanceDTOexpect = attendanceConvector.toDTO(attendance);
        AttendanceDTO attendanceDTO = attendanceService.getById(1);
        assertEquals(attendanceDTOexpect, attendanceDTO);
    }

    @Test(expected = AttendanceNotFoundException.class)
    public void getByIdWhenAttendanceNotFound() {
        when(attendanceRepository.findById(anyInt())).thenReturn(Optional.empty());
        attendanceService.getById(0);
    }

    @Test
    public void getAllAttendancesAndReturnListOfAttendances() {
        doReturn(Collections.singletonList(attendance)).when(attendanceRepository).findAll();
        List<AttendanceDTO> attendanceDTOsExpected = Collections.singletonList(attendanceConvector.toDTO(attendance));
        List<AttendanceDTO> attendanceDTOs = attendanceService.getAll();
        verify(attendanceRepository).findAll();
        assertEquals(attendanceDTOsExpected, attendanceDTOs);
    }

    @Test
    public void createAttendanceReturnedCreatedAttendance() {
        doReturn(attendance).when(attendanceRepository).saveAndFlush(any());
        doReturn(false).when(attendanceRepository).existsByName(anyString());
        AttendanceDTO attendanceDTOexpect = attendanceConvector.toDTO(attendance);
        AttendanceDTO attendanceDTO = attendanceService.create(attendanceDTOexpect);
        assertEquals(attendanceDTOexpect, attendanceDTO);
        verify(attendanceRepository).saveAndFlush(attendance);
    }

    @Test(expected = AttendanceAlreadyExistException.class)
    public void createAttendanceWhenAttendanceAlreadyExistThrowAlreadyExistException() {
        doReturn(true).when(attendanceRepository).existsByName(anyString());
        AttendanceDTO attendanceDTO = attendanceConvector.toDTO(attendance);
        attendanceService.create(attendanceDTO);
    }

    @Test
    public void updateAttendanceReturnedFeatureWhichWasUpdated() {
        doReturn(attendance).when(attendanceRepository).saveAndFlush(any());
        doReturn(Optional.of(attendance)).when(attendanceRepository).findById(anyInt());
        AttendanceDTO attendanceDTOexpect = attendanceConvector.toDTO(attendance);
        AttendanceDTO attendanceDTO = attendanceService.update(attendanceDTOexpect);
        assertEquals(attendanceDTOexpect, attendanceDTO);
        verify(attendanceRepository).saveAndFlush(attendance);
        verify(attendanceRepository).findById(anyInt());
    }

    @Test(expected = AttendanceNotFoundException.class)
    public void updateWhenRoomNotFoundAndThrowNotFoundException() {
        when(attendanceRepository.findById(any())).thenReturn(Optional.empty());
        attendanceService.update(new AttendanceDTO());
    }

    @Test
    public void deleteByIdCheckMethodCall() {
        doReturn(Optional.of(attendance)).when(attendanceRepository).findById(anyInt());
        attendanceRepository.deleteById(1);
        verify(attendanceRepository).deleteById(anyInt());
    }
}