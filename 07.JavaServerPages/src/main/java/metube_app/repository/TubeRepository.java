package metube_app.repository;

import metube_app.domain.entities.Tube;

public interface TubeRepository extends GenericRepository<Tube, String> {
    Tube findByName(String name);
}
