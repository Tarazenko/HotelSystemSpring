package by.nc.tarazenko.service.implementations;

import by.nc.tarazenko.dtos.GuestDTO;
import by.nc.tarazenko.entity.Attendance;
import by.nc.tarazenko.entity.AttendancesGuestsConnect;
import by.nc.tarazenko.entity.Guest;
import by.nc.tarazenko.entity.Passport;
import by.nc.tarazenko.repository.AttendanceRepository;
import by.nc.tarazenko.repository.AttendancesGuestsConnectRepository;
import by.nc.tarazenko.repository.GuestRepository;
import by.nc.tarazenko.repository.PassportRepository;
import by.nc.tarazenko.service.GuestService;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class GuestServiceImpl implements GuestService {
    final static Logger logger = Logger.getLogger(GuestServiceImpl.class);

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private GuestRepository guestRepository;

    @Override
    public GuestDTO getById(int id) {
        Guest guest = guestRepository.findById(id).get();
        return toDTO(guest);
    }

    @Override
    public List<GuestDTO> getAll() {
        logger.info("Get all from database.");
        List<Guest> guests = guestRepository.findAll();
        List<GuestDTO> guestDTOs = new ArrayList<>();
        for (Guest guest : guests) {
            guestDTOs.add(toDTO(guest));
        }
        return guestDTOs;
    }

    @Override
    public void create(GuestDTO guestDTO) {
        guestRepository.saveAndFlush(fromDTO(guestDTO));
    }

    private Guest fromDTO(GuestDTO guestDTO) {
        Guest guest = new Guest();
        guest.setPhoneNumber(guestDTO.getPhoneNumber());
        guest.setId(guestDTO.getId());
        Passport passport = new Passport();
        passport.setNumber(guestDTO.getPassportNumber());
        passport.setFirstName(guestDTO.getFirstName());
        passport.setSecondName(guestDTO.getSecondName());
        passport.setThirdName(guestDTO.getThirdName());
        guest.setPassport(passport);
        return guest;
    }

    private GuestDTO toDTO(Guest guest) {
        if (guest != null) {
            GuestDTO guestDTO = new GuestDTO();
            guestDTO.setId(guest.getId());
            guestDTO.setBill(guest.getBill());
            guestDTO.setPhoneNumber(guest.getPhoneNumber());
            /*List<Reservation> reservations = reservationRepository.getByGuest(guest.getId());
            List<Integer> numbers = new ArrayList<>();
            if (reservations != null) {
                for (Reservation reservation : reservations)
                    numbers.add(reservation.getGuest().getId());
                guestDTO.setRoomNumbers(numbers);
            }
            */
            guestDTO.setFirstName(guest.getPassport().getFirstName());
            guestDTO.setSecondName(guest.getPassport().getSecondName());
            guestDTO.setThirdName(guest.getPassport().getThirdName());
            guestDTO.setPassportNumber(guest.getPassport().getNumber());
            List<Attendance> attendances = guestRepository.findById(guest.getId()).get().getAttendances();
           /* List<String> attendanceNames = new ArrayList<>();
            List<Double> attendanceCosts = new ArrayList<>();
            for (Attendance attendance : attendances) {
                attendanceNames.add(attendance.getName());
                attendanceCosts.add(attendance.getCost());
            }
            guestDTO.setAttendanceCost(attendanceCosts);
            guestDTO.setAttendanceName(attendanceNames);*/
            guestDTO.setAttendances(attendances);
            return guestDTO;
        }
        return null;
    }

    @Override
    public boolean update(GuestDTO guestDTO) {
        Guest guest = fromDTO(guestDTO);
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
