package app.services;

import app.domain.entities.Product;
import app.domain.models.services.ProductServiceModel;
import app.domain.models.views.ProductAllViewModel;
import app.domain.models.views.ProductDetailsView;

import java.util.List;

public interface ProductService {
    void save(ProductServiceModel productServiceModel);

    List<ProductAllViewModel> getProductsAllViewModels();

    ProductDetailsView getProductDetailsView(String productName);
}
