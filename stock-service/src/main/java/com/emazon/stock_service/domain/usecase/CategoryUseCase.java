package com.emazon.stock_service.domain.usecase;

import com.emazon.stock_service.domain.exception.InvalidSortDirectionException;
import com.emazon.stock_service.domain.model.Category;
import com.emazon.stock_service.domain.model.CustomPage;
import com.emazon.stock_service.domain.model.SortDirection;
import com.emazon.stock_service.domain.spi.ICategoryPersistencePort;
import com.emazon.stock_service.domain.api.ICategoryServicePort;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;

@RestController
@RequestMapping("/api/categories")

public class CategoryUseCase implements ICategoryServicePort {

    private final ICategoryPersistencePort categoryPersistencePort;

    public CategoryUseCase(ICategoryPersistencePort categoryPersistencePort) {
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public void saveCategory(Category category) {

        categoryPersistencePort.saveCategory(category);
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryPersistencePort.getCategoryById(id);
    }

    @Override
    public CustomPage<Category> getPaginationCategoriesByAscAndDesc(SortDirection sortDirection, int page, int size) {
        CustomPage<Category> customPageCategory = categoryPersistencePort.getPaginationCategories(sortDirection, page, size);

        if ("ASC".equalsIgnoreCase(sortDirection.name()) || "DESC".equalsIgnoreCase(sortDirection.name())) {
            customPageCategory.setContent(
                    customPageCategory.getContent().stream()
                            .sorted("ASC".equalsIgnoreCase(sortDirection.name()) ?
                                    Comparator.comparing(Category::getName) :
                                    Comparator.comparing(Category::getName).reversed())
                            .toList()
            );
        }   else {
            throw new InvalidSortDirectionException();
        }

        return customPageCategory;
    }
//    @Override
//    public ListPage<Category> getPaginationCategoriesByAscAndDesc(SortDirection sortDirection, int page, int size) {
//        // Implementación del método de paginación y ordenación
//        ListPage<Category> listPageCategory = persistencePort.getPaginationCategories(page, size);
//
//        if ("ASC".equalsIgnoreCase(sortDirection.name()) || "DESC".equalsIgnoreCase(sortDirection.name())) {
//            listPageCategory.setContent(
//                    listPageCategory.getContent().stream()
//                            .sorted("ASC".equalsIgnoreCase(sortDirection.name()) ?
//                                    Comparator.comparing(Category::getName) :
//                                    Comparator.comparing(Category::getName).reversed())
//                            .toList()
//            );
//        } else {
//            throw new InvalidSortDirectionException();
//        }
//
//        return listPageCategory;
//    }

}






