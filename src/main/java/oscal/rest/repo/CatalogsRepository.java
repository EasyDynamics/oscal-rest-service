package oscal.rest.repo;

import org.springframework.stereotype.Repository;
import oscal.rest.model.OSCALCatalog;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class CatalogsRepository {

    private Map<String, OSCALCatalog> catalogs = new HashMap<>();

    public Optional<OSCALCatalog> findById(String catalogId) {
        return Optional.ofNullable(catalogs.get(catalogId));
    }

    public void add(OSCALCatalog catalog) {
        catalogs.put(catalog.getId(), catalog);
    }

    public Collection<OSCALCatalog> getCatalogs() {
        return catalogs.values();
    }


}
