package ru.nsu.kolodina.taskmanager;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category getCategoryByCategoryName(String categoryName);
}
