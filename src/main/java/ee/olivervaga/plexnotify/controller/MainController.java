package ee.olivervaga.plexnotify.controller;

import ee.olivervaga.plexnotify.plex.service.PlexPollerService;
import ee.olivervaga.plexnotify.settings.model.Settings;
import ee.olivervaga.plexnotify.settings.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class MainController {

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
        model.addAttribute("plexSessions", plexPollerService.getCurrentSessions());
        model.addAttribute("serverAddress", settings.getServerAddress() + ":" + settings.getServerPort());
        return "index";
    }

    @RequestMapping(value = "settings", method = RequestMethod.GET)
    public String settings(Model model) {
        Settings settings = settingsService.getSettings();
        if (settings == null) {
            model.addAttribute("settings", new Settings());
        } else {
            model.addAttribute("settings", settings);
        }
        return "settings";
    }

    @RequestMapping(value = "settings", method = RequestMethod.POST)
    public String settings(Settings settings, Model model) {
        settingsService.saveSettings(settings);
        model.addAttribute("settings", settings);
        return "settings";
    }
}
