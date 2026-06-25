package rs.metropolitan.motoservisapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.metropolitan.motoservisapi.model.ServiceRecord;
import rs.metropolitan.motoservisapi.service.ServiceRecordService;

import java.util.List;

@RestController
@RequestMapping("/api/service-records")
public class ServiceRecordController {

    private final ServiceRecordService serviceRecordService;

    public ServiceRecordController(ServiceRecordService serviceRecordService) {
        this.serviceRecordService = serviceRecordService;
    }

    @GetMapping
    public ResponseEntity<List<ServiceRecord>> findAll() {
        return ResponseEntity.ok(serviceRecordService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceRecord> findById(@PathVariable Long id) {
        return ResponseEntity.ok(serviceRecordService.findById(id));
    }

    @GetMapping("/motorcycle/{motorcycleId}")
    public ResponseEntity<List<ServiceRecord>> findByMotorcycle(
            @PathVariable Long motorcycleId) {
        return ResponseEntity.ok(serviceRecordService.findByMotorcycleId(motorcycleId));
    }

    @GetMapping("/{id}/total-cost")
    public ResponseEntity<Double> getTotalCost(@PathVariable Long id) {
        return ResponseEntity.ok(serviceRecordService.calculateTotalCost(id));
    }

    @PostMapping
    public ResponseEntity<ServiceRecord> save(@RequestBody ServiceRecord serviceRecord,
                                              @RequestParam Long motorcycleId,
                                              @RequestParam Long mechanicId) {
        return ResponseEntity.ok(
                serviceRecordService.save(serviceRecord, motorcycleId, mechanicId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceRecord> update(@PathVariable Long id,
                                                @RequestBody ServiceRecord serviceRecord,
                                                @RequestParam Long motorcycleId,
                                                @RequestParam Long mechanicId) {
        return ResponseEntity.ok(
                serviceRecordService.update(id, serviceRecord, motorcycleId, mechanicId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        serviceRecordService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
