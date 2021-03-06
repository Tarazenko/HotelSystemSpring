package by.nc.tarazenko.convector;

import by.nc.tarazenko.dtos.GuestDTO;
import by.nc.tarazenko.entity.Attendance;
import by.nc.tarazenko.entity.Guest;
import by.nc.tarazenko.entity.Passport;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class GuestConvector implements Convector<GuestDTO, Guest> {
    @Override
    public GuestDTO toDTO(Guest guest) {
        if (guest != null) {
            GuestDTO guestDTO = new GuestDTO();
            guestDTO.setId(guest.getId());
            guestDTO.setBill(guest.getBill());
            guestDTO.setPhoneNumber(guest.getPhoneNumber());
            guestDTO.setFirstName(guest.getPassport().getFirstName());
            guestDTO.setSecondName(guest.getPassport().getSecondName());
            guestDTO.setThirdName(guest.getPassport().getThirdName());
            guestDTO.setPassportNumber(guest.getPassport().getNumber());
            List<Attendance> attendances = guest.getAttendances();
            guestDTO.setAttendances(attendances);
            return guestDTO;
        }
        return null;
    }

    @Override
    public Guest fromDTO(GuestDTO guestDTO) {
        Guest guest = new Guest();
        guest.setPhoneNumber(guestDTO.getPhoneNumber());
        guest.setId(guestDTO.getId());
        guest.setBill(guestDTO.getBill());
        Passport passport = new Passport();
        passport.setNumber(guestDTO.getPassportNumber());
        passport.setFirstName(guestDTO.getFirstName());
        passport.setSecondName(guestDTO.getSecondName());
        passport.setThirdName(guestDTO.getThirdName());
        guest.setPassport(passport);
        guest.setAttendances(guestDTO.getAttendances());
        return guest;
    }
}
