package rs.metropolitan.motoservisapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.metropolitan.motoservisapi.model.Owner;
import rs.metropolitan.motoservisapi.repository.OwnerRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerServiceTest {

    @Mock
    private OwnerRepository ownerRepository;

    @InjectMocks
    private OwnerService ownerService;

    private Owner owner;

    @BeforeEach
    void setUp() {
        owner = new Owner(1L, "Milos", "Pavlovic", "0641234567", "milos@email.com");
    }

    @Test
    void findAll_shouldReturnListOfOwners() {
        when(ownerRepository.findAll()).thenReturn(List.of(owner));

        List<Owner> result = ownerService.findAll();

        assertEquals(1, result.size());
        assertEquals("Milos", result.get(0).getFirstName());
        verify(ownerRepository, times(1)).findAll();
    }

    @Test
    void findById_shouldReturnOwner_whenExists() {
        when(ownerRepository.findById(1L)).thenReturn(Optional.of(owner));

        Owner result = ownerService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Pavlovic", result.getLastName());
    }

    @Test
    void findById_shouldThrowException_whenNotFound() {
        when(ownerRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> ownerService.findById(99L));

        assertEquals("Owner not found with id: 99", exception.getMessage());
    }

    @Test
    void save_shouldReturnSavedOwner() {
        when(ownerRepository.save(owner)).thenReturn(owner);

        Owner result = ownerService.save(owner);

        assertNotNull(result);
        assertEquals("Milos", result.getFirstName());
        verify(ownerRepository, times(1)).save(owner);
    }

    @Test
    void update_shouldUpdateAndReturnOwner() {
        Owner updatedData = new Owner(null, "Nikola", "Nikolic", "0691234567", "nikola@email.com");
        when(ownerRepository.findById(1L)).thenReturn(Optional.of(owner));
        when(ownerRepository.save(any(Owner.class))).thenReturn(owner);

        Owner result = ownerService.update(1L, updatedData);

        assertEquals("Nikola", result.getFirstName());
        assertEquals("Nikolic", result.getLastName());
        verify(ownerRepository, times(1)).save(owner);
    }

    @Test
    void deleteById_shouldDeleteOwner_whenExists() {
        when(ownerRepository.findById(1L)).thenReturn(Optional.of(owner));
        doNothing().when(ownerRepository).deleteById(1L);

        assertDoesNotThrow(() -> ownerService.deleteById(1L));

        verify(ownerRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteById_shouldThrowException_whenNotFound() {
        when(ownerRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> ownerService.deleteById(99L));

        verify(ownerRepository, never()).deleteById(any());
    }
}
