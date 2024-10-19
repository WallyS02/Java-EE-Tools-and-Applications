package org.demo.demo.musician.repository.persistence;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.ws.rs.NotFoundException;
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

@Dependent
public class MusicianPersistenceRepository implements MusicianRepository {
    @Resource(name="uploadPath")
    private String uploadPathString;

    private Path uploadPath;

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Musician> findByLogin(String login) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Musician> cq = cb.createQuery(Musician.class);
        Root<Musician> musician = cq.from(Musician.class);
        cq.select(musician).where(cb.equal(musician.get("login"), login));

        try {
            return Optional.of(em.createQuery(cq).getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }

        /*try {
            return Optional.of(em.createQuery("select m from Musician m where m.login = :login", Musician.class)
                    .setParameter("login", login)
                    .getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }*/
    }

    @Override
    public Optional<Musician> findByEmail(String email) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Musician> cq = cb.createQuery(Musician.class);
        Root<Musician> musician = cq.from(Musician.class);
        cq.select(musician).where(cb.equal(musician.get("email"), email));

        try {
            return Optional.of(em.createQuery(cq).getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }

        /*try {
            return Optional.of(em.createQuery("select m from Musician m where m.email = :email", Musician.class)
                    .setParameter("email", email)
                    .getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }*/
    }

    @Override
    public List<Musician> findByFirstName(String firstName) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Musician> cq = cb.createQuery(Musician.class);
        Root<Musician> musician = cq.from(Musician.class);
        cq.select(musician).where(cb.equal(musician.get("firstName"), firstName));

        return em.createQuery(cq).getResultList();

        /*return em.createQuery("select m from Musician m where m.firstName = :firstName", Musician.class)
                .setParameter("firstName", firstName)
                .getResultList();*/
    }

    @Override
    public List<Musician> findByLastName(String lastName) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Musician> cq = cb.createQuery(Musician.class);
        Root<Musician> musician = cq.from(Musician.class);
        cq.select(musician).where(cb.equal(musician.get("lastName"), lastName));

        return em.createQuery(cq).getResultList();

        /*return em.createQuery("select m from Musician m where m.lastName = :lastName", Musician.class)
                .setParameter("lastName", lastName)
                .getResultList();*/
    }

    @Override
    public void updateImage(UUID id, InputStream is) {
        if(this.uploadPath == null) {
            this.uploadPath = Paths.get(uploadPathString);
        }
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
        if(this.uploadPath == null) {
            this.uploadPath = Paths.get(uploadPathString);
        }
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
        if(this.uploadPath == null) {
            this.uploadPath = Paths.get(uploadPathString);
        }
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

    @Override
    public Optional<Musician> find(UUID id) {
        return Optional.ofNullable(em.find(Musician.class, id));
    }

    @Override
    public List<Musician> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Musician> cq = cb.createQuery(Musician.class);
        Root<Musician> musician = cq.from(Musician.class);
        cq.select(musician);

        return em.createQuery(cq).getResultList();

        //return em.createQuery("select m from Musician m", Musician.class).getResultList();
    }

    @Override
    public void create(Musician entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Musician entity) {
        em.remove(em.find(Musician.class, entity.getId()));
    }

    @Override
    public void update(Musician entity) {
        em.merge(entity);
    }
}
