package org.demo.demo.instrument.repository.memory;

import org.demo.demo.datastore.component.DataStore;
import org.demo.demo.instrument.entity.Instrument;
import org.demo.demo.instrument.repository.api.InstrumentRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class InstrumentInMemoryRepository implements InstrumentRepository {
    private final DataStore store;

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
        return store.findAllInstruments().stream()
                .filter(instrument -> instrument.getId().equals(id))
                .findFirst();
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
