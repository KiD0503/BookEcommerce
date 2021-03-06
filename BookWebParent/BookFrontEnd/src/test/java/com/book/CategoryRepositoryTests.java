package com.book;

import com.book.common.entity.Category;
import com.book.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CategoryRepositoryTests {
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testListEnabledCategories() {
        List<Category> categories = categoryRepository.findAllEnabled();
        categories.forEach(category -> {
            System.out.println(category.getName() + " (" + category.isEnabled() + ")");
        });
    }

    @Test
    public void testFindCategoryByAlias() {
        String alias = "Sub Test";
        Category category = categoryRepository.findByAliasEnabled(alias);

        assertThat(category).isNotNull();
    }
}
