package com.example.flowlet.application.service;

import com.example.flowlet.application.dto.CategoryRequest;
import com.example.flowlet.application.dto.CategoryResponse;
import com.example.flowlet.domain.model.Category;
import com.example.flowlet.domain.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryApplicationService {

    private final CategoryRepository categoryRepository;

    /**
     * カテゴリーを階層構造で取得します。
     */
    @Transactional(readOnly = true)
    public List<CategoryResponse> findAllAsTree() {
        List<Category> allCategories = categoryRepository.findAll();
    
        // IDをキーとしたResponseのマップを作成（childrenは空）
        Map<String, CategoryResponse> responseMap = allCategories.stream()
                .collect(Collectors.toMap(
                        Category::categoryCd,
                        c -> new CategoryResponse(
                                c.categoryCd(),
                                c.categoryName(),
                                c.parentCategory() != null ? c.parentCategory().categoryCd() : null,
                                c.flowType(),
                                new java.util.ArrayList<>()
                        )
                ));

        // 親子関係を構築
        List<CategoryResponse> rootNodes = new java.util.ArrayList<>();
        for (Category category : allCategories) {
            CategoryResponse current = responseMap.get(category.categoryCd());
            if (category.parentCategory() == null) {
                rootNodes.add(current);
            } else {
                CategoryResponse parent = responseMap.get(category.parentCategory().categoryCd());
                if (parent != null) {
                    parent.childrenCategories().add(current);
                }
            }
        }
        return rootNodes;
    }

    @Transactional
    public CategoryResponse create(CategoryRequest request) {
        Category parent = null;
        if (request.parentCategoryCd() != null) {
            parent = categoryRepository.findById(request.parentCategoryCd())
                    .orElseThrow(() -> new IllegalArgumentException("Parent category not found"));
        }

        Category category = new Category(
                null,
                parent,
                request.categoryName(),
                request.flowType()
        );
        Category saved = categoryRepository.save(category);
        return toResponse(saved);
    }

    @Transactional
    public CategoryResponse update(String categoryCd, CategoryRequest request) {
        Category parent = null;
        if (request.parentCategoryCd() != null) {
            parent = categoryRepository.findById(request.parentCategoryCd())
                    .orElseThrow(() -> new IllegalArgumentException("Parent category not found"));
        }

        Category category = new Category(
                categoryCd,
                parent,
                request.categoryName(),
                request.flowType()
        );
        Category updated = categoryRepository.save(category);
        return toResponse(updated);
    }

    private CategoryResponse toResponse(Category category) {
        return new CategoryResponse(
                category.categoryCd(),
                category.categoryName(),
                category.parentCategory() != null ? category.parentCategory().categoryCd() : null,
                category.flowType(),
                null // 単体レスポンスではchildrenは不要または空
        );
    }
}
