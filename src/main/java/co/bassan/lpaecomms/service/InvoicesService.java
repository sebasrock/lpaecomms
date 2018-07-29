package co.bassan.lpaecomms.service;

import co.bassan.lpaecomms.service.dto.InvoicesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Invoices.
 */
public interface InvoicesService {

    /**
     * Save a invoices.
     *
     * @param invoicesDTO the entity to save
     * @return the persisted entity
     */
    InvoicesDTO save(InvoicesDTO invoicesDTO);

    /**
     * Get all the invoices.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<InvoicesDTO> findAll(Pageable pageable);


    /**
     * Get the "id" invoices.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<InvoicesDTO> findOne(Long id);

    /**
     * Delete the "id" invoices.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
