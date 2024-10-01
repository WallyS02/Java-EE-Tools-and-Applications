package org.demo.demo.configuration.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.demo.demo.crypto.component.Pbkdf2PasswordHash;
import org.demo.demo.datastore.component.DataStore;
import org.demo.demo.instrument.repository.api.InstrumentRepository;
import org.demo.demo.instrument.repository.memory.InstrumentInMemoryRepository;
import org.demo.demo.instrument.service.InstrumentService;
import org.demo.demo.musician.repository.api.MusicianRepository;
import org.demo.demo.musician.repository.memory.MusicianInMemoryRepository;
import org.demo.demo.musician.service.MusicianService;
import org.demo.demo.skill.repository.api.SkillRepository;
import org.demo.demo.skill.repository.memory.SkillInMemoryRepository;
import org.demo.demo.skill.service.SkillService;

@WebListener
public class CreateServices implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        DataStore dataSource = (DataStore) event.getServletContext().getAttribute("datasource");

        MusicianRepository musicianRepository = new MusicianInMemoryRepository(dataSource);
        InstrumentRepository instrumentRepository = new InstrumentInMemoryRepository(dataSource);
        SkillRepository skillRepository = new SkillInMemoryRepository(dataSource);

        event.getServletContext().setAttribute("musicianService", new MusicianService(musicianRepository, new Pbkdf2PasswordHash(), event.getServletContext()));
        event.getServletContext().setAttribute("instrumentService", new InstrumentService(instrumentRepository));
        event.getServletContext().setAttribute("skillService", new SkillService(instrumentRepository, skillRepository, musicianRepository));
    }
}
