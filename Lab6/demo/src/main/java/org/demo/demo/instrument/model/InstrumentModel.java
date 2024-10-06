package org.demo.demo.instrument.model;

import lombok.*;
import org.demo.demo.util.Level;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class InstrumentModel implements Serializable {
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
}
