package rs.metropolitan.motoservisapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.metropolitan.motoservisapi.model.Mechanic;
import rs.metropolitan.motoservisapi.service.MechanicService;

import java.util.List;

@RestController
@RequestMapping("/api/mechanics")
public class MechanicController {

    private final MechanicService mechanicService;

    public MechanicController(MechanicService mechanicService) {
        this.mechanicService = mechanicService;
    }

    @GetMapping
    public ResponseEntity<List<Mechanic>> findAll() {
        return ResponseEntity.ok(mechanicService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mechanic> findById(@PathVariable Long id) {
        return ResponseEntity.ok(mechanicService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Mechanic> save(@RequestBody Mechanic mechanic) {
        return ResponseEntity.ok(mechanicService.save(mechanic));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mechanic> update(@PathVariable Long id,
                                           @RequestBody Mechanic mechanic) {
        return ResponseEntity.ok(mechanicService.update(id, mechanic));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        mechanicService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
