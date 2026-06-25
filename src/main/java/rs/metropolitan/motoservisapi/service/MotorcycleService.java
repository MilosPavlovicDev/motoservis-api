package rs.metropolitan.motoservisapi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.metropolitan.motoservisapi.model.Motorcycle;
import rs.metropolitan.motoservisapi.model.Owner;
import rs.metropolitan.motoservisapi.repository.MotorcycleRepository;

import java.util.List;

@Service
public class MotorcycleService {

    private final MotorcycleRepository motorcycleRepository;
    private final OwnerService ownerService;

    public MotorcycleService(MotorcycleRepository motorcycleRepository,
                             OwnerService ownerService) {
        this.motorcycleRepository = motorcycleRepository;
        this.ownerService = ownerService;
    }

    public List<Motorcycle> findAll() {
        return motorcycleRepository.findAll();
    }

    public Motorcycle findById(Long id) {
        return motorcycleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Motorcycle not found with id: " + id));
    }

    public List<Motorcycle> findByOwnerId(Long ownerId) {
        return motorcycleRepository.findByOwnerId(ownerId);
    }

    @Transactional
    public Motorcycle save(Motorcycle motorcycle) {
        return motorcycleRepository.save(motorcycle);
    }

    @Transactional
    public Motorcycle update(Long id, Motorcycle motorcycleData, Long ownerId) {
        Motorcycle motorcycle = findById(id);
        Owner owner = ownerService.findById(ownerId);
        motorcycle.setBrand(motorcycleData.getBrand());
        motorcycle.setModel(motorcycleData.getModel());
        motorcycle.setYearOfManufacture(motorcycleData.getYearOfManufacture());
        motorcycle.setLicensePlate(motorcycleData.getLicensePlate());
        motorcycle.setOwner(owner);
        return motorcycleRepository.save(motorcycle);
    }

    @Transactional
    public void deleteById(Long id) {
        findById(id);
        motorcycleRepository.deleteById(id);
    }
}
