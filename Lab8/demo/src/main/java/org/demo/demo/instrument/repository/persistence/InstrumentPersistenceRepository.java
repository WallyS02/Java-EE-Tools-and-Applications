package org.demo.demo.instrument.repository.persistence;

import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.demo.demo.instrument.entity.Instrument;
import org.demo.demo.instrument.repository.api.InstrumentRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
public class InstrumentPersistenceRepository implements InstrumentRepository {
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Instrument> findAllByName(String name) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Instrument> cq = cb.createQuery(Instrument.class);
        Root<Instrument> instrument = cq.from(Instrument.class);
        cq.select(instrument).where(cb.equal(instrument.get("name"), name));

        return em.createQuery(cq).getResultList();

        /*return em.createQuery("select i from Instrument i where i.name = :name", Instrument.class)
                .setParameter("name", name)
                .getResultList();*/
    }

    @Override
    public List<Instrument> findAllByType(String type) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Instrument> cq = cb.createQuery(Instrument.class);
        Root<Instrument> instrument = cq.from(Instrument.class);
        cq.select(instrument).where(cb.equal(instrument.get("type"), type));

        return em.createQuery(cq).getResultList();

        /*return em.createQuery("select i from Instrument i where i.type = :type", Instrument.class)
                .setParameter("type", type)
                .getResultList();*/
    }

    @Override
    public Optional<Instrument> find(UUID id) {
        return Optional.ofNullable(em.find(Instrument.class, id));
    }

    @Override
    public List<Instrument> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Instrument> cq = cb.createQuery(Instrument.class);
        Root<Instrument> instrument = cq.from(Instrument.class);
        cq.select(instrument);

        return em.createQuery(cq).getResultList();

        //return em.createQuery("select i from Instrument i", Instrument.class).getResultList();
    }

    @Override
    public void create(Instrument entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Instrument entity) {
        em.getEntityManagerFactory().getCache().evictAll();
        em.clear();
        em.remove(em.find(Instrument.class, entity.getId()));
    }

    @Override
    public void update(Instrument entity) {
        em.merge(entity);
    }
}
