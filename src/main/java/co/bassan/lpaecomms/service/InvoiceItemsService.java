package co.bassan.lpaecomms.service;

import co.bassan.lpaecomms.service.dto.InvoiceItemsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing InvoiceItems.
 */
public interface InvoiceItemsService {

    /**
     * Save a invoiceItems.
     *
     * @param invoiceItemsDTO the entity to save
     * @return the persisted entity
     */
    InvoiceItemsDTO save(InvoiceItemsDTO invoiceItemsDTO);

    /**
     * Get all the invoiceItems.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<InvoiceItemsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" invoiceItems.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<InvoiceItemsDTO> findOne(Long id);

    /**
     * Delete the "id" invoiceItems.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
