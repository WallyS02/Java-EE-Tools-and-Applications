package org.demo.demo.skill.service;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJBAccessException;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import lombok.NoArgsConstructor;
import org.demo.demo.annotation.Loggable;
import org.demo.demo.instrument.repository.api.InstrumentRepository;
import org.demo.demo.musician.entity.MusicianRoles;
import org.demo.demo.musician.repository.api.MusicianRepository;
import org.demo.demo.skill.entity.Skill;
import org.demo.demo.skill.repository.api.SkillRepository;
import org.demo.demo.util.Level;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class SkillService {
    private final SkillRepository skillRepository;

    private final InstrumentRepository instrumentRepository;

    private final MusicianRepository musicianRepository;

    private final SecurityContext securityContext;

    @Inject
    public SkillService(InstrumentRepository instrumentRepository, SkillRepository skillRepository, MusicianRepository musicianRepository, @SuppressWarnings("CdiInjectionPointsInspection") SecurityContext securityContext) {
        this.instrumentRepository = instrumentRepository;
        this.skillRepository = skillRepository;
        this.musicianRepository = musicianRepository;
        this.securityContext = securityContext;
    }

    @RolesAllowed(MusicianRoles.ADMIN)
    public List<Skill> findAllByLevel(Level level) {
        return skillRepository.findAllByLevel(level);
    }

    @RolesAllowed(MusicianRoles.ADMIN)
    public Optional<List<Skill>> findAllByInstrument(UUID instrumentId) {
        return instrumentRepository.find(instrumentId)
                .map(skillRepository::findAllByInstrument);
    }

    @RolesAllowed(MusicianRoles.USER)
    public Optional<List<Skill>> findAllByInstrumentForCallerPrincipal(UUID instrumentId) {
        if(securityContext.isCallerInRole(MusicianRoles.ADMIN)) {
            return instrumentRepository.find(instrumentId)
                    .map(skillRepository::findAllByInstrument);
        }
        return Optional.ofNullable(skillRepository.findAllByInstrumentAndMusician(instrumentRepository.find(instrumentId).orElseThrow(IllegalStateException::new), musicianRepository.findByLogin(securityContext.getCallerPrincipal().getName()).orElseThrow(IllegalStateException::new)));
    }

    @RolesAllowed(MusicianRoles.ADMIN)
    public Optional<List<Skill>> findAllByMusician(UUID musicianId) {
        return musicianRepository.find(musicianId)
                .map(skillRepository::findAllByMusician);
    }

    @RolesAllowed(MusicianRoles.ADMIN)
    public List<Skill> findAll() {
        return skillRepository.findAll();
    }

    @RolesAllowed(MusicianRoles.USER)
    public List<Skill> findAllForCallerPrincipal() {
        if(securityContext.isCallerInRole(MusicianRoles.ADMIN)) {
            return skillRepository.findAll();
        }
        return skillRepository.findAllByMusician(musicianRepository.findByLogin(securityContext.getCallerPrincipal().getName()).orElseThrow(IllegalStateException::new));
    }

    @RolesAllowed(MusicianRoles.USER)
    public Optional<Skill> find(UUID id) {
        return skillRepository.find(id);
    }

    @RolesAllowed(MusicianRoles.USER)
    public Optional<Skill> findForCallerPrincipal(UUID id) {
        if(securityContext.isCallerInRole(MusicianRoles.ADMIN)) {
            return skillRepository.find(id);
        }
        return skillRepository.findByIdAndMusician(id, musicianRepository.findByLogin(securityContext.getCallerPrincipal().getName()).orElseThrow(IllegalStateException::new));
    }

    @RolesAllowed(MusicianRoles.USER)
    @Loggable
    public void create(Skill entity) {
        if(skillRepository.find(entity.getId()).isPresent()) {
            throw new IllegalArgumentException("Skill already exists");
        }
        if(instrumentRepository.find(entity.getInstrument().getId()).isEmpty()) {
            throw new IllegalArgumentException("Instrument does not exists");
        }
        skillRepository.create(entity);
    }

    @RolesAllowed(MusicianRoles.USER)
    @Loggable
    public void createForCallerPrincipal(Skill entity) {
        entity.setMusician(musicianRepository.findByLogin(securityContext.getCallerPrincipal().getName()).orElseThrow(IllegalStateException::new));
        create(entity);
    }

    @RolesAllowed(MusicianRoles.USER)
    @Loggable
    public void delete(UUID id) {
        checkAdminRoleOrOwner(skillRepository.find(id));
        skillRepository.delete(skillRepository.find(id).orElseThrow());
    }

    @RolesAllowed(MusicianRoles.USER)
    @Loggable
    public void update(Skill entity) {
        checkAdminRoleOrOwner(skillRepository.find(entity.getId()));
        skillRepository.update(entity);
    }

    private void checkAdminRoleOrOwner(Optional<Skill> skill) throws EJBAccessException {
        if (securityContext.isCallerInRole(MusicianRoles.ADMIN)) {
            return;
        }
        if (securityContext.isCallerInRole(MusicianRoles.USER)
                && skill.isPresent()
                && skill.get().getMusician().getLogin().equals(securityContext.getCallerPrincipal().getName())) {
            return;
        }
        throw new EJBAccessException("Caller not authorized.");
    }
}
