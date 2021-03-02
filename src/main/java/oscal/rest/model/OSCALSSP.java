package oscal.rest.model;

import org.springframework.data.annotation.Id;


public class OSCALSSP {

    @Id
    private String uuid;

    private String system_security_plan;


    public String getId() {
        return uuid;
    }
    public void setUuid(String uuid) {this.uuid = uuid;}
    public void setSystem_security_plan(String system_security_plan) {
        this.system_security_plan = system_security_plan;
    }

}
