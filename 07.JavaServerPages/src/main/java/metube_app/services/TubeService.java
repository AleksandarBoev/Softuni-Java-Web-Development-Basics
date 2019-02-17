package metube_app.services;

import metube_app.domain.models.service.TubeServiceModel;
import metube_app.repository.TubeRepository;

import java.util.List;

public interface TubeService {
    void save(TubeServiceModel tubeServiceModel);

    TubeServiceModel findById(String id);

    List<TubeServiceModel> findAll();

    TubeServiceModel findByName(String name);
}
