package org.demo.demo.datastore.component;

import lombok.extern.java.Log;
import org.demo.demo.instrument.entity.Instrument;
import org.demo.demo.musician.entity.Musician;
import org.demo.demo.serialization.component.CloningUtility;
import org.demo.demo.skill.entity.Skill;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Log
public class DataStore {
    private final Set<Instrument> instruments = new HashSet<>();

    private final Set<Musician> musicians = new HashSet<>();

    private final Set<Skill> skills = new HashSet<>();

    private final CloningUtility cloningUtility;

    public DataStore(CloningUtility cloningUtility) {
        this.cloningUtility = cloningUtility;
    }

    public synchronized List<Instrument> findAllInstruments() {
        return instruments.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized List<Musician> findAllMusicians() {
        return musicians.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized List<Skill> findAllSkills() {
        return skills.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized void createInstrument(Instrument value) throws IllegalArgumentException {
        if (instruments.stream().anyMatch(instrument -> instrument.getId().equals(value.getId()))) {
            throw new IllegalArgumentException("The instrument id \"%s\" is not unique".formatted(value.getId()));
        }
        instruments.add(cloningUtility.clone(value));
    }

    public synchronized void createMusician(Musician value) throws IllegalArgumentException {
        if (musicians.stream().anyMatch(musician -> musician.getId().equals(value.getId()))) {
            throw new IllegalArgumentException("The musician id \"%s\" is not unique".formatted(value.getId()));
        }
        musicians.add(cloningUtility.clone(value));
    }

    public synchronized void createSkill(Skill value) throws IllegalArgumentException {
        if (skills.stream().anyMatch(skill -> skill.getId().equals(value.getId()))) {
            throw new IllegalArgumentException("The skill id \"%s\" is not unique".formatted(value.getId()));
        }
        Skill entity = cloneWithRelationships(value);
        skills.add(entity);
    }

    public synchronized void updateInstrument(Instrument value) throws IllegalArgumentException {
        //Instrument entity = cloneWithRelationships(value);
        if (instruments.removeIf(instrument -> instrument.getId().equals(value.getId()))) {
            instruments.add(cloningUtility.clone(value));
        } else {
            throw new IllegalArgumentException("The instrument with id \"%s\" does not exist".formatted(value.getId()));
        }
    }

    public synchronized void updateMusician(Musician value) throws IllegalArgumentException {
        //Musician entity = cloneWithRelationships(value);
        if (musicians.removeIf(musician -> musician.getId().equals(value.getId()))) {
            musicians.add(cloningUtility.clone(value));
        } else {
            throw new IllegalArgumentException("The musician with id \"%s\" does not exist".formatted(value.getId()));
        }
    }

    public synchronized void updateSkill(Skill value) throws IllegalArgumentException {
        Skill entity = cloneWithRelationships(value);
        if (skills.removeIf(skill -> skill.getId().equals(value.getId()))) {
            skills.add(entity);
        } else {
            throw new IllegalArgumentException("The skill with id \"%s\" does not exist".formatted(value.getId()));
        }
    }

    public synchronized void deleteInstrument(UUID id) throws IllegalArgumentException {
        if (!instruments.removeIf(instrument -> instrument.getId().equals(id))) {
            throw new IllegalArgumentException("The instrument with id \"%s\" does not exist".formatted(id));
        }
    }

    public synchronized void deleteMusician(UUID id) throws IllegalArgumentException {
        if (!musicians.removeIf(musician -> musician.getId().equals(id))) {
            throw new IllegalArgumentException("The musician with id \"%s\" does not exist".formatted(id));
        }
    }

    public synchronized void deleteSkill(UUID id) throws IllegalArgumentException {
        if (!skills.removeIf(skill -> skill.getId().equals(id))) {
            throw new IllegalArgumentException("The skill with id \"%s\" does not exist".formatted(id));
        }
    }

    private Skill cloneWithRelationships(Skill value) {
        Skill entity = cloningUtility.clone(value);

        if (entity.getInstrument() != null) {
            entity.setInstrument(instruments.stream()
                    .filter(instrument -> instrument.getId().equals(value.getInstrument().getId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No instrument with id \"%s\".".formatted(value.getInstrument().getId()))));
        }

        if (entity.getMusician() != null) {
            entity.setMusician(musicians.stream()
                    .filter(musician -> musician.getId().equals(value.getMusician().getId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No musician with id \"%s\".".formatted(value.getMusician().getId()))));
        }

        return entity;
    }
}
