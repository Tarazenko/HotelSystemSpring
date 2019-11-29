package by.nc.tarazenko.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
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

    @OneToOne
    @JoinTable(name = "passport",
            joinColumns = @JoinColumn(name = "passport_id", insertable = false, updatable = false),
    inverseJoinColumns = @JoinColumn(name = "passport_id", insertable = false, updatable = false))
    private Passport passport;
}
