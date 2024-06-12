package de.tu.darmstadt.backend.database;

import de.tu.darmstadt.backend.exceptions.items.InvalidItemIDFormatException;
import de.tu.darmstadt.backend.exceptions.items.ItemPropertiesException;
import de.tu.darmstadt.dataModel.Item;
import de.tu.darmstadt.dataModel.ItemImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    public static void main(String[] args) {
        try {

            ItemImage itemImage = new ItemImage("No path");

            Item item = new Item(1.25, "White Chocolate", itemImage, "Best item ever");

            ItemService service = SpringContext.getBean(ItemService.class);
            service.saveItem(item);

            System.out.println(item);
        } catch (ItemPropertiesException e) {
            throw new RuntimeException(e);
        }
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

    public Optional<Item> getItemByID(final String ID) {

        return itemRepository.findItemByID(ID);
    }

    /**
     * Returns all {@link Item}s from the data source.
     * @return all {@link Item}s from the data source.
     */
    public List<Item> getAllItems() {
        // Returns a list of items
        return new ArrayList<>( itemRepository.findAll() );
    }

}
