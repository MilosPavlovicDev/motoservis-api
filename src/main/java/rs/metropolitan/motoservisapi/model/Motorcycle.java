package rs.metropolitan.motoservisapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "motorcycles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Motorcycle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    private Integer yearOfManufacture;
    private String licensePlate;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;
}