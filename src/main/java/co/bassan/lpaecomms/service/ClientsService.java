package co.bassan.lpaecomms.service;

import co.bassan.lpaecomms.service.dto.ClientsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Clients.
 */
public interface ClientsService {

    /**
     * Save a clients.
     *
     * @param clientsDTO the entity to save
     * @return the persisted entity
     */
    ClientsDTO save(ClientsDTO clientsDTO);

    /**
     * Get all the clients.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ClientsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" clients.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ClientsDTO> findOne(Long id);

    /**
     * Delete the "id" clients.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
