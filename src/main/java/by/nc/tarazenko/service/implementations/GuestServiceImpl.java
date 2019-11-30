package by.nc.tarazenko.service.implementations;

import by.nc.tarazenko.dtos.GuestDTO;
import by.nc.tarazenko.entity.Guest;
import by.nc.tarazenko.entity.Passport;
import by.nc.tarazenko.repository.GuestRepository;
import by.nc.tarazenko.repository.PassportRepository;
import by.nc.tarazenko.service.GuestService;
//import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class GuestServiceImpl implements GuestService {
    final static Logger logger = Logger.getLogger(GuestServiceImpl.class);

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private PassportRepository passportRepository;

    @Override
    public Optional<GuestDTO> getById(int id) {
        Guest guest = guestRepository.findById(id).get();
       // guestDTO.setPhoneNumber(guest.getPhoneNumber());
        return null;
        //return null;
    }

    @Override
    public List<Guest> getAll() {
        //todo Сделал говно
        logger.info(guestRepository.findAll());
        return guestRepository.findAll();
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
       // guest.setPassport(passport);
        return guest;
    }

    @Override
    public Optional<GuestDTO> update(GuestDTO entity) {
        return Optional.empty();
    }


    @Override
    public void delete(GuestDTO entity) {

    }

    @Override
    public void deleteById(int entityId) {

    }
}
