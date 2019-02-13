package app.repositories;

import app.domain.entities.Product;

public interface ProductRepository extends GenericRepository<Product, String> {
    Product getProductByName(String name);
}
