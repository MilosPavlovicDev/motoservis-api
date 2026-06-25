package rs.metropolitan.motoservisapi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.metropolitan.motoservisapi.model.Mechanic;
import rs.metropolitan.motoservisapi.repository.MechanicRepository;

import java.util.List;

@Service
public class MechanicService {

    private final MechanicRepository mechanicRepository;

    public MechanicService(MechanicRepository mechanicRepository) {
        this.mechanicRepository = mechanicRepository;
    }

    public List<Mechanic> findAll() {
        return mechanicRepository.findAll();
    }

    public Mechanic findById(Long id) {
        return mechanicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mechanic not found with id: " + id));
    }

    @Transactional
    public Mechanic save(Mechanic mechanic) {
        return mechanicRepository.save(mechanic);
    }

    @Transactional
    public Mechanic update(Long id, Mechanic mechanicData) {
        Mechanic mechanic = findById(id);
        mechanic.setFirstName(mechanicData.getFirstName());
        mechanic.setLastName(mechanicData.getLastName());
        mechanic.setSpecialization(mechanicData.getSpecialization());
        return mechanicRepository.save(mechanic);
    }

    @Transactional
    public void deleteById(Long id) {
        findById(id);
        mechanicRepository.deleteById(id);
    }
}
