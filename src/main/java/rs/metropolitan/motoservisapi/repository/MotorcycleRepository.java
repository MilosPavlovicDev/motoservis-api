package rs.metropolitan.motoservisapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.metropolitan.motoservisapi.model.Motorcycle;

import java.util.List;

@Repository
public interface MotorcycleRepository extends JpaRepository<Motorcycle, Long> {
    List<Motorcycle> findByOwnerId(Long ownerId);
}
