package org.demo.demo.configuration.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.SneakyThrows;
import org.demo.demo.instrument.entity.Instrument;
import org.demo.demo.instrument.service.InstrumentService;
import org.demo.demo.musician.entity.Musician;
import org.demo.demo.musician.service.MusicianService;
import org.demo.demo.skill.entity.Skill;
import org.demo.demo.skill.service.SkillService;
import org.demo.demo.util.Level;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@WebListener
public class InitializeData implements ServletContextListener {
    private MusicianService musicianService;

    private InstrumentService instrumentService;

    private SkillService skillService;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        musicianService = (MusicianService) event.getServletContext().getAttribute("musicianService");
        instrumentService = (InstrumentService) event.getServletContext().getAttribute("instrumentService");
        skillService = (SkillService) event.getServletContext().getAttribute("skillService");
        init();
    }

    @SneakyThrows
    private void init() {
        Musician musician1 = Musician.builder()
                .id(UUID.fromString("00507393-fb97-4f3e-accf-8a9b2ceb2026"))
                .firstName("John")
                .lastName("Lennon")
                .birthday(LocalDate.of(1940, 10, 9))
                .login("johnlennon")
                .password("password123")
                .email("john.lennon@example.com")
                .skills(List.of())
                .build();

        Musician musician2 = Musician.builder()
                .id(UUID.fromString("26343d2b-d3c0-4c6f-8d7a-af6284782157"))
                .firstName("Paul")
                .lastName("McCartney")
                .birthday(LocalDate.of(1942, 6, 18))
                .login("paulmccartney")
                .password("password456")
                .email("paul.mccartney@example.com")
                .skills(List.of())
                .build();

        Musician musician3 = Musician.builder()
                .id(UUID.fromString("cb58851c-cfd6-4d5d-86c8-8bd2aa1e5e93"))
                .firstName("Ringo")
                .lastName("Starr")
                .birthday(LocalDate.of(1940, 7, 7))
                .login("ringostarr")
                .password("password789")
                .email("ringo.starr@example.com")
                .skills(List.of())
                .build();

        Musician musician4 = Musician.builder()
                .id(UUID.fromString("97cdadd2-8384-4461-a929-9cfc2338036b"))
                .firstName("George")
                .lastName("Harrison")
                .birthday(LocalDate.of(1943, 2, 25))
                .login("georgeharrison")
                .password("password101112")
                .email("george.harrison@example.com")
                .skills(List.of())
                .build();

        musicianService.create(musician1);
        musicianService.create(musician2);
        musicianService.create(musician3);
        musicianService.create(musician4);

        Instrument instrument1 = Instrument.builder()
                .id(UUID.fromString("ce087038-fa2b-44e6-8f24-a786d375b2ec"))
                .name("Acoustic Guitar")
                .type("String")
                .typicalPrice(1000)
                .tuningFrequency(440)
                .skills(List.of())
                .build();

        Instrument instrument2 = Instrument.builder()
                .id(UUID.fromString("7d9d2e0e-cf45-4078-bf84-edb16423a30e"))
                .name("Piano")
                .type("Keyboard")
                .typicalPrice(40000)
                .tuningFrequency(440)
                .skills(List.of())
                .build();

        Instrument instrument3 = Instrument.builder()
                .id(UUID.fromString("7fc91ddb-0ef7-40f8-b5b6-8db9fef2ee55"))
                .name("Drums")
                .type("Percussion")
                .typicalPrice(2300)
                .tuningFrequency(220)
                .skills(List.of())
                .build();

        Instrument instrument4 = Instrument.builder()
                .id(UUID.fromString("8386c961-3808-43ec-bfa2-760eacb6d605"))
                .name("Electric Guitar")
                .type("String")
                .typicalPrice(1500)
                .tuningFrequency(440)
                .skills(List.of())
                .build();

        instrumentService.create(instrument1);
        instrumentService.create(instrument2);
        instrumentService.create(instrument3);
        instrumentService.create(instrument4);

        Skill skill1 = Skill.builder()
                .id(UUID.fromString("9b3bec33-428d-4c7d-a659-50aa5892a05a"))
                .numberOfPlayingYears(30)
                .level(Level.INTERMEDIATE)
                .favouriteModelName("Epiphone Casino")
                .musician(musician1)
                .instrument(instrument1)
                .build();

        Skill skill2 = Skill.builder()
                .id(UUID.fromString("bf07d104-9cdf-408a-a599-176aa568fd7d"))
                .numberOfPlayingYears(30)
                .level(Level.INTERMEDIATE)
                .favouriteModelName("Blüthner Grand Piano")
                .musician(musician2)
                .instrument(instrument2)
                .build();

        Skill skill3 = Skill.builder()
                .id(UUID.fromString("ab686ff2-b21b-470e-911e-965741f16b36"))
                .numberOfPlayingYears(30)
                .level(Level.ADVANCED)
                .favouriteModelName("Ludwig Downbeat Kit")
                .musician(musician3)
                .instrument(instrument3)
                .build();

        Skill skill4 = Skill.builder()
                .id(UUID.fromString("8f769997-623d-4eb4-b4e0-9fb6f5234fbb"))
                .numberOfPlayingYears(30)
                .level(Level.ADVANCED)
                .favouriteModelName("Gibson SG Standard")
                .musician(musician4)
                .instrument(instrument4)
                .build();

        skillService.create(skill1);
        skillService.create(skill2);
        skillService.create(skill3);
        skillService.create(skill4);
    }

    @SneakyThrows
    private byte[] getResourceAsByteArray(String name) {
        try (InputStream is = this.getClass().getResourceAsStream(name)) {
            if (is != null) {
                return is.readAllBytes();
            } else {
                throw new IllegalStateException("Unable to get resource %s".formatted(name));
            }
        }
    }
}
