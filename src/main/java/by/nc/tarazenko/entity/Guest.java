package by.nc.tarazenko.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@Table(name = "guests")
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guestId")
    private int id;

    @Column(name = "phone")
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

    @OneToMany(mappedBy = "guest")
    private List<Reservation> reservations;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Guest guest = (Guest) o;
        return id == guest.id &&
                Double.compare(guest.bill, bill) == 0 &&
                Objects.equals(phoneNumber, guest.phoneNumber) &&
                Objects.equals(passport, guest.passport);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, phoneNumber, bill, passport);
    }

    @Override
    public String toString() {
        return "Guest{" +
                "id=" + id +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", bill=" + bill +
                ", passport=" + passport +
                ", attendances=" + attendances +
                '}';
    }
}
