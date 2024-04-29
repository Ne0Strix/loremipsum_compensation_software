package at.loremipsum.books.entities;

import org.springframework.data.domain.OffsetScrollPosition;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Window;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends CrudRepository<BookEntity, String> {

    /**
     * Gets up to 10 un-enriched book-entities based on the given position.
     * @param position pagination details for the queryj
     * @return a Slice<BookEntity>
     */
    Slice<BookEntity> findFirst10BookEntityByIsEnrichedIsFalse(Pageable position);
}
