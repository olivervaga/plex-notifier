package ee.olivervaga.plexnotify.settings.model;

public class Settings {

    private String serverAddress;

    private String serverPort;

    private String plexUser;

    private String plexPassword;

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

    public String getPlexUser() {
        return plexUser;
    }

    public void setPlexUser(String plexUser) {
        this.plexUser = plexUser;
    }

    public String getPlexPassword() {
        return plexPassword;
    }

    public void setPlexPassword(String plexPassword) {
        this.plexPassword = plexPassword;
    }
}
