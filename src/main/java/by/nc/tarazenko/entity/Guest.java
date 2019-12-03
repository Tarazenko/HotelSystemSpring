package by.nc.tarazenko.entity;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.util.*;

@Entity
//@Data
@Table(name = "guests")
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guest_id")
    private int id;

    @Column(name = "phone", nullable = true)
    private String phoneNumber;

    @Column(name = "bill")
    private double bill;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="passport_id")
    private Passport passport;

    /*@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "attendance_has_guests",
            joinColumns = {
                    @JoinColumn(name = "guest_id", referencedColumnName = "guest_id",
                            nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "attendance_id", referencedColumnName = "attendance_id",
                            nullable = false, updatable = false)})*/
    @ManyToMany
    @JoinTable(
            name = "attendance_has_guests",
            joinColumns = @JoinColumn(name = "guest_id"),
            inverseJoinColumns = @JoinColumn(name = "attendance_id"))
    List<Attendance> attendances = new ArrayList<>();

    public Guest() {
    }

    public Guest(String phoneNumber, double bill, Passport passport, List<Attendance> attendances) {
        this.phoneNumber = phoneNumber;
        this.bill = bill;
        this.passport = passport;
        this.attendances = attendances;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getBill() {
        return bill;
    }

    public void setBill(double bill) {
        this.bill = bill;
    }

    public Passport getPassport() {
        return passport;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }

    public List<Attendance> getAttendances() {
        return attendances;
    }

    public void setAttendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Guest guest = (Guest) o;
        return id == guest.id &&
                Double.compare(guest.bill, bill) == 0 &&
                Objects.equals(phoneNumber, guest.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, phoneNumber, bill);
    }

    @Override
    public String toString() {
        return "Guest{" +
                "id=" + id +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", bill=" + bill +
                '}';
    }
    /*  @OneToMany(mappedBy = "guest")
    Set<AttendancesGuestsConnect> attendancesGuestsConnects = new HashSet<>();*/
}
