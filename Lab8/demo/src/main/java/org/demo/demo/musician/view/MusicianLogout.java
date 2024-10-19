package org.demo.demo.musician.view;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;

@RequestScoped
@Named
public class MusicianLogout {
    private final HttpServletRequest request;

    @Inject
    public MusicianLogout(HttpServletRequest request) {
        this.request = request;
    }

    @SneakyThrows
    public String logout() {
        request.logout();
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }
}
