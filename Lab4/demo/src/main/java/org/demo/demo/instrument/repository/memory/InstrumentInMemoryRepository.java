package org.demo.demo.instrument.repository.memory;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.demo.demo.datastore.component.DataStore;
import org.demo.demo.instrument.entity.Instrument;
import org.demo.demo.instrument.repository.api.InstrumentRepository;
import org.demo.demo.skill.entity.Skill;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class InstrumentInMemoryRepository implements InstrumentRepository {
    private final DataStore store;

    @Inject
    public InstrumentInMemoryRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public List<Instrument> findAllByName(String name) {
        return store.findAllInstruments().stream()
                .filter(instrument -> instrument.getName().equals(name))
                .toList();
    }

    @Override
    public List<Instrument> findAllByType(String type) {
        return store.findAllInstruments().stream()
                .filter(instrument -> instrument.getType().equals(type))
                .toList();
    }

    @Override
    public Optional<Instrument> find(UUID id) {
        Instrument instrument = store.findAllInstruments().stream()
                .filter(instrument1 -> instrument1.getId().equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);

        List<Skill> skills = store.findAllSkills().stream()
                .filter(skill -> skill.getInstrument().getId().equals(instrument.getId()))
                .toList();

        instrument.setSkills(skills);

        return Optional.of(instrument);
    }

    @Override
    public List<Instrument> findAll() {
        return store.findAllInstruments();
    }

    @Override
    public void create(Instrument entity) {
        store.createInstrument(entity);
    }

    @Override
    public void delete(Instrument entity) {
        store.deleteInstrument(entity.getId());
    }

    @Override
    public void update(Instrument entity) {
        store.updateInstrument(entity);
    }
}
