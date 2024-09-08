package de.tu.darmstadt.backend.database.Item;

import de.tu.darmstadt.dataModel.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    public static void main(String[] args) {


    }

    /**
     * An {@link ItemRepository} is used as a communication interface between an {@link Item} and
     * the data source.
     */
    private final ItemRepository itemRepository;


    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    // :::::::::::::::::::::::::::::::::::::: METHODS :::::::::::::::::::::::::::::::::::::::

    public void saveItem(final Item item) {
        itemRepository.save(item);
    }

    public Optional<Item> deleteItem(final String id) {
        return null;
    }

    public Optional<Item> getItemByName(final String name) {
        return itemRepository.findItemByName(name);
    }

    public Optional<Item> getItemById(final Long id) {
        return itemRepository.findItemById(id);
    }

    /**
     * Returns all {@link Item}s from the data source.
     * @return all {@link Item}s from the data source.
     */
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public void deleteItem(Item item) {
        // Ensure the item exists in the database before deleting
        Optional<Item> existingItem = itemRepository.findById(item.getId());
        if (existingItem.isPresent()) {
            itemRepository.delete(item);
        } else {
            // Handle case where item doesn't exist, e.g., throw an exception
            throw new RuntimeException("Item not found for deletion");
        }
    }

}
