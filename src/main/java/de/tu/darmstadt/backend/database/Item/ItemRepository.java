package de.tu.darmstadt.backend.database.Item;

import de.tu.darmstadt.dataModel.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The interface {@code ItemRepository} is used to access to {@link Item} objects on the data source.
 */
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    /**
     * This method returns an {@link Optional<Item>} of {@link Item} by a specified {@link Item#getName()}.
     * @param itemName the specified {@link Item#getName()}
     * @return {@link Optional<Item>} of {@link Item}
     */
    Optional<Item> findItemByName(String itemName);

    /**
     * This method returns an {@link Optional<Item>} of {@link Item} by a specified {@link Item#get_ITEM_ID()}.
     * @param id the specified {@link Item#getId()}
     * @return {@link Optional<Item>} of {@link Item}
     */
    Optional<Item> findItemById(final Long id);

}
