package by.nc.tarazenko.util;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class GuestAttendancesKey implements Serializable {
    @Column(name = "attendance_id")
    int attendanceId;

    @Column(name = "guest_id")
    int guest_id;
}
