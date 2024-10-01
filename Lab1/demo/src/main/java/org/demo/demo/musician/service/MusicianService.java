package org.demo.demo.musician.service;

import jakarta.servlet.ServletContext;
import org.demo.demo.controller.servlet.exception.NotFoundException;
import org.demo.demo.crypto.component.Pbkdf2PasswordHash;
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

public class MusicianService {
    private final MusicianRepository musicianRepository;

    private final Pbkdf2PasswordHash passwordHash;

    private final Path uploadPath;

    public MusicianService(MusicianRepository musicianRepository, Pbkdf2PasswordHash passwordHash, ServletContext servletContext) {
        this.musicianRepository = musicianRepository;
        this.passwordHash = passwordHash;
        this.uploadPath = Paths.get(servletContext.getInitParameter("uploadDirectory"));
    }

    public Optional<Musician> findByLogin(String login) {
        return musicianRepository.findByLogin(login);
    }

    public List<Musician> findByEmail(String email) {
        return musicianRepository.findByEmail(email);
    }

    public List<Musician> findByFirstName(String firstName) {
        return musicianRepository.findByFirstName(firstName);
    }

    public List<Musician> findByLastName(String lastName) {
        return musicianRepository.findByLastName(lastName);
    }

    public Optional<Musician> find(UUID id) {
        return musicianRepository.find(id);
    }

    public List<Musician> findAll() {
        return musicianRepository.findAll();
    }

    public void create(Musician musician) {
        musician.setPassword(passwordHash.generate(musician.getPassword().toCharArray()));
        musicianRepository.create(musician);
    }

    public void update(Musician musician) {
        musicianRepository.update(musician);
    }

    public void delete(UUID id) {
        musicianRepository.delete(musicianRepository.find(id).orElseThrow());
    }

    public boolean verify(String login, String password) {
        return findByLogin(login)
                .map(user -> passwordHash.verify(password.toCharArray(), user.getPassword()))
                .orElse(false);
    }

    public void updateImage(UUID id, InputStream is) {
        musicianRepository.find(id).ifPresent(musician -> {
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

    public byte[] getImage(UUID id) throws IOException {
        Optional<Musician> musician = musicianRepository.find(id);
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

    public void deleteImage(UUID id) {
        musicianRepository.find(id).ifPresent(musician -> {
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
}
