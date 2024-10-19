package org.demo.demo.skill.repository.persistence;

import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.demo.demo.instrument.entity.Instrument;
import org.demo.demo.musician.entity.Musician;
import org.demo.demo.skill.entity.Skill;
import org.demo.demo.skill.repository.api.SkillRepository;
import org.demo.demo.util.Level;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
public class SkillPersistenceRepository implements SkillRepository {
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Skill> findAllByLevel(Level level) {
        return em.createQuery("select s from Skill s where s.level = :level", Skill.class)
                .setParameter("level", level)
                .getResultList();
    }

    @Override
    public List<Skill> findAllByInstrument(Instrument instrument) {
        return em.createQuery("select s from Skill s where s.instrument = :instrument", Skill.class)
                .setParameter("instrument", instrument)
                .getResultList();
    }

    @Override
    public List<Skill> findAllByMusician(Musician musician) {
        return em.createQuery("select s from Skill s where s.musician = :musician", Skill.class)
                .setParameter("musician", musician)
                .getResultList();
    }

    @Override
    public List<Skill> findAllByInstrumentAndMusician(Instrument instrument, Musician musician) {
        return em.createQuery("select s from Skill s where s.musician = :musician and s.instrument = :instrument", Skill.class)
                .setParameter("musician", musician)
                .setParameter("instrument", instrument)
                .getResultList();
    }

    @Override
    public Optional<Skill> findByIdAndMusician(UUID id, Musician musician) {
        try {
            return Optional.of(em.createQuery("select s from Skill s where s.id = :id and s.musician = :musician", Skill.class)
                    .setParameter("musician", musician)
                    .setParameter("id", id)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Skill> find(UUID id) {
        return Optional.ofNullable(em.find(Skill.class, id));
    }

    @Override
    public List<Skill> findAll() {
        return em.createQuery("select s from Skill s", Skill.class).getResultList();
    }

    @Override
    public void create(Skill entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Skill entity) {
        em.remove(em.find(Skill.class, entity.getId()));
    }

    @Override
    public void update(Skill entity) {
        em.merge(entity);
    }
}
