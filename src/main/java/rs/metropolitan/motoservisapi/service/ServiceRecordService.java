package rs.metropolitan.motoservisapi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.metropolitan.motoservisapi.model.Mechanic;
import rs.metropolitan.motoservisapi.model.Motorcycle;
import rs.metropolitan.motoservisapi.model.ServiceRecord;
import rs.metropolitan.motoservisapi.model.ServiceStatus;
import rs.metropolitan.motoservisapi.repository.ServiceRecordRepository;

import java.util.List;

@Service
public class ServiceRecordService {

    private final ServiceRecordRepository serviceRecordRepository;
    private final MotorcycleService motorcycleService;
    private final MechanicService mechanicService;

    public ServiceRecordService(ServiceRecordRepository serviceRecordRepository,
                                MotorcycleService motorcycleService,
                                MechanicService mechanicService) {
        this.serviceRecordRepository = serviceRecordRepository;
        this.motorcycleService = motorcycleService;
        this.mechanicService = mechanicService;
    }

    public List<ServiceRecord> findAll() {
        return serviceRecordRepository.findAll();
    }

    public ServiceRecord findById(Long id) {
        return serviceRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ServiceRecord not found with id: " + id));
    }

    public List<ServiceRecord> findByMotorcycleId(Long motorcycleId) {
        return serviceRecordRepository.findByMotorcycleId(motorcycleId);
    }

    @Transactional
    public ServiceRecord save(ServiceRecord serviceRecord,
                              Long motorcycleId,
                              Long mechanicId) {
        Motorcycle motorcycle = motorcycleService.findById(motorcycleId);
        Mechanic mechanic = mechanicService.findById(mechanicId);
        serviceRecord.setMotorcycle(motorcycle);
        serviceRecord.setMechanic(mechanic);
        return serviceRecordRepository.save(serviceRecord);
    }

    @Transactional
    public ServiceRecord update(Long id, ServiceRecord data,
                                Long motorcycleId, Long mechanicId) {
        ServiceRecord sr = findById(id);
        Motorcycle motorcycle = motorcycleService.findById(motorcycleId);
        Mechanic mechanic = mechanicService.findById(mechanicId);
        sr.setMotorcycle(motorcycle);
        sr.setMechanic(mechanic);
        sr.setDate(data.getDate());
        sr.setProblemDescription(data.getProblemDescription());
        sr.setLaborCost(data.getLaborCost());
        sr.setStatus(data.getStatus());
        return serviceRecordRepository.save(sr);
    }

    // Posebna funkcionalnost - ukupna cena servisa (rad + delovi)
    public Double calculateTotalCost(Long serviceRecordId) {
        ServiceRecord sr = findById(serviceRecordId);
        Double partsCost = sr.getParts() == null ? 0.0 :
                sr.getParts().stream()
                        .mapToDouble(p -> p.getPrice() != null ? p.getPrice() : 0.0)
                        .sum();
        return sr.getLaborCost() + partsCost;
    }

    @Transactional
    public void deleteById(Long id) {
        findById(id);
        serviceRecordRepository.deleteById(id);
    }
}
