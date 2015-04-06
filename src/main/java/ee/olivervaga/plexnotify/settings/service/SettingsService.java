package ee.olivervaga.plexnotify.settings.service;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ee.olivervaga.plexnotify.plex.service.PlexPollerService;
import ee.olivervaga.plexnotify.settings.model.Settings;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

@Service
public class SettingsService {

    private static final String HOME_PROPERTY = "user.home";
    private static final String PLEX_DIRECTORY_NAME = ".plexnotifier";
    private static final String PLEX_SETTINGS_FILE = "settings.json";

    private static final Logger logger = LoggerFactory.getLogger(SettingsService.class);

    private String userDirectory;
    private Settings settings;

    @Autowired
    private PlexPollerService plexPollerService;

    public Settings getSettings() {
        if (settings != null) {
            return settings;
        }
        if (userDirectory == null) {
            userDirectory = System.getProperty(HOME_PROPERTY);
        }
        File settingsDirectory = new File(userDirectory + "/" + PLEX_DIRECTORY_NAME);
        File settingsFile = new File(userDirectory + "/" + PLEX_DIRECTORY_NAME + "/" + PLEX_SETTINGS_FILE);
        if (!settingsDirectory.exists() || !settingsFile.exists()) {
            return null;
        } else {
            try {
                String settingsString = FileUtils.readFileToString(settingsFile, Charset.forName("UTF-8"));
                Gson gson = new Gson();
                this.settings = gson.fromJson(settingsString, Settings.class);
                return settings;
            } catch (IOException e) {
                logger.error("Settings file not found", e);
                return null;
            }
        }
    }

    public void saveSettings(Settings settings) {
        if (settings == null) {
            return;
        }
        if (userDirectory == null) {
            userDirectory = System.getProperty(HOME_PROPERTY);
        }
        File settingsDirectory = new File(userDirectory + "/" + PLEX_DIRECTORY_NAME);
        File settingsFile = new File(userDirectory + "/" + PLEX_DIRECTORY_NAME + "/" + PLEX_SETTINGS_FILE);
        if (!settingsDirectory.exists()) {
            settingsDirectory.mkdirs();
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String settingsToFile = gson.toJson(settings);
        try {
            FileUtils.write(settingsFile, settingsToFile, Charset.forName("UTF-8"));
            this.settings = settings;
        } catch (IOException e) {
            logger.error("Error saving settings", e);
        }
    }


}
