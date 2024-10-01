package org.demo.demo.configuration.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.demo.demo.component.DtoFunctionFactory;
import org.demo.demo.instrument.controller.simple.InstrumentSimpleController;
import org.demo.demo.instrument.service.InstrumentService;
import org.demo.demo.musician.controller.simple.MusicianSimpleController;
import org.demo.demo.musician.service.MusicianService;
import org.demo.demo.skill.controller.simple.SkillSimpleController;
import org.demo.demo.skill.service.SkillService;

@WebListener
public class CreateControllers implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        MusicianService musicianService = (MusicianService) event.getServletContext().getAttribute("musicianService");
        InstrumentService instrumentService = (InstrumentService) event.getServletContext().getAttribute("instrumentService");
        SkillService skillService = (SkillService) event.getServletContext().getAttribute("skillService");

        event.getServletContext().setAttribute("musicianController", new MusicianSimpleController(
                musicianService,
                new DtoFunctionFactory()
        ));

        event.getServletContext().setAttribute("instrumentController", new InstrumentSimpleController(
                instrumentService,
                new DtoFunctionFactory()
        ));

        event.getServletContext().setAttribute("skillController", new SkillSimpleController(
                skillService,
                new DtoFunctionFactory()
        ));
    }
}
