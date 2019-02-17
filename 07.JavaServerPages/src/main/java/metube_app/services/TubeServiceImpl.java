package metube_app.services;

import metube_app.domain.entities.Tube;
import metube_app.domain.models.service.TubeServiceModel;
import metube_app.repository.TubeRepository;
import metube_app.utils.MyModelMapper;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class TubeServiceImpl implements TubeService {
    private TubeRepository repository;
    private MyModelMapper myModelMapper;

    @Inject
    public TubeServiceImpl(TubeRepository repository, MyModelMapper myModelMapper) {
        this.repository = repository;
        this.myModelMapper = myModelMapper;
    }

    @Override
    public void save(TubeServiceModel tubeServiceModel) {
        Tube tube = this.myModelMapper.map(tubeServiceModel, Tube.class);
        this.repository.save(tube);
    }

    @Override
    public TubeServiceModel findById(String id) {
        return this.myModelMapper.map(this.repository.findById(id), TubeServiceModel.class);
    }

    @Override
    public List<TubeServiceModel> findAll() {
        return this.repository.findAll()
                .stream()
                .map(t -> this.myModelMapper.map(t, TubeServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public TubeServiceModel findByName(String name) {
        Tube tube = this.repository.findByName(name);
        if (tube == null) {
            return null;
        }
        return this.myModelMapper.map(tube, TubeServiceModel.class);
    }
}
