package org.demo.demo.instrument.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import org.demo.demo.instrument.entity.Instrument;
import org.demo.demo.instrument.repository.api.InstrumentRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class InstrumentService {
    private final InstrumentRepository instrumentRepository;

    @Inject
    public InstrumentService(InstrumentRepository instrumentRepository) {
        this.instrumentRepository = instrumentRepository;
    }

    public List<Instrument> findAllByName(String name) {
        return instrumentRepository.findAllByName(name);
    }

    public List<Instrument> findAllByType(String type) {
        return instrumentRepository.findAllByType(type);
    }

    public Optional<Instrument> find(UUID id) {
        return instrumentRepository.find(id);
    }

    public List<Instrument> findAll() {
        return instrumentRepository.findAll();
    }

    @Transactional
    public void create(Instrument entity) {
        if(instrumentRepository.find(entity.getId()).isPresent()) {
            throw new IllegalArgumentException("Instrument already exists");
        }
        instrumentRepository.create(entity);
    }

    @Transactional
    public void delete(UUID id) {
        instrumentRepository.delete(instrumentRepository.find(id).orElseThrow());
    }

    @Transactional
    public void update(Instrument entity) {
        instrumentRepository.update(entity);
    }
}
