package rs.metropolitan.motoservisapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.metropolitan.motoservisapi.model.ServiceRecord;

import java.util.List;

@Repository
public interface ServiceRecordRepository extends JpaRepository<ServiceRecord, Long> {
    List<ServiceRecord> findByMotorcycleId(Long motorcycleId);
}