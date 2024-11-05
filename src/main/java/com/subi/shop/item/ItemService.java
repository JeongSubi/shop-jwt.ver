package com.subi.shop.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public void saveItem(String title, Integer price) {
        Item newItem = new Item();
        newItem.setTitle(title);
        newItem.setPrice(price);
        itemRepository.save(newItem);
    }

    public void editItem(String title, Integer price, Long id) {
        Item item = new Item();
        item.setId(id);
        item.setTitle(title);
        item.setPrice(price);
        itemRepository.save(item);
    }
}
