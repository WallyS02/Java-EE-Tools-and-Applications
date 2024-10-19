package org.demo.demo.configuration.singleton;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.demo.demo.musician.entity.Musician;
import org.demo.demo.musician.entity.MusicianRoles;
import org.demo.demo.musician.repository.api.MusicianRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Singleton
@Startup
@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
@NoArgsConstructor(force = true)
public class InitializeAdminService {
    private final MusicianRepository musicianRepository;

    @Inject
    public InitializeAdminService(MusicianRepository musicianRepository) {
        this.musicianRepository = musicianRepository;
    }

    @PostConstruct
    @SneakyThrows
    private void init() {
        if (musicianRepository.findByLogin("admin-service").isEmpty()) {

            Musician admin = Musician.builder()
                    .id(UUID.fromString("14d59f3a-057c-44d5-825a-19295a6600a8"))
                    .login("admin-service")
                    .firstName("admin")
                    .lastName("admin")
                    .birthday(LocalDate.of(1990, 10, 21))
                    .albumsReleased(0)
                    .email("admin-service@music.example.com")
                    .password("adminadmin")
                    .skills(List.of())
                    .roles(List.of(MusicianRoles.ADMIN, MusicianRoles.USER))
                    .build();

            musicianRepository.create(admin);
        }
    }
}
