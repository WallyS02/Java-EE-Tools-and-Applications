package org.demo.demo.musician.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import org.demo.demo.crypto.component.Pbkdf2PasswordHash;
import org.demo.demo.musician.entity.Musician;
import org.demo.demo.musician.repository.api.MusicianRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class MusicianService {
    private final MusicianRepository musicianRepository;

    private final Pbkdf2PasswordHash passwordHash;

    @Inject
    public MusicianService(MusicianRepository musicianRepository, Pbkdf2PasswordHash passwordHash) {
        this.musicianRepository = musicianRepository;
        this.passwordHash = passwordHash;
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
        musicianRepository.updateImage(id, is);
    }

    public byte[] getImage(UUID id) throws IOException {
        return musicianRepository.getImage(id);
    }

    public void deleteImage(UUID id) {
        musicianRepository.deleteImage(id);
    }
}
