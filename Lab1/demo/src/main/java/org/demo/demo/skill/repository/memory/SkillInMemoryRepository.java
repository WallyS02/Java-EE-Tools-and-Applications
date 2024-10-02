package org.demo.demo.skill.repository.memory;

import org.demo.demo.datastore.component.DataStore;
import org.demo.demo.instrument.entity.Instrument;
import org.demo.demo.musician.entity.Musician;
import org.demo.demo.skill.entity.Skill;
import org.demo.demo.skill.repository.api.SkillRepository;
import org.demo.demo.util.Level;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SkillInMemoryRepository implements SkillRepository {
    private final DataStore store;

    public SkillInMemoryRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public List<Skill> findAllByLevel(Level level) {
        return store.findAllSkills().stream()
                .filter(skill -> skill.getLevel().equals(level))
                .toList();
    }

    @Override
    public List<Skill> findAllByInstrument(Instrument instrument) {
        return store.findAllSkills().stream()
                .filter(skill -> skill.getInstrument().equals(instrument))
                .toList();
    }

    @Override
    public List<Skill> findAllByMusician(Musician musician) {
        return store.findAllSkills().stream()
                .filter(skill -> skill.getMusician().equals(musician))
                .toList();
    }

    @Override
    public Optional<Skill> find(UUID id) {
        return store.findAllSkills().stream()
                .filter(skill -> skill.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Skill> findAll() {
        return store.findAllSkills();
    }

    @Override
    public void create(Skill entity) {
        store.createSkill(entity);
    }

    @Override
    public void delete(Skill entity) {
        store.deleteSkill(entity.getId());
    }

    @Override
    public void update(Skill entity) {
        store.updateSkill(entity);
    }
}
