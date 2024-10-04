package org.demo.demo.instrument.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PutInstrumentRequest {
    private String name;
    private String type;
    private int typicalPrice;
    private float tuningFrequency;
}
