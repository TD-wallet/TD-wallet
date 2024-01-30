package td.wallet.service;

import org.springframework.stereotype.Service;
import td.wallet.models.Category;
import td.wallet.repository.CategoryCrudOperations;

import java.util.List;


@Service
public class CategoryService {
    private final CategoryCrudOperations categoryCrudOperations;

    public CategoryService(CategoryCrudOperations categoryCrudOperations) {
        this.categoryCrudOperations = categoryCrudOperations;
    }

    public Category findCategoryById(long id) {
        return categoryCrudOperations.findById(id);
    }
    public List<Category> findAllCategories() {
        return categoryCrudOperations.findAll();
    }
    public Category saveSingleCategory(Category category) {
        return categoryCrudOperations.save(category);
    }
    public List<Category> saveCategories(List<Category> categories) {
        return categoryCrudOperations.saveAll(categories);
    }
    public Category deleteCategory(long id) {
        Category toDelete = categoryCrudOperations.findById(id);
        return categoryCrudOperations.delete(toDelete);
    }
}
