package org.demo.demo.skill.repository.persistence;

import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
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
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Skill> cq = cb.createQuery(Skill.class);
        Root<Skill> skill = cq.from(Skill.class);
        cq.select(skill).where(cb.equal(skill.get("level"), level));

        return em.createQuery(cq).getResultList();

        /*return em.createQuery("select s from Skill s where s.level = :level", Skill.class)
                .setParameter("level", level)
                .getResultList();*/
    }

    @Override
    public List<Skill> findAllByInstrument(Instrument instrument) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Skill> cq = cb.createQuery(Skill.class);
        Root<Skill> skill = cq.from(Skill.class);
        cq.select(skill).where(cb.equal(skill.get("instrument"), instrument));

        return em.createQuery(cq).getResultList();

        /*return em.createQuery("select s from Skill s where s.instrument = :instrument", Skill.class)
                .setParameter("instrument", instrument)
                .getResultList();*/
    }

    @Override
    public List<Skill> findAllByMusician(Musician musician) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Skill> cq = cb.createQuery(Skill.class);
        Root<Skill> skill = cq.from(Skill.class);
        cq.select(skill).where(cb.equal(skill.get("musician"), musician));

        return em.createQuery(cq).getResultList();

        /*return em.createQuery("select s from Skill s where s.musician = :musician", Skill.class)
                .setParameter("musician", musician)
                .getResultList();*/
    }

    @Override
    public List<Skill> findAllByInstrumentAndMusician(Instrument instrument, Musician musician) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Skill> cq = cb.createQuery(Skill.class);
        Root<Skill> skill = cq.from(Skill.class);
        cq.select(skill).where(
                cb.equal(skill.get("musician"), musician),
                cb.equal(skill.get("instrument"), instrument)
        );

        return em.createQuery(cq).getResultList();

        /*return em.createQuery("select s from Skill s where s.musician = :musician and s.instrument = :instrument", Skill.class)
                .setParameter("musician", musician)
                .setParameter("instrument", instrument)
                .getResultList();*/
    }

    @Override
    public Optional<Skill> findByIdAndMusician(UUID id, Musician musician) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Skill> cq = cb.createQuery(Skill.class);
        Root<Skill> skill = cq.from(Skill.class);
        cq.select(skill).where(
                cb.equal(skill.get("id"), id),
                cb.equal(skill.get("musician"), musician)
        );

        try {
            return Optional.of(em.createQuery(cq).getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }

        /*try {
            return Optional.of(em.createQuery("select s from Skill s where s.id = :id and s.musician = :musician", Skill.class)
                    .setParameter("musician", musician)
                    .setParameter("id", id)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }*/
    }

    @Override
    public Optional<Skill> find(UUID id) {
        return Optional.ofNullable(em.find(Skill.class, id));
    }

    @Override
    public List<Skill> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Skill> cq = cb.createQuery(Skill.class);
        Root<Skill> skill = cq.from(Skill.class);
        cq.select(skill);

        return em.createQuery(cq).getResultList();

        //return em.createQuery("select s from Skill s", Skill.class).getResultList();
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
