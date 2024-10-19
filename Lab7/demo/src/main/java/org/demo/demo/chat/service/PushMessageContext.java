package org.demo.demo.chat.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.push.Push;
import jakarta.faces.push.PushContext;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

@ApplicationScoped
@Log
@NoArgsConstructor(force = true)
public class PushMessageContext {
    private PushContext chat;
    private PushContext privateChat;

    @Inject
    public PushMessageContext(@Push(channel = "chat") PushContext chat, @Push(channel = "privateChat") PushContext privateChat) {
        this.chat = chat;
        this.privateChat = privateChat;
    }

    public void sendToAll(Message message) {
        chat.send(message);
    }

    public void sendToUser(Message message, String login) {
        privateChat.send(message, login);
    }
}
