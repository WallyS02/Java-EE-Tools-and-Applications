package org.demo.demo.skill.controller.api;

import org.demo.demo.skill.dto.GetSkillResponse;
import org.demo.demo.skill.dto.GetSkillsResponse;
import org.demo.demo.skill.dto.PatchSkillRequest;
import org.demo.demo.skill.dto.PutSkillRequest;
import org.demo.demo.util.Level;

import java.util.UUID;

public interface SkillController {
    GetSkillsResponse getSkills();
    GetSkillsResponse getSkillsByLevel(Level level);
    GetSkillsResponse getSkillsByMusician(UUID musicianId);
    GetSkillsResponse getSkillsByInstrument(UUID instrumentId);
    GetSkillResponse getSkill(UUID id);
    void create(UUID id, PutSkillRequest request);
    void update(UUID id, PatchSkillRequest request);
    void delete(UUID id);
}
