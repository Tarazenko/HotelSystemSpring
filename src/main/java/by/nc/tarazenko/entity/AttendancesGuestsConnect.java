package by.nc.tarazenko.entity;

import by.nc.tarazenko.util.GuestAttendancesKey;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "attendance_has_guests")
public class AttendancesGuestsConnect {

    @EmbeddedId
    GuestAttendancesKey id;

    @ManyToOne
    @MapsId("guest_id")
    @JoinColumn(name = "guest_id")
    Guest guest;

    @ManyToOne
    @MapsId("attendance_id")
    @JoinColumn(name = "attendance_id")
    Attendance attendance;

    int amount;
}
