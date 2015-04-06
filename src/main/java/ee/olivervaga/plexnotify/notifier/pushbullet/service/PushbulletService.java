package ee.olivervaga.plexnotify.notifier.pushbullet.service;

import com.google.gson.Gson;
import ee.olivervaga.plexnotify.notifier.pushbullet.model.DeviceList;
import ee.olivervaga.plexnotify.plex.model.session.Video;
import ee.olivervaga.plexnotify.settings.service.SettingsService;
import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;

@Service
public class PushbulletService {

    private static Logger logger = LoggerFactory.getLogger(PushbulletService.class);

    private static final String PUSHBULLET_API_ADDRESS = "https://api.pushbullet.com";
    private static final String PUSHBULLET_PUSH = "/v2/pushes";
    private static final String PUSHBULLET_DEVICES = "/v2/devices";

    @Autowired
    private SettingsService settingsService;

    public void sendNotification(Set<Video> sessions) {
        // TODO

    }

    public DeviceList getDevices() {
        return getDevices(settingsService.getSettings().getPushbullet().getAccessToken());
    }

    public DeviceList getDevices(String token) {
        try {
            logger.debug("Getting devices");
            HttpClient client = new HttpClient();
            Credentials credentials = new UsernamePasswordCredentials(token, null);
            client.getState().setCredentials(AuthScope.ANY, credentials);
            GetMethod method = new GetMethod(PUSHBULLET_API_ADDRESS + PUSHBULLET_DEVICES);
            int code = client.executeMethod(method);
            logger.debug("Got HTTP status " + code);
            if (code == HttpStatus.SC_OK) {
                return new Gson().fromJson(new InputStreamReader(method.getResponseBodyAsStream()), DeviceList.class);
            }
            return null;
        } catch (IOException e) {
            logger.error("Error with HttpClient", e);
            return null;
        }
    }
}
