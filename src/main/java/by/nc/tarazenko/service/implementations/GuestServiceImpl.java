package by.nc.tarazenko.service.implementations;

import by.nc.tarazenko.dtos.GuestDTO;
import by.nc.tarazenko.entity.Guest;
import by.nc.tarazenko.entity.Passport;
import by.nc.tarazenko.repository.GuestRepository;
import by.nc.tarazenko.repository.PassportRepository;
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
    private GuestRepository guestRepository;

    @Autowired
    private PassportRepository passportRepository;

    @Override
    public GuestDTO getById(int id) {
        Guest guest = guestRepository.findById(id).get();
        return toDTO(guest);
    }

    @Override
    public List<GuestDTO> getAll() {
        logger.info("Get all from database.");
        logger.debug(guestRepository.getOne(1).toString());
        List<Guest> guests = guestRepository.findAll();
        logger.debug(guests);

        List<GuestDTO> guestDTOs = new ArrayList<>();
        for (Guest guest : guests){
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
            /*List<Attendance> attendances = guestRepository.getAttendances(guest.getId());
            List<String> attendanceNames = new ArrayList<>();
            List<Double> attendanceCosts = new ArrayList<>();
            for (Attendance attendance : attendances) {
                attendanceNames.add(attendance.getName());
                attendanceCosts.add(attendance.getCost());
            }
            guestDTO.setAttendanceCost(attendanceCosts);
            guestDTO.setAttendanceName(attendanceNames);*/
            return guestDTO;
        }
        return null;
    }

    @Override
    public GuestDTO update(GuestDTO entity) {
        return null;
    }


    @Override
    public void delete(GuestDTO entity) {

    }

    @Override
    public void deleteById(int entityId) {

    }
}
