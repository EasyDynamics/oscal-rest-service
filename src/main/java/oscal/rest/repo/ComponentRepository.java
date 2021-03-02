package oscal.rest.repo;

import org.springframework.stereotype.Repository;
import oscal.rest.model.OSCALComponent;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class ComponentRepository {

    private Map<String, OSCALComponent> components = new HashMap<>();

    public Optional<OSCALComponent> findById(String uuid) {
        return Optional.ofNullable(components.get(uuid));
    }

    public void add(OSCALComponent component) {
        components.put(component.getId(), component);
    }

    public Collection<OSCALComponent> getComponents() {
        return components.values();
    }


}
