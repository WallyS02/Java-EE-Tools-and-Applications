package org.demo.demo.musician.repository.memory;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContext;
import org.demo.demo.controller.servlet.exception.NotFoundException;
import org.demo.demo.datastore.component.DataStore;
import org.demo.demo.musician.entity.Musician;
import org.demo.demo.musician.repository.api.MusicianRepository;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class MusicianInMemoryRepository implements MusicianRepository {
    private final DataStore store;

    private final Path uploadPath;

    @Inject
    public MusicianInMemoryRepository(DataStore store, ServletContext servletContext) {
        this.store = store;
        this.uploadPath = Paths.get(servletContext.getInitParameter("uploadDirectory"));
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
                .toList();
    }

    @Override
    public List<Musician> findByFirstName(String firstName) {
        return store.findAllMusicians().stream()
                .filter(musician -> musician.getFirstName().equals(firstName))
                .toList();
    }

    @Override
    public List<Musician> findByLastName(String lastName) {
        return store.findAllMusicians().stream()
                .filter(musician -> musician.getLastName().equals(lastName))
                .toList();
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

    @Override
    public void updateImage(UUID id, InputStream is) {
        find(id).ifPresent(musician -> {
            Path filePath = uploadPath.resolve(musician.getLogin() + ".png");
            if (Files.exists(filePath)) {
                try {
                    Files.delete(filePath);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    Files.copy(is, filePath);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                try {
                    Files.copy(is, filePath);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public void deleteImage(UUID id) {
        find(id).ifPresent(musician -> {
            Path filePath = uploadPath.resolve(musician.getLogin() + ".png");
            if(Files.exists(filePath)) {
                try {
                    Files.delete(filePath);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public byte[] getImage(UUID id) throws IOException {
        Optional<Musician> musician = find(id);
        if (musician.isPresent()) {
            Path filePath = uploadPath.resolve(musician.get().getLogin() + ".png");
            if (Files.exists(filePath)) {
                return Files.readAllBytes(filePath);
            } else {
                throw new NotFoundException();
            }
        } else {
            throw new NotFoundException();
        }
    }
}
