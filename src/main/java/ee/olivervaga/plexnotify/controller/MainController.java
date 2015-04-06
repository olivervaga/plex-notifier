package ee.olivervaga.plexnotify.controller;

import com.google.gson.Gson;
import ee.olivervaga.plexnotify.plex.service.PlexPollerService;
import ee.olivervaga.plexnotify.settings.model.Settings;
import ee.olivervaga.plexnotify.settings.service.SettingsService;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class MainController {

    private final static Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private SettingsService settingsService;
    @Autowired
    private PlexPollerService plexPollerService;

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        Settings settings = settingsService.getSettings();
        if (settings == null) {
            return "redirect:settings?setup=true";
        }
        model.addAttribute("plexResponse", plexPollerService.pingServer());
        model.addAttribute("serverAddress", settings.getServerAddress() + ":" + settings.getServerPort());
        return "index";
    }

    @RequestMapping(value = "settings", method = RequestMethod.GET)
    public String settings(Model model) {
        Settings settings = settingsService.getSettings();
        if (settings == null) {
            model.addAttribute("settings", new Settings());
        } else {
            logger.debug(ReflectionToStringBuilder.toString(settings, ToStringStyle.MULTI_LINE_STYLE));
            logger.debug(ReflectionToStringBuilder.toString(settings.getPushbullet(), ToStringStyle.MULTI_LINE_STYLE));
            model.addAttribute("settings", settings);
            model.addAttribute("devices", new Gson().toJson(settings.getPushbullet().getDevices()));
        }
        return "settings";
    }

    @RequestMapping(value = "settings", method = RequestMethod.POST)
    public String settings(Settings settings, Model model) {
        logger.debug(ReflectionToStringBuilder.toString(settings, ToStringStyle.MULTI_LINE_STYLE));
        logger.debug(ReflectionToStringBuilder.toString(settings.getPushbullet(), ToStringStyle.MULTI_LINE_STYLE));
        settingsService.saveSettings(settings);
        model.addAttribute("settings", settings);
        model.addAttribute("devices", new Gson().toJson(settings.getPushbullet().getDevices()));
        return "settings";
    }
}
