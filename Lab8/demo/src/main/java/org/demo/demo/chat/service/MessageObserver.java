package org.demo.demo.chat.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import lombok.extern.java.Log;

@ApplicationScoped
@Log
public class MessageObserver {
    private final PushMessageContext pushMessageContext;

    @Inject
    public MessageObserver(PushMessageContext pushMessageContext) {
        this.pushMessageContext = pushMessageContext;
    }

    public void processMessage(@Observes @MessageEvent Message message) {
        if (message.isPrivate()) {
            pushMessageContext.sendToUser(message, message.getRecipient());
        } else {
            pushMessageContext.sendToAll(message);
        }
    }
}
