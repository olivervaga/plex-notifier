package ee.olivervaga.plexnotify.plex.service;

import ee.olivervaga.plexnotify.plex.model.XMLObjectifier;
import ee.olivervaga.plexnotify.plex.model.session.PlexSessionResponse;
import ee.olivervaga.plexnotify.plex.model.session.Video;
import ee.olivervaga.plexnotify.plex.model.status.PlexStatusResponse;
import ee.olivervaga.plexnotify.settings.model.Settings;
import ee.olivervaga.plexnotify.settings.service.SettingsService;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@Service
public class PlexPollerService {

    private static Logger logger = LoggerFactory.getLogger(PlexPollerService.class);

    private static final String SESSION_PATH = "/status/sessions";

    private Set<Video> currentSessions = new TreeSet<>();

    private boolean plexUp;

    private final Object lock = new Object();

    @Autowired
    private XMLObjectifier objectifier;
    @Autowired
    private SettingsService settingsService;
    @Autowired
    private SimpMessagingTemplate brokerMessagingTemplate;

    public PlexStatusResponse pingServer() {
        Settings settings = settingsService.getSettings();
        if (settings == null || settings.getServerAddress() == null || settings.getServerPort() == null) {
            plexUp = false;
            return null;
        }
        try {
//            HttpClientParams params = new HttpClientParams();
//            params.setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 5000);
            HttpClient client = new HttpClient();
//            client.setParams(params);
            GetMethod method = new GetMethod("http://" + settings.getServerAddress() + ":" + settings.getServerPort());
            int code = client.executeMethod(method);
            if (code == HttpStatus.SC_OK) {
                if (!plexUp) plexUp = true;
                return (PlexStatusResponse) objectifier.toObject(method.getResponseBodyAsString(), XMLObjectifier.ModelType.STATUS);
            } else {
                return null;
            }
        } catch (IOException e) {
            logger.error("Error with HttpClient", e);
            return null;
        }
    }

    private PlexSessionResponse getPlexSession() {
        Settings settings = settingsService.getSettings();
        if (settings == null || settings.getServerAddress() == null || settings.getServerPort() == null) {
            plexUp = false;
            return null;
        }
        try {
//            HttpClientParams params = new HttpClientParams();
//            params.setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 5000);
            HttpClient client = new HttpClient();
//            client.setParams(params);
            GetMethod method = new GetMethod("http://" + settings.getServerAddress() + ":" + settings.getServerPort() + SESSION_PATH);
            int code = client.executeMethod(method);
            if (code == HttpStatus.SC_OK) {
                if (!plexUp) plexUp = true;
                PlexSessionResponse response = (PlexSessionResponse) objectifier.toObject(method.getResponseBodyAsString(), XMLObjectifier.ModelType.SESSION);
//                logger.info(ReflectionToStringBuilder.toString(response, ToStringStyle.MULTI_LINE_STYLE));
                updateSessionsList(response);
                return response;
            } else {
                return null;
            }
        } catch (IOException e) {
            logger.error("Error with HttpClient", e);
            return null;
        }
    }

    @Scheduled(initialDelay = 5000, fixedRate = 5000)
    private void updatePlexSessions() {
        if (!plexUp)
            return;
        updateSessionsList(getPlexSession());
        brokerMessagingTemplate.convertAndSend("/sessions", currentSessions);
    }

    private void updateSessionsList(PlexSessionResponse sessions) {
        if (sessions.getVideos() != null) {
            Set<Video> newSessions = new HashSet<>(sessions.getVideos());
            Set<Video> videosToRemove = new HashSet<>(currentSessions);
            videosToRemove.removeAll(newSessions);
            Set<Video> videosToAdd = new HashSet<>(newSessions);
            videosToAdd.removeAll(currentSessions);
            synchronized (lock) {
                videosToRemove.stream().forEach(currentSessions::remove);
                videosToAdd.stream().forEach(currentSessions::add);
            }
        } else {
            synchronized (lock) {
                currentSessions.clear();
            }
        }
    }

    public boolean isPlexUp() {
        return plexUp;
    }

    public void setPlexUp(boolean plexUp) {
        this.plexUp = plexUp;
    }

    public Set<Video> getCurrentSessions() {
        synchronized (lock) {
            return currentSessions;
        }
    }
}
