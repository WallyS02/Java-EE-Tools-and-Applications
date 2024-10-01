package org.demo.demo.component;

import org.demo.demo.instrument.function.InstrumentToResponseFunction;
import org.demo.demo.instrument.function.InstrumentsToResponseFunction;
import org.demo.demo.instrument.function.RequestToInstrumentFunction;
import org.demo.demo.instrument.function.UpdateInstrumentWithRequestFunction;
import org.demo.demo.musician.function.*;
import org.demo.demo.skill.function.RequestToSkillFunction;
import org.demo.demo.skill.function.SkillToResponseFunction;
import org.demo.demo.skill.function.SkillsToResponseFunction;
import org.demo.demo.skill.function.UpdateSkillWithRequestFunction;

public class DtoFunctionFactory {
    public InstrumentsToResponseFunction instrumentsToResponseFunction() {
        return new InstrumentsToResponseFunction();
    }

    public InstrumentToResponseFunction instrumentToResponseFunction() {
        return new InstrumentToResponseFunction();
    }

    public RequestToInstrumentFunction requestToInstrumentFunction() {
        return new RequestToInstrumentFunction();
    }

    public UpdateInstrumentWithRequestFunction updateInstrumentWithRequestFunction() {
        return new UpdateInstrumentWithRequestFunction();
    }

    public MusiciansToResponseFunction musiciansToResponseFunction() {
        return new MusiciansToResponseFunction();
    }

    public MusicianToResponseFunction musicianToResponseFunction() {
        return new MusicianToResponseFunction();
    }

    public RequestToMusicianFunction requestToMusicianFunction() {
        return new RequestToMusicianFunction();
    }

    public UpdateMusicianWithRequestFunction updateMusicianWithRequestFunction() {
        return new UpdateMusicianWithRequestFunction();
    }

    public UpdateMusicianPasswordRequestFunction updateMusicianPasswordRequestFunction() {
        return new UpdateMusicianPasswordRequestFunction();
    }

    public RequestToSkillFunction requestToSkillFunction() {
        return new RequestToSkillFunction();
    }

    public SkillsToResponseFunction skillsToResponseFunction() {
        return new SkillsToResponseFunction();
    }

    public SkillToResponseFunction skillToResponseFunction() {
        return new SkillToResponseFunction();
    }

    public UpdateSkillWithRequestFunction updateSkillWithRequestFunction() {
        return new UpdateSkillWithRequestFunction();
    }
}
