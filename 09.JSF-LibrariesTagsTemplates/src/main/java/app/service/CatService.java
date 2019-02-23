package app.service;

import app.domain.models.service.CatServiceModel;

import java.util.List;

public interface CatService {
    boolean save(CatServiceModel cat);

    List<CatServiceModel> getAllCats();
}
