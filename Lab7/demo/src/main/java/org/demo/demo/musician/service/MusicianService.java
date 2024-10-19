package org.demo.demo.musician.service;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJBAccessException;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import lombok.NoArgsConstructor;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import org.demo.demo.musician.entity.Musician;
import org.demo.demo.musician.entity.MusicianRoles;
import org.demo.demo.musician.repository.api.MusicianRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class MusicianService {
    private final MusicianRepository musicianRepository;

    private final Pbkdf2PasswordHash passwordHash;

    private final SecurityContext securityContext;

    @Inject
    public MusicianService(MusicianRepository musicianRepository, @SuppressWarnings("CdiInjectionPointsInspection") Pbkdf2PasswordHash passwordHash, SecurityContext securityContext) {
        this.musicianRepository = musicianRepository;
        this.passwordHash = passwordHash;
        this.securityContext = securityContext;
    }

    @RolesAllowed(MusicianRoles.ADMIN)
    public Optional<Musician> findByLogin(String login) {
        return musicianRepository.findByLogin(login);
    }

    @RolesAllowed(MusicianRoles.ADMIN)
    public Optional<Musician> findByEmail(String email) {
        return musicianRepository.findByEmail(email);
    }

    @RolesAllowed(MusicianRoles.ADMIN)
    public List<Musician> findByFirstName(String firstName) {
        return musicianRepository.findByFirstName(firstName);
    }

    @RolesAllowed(MusicianRoles.ADMIN)
    public List<Musician> findByLastName(String lastName) {
        return musicianRepository.findByLastName(lastName);
    }

    @RolesAllowed(MusicianRoles.USER)
    public Optional<Musician> find(UUID id) {
        //checkAdminRoleOrOwner(musicianRepository.find(id));
        return musicianRepository.find(id);
    }

    @RolesAllowed(MusicianRoles.USER)
    public List<Musician> findAll() {
        return musicianRepository.findAll();
    }

    @PermitAll
    public void create(Musician musician) {
        if(musicianRepository.find(musician.getId()).isPresent()) {
            throw new IllegalArgumentException("Musician already exists");
        }
        musician.setPassword(passwordHash.generate(musician.getPassword().toCharArray()));
        if(musician.getRoles() == null || musician.getRoles().isEmpty()) {
            musician.setRoles(List.of(MusicianRoles.USER));
        }
        musicianRepository.create(musician);
    }

    @RolesAllowed(MusicianRoles.USER)
    public void update(Musician musician) {
        checkAdminRoleOrOwner(musicianRepository.find(musician.getId()));
        musicianRepository.update(musician);
    }

    @RolesAllowed(MusicianRoles.USER)
    public void delete(UUID id) {
        checkAdminRoleOrOwner(musicianRepository.find(id));
        musicianRepository.delete(musicianRepository.find(id).orElseThrow());
    }

    @PermitAll
    public boolean verify(String login, String password) {
        return findByLogin(login)
                .map(user -> passwordHash.verify(password.toCharArray(), user.getPassword()))
                .orElse(false);
    }

    @RolesAllowed(MusicianRoles.USER)
    public void updateImage(UUID id, InputStream is) {
        checkAdminRoleOrOwner(musicianRepository.find(id));
        musicianRepository.updateImage(id, is);
    }

    @RolesAllowed(MusicianRoles.USER)
    public byte[] getImage(UUID id) throws IOException {
        checkAdminRoleOrOwner(musicianRepository.find(id));
        return musicianRepository.getImage(id);
    }

    @RolesAllowed(MusicianRoles.USER)
    public void deleteImage(UUID id) {
        checkAdminRoleOrOwner(musicianRepository.find(id));
        musicianRepository.deleteImage(id);
    }

    private void checkAdminRoleOrOwner(Optional<Musician> musician) throws EJBAccessException {
        if (securityContext.isCallerInRole(MusicianRoles.ADMIN)) {
            return;
        }
        if (securityContext.isCallerInRole(MusicianRoles.USER)
                && musician.isPresent()
                && musician.get().getLogin().equals(securityContext.getCallerPrincipal().getName())) {
            return;
        }
        throw new EJBAccessException("Caller not authorized.");
    }
}
