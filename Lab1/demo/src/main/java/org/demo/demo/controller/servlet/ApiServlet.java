package org.demo.demo.controller.servlet;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.demo.demo.instrument.controller.api.InstrumentController;
import org.demo.demo.instrument.dto.PatchInstrumentRequest;
import org.demo.demo.instrument.dto.PutInstrumentRequest;
import org.demo.demo.musician.controller.api.MusicianController;
import org.demo.demo.musician.dto.PatchMusicianRequest;
import org.demo.demo.musician.dto.PutMusicianRequest;
import org.demo.demo.musician.dto.PutPasswordRequest;
import org.demo.demo.skill.controller.api.SkillController;
import org.demo.demo.skill.dto.PatchSkillRequest;
import org.demo.demo.skill.dto.PutSkillRequest;
import org.demo.demo.util.Level;

import java.io.IOException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(urlPatterns = {
        ApiServlet.Paths.API + "/*"
})
@MultipartConfig(maxFileSize = 200 * 1024)
public class ApiServlet extends HttpServlet {
    private MusicianController musicianController;

    private InstrumentController instrumentController;

    private SkillController skillController;

    public static final class Paths {
        public static final String API = "/api";
    }

    public static final class Patterns {
        private static final Pattern UUID = Pattern.compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");

        public static final Pattern MUSICIANS = Pattern.compile("/musicians/?");

        public static final Pattern MUSICIAN = Pattern.compile("/musicians/(%s)".formatted(UUID.pattern()));

        public static final Pattern MUSICIAN_BY_LOGIN = Pattern.compile("/musicians/login/(%s)");

        public static final Pattern MUSICIAN_BY_EMAIL = Pattern.compile("/musicians/email/(%s)");

        public static final Pattern MUSICIAN_BY_FIRST_NAME = Pattern.compile("/musicians/first-name/(%s)");

        public static final Pattern MUSICIAN_BY_LAST_NAME = Pattern.compile("/musicians/last-name/(%s)");

        public static final Pattern MUSICIAN_PASSWORD = Pattern.compile("/musicians/(%s)/password".formatted(UUID.pattern()));

        public static final Pattern MUSICIAN_IMAGE = Pattern.compile("/musicians/(%s)/image".formatted(UUID.pattern()));

        public static final Pattern INSTRUMENTS = Pattern.compile("/instruments/?");

        public static final Pattern INSTRUMENT = Pattern.compile("/instruments/(%s)".formatted(UUID.pattern()));

        public static final Pattern INSTRUMENT_BY_NAME = Pattern.compile("/instruments/name/(%s)");

        public static final Pattern INSTRUMENT_BY_TYPE = Pattern.compile("/instruments/type/(%s)");

        public static final Pattern SKILLS = Pattern.compile("/skills/?");

        public static final Pattern SKILL = Pattern.compile("/skills/(%s)".formatted(UUID.pattern()));

        public static final Pattern SKILL_BY_LEVEL = Pattern.compile("/skills/level/(%s)");

        public static final Pattern MUSICIAN_SKILLS = Pattern.compile("/skills/(%s)/musicians/?".formatted(UUID.pattern()));

        public static final Pattern INSTRUMENT_SKILLS = Pattern.compile("/skills/(%s)/instruments/?".formatted(UUID.pattern()));
    }

