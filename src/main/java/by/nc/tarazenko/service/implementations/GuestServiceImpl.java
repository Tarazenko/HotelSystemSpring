package by.nc.tarazenko.service.implementations;

import by.nc.tarazenko.convector.GuestConvector;
import by.nc.tarazenko.dtos.GuestDTO;
import by.nc.tarazenko.entity.Attendance;
import by.nc.tarazenko.entity.Guest;
import by.nc.tarazenko.repository.AttendanceRepository;
import by.nc.tarazenko.repository.GuestRepository;
import by.nc.tarazenko.service.GuestService;
import by.nc.tarazenko.service.exceptions.AttendanceNotFoundException;
import by.nc.tarazenko.service.exceptions.GuestAlreadyExistException;
import by.nc.tarazenko.service.exceptions.GuestNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class GuestServiceImpl implements GuestService {

    private final AttendanceRepository attendanceRepository;

    private final GuestRepository guestRepository;

    private final GuestConvector guestConvector = new GuestConvector();

    @Autowired
    public GuestServiceImpl(AttendanceRepository attendanceRepository, GuestRepository guestRepository) {
        this.attendanceRepository = attendanceRepository;
        this.guestRepository = guestRepository;
    }

    @Override
    public GuestDTO getById(int id) {
        log.debug("Getting guest(id - {})", id);
        Guest guest = guestRepository.findById(id).orElseThrow(() ->
                new GuestNotFoundException(String.format("There is no guest with id - %d", id)));
        log.debug("Returning guest - {}", guest);
        return guestConvector.toDTO(guest);
    }

    @Override
    public List<GuestDTO> getAll() {
        log.debug("Getting all guests");
        List<Guest> guests = guestRepository.findAll();
        List<GuestDTO> guestDTOs = new ArrayList<>();
        for (Guest guest : guests) {
            guestDTOs.add(guestConvector.toDTO(guest));
        }
        log.debug("Guests - {}" + guestDTOs);
        return guestDTOs;
    }

    @Override
    public GuestDTO create(GuestDTO guestDTO) {
        log.debug("Creating {}", guestDTO);
        Guest guest = guestConvector.fromDTO(guestDTO);
        if (guestRepository.getGuestByPhoneNumber(guest.getPhoneNumber()) != null) {
            throw new GuestAlreadyExistException("Guest with number " + guest.getPhoneNumber()
                    + " already exist.");
        }
        double billAdd = 0;
        if (guest.getAttendances() != null) {
            for (Attendance attendance : guest.getAttendances()) {
                if (!attendanceRepository.existsById(attendance.getId())) {
                    throw new AttendanceNotFoundException("There is no attendance with id = "
                            + attendance.getId());
                }
                billAdd += attendanceRepository.getOne(attendance.getId()).getCost();
            }
        }
        guest.setBill(billAdd);
        guestRepository.saveAndFlush(guest);
        log.debug("Created {}", guest);
        return guestConvector.toDTO(guest);
    }

    @Override
    public GuestDTO update(GuestDTO guestDTO) {
        log.debug("Updating {}", guestDTO);
        Guest guest = guestConvector.fromDTO(guestDTO);
        int id = guest.getId();
        guestRepository.findById(guest.getId()).orElseThrow(() ->
                new GuestNotFoundException(String.format("There is no guest with id - %d", id)));
        guest = guestRepository.saveAndFlush(guest);
        log.debug("Update {}", guest);
        return guestConvector.toDTO(guest);
    }

    @Override
    public void deleteById(int guestId) {
        log.debug("Deleting guest id - ", guestId);
        guestRepository.findById(guestId).orElseThrow(() ->
                new GuestNotFoundException("There is no guest with id - " + guestId));

        guestRepository.deleteById(guestId);
        log.debug("Deleted guest id - {}", guestId);
    }

    @Override
    public List<Attendance> getAttendances(int guestId) {
        log.debug("Get guest attendances guestId - {}", guestId);
        Guest guest = guestRepository.findById(guestId).orElseThrow(() ->
                new GuestNotFoundException("There is no guest with id - " + guestId));
        List<Attendance> attendances = guest.getAttendances();
        log.debug("Get guest attendances: attendances - {}", attendances);
        return attendances;
    }

    @Override
    public GuestDTO addAttendance(int guestId, int attendanceId) {
        log.debug("Add guest attendances: guestId - {}, attendanceId - {}", guestId, attendanceId);
        Attendance attendance = attendanceRepository.findById(attendanceId).orElseThrow(() ->
                new AttendanceNotFoundException("There is no attendance with id - " + attendanceId));
        Guest guest = guestRepository.findById(guestId).orElseThrow(() ->
                new GuestNotFoundException("There is no guest with id - " + guestId));
        guest.getAttendances().add(attendance);
        guest.setBill(guest.getBill() + attendance.getCost());
        guest = guestRepository.saveAndFlush(guest);
        log.debug("Add guest attendances returning: guest - {}", guest);
        return guestConvector.toDTO(guest);
    }
}
