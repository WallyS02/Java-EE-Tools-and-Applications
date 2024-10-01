package org.demo.demo.skill.repository.api;

import org.demo.demo.instrument.entity.Instrument;
import org.demo.demo.musician.entity.Musician;
import org.demo.demo.repository.api.Repository;
import org.demo.demo.skill.entity.Skill;
import org.demo.demo.util.Level;

import java.util.List;
import java.util.UUID;

public interface SkillRepository extends Repository<Skill, UUID> {
    List<Skill> findAllByLevel(Level level);
    List<Skill> findAllByInstrument(Instrument instrument);
    List<Skill> findAllByMusician(Musician musician);
}