    private final Jsonb jsonb = JsonbBuilder.create();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getMethod().equals("PATCH")) {
            doPatch(request, response);
        } else {
            super.service(request, response);
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();
        musicianController = (MusicianController) getServletContext().getAttribute("musicianController");
        instrumentController = (InstrumentController) getServletContext().getAttribute("instrumentController");
        skillController = (SkillController) getServletContext().getAttribute("skillController");
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.MUSICIANS.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(musicianController.getMusicians()));
                return;
            } else if (path.matches(Patterns.MUSICIAN.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.MUSICIAN, path);
                response.getWriter().write(jsonb.toJson(musicianController.getMusician(uuid)));
                return;
            } else if (path.matches(Patterns.MUSICIAN_BY_LOGIN.pattern())) {
                response.setContentType("application/json");
                String login = extractString(Patterns.MUSICIAN_BY_LOGIN, path);
                response.getWriter().write(jsonb.toJson(musicianController.getMusicianByLogin(login)));
                return;
            } else if (path.matches(Patterns.MUSICIAN_BY_EMAIL.pattern())) {
                response.setContentType("application/json");
                String email = extractString(Patterns.MUSICIAN_BY_EMAIL, path);
                response.getWriter().write(jsonb.toJson(musicianController.getMusiciansByEmail(email)));
                return;
            } else if (path.matches(Patterns.MUSICIAN_BY_FIRST_NAME.pattern())) {
                response.setContentType("application/json");
                String firstName = extractString(Patterns.MUSICIAN_BY_FIRST_NAME, path);
                response.getWriter().write(jsonb.toJson(musicianController.getMusiciansByFirstName(firstName)));
                return;
            } else if (path.matches(Patterns.MUSICIAN_BY_LAST_NAME.pattern())) {
                response.setContentType("application/json");
                String lastName = extractString(Patterns.MUSICIAN_BY_LAST_NAME, path);
                response.getWriter().write(jsonb.toJson(musicianController.getMusiciansByLastName(lastName)));
                return;
            } else if (path.matches(Patterns.INSTRUMENTS.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(instrumentController.getInstruments()));
                return;
            } else if (path.matches(Patterns.INSTRUMENT.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.INSTRUMENT, path);
                response.getWriter().write(jsonb.toJson(instrumentController.getInstrument(uuid)));
                return;
            } else if (path.matches(Patterns.INSTRUMENT_BY_NAME.pattern())) {
                response.setContentType("application/json");
                String name = extractString(Patterns.INSTRUMENT_BY_NAME, path);
                response.getWriter().write(jsonb.toJson(instrumentController.getInstrumentsByName(name)));
                return;
            } else if (path.matches(Patterns.INSTRUMENT_BY_TYPE.pattern())) {
                response.setContentType("application/json");
                String type = extractString(Patterns.INSTRUMENT_BY_TYPE, path);
                response.getWriter().write(jsonb.toJson(instrumentController.getInstrumentsByType(type)));
                return;
            } else if (path.matches(Patterns.SKILLS.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(skillController.getSkills()));
                return;
            } else if (path.matches(Patterns.SKILL.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.SKILL, path);
                response.getWriter().write(jsonb.toJson(skillController.getSkill(uuid)));
                return;
            } else if (path.matches(Patterns.SKILL_BY_LEVEL.pattern())) {
                response.setContentType("application/json");
                String level = extractString(Patterns.SKILL_BY_LEVEL, path);
                response.getWriter().write(jsonb.toJson(skillController.getSkillsByLevel(Level.valueOf(level))));
                return;
            } else if (path.matches(Patterns.MUSICIAN_SKILLS.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.MUSICIAN_SKILLS, path);
                response.getWriter().write(jsonb.toJson(skillController.getSkillsByMusician(uuid)));
                return;
            } else if (path.matches(Patterns.INSTRUMENT_SKILLS.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.INSTRUMENT_SKILLS, path);
                response.getWriter().write(jsonb.toJson(skillController.getSkillsByInstrument(uuid)));
                return;
            } else if (path.matches(Patterns.MUSICIAN_IMAGE.pattern())) {
                response.setContentType("image/png");
                UUID uuid = extractUuid(Patterns.MUSICIAN_IMAGE, path);
                byte[] portrait = musicianController.getImage(uuid);
                response.setContentLength(portrait.length);
                response.getOutputStream().write(portrait);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.MUSICIAN.pattern())) {
                UUID uuid = extractUuid(Patterns.MUSICIAN, path);
                musicianController.create(uuid, jsonb.fromJson(request.getReader(), PutMusicianRequest.class));
                response.addHeader("Location", createUrl(request, Paths.API, "musicians", uuid.toString()));
                return;
            } else if (path.matches(Patterns.MUSICIAN_IMAGE.pattern())) {
                UUID uuid = extractUuid(Patterns.MUSICIAN_IMAGE, path);
                musicianController.updateImage(uuid, request.getPart("image").getInputStream());
                return;
            } else if (path.matches(Patterns.INSTRUMENT.pattern())) {
                UUID uuid = extractUuid(Patterns.INSTRUMENT, path);
                instrumentController.create(uuid, jsonb.fromJson(request.getReader(), PutInstrumentRequest.class));
                response.addHeader("Location", createUrl(request, Paths.API, "instruments", uuid.toString()));
                return;
            } else if (path.matches(Patterns.SKILL.pattern())) {
                UUID uuid = extractUuid(Patterns.SKILL, path);
                skillController.create(uuid, jsonb.fromJson(request.getReader(), PutSkillRequest.class));
                response.addHeader("Location", createUrl(request, Paths.API, "skills", uuid.toString()));
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.MUSICIAN.pattern())) {
                UUID uuid = extractUuid(Patterns.MUSICIAN, path);
                musicianController.delete(uuid);
                return;
            } else if (path.matches(Patterns.MUSICIAN_IMAGE.pattern())) {
                UUID uuid = extractUuid(Patterns.MUSICIAN_IMAGE, path);
                musicianController.deleteImage(uuid);
                return;
            } else if (path.matches(Patterns.INSTRUMENT.pattern())) {
                UUID uuid = extractUuid(Patterns.INSTRUMENT, path);
                instrumentController.delete(uuid);
                return;
            } else if (path.matches(Patterns.SKILL.pattern())) {
                UUID uuid = extractUuid(Patterns.SKILL, path);
                skillController.delete(uuid);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @SuppressWarnings("RedundantThrows")
    protected void doPatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.MUSICIAN.pattern())) {
                UUID uuid = extractUuid(Patterns.MUSICIAN, path);
                musicianController.update(uuid, jsonb.fromJson(request.getReader(), PatchMusicianRequest.class));
                return;
            } else if (path.matches(Patterns.MUSICIAN_IMAGE.pattern())) {
                UUID uuid = extractUuid(Patterns.MUSICIAN_IMAGE, path);
                musicianController.updateImage(uuid, request.getPart("image").getInputStream());
                return;
            } else if (path.matches(Patterns.MUSICIAN_PASSWORD.pattern())) {
                UUID uuid = extractUuid(Patterns.MUSICIAN_PASSWORD, path);
                musicianController.updatePassword(uuid, jsonb.fromJson(request.getReader(), PutPasswordRequest.class));
                return;
            } else if (path.matches(Patterns.INSTRUMENT.pattern())) {
                UUID uuid = extractUuid(Patterns.INSTRUMENT, path);
                instrumentController.update(uuid, jsonb.fromJson(request.getReader(), PatchInstrumentRequest.class));
                return;
            } else if (path.matches(Patterns.SKILL.pattern())) {
                UUID uuid = extractUuid(Patterns.SKILL, path);
                skillController.update(uuid, jsonb.fromJson(request.getReader(), PatchSkillRequest.class));
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private static UUID extractUuid(Pattern pattern, String path) {
        Matcher matcher = pattern.matcher(path);
        if (matcher.matches()) {
            return UUID.fromString(matcher.group(1));
        }
        throw new IllegalArgumentException("No UUID in path.");
    }

    private static String extractString(Pattern pattern, String path) {
        Matcher matcher = pattern.matcher(path);
        if (matcher.matches()) {
            return matcher.group(2);
        }
        throw new IllegalArgumentException("No UUID in path.");
    }

    private String parseRequestPath(HttpServletRequest request) {
        String path = request.getPathInfo();
        path = path != null ? path : "";
        return path;
    }

    public static String createUrl(HttpServletRequest request, String... paths) {
        StringBuilder builder = new StringBuilder();
        builder.append(request.getScheme())
                .append("://")
                .append(request.getServerName())
                .append(":")
                .append(request.getServerPort())
                .append(request.getContextPath());
        for (String path : paths) {
            builder.append("/")
                    .append(path, path.startsWith("/") ? 1 : 0, path.endsWith("/") ? path.length() - 1 : path.length());
        }
        return builder.toString();
    }
}
