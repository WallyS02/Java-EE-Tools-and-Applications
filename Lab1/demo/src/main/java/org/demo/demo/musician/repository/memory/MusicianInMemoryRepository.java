package org.demo.demo.musician.repository.memory;

import org.demo.demo.datastore.component.DataStore;
import org.demo.demo.musician.entity.Musician;
import org.demo.demo.musician.repository.api.MusicianRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class MusicianInMemoryRepository implements MusicianRepository {
    private final DataStore store;

    public MusicianInMemoryRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<Musician> findByLogin(String login) {
        return store.findAllMusicians().stream()
                .filter(musician -> musician.getLogin().equals(login))
                .findFirst();
    }

    @Override
    public List<Musician> findByEmail(String email) {
        return store.findAllMusicians().stream()
                .filter(musician -> musician.getEmail().equals(email))
                .collect(Collectors.toList());
    }

    @Override
    public List<Musician> findByFirstName(String firstName) {
        return store.findAllMusicians().stream()
                .filter(musician -> musician.getFirstName().equals(firstName))
                .collect(Collectors.toList());
    }

    @Override
    public List<Musician> findByLastName(String lastName) {
        return store.findAllMusicians().stream()
                .filter(musician -> musician.getLastName().equals(lastName))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Musician> find(UUID id) {
        return store.findAllMusicians().stream()
                .filter(musician -> musician.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Musician> findAll() {
        return store.findAllMusicians();
    }

    @Override
    public void create(Musician entity) {
        store.createMusician(entity);
    }

    @Override
    public void delete(Musician entity) {
        store.deleteMusician(entity.getId());
    }

    @Override
    public void update(Musician entity) {
        store.updateMusician(entity);
    }
}
