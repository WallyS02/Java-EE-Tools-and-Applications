package org.demo.demo.musician.repository.api;

import org.demo.demo.musician.entity.Musician;
import org.demo.demo.repository.api.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MusicianRepository extends Repository<Musician, UUID> {
    Optional<Musician> findByLogin(String login);
    Optional<Musician> findByEmail(String email);
    List<Musician> findByFirstName(String firstName);
    List<Musician> findByLastName(String lastName);
    void updateImage(UUID id, InputStream is);
    void deleteImage(UUID id);
    byte[] getImage(UUID id) throws IOException;
}
