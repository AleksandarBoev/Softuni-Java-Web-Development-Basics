package app.services;

import app.domain.entities.Product;
import app.domain.models.services.ProductServiceModel;

import java.util.List;

public interface ProductService {
    void save(ProductServiceModel productServiceModel);

    List<Product> getProducts();
}
