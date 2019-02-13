package app.services;

import app.domain.entities.Product;
import app.domain.enums.ProductType;
import app.domain.models.services.ProductServiceModel;
import app.repositories.ProductRepository;
import app.utils.MyModelMapper;
import org.modelmapper.TypeMap;

import javax.inject.Inject;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private MyModelMapper myModelMapper;
    private TypeMap<ProductServiceModel, Product> typeMap;

    @Inject
    public ProductServiceImpl(ProductRepository productRepository, MyModelMapper myModelMapper) {
        this.productRepository = productRepository;
        this.myModelMapper = myModelMapper;
        this.typeMap = this.myModelMapper.createTypeMap(ProductServiceModel.class, Product.class);
    }

    @Override
    public void save(ProductServiceModel productServiceModel) {
        Product product = this.typeMap.map(productServiceModel);
        product.setType(ProductType.valueOf(productServiceModel.getType().toUpperCase()));

        this.productRepository.save(product);
    }

    @Override
    public List<Product> getProducts() {
        return this.productRepository.getAll();
    }
}
