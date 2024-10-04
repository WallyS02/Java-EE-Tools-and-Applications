package org.demo.demo.instrument.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
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

    public void create(Instrument entity) {
        instrumentRepository.create(entity);
    }

    public void delete(UUID id) {
        instrumentRepository.delete(instrumentRepository.find(id).orElseThrow());
    }

    public void update(Instrument entity) {
        instrumentRepository.update(entity);
    }
}
