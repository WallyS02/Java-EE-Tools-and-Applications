package org.demo.demo.chat.view;

import jakarta.ejb.EJB;
import jakarta.enterprise.event.Event;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.SecurityContext;
import lombok.Getter;
import lombok.Setter;
import org.demo.demo.chat.service.Message;
import org.demo.demo.chat.service.MessageEvent;
import org.demo.demo.musician.entity.Musician;
import org.demo.demo.musician.service.MusicianService;

import java.io.Serializable;
import java.util.*;

@Named
@ViewScoped
public class ChatView implements Serializable {
    @Setter
    @Getter
    private String message;

    @Setter
    @Getter
    private String recipient;

    @Getter
    @Setter
    private List<String> recipients;

    private final Event<Message> messageEvent;

    private final SecurityContext securityContext;

    @EJB
    private MusicianService musicianService;

    @Inject
    public ChatView(@MessageEvent Event<Message> messageEvent, SecurityContext securityContext) {
        this.messageEvent = messageEvent;
        this.securityContext = securityContext;
    }

    public void init() {
        List<Musician> musicians = musicianService.findAll();
        List<String> musicianRecipients = new ArrayList<>();
        for (Musician musician : musicians) {
            musicianRecipients.add(musician.getLogin());
        }
        recipients = new ArrayList<>();
        recipients.add("all");
        recipients.addAll(musicianRecipients);
    }

    public void sendMessage() {
        messageEvent.fire(new Message(securityContext.getCallerPrincipal().getName(), message, recipient.equals("all") ? null : recipient));
        message = "";
    }
}
