package co.bassan.lpaecomms.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.bassan.lpaecomms.service.InvoiceItemsService;
import co.bassan.lpaecomms.web.rest.errors.BadRequestAlertException;
import co.bassan.lpaecomms.web.rest.util.HeaderUtil;
import co.bassan.lpaecomms.web.rest.util.PaginationUtil;
import co.bassan.lpaecomms.service.dto.InvoiceItemsDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing InvoiceItems.
 */
@RestController
@RequestMapping("/api")
public class InvoiceItemsResource {

    private final Logger log = LoggerFactory.getLogger(InvoiceItemsResource.class);

    private static final String ENTITY_NAME = "invoiceItems";

    private final InvoiceItemsService invoiceItemsService;

    public InvoiceItemsResource(InvoiceItemsService invoiceItemsService) {
        this.invoiceItemsService = invoiceItemsService;
    }

    /**
     * POST  /invoice-items : Create a new invoiceItems.
     *
     * @param invoiceItemsDTO the invoiceItemsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new invoiceItemsDTO, or with status 400 (Bad Request) if the invoiceItems has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/invoice-items")
    @Timed
    public ResponseEntity<InvoiceItemsDTO> createInvoiceItems(@Valid @RequestBody InvoiceItemsDTO invoiceItemsDTO) throws URISyntaxException {
        log.debug("REST request to save InvoiceItems : {}", invoiceItemsDTO);
        if (invoiceItemsDTO.getId() != null) {
            throw new BadRequestAlertException("A new invoiceItems cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InvoiceItemsDTO result = invoiceItemsService.save(invoiceItemsDTO);
        return ResponseEntity.created(new URI("/api/invoice-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /invoice-items : Updates an existing invoiceItems.
     *
     * @param invoiceItemsDTO the invoiceItemsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated invoiceItemsDTO,
     * or with status 400 (Bad Request) if the invoiceItemsDTO is not valid,
     * or with status 500 (Internal Server Error) if the invoiceItemsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/invoice-items")
    @Timed
    public ResponseEntity<InvoiceItemsDTO> updateInvoiceItems(@Valid @RequestBody InvoiceItemsDTO invoiceItemsDTO) throws URISyntaxException {
        log.debug("REST request to update InvoiceItems : {}", invoiceItemsDTO);
        if (invoiceItemsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InvoiceItemsDTO result = invoiceItemsService.save(invoiceItemsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, invoiceItemsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /invoice-items : get all the invoiceItems.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of invoiceItems in body
     */
    @GetMapping("/invoice-items")
    @Timed
    public ResponseEntity<List<InvoiceItemsDTO>> getAllInvoiceItems(Pageable pageable) {
        log.debug("REST request to get a page of InvoiceItems");
        Page<InvoiceItemsDTO> page = invoiceItemsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/invoice-items");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /invoice-items/:id : get the "id" invoiceItems.
     *
     * @param id the id of the invoiceItemsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the invoiceItemsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/invoice-items/{id}")
    @Timed
    public ResponseEntity<InvoiceItemsDTO> getInvoiceItems(@PathVariable Long id) {
        log.debug("REST request to get InvoiceItems : {}", id);
        Optional<InvoiceItemsDTO> invoiceItemsDTO = invoiceItemsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(invoiceItemsDTO);
    }

    /**
     * DELETE  /invoice-items/:id : delete the "id" invoiceItems.
     *
     * @param id the id of the invoiceItemsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/invoice-items/{id}")
    @Timed
    public ResponseEntity<Void> deleteInvoiceItems(@PathVariable Long id) {
        log.debug("REST request to delete InvoiceItems : {}", id);
        invoiceItemsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
