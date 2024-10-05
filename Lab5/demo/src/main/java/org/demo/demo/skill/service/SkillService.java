package org.demo.demo.skill.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import org.demo.demo.instrument.repository.api.InstrumentRepository;
import org.demo.demo.musician.repository.api.MusicianRepository;
import org.demo.demo.skill.entity.Skill;
import org.demo.demo.skill.repository.api.SkillRepository;
import org.demo.demo.util.Level;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class SkillService {
    private final SkillRepository skillRepository;

    private final InstrumentRepository instrumentRepository;

    private final MusicianRepository musicianRepository;

    @Inject
    public SkillService(InstrumentRepository instrumentRepository, SkillRepository skillRepository, MusicianRepository musicianRepository) {
        this.instrumentRepository = instrumentRepository;
        this.skillRepository = skillRepository;
        this.musicianRepository = musicianRepository;
    }

    public List<Skill> findAllByLevel(Level level) {
        return skillRepository.findAllByLevel(level);
    }

    public Optional<List<Skill>> findAllByInstrument(UUID instrumentId) {
        return instrumentRepository.find(instrumentId)
                .map(skillRepository::findAllByInstrument);
    }

    /*public Optional<List<Skill>> findAllByMusician(UUID musicianId) {
        return musicianRepository.find(musicianId)
                .map(skillRepository::findAllByMusician);
    }*/

    public Optional<Skill> find(UUID id) {
        return skillRepository.find(id);
    }

    public List<Skill> findAll() {
        return skillRepository.findAll();
    }

    @Transactional
    public void create(Skill entity) {
        if(skillRepository.find(entity.getId()).isPresent()) {
            throw new IllegalArgumentException("Skill already exists");
        }
        if(instrumentRepository.find(entity.getInstrument().getId()).isEmpty()) {
            throw new IllegalArgumentException("Instrument does not exists");
        }
        skillRepository.create(entity);
    }

    @Transactional
    public void delete(UUID id) {
        skillRepository.delete(skillRepository.find(id).orElseThrow());
    }

    @Transactional
    public void update(Skill entity) {
        skillRepository.update(entity);
    }
}
