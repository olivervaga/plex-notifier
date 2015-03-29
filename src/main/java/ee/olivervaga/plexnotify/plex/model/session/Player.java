package ee.olivervaga.plexnotify.plex.model.session;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("Player")
public class Player {

    @XStreamAlias("platform")
    @XStreamAsAttribute
    private String platform;

    @XStreamAlias("product")
    @XStreamAsAttribute
    private String product;

    @XStreamAlias("title")
    @XStreamAsAttribute
    private String name;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
