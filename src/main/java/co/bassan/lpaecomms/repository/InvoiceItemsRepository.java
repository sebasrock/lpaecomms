package co.bassan.lpaecomms.repository;

import co.bassan.lpaecomms.domain.InvoiceItems;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the InvoiceItems entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InvoiceItemsRepository extends JpaRepository<InvoiceItems, Long> {

}
