package ee.olivervaga.plexnotify.plex.model.session;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("Video")
public class Video implements Comparable<Video> {

    @XStreamAlias("thumb")
    @XStreamAsAttribute
    private String thumbnail;

    @XStreamAlias("title")
    @XStreamAsAttribute
    private String title;

    @XStreamAlias("grandparentTitle")
    @XStreamAsAttribute
    private String grandparentTitle;

    @XStreamAlias("grandparentThumb")
    @XStreamAsAttribute
    private String grandparentThumbnail;

    @XStreamAlias("sessionKey")
    @XStreamAsAttribute
    private String sessionKey;

    @XStreamAlias("User")
    private User user;

    @XStreamAlias("Player")
    private Player player;

    // Titles work differently TV shows and movies
    public String getActualTitle() {
        if (grandparentTitle != null) {
            return grandparentTitle + " - " + title;
        } else {
            return title;
        }
    }

    public String getActualThumbnail() {
        if (grandparentThumbnail != null) {
            return grandparentThumbnail;
        } else {
            return thumbnail;
        }
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGrandparentTitle() {
        return grandparentTitle;
    }

    public void setGrandparentTitle(String grandparentTitle) {
        this.grandparentTitle = grandparentTitle;
    }

    public String getGrandparentThumbnail() {
        return grandparentThumbnail;
    }

    public void setGrandparentThumbnail(String grandparentThumbnail) {
        this.grandparentThumbnail = grandparentThumbnail;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public int compareTo(Video v) {
        return this.sessionKey.compareTo(v.getSessionKey());
    }
}
