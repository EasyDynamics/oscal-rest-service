package oscal.rest.repo;


import org.springframework.stereotype.Repository;
import oscal.rest.model.OSCALSSP;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class SSPRepository {

    private Map<String, OSCALSSP> ssps = new HashMap<>();

    public Optional<OSCALSSP> findById(String uuid) {
        return Optional.ofNullable(ssps.get(uuid));
    }

    public void add(OSCALSSP ssp) {
        ssps.put(ssp.getId(), ssp);
    }

    public Collection<OSCALSSP> getSsps() {
        return ssps.values();
    }


}
