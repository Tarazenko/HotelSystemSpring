package by.nc.tarazenko.service.implementations;

import by.nc.tarazenko.convector.GuestConvector;
import by.nc.tarazenko.dtos.GuestDTO;
import by.nc.tarazenko.entity.Attendance;
import by.nc.tarazenko.entity.Guest;
import by.nc.tarazenko.repository.AttendanceRepository;
import by.nc.tarazenko.repository.GuestRepository;
import by.nc.tarazenko.service.GuestService;

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
        Guest guest = guestRepository.findById(id).get();
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
    public void create(GuestDTO guestDTO) {
        guestRepository.saveAndFlush(guestConvector.fromDTO(guestDTO));
    }

    @Override
    public boolean update(GuestDTO guestDTO) {
        Guest guest = guestConvector.fromDTO(guestDTO);
        logger.debug(guest);
        boolean ok = true;
        if (guestRepository.findById(guest.getId()).isPresent()) {
            guestRepository.saveAndFlush(guest);
            logger.debug("Update guest id = " + guest.getId());
        } else {
            logger.debug("Guest not found.");
            ok = false;
        }
        return ok;
    }

    @Override
    public boolean deleteById(int guestId) {
        boolean ok = true;
        if (guestRepository.findById(guestId).isPresent()) {
            guestRepository.deleteById(guestId);
            logger.debug("Found and deleted with id = " + guestId);
        } else {
            logger.debug("Guest not found.");
            ok = false;
        }
        return ok;
    }

    @Override
    public List<Attendance> getAttendances(int guestId) {
        return guestRepository.findById(guestId).get().getAttendances();
    }

    @Override
    public int addAttendance(int guestId, int attendanceId) {
        Attendance attendance = attendanceRepository.getOne(attendanceId);
        Guest guest = guestRepository.getOne(guestId);
        int checker = 1;
        if (attendance == null) {
            checker = -1;
        } else if (guest == null) {
            checker = 0;
        } else {
            guest.getAttendances().add(attendance);
            guest.setBill(guest.getBill() + attendance.getCost());
            guestRepository.saveAndFlush(guest);
        }
        return checker;
    }
}
