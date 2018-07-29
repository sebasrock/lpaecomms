package co.bassan.lpaecomms.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.bassan.lpaecomms.service.ClientsService;
import co.bassan.lpaecomms.web.rest.errors.BadRequestAlertException;
import co.bassan.lpaecomms.web.rest.util.HeaderUtil;
import co.bassan.lpaecomms.web.rest.util.PaginationUtil;
import co.bassan.lpaecomms.service.dto.ClientsDTO;
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
 * REST controller for managing Clients.
 */
@RestController
@RequestMapping("/api")
public class ClientsResource {

    private final Logger log = LoggerFactory.getLogger(ClientsResource.class);

    private static final String ENTITY_NAME = "clients";

    private final ClientsService clientsService;

    public ClientsResource(ClientsService clientsService) {
        this.clientsService = clientsService;
    }

    /**
     * POST  /clients : Create a new clients.
     *
     * @param clientsDTO the clientsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new clientsDTO, or with status 400 (Bad Request) if the clients has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/clients")
    @Timed
    public ResponseEntity<ClientsDTO> createClients(@Valid @RequestBody ClientsDTO clientsDTO) throws URISyntaxException {
        log.debug("REST request to save Clients : {}", clientsDTO);
        if (clientsDTO.getId() != null) {
            throw new BadRequestAlertException("A new clients cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClientsDTO result = clientsService.save(clientsDTO);
        return ResponseEntity.created(new URI("/api/clients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /clients : Updates an existing clients.
     *
     * @param clientsDTO the clientsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated clientsDTO,
     * or with status 400 (Bad Request) if the clientsDTO is not valid,
     * or with status 500 (Internal Server Error) if the clientsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/clients")
    @Timed
    public ResponseEntity<ClientsDTO> updateClients(@Valid @RequestBody ClientsDTO clientsDTO) throws URISyntaxException {
        log.debug("REST request to update Clients : {}", clientsDTO);
        if (clientsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ClientsDTO result = clientsService.save(clientsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, clientsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /clients : get all the clients.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of clients in body
     */
    @GetMapping("/clients")
    @Timed
    public ResponseEntity<List<ClientsDTO>> getAllClients(Pageable pageable) {
        log.debug("REST request to get a page of Clients");
        Page<ClientsDTO> page = clientsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/clients");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /clients/:id : get the "id" clients.
     *
     * @param id the id of the clientsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the clientsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/clients/{id}")
    @Timed
    public ResponseEntity<ClientsDTO> getClients(@PathVariable Long id) {
        log.debug("REST request to get Clients : {}", id);
        Optional<ClientsDTO> clientsDTO = clientsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(clientsDTO);
    }

    /**
     * DELETE  /clients/:id : delete the "id" clients.
     *
     * @param id the id of the clientsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/clients/{id}")
    @Timed
    public ResponseEntity<Void> deleteClients(@PathVariable Long id) {
        log.debug("REST request to delete Clients : {}", id);
        clientsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
