package rs.metropolitan.motoservisapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.metropolitan.motoservisapi.model.Part;
import rs.metropolitan.motoservisapi.service.PartService;

import java.util.List;

@RestController
@RequestMapping("/api/parts")
public class PartController {

    private final PartService partService;

    public PartController(PartService partService) {
        this.partService = partService;
    }

    @GetMapping
    public ResponseEntity<List<Part>> findAll() {
        return ResponseEntity.ok(partService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Part> findById(@PathVariable Long id) {
        return ResponseEntity.ok(partService.findById(id));
    }

    @GetMapping("/service-record/{serviceRecordId}")
    public ResponseEntity<List<Part>> findByServiceRecord(
            @PathVariable Long serviceRecordId) {
        return ResponseEntity.ok(partService.findByServiceRecordId(serviceRecordId));
    }

    @PostMapping
    public ResponseEntity<Part> save(@RequestBody Part part,
                                     @RequestParam Long serviceRecordId) {
        return ResponseEntity.ok(partService.save(part, serviceRecordId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Part> update(@PathVariable Long id,
                                       @RequestBody Part part,
                                       @RequestParam Long serviceRecordId) {
        return ResponseEntity.ok(partService.update(id, part, serviceRecordId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        partService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
