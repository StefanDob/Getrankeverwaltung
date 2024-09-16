package de.tu.darmstadt.backend.backendOperations;

import de.tu.darmstadt.backend.database.Item.ItemService;
import de.tu.darmstadt.backend.database.SpringContext;
import de.tu.darmstadt.backend.exceptions.items.InvalidItemIDFormatException;
import de.tu.darmstadt.dataModel.Item;
import de.tu.darmstadt.frontend.store.ItemView;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemOperations {

    /**
     * Retrieves all items available in the shop from the database.
     *
     * @return a {@link List} of {@link Item} objects representing all shop items.
     */
    public static List<Item> getAllShopItems() {
        ItemService itemService = SpringContext.getBean(ItemService.class);
        return itemService.getAllItems();
    }

    /**
     * Retrieves an {@link Item} by its specified ID.
     * If the item with the given ID is not found, {@code null} is returned.
     *
     * @param id the ID of the {@link Item} to retrieve.
     * @return the {@link Item} associated with the given ID, or {@code null} if not found.
     * @throws InvalidItemIDFormatException if the ID format is invalid.
     */
    public static @Nullable Item getItemById(Long id) throws InvalidItemIDFormatException {
        ItemService itemService = SpringContext.getBean(ItemService.class);
        return itemService.getItemById(id).orElse(null);
    }

    /**
     * Saves the given {@link Item} to the database.
     *
     * @param item the {@link Item} to save.
     */
    public static void saveItem(Item item) {
        ItemService itemService = SpringContext.getBean(ItemService.class);
        itemService.saveItem(item);
    }

    /**
     * Deletes the given {@link Item} from the database.
     *
     * @param item the {@link Item} to delete.
     */
    public static void deleteItem(Item item) {
        ItemService itemService = SpringContext.getBean(ItemService.class);
        itemService.deleteItem(item);
    }
}

