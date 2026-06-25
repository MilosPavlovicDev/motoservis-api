package rs.metropolitan.motoservisapi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.metropolitan.motoservisapi.model.Part;
import rs.metropolitan.motoservisapi.model.ServiceRecord;
import rs.metropolitan.motoservisapi.repository.PartRepository;

import java.util.List;

@Service
public class PartService {

    private final PartRepository partRepository;
    private final ServiceRecordService serviceRecordService;

    public PartService(PartRepository partRepository,
                       ServiceRecordService serviceRecordService) {
        this.partRepository = partRepository;
        this.serviceRecordService = serviceRecordService;
    }

    public List<Part> findAll() {
        return partRepository.findAll();
    }

    public Part findById(Long id) {
        return partRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Part not found with id: " + id));
    }

    public List<Part> findByServiceRecordId(Long serviceRecordId) {
        return partRepository.findByServiceRecordId(serviceRecordId);
    }

    @Transactional
    public Part save(Part part, Long serviceRecordId) {
        ServiceRecord serviceRecord = serviceRecordService.findById(serviceRecordId);
        part.setServiceRecord(serviceRecord);
        return partRepository.save(part);
    }

    @Transactional
    public Part update(Long id, Part partData, Long serviceRecordId) {
        Part part = findById(id);
        ServiceRecord serviceRecord = serviceRecordService.findById(serviceRecordId);
        part.setName(partData.getName());
        part.setPrice(partData.getPrice());
        part.setServiceRecord(serviceRecord);
        return partRepository.save(part);
    }

    @Transactional
    public void deleteById(Long id) {
        findById(id);
        partRepository.deleteById(id);
    }
}