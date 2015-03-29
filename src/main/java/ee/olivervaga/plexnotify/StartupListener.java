package ee.olivervaga.plexnotify;

import ee.olivervaga.plexnotify.plex.service.PlexPollerService;
import ee.olivervaga.plexnotify.settings.service.SettingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {

    private final Logger logger = LoggerFactory.getLogger(StartupListener.class);

    @Autowired
    private PlexPollerService plexPollerService;
    @Autowired
    private SettingsService settingsService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (settingsService.getSettings() == null) {
            logger.info("No settings file");
            plexPollerService.setPlexUp(false);
            return;
        }
        else {
            plexPollerService.pingServer();
        }
        logger.info("Plex is " + (plexPollerService.isPlexUp() ? "up" : "down"));
    }
}
