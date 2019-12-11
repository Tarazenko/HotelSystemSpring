package by.nc.tarazenko.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "passport")
@NoArgsConstructor
public class Passport {
    @Id
    @Column(name = "passport_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private int id;

    @Column(name = "first_name")
    @Getter
    @Setter
    private String firstName;

    @Column(name = "second_name")
    @Getter
    @Setter
    private String secondName;

    @Column(name = "third_name")
    @Getter
    @Setter
    private String thirdName;

    @Column(name = "number")
    @Getter
    @Setter
    private String number;

    @OneToOne(mappedBy = "passport")
    private Guest guest;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passport passport = (Passport) o;
        return id == passport.id &&
                Objects.equals(firstName, passport.firstName) &&
                Objects.equals(secondName, passport.secondName) &&
                Objects.equals(thirdName, passport.thirdName) &&
                Objects.equals(number, passport.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, secondName, thirdName, number);
    }

    @Override
    public String toString() {
        return "Passport{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", thirdName='" + thirdName + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
