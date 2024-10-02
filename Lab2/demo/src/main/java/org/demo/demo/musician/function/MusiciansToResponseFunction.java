package org.demo.demo.musician.function;

import org.demo.demo.musician.dto.GetMusiciansResponse;
import org.demo.demo.musician.entity.Musician;

import java.util.List;
import java.util.function.Function;

public class MusiciansToResponseFunction implements Function<List<Musician>, GetMusiciansResponse> {
    @Override
    public GetMusiciansResponse apply(List<Musician> musicians) {
        return GetMusiciansResponse.builder()
                .musicians(musicians.stream()
                        .map(musician -> GetMusiciansResponse.Musician.builder()
                                .id(musician.getId())
                                .login(musician.getLogin())
                                .build())
                        .toList())
                .build();
    }
}
