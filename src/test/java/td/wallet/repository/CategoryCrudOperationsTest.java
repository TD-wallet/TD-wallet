package td.wallet.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import td.wallet.models.Category;
import td.wallet.utils.ConnectionProvider;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryCrudOperationsTest implements CrudOperationsTest {
    public CategoryCrudOperationsTest(CategoryCrudOperations categoryCrudOperations) {
        this.categoryCrudOperations = categoryCrudOperations;
    }

    private final CategoryCrudOperations categoryCrudOperations;

    @BeforeAll
    public static void setOriginalConnection() {
        ConnectionProvider connectionProvider = new ConnectionProvider();
        Connection connection = connectionProvider.getConnection();
    }


    @AfterEach
    public void cleanUpTestSave() {
        cleanUpTestData();
    }

    @Test
    @Override
    public void testFindById() {
        Category toFind = categoryCrudOperations.findById(44);
        assertNotNull(toFind);
    }

    @Test
    @Override
    public void testFindAll() {
        List<Category> categoryList = categoryCrudOperations.findAll();
        assertNotNull(categoryList);
        assertFalse(categoryList.isEmpty());
    }

    @Test
    @Override
    public void testSave() {
        Category toSave = new Category("blablabla", "blibli");
        Category saved = categoryCrudOperations.save(toSave);
        assertNotNull(saved);
        assertNotEquals(0, saved.getId());
    }

    @Test
    @Override
    public void testSaveAll() {
        Category category1 = new Category("bababa", "lalalala");
        Category category2 = new Category("bobobo", "lalalala");
        List<Category> categoryList = Arrays.asList(category1, category2);
        List<Category> categorySaved = categoryCrudOperations.saveAll(categoryList);
        assertNotNull(categorySaved);
        assertEquals(categoryList.size(), categorySaved.size());
    }

    @Test
    @Override
    public void testDelete() {
        Category category = categoryCrudOperations.findById(80);
        Category toDelete = categoryCrudOperations.delete(category);
        assertNotNull(toDelete);
        assertEquals(category.getId(), toDelete.getId());
    }

    private void cleanUpTestData() {
        Category category1 = categoryCrudOperations.findById(80);
        Category category2 = categoryCrudOperations.findById(81);
        Category category3 = categoryCrudOperations.findById(82);

        if (category1 != null) {
            categoryCrudOperations.delete(category1);
        }
        if (category2 != null) {
            categoryCrudOperations.delete(category2);
        }
        if (category3 != null) {
            categoryCrudOperations.delete(category3);
        }
    }
}
