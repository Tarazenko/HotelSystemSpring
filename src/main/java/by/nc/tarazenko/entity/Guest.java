package by.nc.tarazenko.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
@Table(name = "guests")
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guestId")
    private int id;

    @Column(name = "phone", nullable = true)
    private String phoneNumber;

    @Column(name = "bill")
    private double bill;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="passport_id")
    private Passport passport;

    @ManyToMany
    @JoinTable(
            name = "attendance_has_guests",
            joinColumns = @JoinColumn(name = "guestId"),
            inverseJoinColumns = @JoinColumn(name = "attendance_id"))
    List<Attendance> attendances = new ArrayList<>();
}
