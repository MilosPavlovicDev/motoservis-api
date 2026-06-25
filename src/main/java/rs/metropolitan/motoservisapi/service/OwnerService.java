package rs.metropolitan.motoservisapi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.metropolitan.motoservisapi.model.Owner;
import rs.metropolitan.motoservisapi.repository.OwnerRepository;

import java.util.List;

@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;

    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    public List<Owner> findAll() {
        return ownerRepository.findAll();
    }

    public Owner findById(Long id) {
        return ownerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Owner not found with id: " + id));
    }

    @Transactional
    public Owner save(Owner owner) {
        return ownerRepository.save(owner);
    }

    @Transactional
    public Owner update(Long id, Owner ownerData) {
        Owner owner = findById(id);
        owner.setFirstName(ownerData.getFirstName());
        owner.setLastName(ownerData.getLastName());
        owner.setPhone(ownerData.getPhone());
        owner.setEmail(ownerData.getEmail());
        return ownerRepository.save(owner);
    }

    @Transactional
    public void deleteById(Long id) {
        findById(id);
        ownerRepository.deleteById(id);
    }
}
