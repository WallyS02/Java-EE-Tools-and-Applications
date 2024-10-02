package org.demo.demo.musician.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PutMusicianRequest {
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private String login;
    private int albumsReleased;
    private String password;
    private String email;
}
