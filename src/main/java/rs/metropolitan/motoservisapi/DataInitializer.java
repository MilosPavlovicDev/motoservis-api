package rs.metropolitan.motoservisapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import rs.metropolitan.motoservisapi.model.*;
import rs.metropolitan.motoservisapi.repository.*;
import rs.metropolitan.motoservisapi.service.UserService;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserService userService;
    private final OwnerRepository ownerRepository;
    private final MotorcycleRepository motorcycleRepository;
    private final MechanicRepository mechanicRepository;
    private final ServiceRecordRepository serviceRecordRepository;
    private final PartRepository partRepository;

    public DataInitializer(UserService userService,
                           OwnerRepository ownerRepository,
                           MotorcycleRepository motorcycleRepository,
                           MechanicRepository mechanicRepository,
                           ServiceRecordRepository serviceRecordRepository,
                           PartRepository partRepository) {
        this.userService = userService;
        this.ownerRepository = ownerRepository;
        this.motorcycleRepository = motorcycleRepository;
        this.mechanicRepository = mechanicRepository;
        this.serviceRecordRepository = serviceRecordRepository;
        this.partRepository = partRepository;
    }

    @Override
    public void run(String... args) {
        if (ownerRepository.count() > 0) return;

        userService.register("admin", "admin123", Role.ROLE_ADMIN);
        userService.register("user", "user123", Role.ROLE_USER);

        Owner owner1 = ownerRepository.save(new Owner(null, "Milos", "Pavlovic", "0641234567", "milos@email.com"));
        Owner owner2 = ownerRepository.save(new Owner(null, "Marko", "Markovic", "0651234567", "marko@email.com"));

        Mechanic mechanic1 = mechanicRepository.save(new Mechanic(null, "Nikola", "Nikolic", "Motori i transmisija"));
        Mechanic mechanic2 = mechanicRepository.save(new Mechanic(null, "Stefan", "Stefanovic", "Elektrika"));

        Motorcycle moto1 = motorcycleRepository.save(new Motorcycle(null, "Yamaha", "MT-07", 2021, "BG123-AA", owner1));
        Motorcycle moto2 = motorcycleRepository.save(new Motorcycle(null, "Honda", "CB650R", 2020, "NS456-BB", owner2));

        ServiceRecord sr1 = serviceRecordRepository.save(new ServiceRecord(null, moto1, mechanic1, LocalDate.now(), "Zamena ulja i filtera", 3500.0, ServiceStatus.COMPLETED, null));
        ServiceRecord sr2 = serviceRecordRepository.save(new ServiceRecord(null, moto2, mechanic2, LocalDate.now(), "Dijagnostika elektrike", 2000.0, ServiceStatus.IN_PROGRESS, null));

        partRepository.save(new Part(null, "Filter ulja", 800.0, sr1));
        partRepository.save(new Part(null, "Motorno ulje 4L", 2400.0, sr1));
        partRepository.save(new Part(null, "Osiguraci set", 350.0, sr2));
    }
}
