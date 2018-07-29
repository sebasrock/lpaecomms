package co.bassan.lpaecomms.service.impl;

import co.bassan.lpaecomms.service.ClientsService;
import co.bassan.lpaecomms.domain.Clients;
import co.bassan.lpaecomms.repository.ClientsRepository;
import co.bassan.lpaecomms.service.dto.ClientsDTO;
import co.bassan.lpaecomms.service.mapper.ClientsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Clients.
 */
@Service
@Transactional
public class ClientsServiceImpl implements ClientsService {

    private final Logger log = LoggerFactory.getLogger(ClientsServiceImpl.class);

    private final ClientsRepository clientsRepository;

    private final ClientsMapper clientsMapper;

    public ClientsServiceImpl(ClientsRepository clientsRepository, ClientsMapper clientsMapper) {
        this.clientsRepository = clientsRepository;
        this.clientsMapper = clientsMapper;
    }

    /**
     * Save a clients.
     *
     * @param clientsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ClientsDTO save(ClientsDTO clientsDTO) {
        log.debug("Request to save Clients : {}", clientsDTO);
        Clients clients = clientsMapper.toEntity(clientsDTO);
        clients = clientsRepository.save(clients);
        return clientsMapper.toDto(clients);
    }

    /**
     * Get all the clients.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ClientsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Clients");
        return clientsRepository.findAll(pageable)
            .map(clientsMapper::toDto);
    }


    /**
     * Get one clients by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ClientsDTO> findOne(Long id) {
        log.debug("Request to get Clients : {}", id);
        return clientsRepository.findById(id)
            .map(clientsMapper::toDto);
    }

    /**
     * Delete the clients by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Clients : {}", id);
        clientsRepository.deleteById(id);
    }
}
