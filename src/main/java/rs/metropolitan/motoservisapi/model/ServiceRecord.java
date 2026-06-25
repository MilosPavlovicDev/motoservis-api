package rs.metropolitan.motoservisapi.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "service_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "motorcycle_id")
    private Motorcycle motorcycle;

    @ManyToOne
    @JoinColumn(name = "mechanic_id")
    private Mechanic mechanic;

    private LocalDate date;
    private String problemDescription;
    private Double laborCost;

    @Enumerated(EnumType.STRING)
    private ServiceStatus status;

    @JsonManagedReference
    @OneToMany(mappedBy = "serviceRecord", cascade = CascadeType.ALL)
    private List<Part> parts;
}