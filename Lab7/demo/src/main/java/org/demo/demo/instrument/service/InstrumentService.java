package org.demo.demo.instrument.service;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import org.demo.demo.instrument.entity.Instrument;
import org.demo.demo.instrument.repository.api.InstrumentRepository;
import org.demo.demo.musician.entity.MusicianRoles;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class InstrumentService {
    private final InstrumentRepository instrumentRepository;

    @Inject
    public InstrumentService(InstrumentRepository instrumentRepository) {
        this.instrumentRepository = instrumentRepository;
    }

    @RolesAllowed(MusicianRoles.USER)
    public List<Instrument> findAllByName(String name) {
        return instrumentRepository.findAllByName(name);
    }

    @RolesAllowed(MusicianRoles.USER)
    public List<Instrument> findAllByType(String type) {
        return instrumentRepository.findAllByType(type);
    }

    @RolesAllowed(MusicianRoles.USER)
    public Optional<Instrument> find(UUID id) {
        return instrumentRepository.find(id);
    }

    @RolesAllowed(MusicianRoles.USER)
    public List<Instrument> findAll() {
        return instrumentRepository.findAll();
    }

    @RolesAllowed(MusicianRoles.ADMIN)
    public void create(Instrument entity) {
        if(instrumentRepository.find(entity.getId()).isPresent()) {
            throw new IllegalArgumentException("Instrument already exists");
        }
        instrumentRepository.create(entity);
    }

    @RolesAllowed(MusicianRoles.ADMIN)
    public void delete(UUID id) {
        instrumentRepository.delete(instrumentRepository.find(id).orElseThrow());
    }

    @RolesAllowed(MusicianRoles.ADMIN)
    public void update(Instrument entity) {
        instrumentRepository.update(entity);
    }
}
