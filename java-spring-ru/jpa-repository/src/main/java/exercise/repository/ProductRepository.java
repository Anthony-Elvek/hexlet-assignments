package exercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import exercise.model.Product;

import org.springframework.data.domain.Sort;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // BEGIN
    @Query("SELECT e FROM Product e WHERE e.price BETWEEN :minPrice and :maxPrice order by e.price asc")
    List<Product> findProductsByPrice(Integer minPrice, Integer maxPrice);

    @Query("SELECT e FROM Product e WHERE e.price > :minPrice order by e.price asc")
    List<Product> findProductsByMinPrice(Integer minPrice);

    @Query("SELECT e FROM Product e WHERE e.price < :maxPrice order by e.price asc")
    List<Product> findProductsByMaxPrice(Integer maxPrice);
    // END
}
