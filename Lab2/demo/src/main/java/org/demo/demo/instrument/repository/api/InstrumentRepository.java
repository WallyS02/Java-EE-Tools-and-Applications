package org.demo.demo.instrument.repository.api;

import org.demo.demo.instrument.entity.Instrument;
import org.demo.demo.repository.api.Repository;

import java.util.List;
import java.util.UUID;

public interface InstrumentRepository extends Repository<Instrument, UUID> {
    List<Instrument> findAllByName(String name);
    List<Instrument> findAllByType(String type);
}
