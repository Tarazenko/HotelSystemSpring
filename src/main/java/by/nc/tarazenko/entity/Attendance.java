package by.nc.tarazenko.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "attendance")
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendance_id")
    int id;

    @Column(name = "name")
    String name;

    @Column(name = "cost")
    double cost;

    @JsonIgnore
    @ManyToMany(mappedBy = "attendances", fetch = FetchType.LAZY)
    private List<Guest> guests = new ArrayList<>();

    public Attendance() {
    }

    public Attendance(int id, String name, double cost, List<Guest> guests) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.guests = guests;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public List<Guest> getGuests() {
        return guests;
    }

    public void setGuests(List<Guest> guests) {
        this.guests = guests;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attendance that = (Attendance) o;
        return id == that.id &&
                Double.compare(that.cost, cost) == 0 &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, cost);
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                '}';
    }
    /* @OneToMany(mappedBy = "attendance")
    Set<AttendancesGuestsConnect> attendancesGuestsConnects;*/
}
