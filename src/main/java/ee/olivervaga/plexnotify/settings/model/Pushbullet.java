package ee.olivervaga.plexnotify.settings.model;

import java.util.List;

public class Pushbullet {

    private String accessToken;

    private List<Device> devices;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }
}
