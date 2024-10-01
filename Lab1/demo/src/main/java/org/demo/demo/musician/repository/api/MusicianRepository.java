package org.demo.demo.musician.repository.api;

import org.demo.demo.musician.entity.Musician;
import org.demo.demo.repository.api.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MusicianRepository extends Repository<Musician, UUID> {
    Optional<Musician> findByLogin(String login);
    List<Musician> findByEmail(String email);
    List<Musician> findByFirstName(String firstName);
    List<Musician> findByLastName(String lastName);
}
