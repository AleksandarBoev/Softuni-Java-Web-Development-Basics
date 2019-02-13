package app.services;

import app.domain.entities.Product;
import app.domain.enums.ProductType;
import app.domain.models.services.ProductServiceModel;
import app.domain.models.views.ProductAllViewModel;
import app.domain.models.views.ProductDetailsView;
import app.repositories.ProductRepository;
import app.utils.MyModelMapper;
import org.modelmapper.TypeMap;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private MyModelMapper myModelMapper;
    private TypeMap<ProductServiceModel, Product> serviceModelToProduct;
    private TypeMap<Product, ProductAllViewModel> productToAllViewModel;
    private TypeMap<Product, ProductDetailsView> productToDetailsView;

    @Inject
    public ProductServiceImpl(ProductRepository productRepository, MyModelMapper myModelMapper) {
        this.productRepository = productRepository;
        this.myModelMapper = myModelMapper;

        this.serviceModelToProduct = this.myModelMapper.createTypeMap(ProductServiceModel.class, Product.class);
        this.productToAllViewModel = this.myModelMapper.createTypeMap(Product.class, ProductAllViewModel.class);
        this.productToDetailsView = this.myModelMapper.createTypeMap(Product.class, ProductDetailsView.class);
    }

    @Override
    public void save(ProductServiceModel productServiceModel) {
        Product product = this.serviceModelToProduct.map(productServiceModel);
        product.setType(ProductType.valueOf(productServiceModel.getType().toUpperCase()));

        this.productRepository.save(product);
    }

    @Override
    public List<ProductAllViewModel> getProductsAllViewModels() {
        return this.productRepository.getAll().stream()
                .map(p -> this.productToAllViewModel.map(p))
                .collect(Collectors.toList());
    }

    @Override
    public ProductDetailsView getProductDetailsView(String productName) {
        Product product = this.productRepository.getProductByName(productName);
        ProductDetailsView productDetailsView = this.productToDetailsView.map(product);
        productDetailsView.setType(product.getType().toString());

        return productDetailsView;
    }


}
