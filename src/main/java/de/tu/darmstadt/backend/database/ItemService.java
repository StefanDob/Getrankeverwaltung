package de.tu.darmstadt.backend.database;

import de.tu.darmstadt.dataModel.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;


    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }


    public void saveItem(final Item item) {
        itemRepository.save(item);
    }

    public Optional<Item> getItemByName(final String name) {
        return itemRepository.findItemByName(name);
    }

    public Optional<Item> getItemByID(final String ID) {
        return itemRepository.findItemByID(ID);
    }

}
