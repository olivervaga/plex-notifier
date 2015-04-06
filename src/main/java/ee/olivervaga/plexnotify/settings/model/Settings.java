package ee.olivervaga.plexnotify.settings.model;

public class Settings {

    private String serverAddress;

    private String serverPort;

    private Pushbullet pushbullet;

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public Pushbullet getPushbullet() {
        return pushbullet;
    }

    public void setPushbullet(Pushbullet pushbullet) {
        this.pushbullet = pushbullet;
    }
}
