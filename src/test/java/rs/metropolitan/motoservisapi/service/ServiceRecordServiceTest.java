package rs.metropolitan.motoservisapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.metropolitan.motoservisapi.model.*;
import rs.metropolitan.motoservisapi.repository.ServiceRecordRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceRecordServiceTest {

    @Mock
    private ServiceRecordRepository serviceRecordRepository;

    @Mock
    private MotorcycleService motorcycleService;

    @Mock
    private MechanicService mechanicService;

    @InjectMocks
    private ServiceRecordService serviceRecordService;

    private Owner owner;
    private Motorcycle motorcycle;
    private Mechanic mechanic;
    private ServiceRecord serviceRecord;
    private Part part1;
    private Part part2;

    @BeforeEach
    void setUp() {
        owner = new Owner(1L, "Milos", "Pavlovic", "064123", "milos@email.com");
        motorcycle = new Motorcycle(1L, "Yamaha", "MT-07", 2021, "BG123-AA", owner);
        mechanic = new Mechanic(1L, "Nikola", "Nikolic", "Motori");
        part1 = new Part(1L, "Filter ulja", 800.0, null);
        part2 = new Part(2L, "Motorno ulje", 2400.0, null);
        serviceRecord = new ServiceRecord(1L, motorcycle, mechanic,
                LocalDate.now(), "Zamena ulja", 3500.0,
                ServiceStatus.IN_PROGRESS, List.of(part1, part2));
    }

    @Test
    void findAll_shouldReturnAllServiceRecords() {
        when(serviceRecordRepository.findAll()).thenReturn(List.of(serviceRecord));

        List<ServiceRecord> result = serviceRecordService.findAll();

        assertEquals(1, result.size());
        assertEquals("Zamena ulja", result.get(0).getProblemDescription());
        verify(serviceRecordRepository, times(1)).findAll();
    }

    @Test
    void findById_shouldReturnServiceRecord_whenExists() {
        when(serviceRecordRepository.findById(1L)).thenReturn(Optional.of(serviceRecord));

        ServiceRecord result = serviceRecordService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(ServiceStatus.IN_PROGRESS, result.getStatus());
    }

    @Test
    void findById_shouldThrowException_whenNotFound() {
        when(serviceRecordRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> serviceRecordService.findById(99L));

        assertEquals("ServiceRecord not found with id: 99", exception.getMessage());
    }

    @Test
    void calculateTotalCost_shouldReturnLaborPlusParts() {
        when(serviceRecordRepository.findById(1L)).thenReturn(Optional.of(serviceRecord));

        Double totalCost = serviceRecordService.calculateTotalCost(1L);

        // laborCost (3500) + part1 (800) + part2 (2400) = 6700
        assertEquals(6700.0, totalCost);
    }

    @Test
    void calculateTotalCost_shouldReturnOnlyLaborCost_whenNoParts() {
        serviceRecord.setParts(null);
        when(serviceRecordRepository.findById(1L)).thenReturn(Optional.of(serviceRecord));

        Double totalCost = serviceRecordService.calculateTotalCost(1L);

        assertEquals(3500.0, totalCost);
    }

    @Test
    void save_shouldSetMotorcycleAndMechanicAndSave() {
        when(motorcycleService.findById(1L)).thenReturn(motorcycle);
        when(mechanicService.findById(1L)).thenReturn(mechanic);
        when(serviceRecordRepository.save(any(ServiceRecord.class))).thenReturn(serviceRecord);

        ServiceRecord newRecord = new ServiceRecord();
        newRecord.setDate(LocalDate.now());
        newRecord.setLaborCost(2000.0);
        newRecord.setStatus(ServiceStatus.IN_PROGRESS);

        ServiceRecord result = serviceRecordService.save(newRecord, 1L, 1L);

        assertNotNull(result);
        verify(motorcycleService, times(1)).findById(1L);
        verify(mechanicService, times(1)).findById(1L);
        verify(serviceRecordRepository, times(1)).save(newRecord);
    }

    @Test
    void findByMotorcycleId_shouldReturnFilteredRecords() {
        when(serviceRecordRepository.findByMotorcycleId(1L)).thenReturn(List.of(serviceRecord));

        List<ServiceRecord> result = serviceRecordService.findByMotorcycleId(1L);

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getMotorcycle().getId());
    }
}
