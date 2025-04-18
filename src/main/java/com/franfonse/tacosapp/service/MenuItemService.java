package com.franfonse.tacosapp.service;

import com.franfonse.tacosapp.model.MenuItem;
import com.franfonse.tacosapp.respository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;

    @Autowired
    public MenuItemService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }

    public List<List<MenuItem>> getSortedMenuItems() {
        List<MenuItem> menuItems = getAllMenuItems();
        List<List<MenuItem>> sortedMenuItems = new ArrayList<>();
        List<Integer> priorityCategory = new ArrayList<>(Arrays.asList(8, 4, 3, 6, 5, 9, 2, 7, 1));

        for (int i = 0; i < priorityCategory.size(); i++) {
            sortedMenuItems.add(new ArrayList<>());
        }

        for (int i = 0; i < priorityCategory.size(); i++) {
            for (MenuItem menuItem : menuItems) {
                if (priorityCategory.get(i) == menuItem.getCategory().getId().intValue()) {
                    sortedMenuItems.get(i).add(menuItem);
                }
            }
        }
        return sortedMenuItems;
    }

}
