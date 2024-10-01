package org.demo.demo.skill.controller.simple;

import org.demo.demo.controller.servlet.exception.NotFoundException;
import org.demo.demo.controller.servlet.exception.BadRequestException;
import org.demo.demo.component.DtoFunctionFactory;
import org.demo.demo.skill.controller.api.SkillController;
import org.demo.demo.skill.dto.GetSkillResponse;
import org.demo.demo.skill.dto.GetSkillsResponse;
import org.demo.demo.skill.dto.PatchSkillRequest;
import org.demo.demo.skill.dto.PutSkillRequest;
import org.demo.demo.skill.service.SkillService;
import org.demo.demo.util.Level;

import java.util.UUID;

public class SkillSimpleController implements SkillController {
    private final SkillService skillService;

    private final DtoFunctionFactory dtoFunctionFactory;

    public SkillSimpleController(final SkillService skillService, final DtoFunctionFactory dtoFunctionFactory) {
        this.skillService = skillService;
        this.dtoFunctionFactory = dtoFunctionFactory;
    }

    @Override
    public GetSkillsResponse getSkills() {
        return dtoFunctionFactory.skillsToResponseFunction().apply(skillService.findAll());
    }

    @Override
    public GetSkillsResponse getSkillsByLevel(Level level) {
        return dtoFunctionFactory.skillsToResponseFunction().apply(skillService.findAllByLevel(level));
    }

    @Override
    public GetSkillsResponse getSkillsByMusician(UUID musicianId) {
        return dtoFunctionFactory.skillsToResponseFunction().apply(skillService.findAllByMusician(musicianId).orElseThrow(NotFoundException::new));
    }

    @Override
    public GetSkillsResponse getSkillsByInstrument(UUID instrumentId) {
        return dtoFunctionFactory.skillsToResponseFunction().apply(skillService.findAllByInstrument(instrumentId).orElseThrow(NotFoundException::new));
    }

    @Override
    public GetSkillResponse getSkill(UUID id) {
        return dtoFunctionFactory.skillToResponseFunction().apply(skillService.find(id).orElseThrow(NotFoundException::new));
    }

    @Override
    public void create(UUID id, PutSkillRequest request) {
        try {
            skillService.create(dtoFunctionFactory.requestToSkillFunction().apply(id, request));
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
        }
    }

    @Override
    public void update(UUID id, PatchSkillRequest request) {
        skillService.find(id).ifPresentOrElse(
                entity -> skillService.update(dtoFunctionFactory.updateSkillWithRequestFunction().apply(entity, request)),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void delete(UUID id) {
        skillService.find(id).ifPresentOrElse(
                entity -> skillService.delete(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }
}
