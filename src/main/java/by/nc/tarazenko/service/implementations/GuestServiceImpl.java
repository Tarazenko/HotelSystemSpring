package by.nc.tarazenko.service.implementations;

import by.nc.tarazenko.convector.GuestConvector;
import by.nc.tarazenko.dtos.GuestDTO;
import by.nc.tarazenko.entity.Attendance;
import by.nc.tarazenko.entity.Guest;
import by.nc.tarazenko.repository.AttendanceRepository;
import by.nc.tarazenko.repository.GuestRepository;
import by.nc.tarazenko.service.GuestService;

import by.nc.tarazenko.service.exceptions.AttendanceNotFoundException;
import by.nc.tarazenko.service.exceptions.GuestNotFoundException;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GuestServiceImpl implements GuestService {
    final static Logger logger = Logger.getLogger(GuestServiceImpl.class);

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private GuestRepository guestRepository;

    private GuestConvector guestConvector = new GuestConvector();

    @Override
    public GuestDTO getById(int id) {
        Guest guest = guestRepository.findById(id).orElseThrow(() ->
                new GuestNotFoundException("There is no such guest."));
        return guestConvector.toDTO(guest);
    }

    @Override
    public List<GuestDTO> getAll() {
        logger.info("Get all from database.");
        List<Guest> guests = guestRepository.findAll();
        List<GuestDTO> guestDTOs = new ArrayList<>();
        for (Guest guest : guests) {
            guestDTOs.add(guestConvector.toDTO(guest));
        }
        return guestDTOs;
    }

    @Override
    public GuestDTO create(GuestDTO guestDTO) {
        Guest guest = guestConvector.fromDTO(guestDTO);
        guest = guestRepository.saveAndFlush(guest);
        return guestConvector.toDTO(guest);
    }

    @Override
    public GuestDTO update(GuestDTO guestDTO) {
        Guest guest = guestConvector.fromDTO(guestDTO);
        guestRepository.findById(guest.getId()).orElseThrow(() ->
                new GuestNotFoundException("There is no such guest."));
        guest = guestRepository.saveAndFlush(guest);
        return guestConvector.toDTO(guest);
    }

    @Override
    public void deleteById(int guestId) {
        Guest guest = guestRepository.findById(guestId).orElseThrow(() ->
                new GuestNotFoundException("There is no such guest."));
        guestRepository.deleteById(guestId);
    }

    @Override
    public List<Attendance> getAttendances(int guestId) {
        guestRepository.findById(guestId).orElseThrow(() ->
                new GuestNotFoundException("There is no such guest."));
        return guestRepository.findById(guestId).get().getAttendances();
    }

    @Override
    public GuestDTO addAttendance(int guestId, int attendanceId) {
        Attendance attendance = attendanceRepository.findById(attendanceId).orElseThrow(() ->
                new AttendanceNotFoundException("There is no such attendance."));
        Guest guest = guestRepository.findById(guestId).orElseThrow(() ->
                new GuestNotFoundException("There is no such guest."));
        guest.getAttendances().add(attendance);
        guest.setBill(guest.getBill() + attendance.getCost());
        guest = guestRepository.saveAndFlush(guest);
        return guestConvector.toDTO(guest);
    }
}
