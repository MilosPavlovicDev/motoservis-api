package rs.metropolitan.motoservisapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.metropolitan.motoservisapi.model.Owner;
import rs.metropolitan.motoservisapi.service.OwnerService;

import java.util.List;

@RestController
@RequestMapping("/api/owners")
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping
    public ResponseEntity<List<Owner>> findAll() {
        return ResponseEntity.ok(ownerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Owner> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ownerService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Owner> save(@RequestBody Owner owner) {
        return ResponseEntity.ok(ownerService.save(owner));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Owner> update(@PathVariable Long id,
                                        @RequestBody Owner owner) {
        return ResponseEntity.ok(ownerService.update(id, owner));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ownerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
