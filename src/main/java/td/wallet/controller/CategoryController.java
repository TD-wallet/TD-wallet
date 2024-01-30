package td.wallet.controller;

import org.springframework.web.bind.annotation.*;
import td.wallet.models.Category;
import td.wallet.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping(path = "/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
     @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable long id){
        return categoryService.findCategoryById(id);
    }
    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.findAllCategories();
    }
    @PostMapping("/new")
    public Category postCategory(@RequestBody Category category) {
        return categoryService.saveSingleCategory(category);
    }
    @PostMapping("/news")
    public List<Category> postNewCategories(@RequestBody List<Category> categories) {
        return categoryService.saveCategories(categories);
    }
    @DeleteMapping("/delete/{id}")
    public Category deleteCategory(@PathVariable long id) {
        return categoryService.deleteCategory(id);
    }
}
