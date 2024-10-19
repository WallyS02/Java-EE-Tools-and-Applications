package org.demo.demo.chat.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private String sender;
    private String content;
    private String recipient;

    public boolean isPrivate() {
        return recipient != null;
    }
}
