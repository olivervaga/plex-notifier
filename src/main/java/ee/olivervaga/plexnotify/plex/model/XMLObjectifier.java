package ee.olivervaga.plexnotify.plex.model;

import com.thoughtworks.xstream.XStream;
import ee.olivervaga.plexnotify.plex.model.session.Player;
import ee.olivervaga.plexnotify.plex.model.session.PlexSessionResponse;
import ee.olivervaga.plexnotify.plex.model.session.User;
import ee.olivervaga.plexnotify.plex.model.session.Video;
import ee.olivervaga.plexnotify.plex.model.status.PlexStatusResponse;
import org.springframework.stereotype.Component;

@Component
public class XMLObjectifier {

    private Class[] statusModel = new Class[]{
            PlexStatusResponse.class,
    };

    private Class[] sessionModel = new Class[]{
            PlexSessionResponse.class,
            Player.class,
            User.class,
            Video.class
    };

    public Object toObject(String xml, ModelType type) {
        XStream xStream = new XStream();
        xStream.ignoreUnknownElements();
        switch (type) {
            case STATUS:
                xStream.processAnnotations(statusModel);
                break;
            case SESSION:
                xStream.processAnnotations(sessionModel);
                break;
        }
        return xStream.fromXML(xml);
    }

    public enum ModelType {
        STATUS, SESSION
    }
}
