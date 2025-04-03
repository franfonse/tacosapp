package com.franfonse.tacosapp.respository;

import com.franfonse.tacosapp.model.Category;
import com.franfonse.tacosapp.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findByCategory(Category category);
}
