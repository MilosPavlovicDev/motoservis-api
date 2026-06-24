package rs.metropolitan.motoservisapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.metropolitan.motoservisapi.model.Part;

import java.util.List;

@Repository
public interface PartRepository extends JpaRepository<Part, Long> {
    List<Part> findByServiceRecordId(Long serviceRecordId);
}