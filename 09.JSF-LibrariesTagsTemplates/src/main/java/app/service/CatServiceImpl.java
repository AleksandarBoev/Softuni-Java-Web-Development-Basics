package app.service;

import app.domain.entities.Cat;
import app.domain.models.service.CatServiceModel;
import app.repository.CatRepository;
import org.modelmapper.ModelMapper;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class CatServiceImpl implements CatService {
    private CatRepository catRepository;
    private ModelMapper modelMapper;

    @Inject
    public CatServiceImpl(CatRepository catRepository, ModelMapper modelMapper) {
        this.catRepository = catRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean save(CatServiceModel cat) {
        //TODO validate cat properties
        this.catRepository.save(this.modelMapper.map(cat, Cat.class));
        return true;
    }

    @Override
    public List<CatServiceModel> getAllCats() {
        return this.catRepository.getAll()
                .stream()
                .map(c -> this.modelMapper.map(c, CatServiceModel.class))
                .collect(Collectors.toList());
    }
}
