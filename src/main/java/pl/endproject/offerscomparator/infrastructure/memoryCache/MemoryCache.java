package pl.endproject.offerscomparator.infrastructure.memoryCache;

import lombok.Data;
import org.springframework.stereotype.Component;
import pl.endproject.offerscomparator.domain.Product;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class MemoryCache {
    private List<List<Product>> cashProducts = new ArrayList<>();

    private int getNextId(){

        return cashProducts.size();
    }

    public List<List<Product>> getCashProducts() {
        return cashProducts;
    }

    public List<Product> findById(int id) {

        return cashProducts.get(id);
    }

    public int saveProducts(List<Product> products){
        int id = getNextId();
        cashProducts.add(id,products);

        return id;
    }
}
