package co.bassan.lpaecomms.service.impl;

import co.bassan.lpaecomms.service.InvoiceItemsService;
import co.bassan.lpaecomms.domain.InvoiceItems;
import co.bassan.lpaecomms.repository.InvoiceItemsRepository;
import co.bassan.lpaecomms.service.dto.InvoiceItemsDTO;
import co.bassan.lpaecomms.service.mapper.InvoiceItemsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing InvoiceItems.
 */
@Service
@Transactional
public class InvoiceItemsServiceImpl implements InvoiceItemsService {

    private final Logger log = LoggerFactory.getLogger(InvoiceItemsServiceImpl.class);

    private final InvoiceItemsRepository invoiceItemsRepository;

    private final InvoiceItemsMapper invoiceItemsMapper;

    public InvoiceItemsServiceImpl(InvoiceItemsRepository invoiceItemsRepository, InvoiceItemsMapper invoiceItemsMapper) {
        this.invoiceItemsRepository = invoiceItemsRepository;
        this.invoiceItemsMapper = invoiceItemsMapper;
    }

    /**
     * Save a invoiceItems.
     *
     * @param invoiceItemsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public InvoiceItemsDTO save(InvoiceItemsDTO invoiceItemsDTO) {
        log.debug("Request to save InvoiceItems : {}", invoiceItemsDTO);
        InvoiceItems invoiceItems = invoiceItemsMapper.toEntity(invoiceItemsDTO);
        invoiceItems = invoiceItemsRepository.save(invoiceItems);
        return invoiceItemsMapper.toDto(invoiceItems);
    }

    /**
     * Get all the invoiceItems.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<InvoiceItemsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all InvoiceItems");
        return invoiceItemsRepository.findAll(pageable)
            .map(invoiceItemsMapper::toDto);
    }


    /**
     * Get one invoiceItems by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<InvoiceItemsDTO> findOne(Long id) {
        log.debug("Request to get InvoiceItems : {}", id);
        return invoiceItemsRepository.findById(id)
            .map(invoiceItemsMapper::toDto);
    }

    /**
     * Delete the invoiceItems by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete InvoiceItems : {}", id);
        invoiceItemsRepository.deleteById(id);
    }
}
