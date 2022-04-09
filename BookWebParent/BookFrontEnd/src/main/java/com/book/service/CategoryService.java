package com.book.service;

import com.book.common.entity.Category;
import com.book.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> listNoChildrenCategories() {
        List<Category> listNoChildrenCategories = new ArrayList<>();

        List<Category> listEnabledCategories = categoryRepository.findAllEnabled();

        listEnabledCategories.forEach(category -> {
            Set<Category> children = category.getChildren();
            if (children == null || children.size() == 0) {
                listNoChildrenCategories.add(category);
            }
        });

        return listNoChildrenCategories;
    }
}
