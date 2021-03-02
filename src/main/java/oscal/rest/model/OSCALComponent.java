package oscal.rest.model;

import org.springframework.data.annotation.Id;


public class OSCALComponent {

    @Id
    private String uuid;

    private String component;


    public String getId() {
        return uuid;
    }
    public void setUuid(String uuid) {this.uuid = uuid;}
    public void setComponent(String component) {
        this.component = component;
    }

}
