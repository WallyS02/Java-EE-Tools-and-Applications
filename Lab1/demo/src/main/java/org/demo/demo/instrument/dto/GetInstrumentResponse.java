package org.demo.demo.instrument.dto;

import lombok.*;
import org.demo.demo.musician.dto.GetMusicianResponse;
import org.demo.demo.util.Level;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetInstrumentResponse {
    private UUID id;
    private String name;
    private String type;
    private int typicalPrice;
    private float tuningFrequency;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Skill {
        private UUID id;
        private Level level;
    }

    @Singular
    private List<GetInstrumentResponse.Skill> skills;
}
