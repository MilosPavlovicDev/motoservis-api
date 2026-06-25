package rs.metropolitan.motoservisapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.metropolitan.motoservisapi.model.Motorcycle;
import rs.metropolitan.motoservisapi.service.MotorcycleService;

import java.util.List;

@RestController
@RequestMapping("/api/motorcycles")
public class MotorcycleController {

    private final MotorcycleService motorcycleService;

    public MotorcycleController(MotorcycleService motorcycleService) {
        this.motorcycleService = motorcycleService;
    }

    @GetMapping
    public ResponseEntity<List<Motorcycle>> findAll() {
        return ResponseEntity.ok(motorcycleService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Motorcycle> findById(@PathVariable Long id) {
        return ResponseEntity.ok(motorcycleService.findById(id));
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<Motorcycle>> findByOwner(@PathVariable Long ownerId) {
        return ResponseEntity.ok(motorcycleService.findByOwnerId(ownerId));
    }

    @PostMapping
    public ResponseEntity<Motorcycle> save(@RequestBody Motorcycle motorcycle,
                                           @RequestParam Long ownerId) {
        return ResponseEntity.ok(motorcycleService.save(motorcycle));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Motorcycle> update(@PathVariable Long id,
                                             @RequestBody Motorcycle motorcycle,
                                             @RequestParam Long ownerId) {
        return ResponseEntity.ok(motorcycleService.update(id, motorcycle, ownerId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        motorcycleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
