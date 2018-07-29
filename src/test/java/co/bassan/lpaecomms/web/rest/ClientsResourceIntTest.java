package co.bassan.lpaecomms.web.rest;

import co.bassan.lpaecomms.LpaecommsApp;

import co.bassan.lpaecomms.domain.Clients;
import co.bassan.lpaecomms.repository.ClientsRepository;
import co.bassan.lpaecomms.service.ClientsService;
import co.bassan.lpaecomms.service.dto.ClientsDTO;
import co.bassan.lpaecomms.service.mapper.ClientsMapper;
import co.bassan.lpaecomms.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static co.bassan.lpaecomms.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ClientsResource REST controller.
 *
 * @see ClientsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LpaecommsApp.class)
public class ClientsResourceIntTest {

    private static final String DEFAULT_CLIENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_PHONE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CLIENT_STATUS = false;
    private static final Boolean UPDATED_CLIENT_STATUS = true;

    @Autowired
    private ClientsRepository clientsRepository;


    @Autowired
    private ClientsMapper clientsMapper;
    

    @Autowired
    private ClientsService clientsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClientsMockMvc;

    private Clients clients;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClientsResource clientsResource = new ClientsResource(clientsService);
        this.restClientsMockMvc = MockMvcBuilders.standaloneSetup(clientsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Clients createEntity(EntityManager em) {
        Clients clients = new Clients()
            .clientID(DEFAULT_CLIENT_ID)
            .clientFirstName(DEFAULT_CLIENT_FIRST_NAME)
            .clientLastName(DEFAULT_CLIENT_LAST_NAME)
            .clientAddress(DEFAULT_CLIENT_ADDRESS)
            .clientPhone(DEFAULT_CLIENT_PHONE)
            .clientStatus(DEFAULT_CLIENT_STATUS);
        return clients;
    }

    @Before
    public void initTest() {
        clients = createEntity(em);
    }

    @Test
    @Transactional
    public void createClients() throws Exception {
        int databaseSizeBeforeCreate = clientsRepository.findAll().size();

        // Create the Clients
        ClientsDTO clientsDTO = clientsMapper.toDto(clients);
        restClientsMockMvc.perform(post("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientsDTO)))
            .andExpect(status().isCreated());

        // Validate the Clients in the database
        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeCreate + 1);
        Clients testClients = clientsList.get(clientsList.size() - 1);
        assertThat(testClients.getClientID()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testClients.getClientFirstName()).isEqualTo(DEFAULT_CLIENT_FIRST_NAME);
        assertThat(testClients.getClientLastName()).isEqualTo(DEFAULT_CLIENT_LAST_NAME);
        assertThat(testClients.getClientAddress()).isEqualTo(DEFAULT_CLIENT_ADDRESS);
        assertThat(testClients.getClientPhone()).isEqualTo(DEFAULT_CLIENT_PHONE);
        assertThat(testClients.isClientStatus()).isEqualTo(DEFAULT_CLIENT_STATUS);
    }

    @Test
    @Transactional
    public void createClientsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clientsRepository.findAll().size();

        // Create the Clients with an existing ID
        clients.setId(1L);
        ClientsDTO clientsDTO = clientsMapper.toDto(clients);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientsMockMvc.perform(post("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Clients in the database
        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkClientIDIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientsRepository.findAll().size();
        // set the field null
        clients.setClientID(null);

        // Create the Clients, which fails.
        ClientsDTO clientsDTO = clientsMapper.toDto(clients);

        restClientsMockMvc.perform(post("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientsDTO)))
            .andExpect(status().isBadRequest());

        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClientFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientsRepository.findAll().size();
        // set the field null
        clients.setClientFirstName(null);

        // Create the Clients, which fails.
        ClientsDTO clientsDTO = clientsMapper.toDto(clients);

        restClientsMockMvc.perform(post("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientsDTO)))
            .andExpect(status().isBadRequest());

        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClientLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientsRepository.findAll().size();
        // set the field null
        clients.setClientLastName(null);

        // Create the Clients, which fails.
        ClientsDTO clientsDTO = clientsMapper.toDto(clients);

        restClientsMockMvc.perform(post("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientsDTO)))
            .andExpect(status().isBadRequest());

        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClientAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientsRepository.findAll().size();
        // set the field null
        clients.setClientAddress(null);

        // Create the Clients, which fails.
        ClientsDTO clientsDTO = clientsMapper.toDto(clients);

        restClientsMockMvc.perform(post("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientsDTO)))
            .andExpect(status().isBadRequest());

        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClientPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientsRepository.findAll().size();
        // set the field null
        clients.setClientPhone(null);

        // Create the Clients, which fails.
        ClientsDTO clientsDTO = clientsMapper.toDto(clients);

        restClientsMockMvc.perform(post("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientsDTO)))
            .andExpect(status().isBadRequest());

        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClients() throws Exception {
        // Initialize the database
        clientsRepository.saveAndFlush(clients);

        // Get all the clientsList
        restClientsMockMvc.perform(get("/api/clients?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clients.getId().intValue())))
            .andExpect(jsonPath("$.[*].clientID").value(hasItem(DEFAULT_CLIENT_ID.toString())))
            .andExpect(jsonPath("$.[*].clientFirstName").value(hasItem(DEFAULT_CLIENT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].clientLastName").value(hasItem(DEFAULT_CLIENT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].clientAddress").value(hasItem(DEFAULT_CLIENT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].clientPhone").value(hasItem(DEFAULT_CLIENT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].clientStatus").value(hasItem(DEFAULT_CLIENT_STATUS.booleanValue())));
    }
    

    @Test
    @Transactional
    public void getClients() throws Exception {
        // Initialize the database
        clientsRepository.saveAndFlush(clients);

        // Get the clients
        restClientsMockMvc.perform(get("/api/clients/{id}", clients.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clients.getId().intValue()))
            .andExpect(jsonPath("$.clientID").value(DEFAULT_CLIENT_ID.toString()))
            .andExpect(jsonPath("$.clientFirstName").value(DEFAULT_CLIENT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.clientLastName").value(DEFAULT_CLIENT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.clientAddress").value(DEFAULT_CLIENT_ADDRESS.toString()))
            .andExpect(jsonPath("$.clientPhone").value(DEFAULT_CLIENT_PHONE.toString()))
            .andExpect(jsonPath("$.clientStatus").value(DEFAULT_CLIENT_STATUS.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingClients() throws Exception {
        // Get the clients
        restClientsMockMvc.perform(get("/api/clients/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClients() throws Exception {
        // Initialize the database
        clientsRepository.saveAndFlush(clients);

        int databaseSizeBeforeUpdate = clientsRepository.findAll().size();

        // Update the clients
        Clients updatedClients = clientsRepository.findById(clients.getId()).get();
        // Disconnect from session so that the updates on updatedClients are not directly saved in db
        em.detach(updatedClients);
        updatedClients
            .clientID(UPDATED_CLIENT_ID)
            .clientFirstName(UPDATED_CLIENT_FIRST_NAME)
            .clientLastName(UPDATED_CLIENT_LAST_NAME)
            .clientAddress(UPDATED_CLIENT_ADDRESS)
            .clientPhone(UPDATED_CLIENT_PHONE)
            .clientStatus(UPDATED_CLIENT_STATUS);
        ClientsDTO clientsDTO = clientsMapper.toDto(updatedClients);

        restClientsMockMvc.perform(put("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientsDTO)))
            .andExpect(status().isOk());

        // Validate the Clients in the database
        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeUpdate);
        Clients testClients = clientsList.get(clientsList.size() - 1);
        assertThat(testClients.getClientID()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testClients.getClientFirstName()).isEqualTo(UPDATED_CLIENT_FIRST_NAME);
        assertThat(testClients.getClientLastName()).isEqualTo(UPDATED_CLIENT_LAST_NAME);
        assertThat(testClients.getClientAddress()).isEqualTo(UPDATED_CLIENT_ADDRESS);
        assertThat(testClients.getClientPhone()).isEqualTo(UPDATED_CLIENT_PHONE);
        assertThat(testClients.isClientStatus()).isEqualTo(UPDATED_CLIENT_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingClients() throws Exception {
        int databaseSizeBeforeUpdate = clientsRepository.findAll().size();

        // Create the Clients
        ClientsDTO clientsDTO = clientsMapper.toDto(clients);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restClientsMockMvc.perform(put("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Clients in the database
        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClients() throws Exception {
        // Initialize the database
        clientsRepository.saveAndFlush(clients);

        int databaseSizeBeforeDelete = clientsRepository.findAll().size();

        // Get the clients
        restClientsMockMvc.perform(delete("/api/clients/{id}", clients.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Clients.class);
        Clients clients1 = new Clients();
        clients1.setId(1L);
        Clients clients2 = new Clients();
        clients2.setId(clients1.getId());
        assertThat(clients1).isEqualTo(clients2);
        clients2.setId(2L);
        assertThat(clients1).isNotEqualTo(clients2);
        clients1.setId(null);
        assertThat(clients1).isNotEqualTo(clients2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientsDTO.class);
        ClientsDTO clientsDTO1 = new ClientsDTO();
        clientsDTO1.setId(1L);
        ClientsDTO clientsDTO2 = new ClientsDTO();
        assertThat(clientsDTO1).isNotEqualTo(clientsDTO2);
        clientsDTO2.setId(clientsDTO1.getId());
        assertThat(clientsDTO1).isEqualTo(clientsDTO2);
        clientsDTO2.setId(2L);
        assertThat(clientsDTO1).isNotEqualTo(clientsDTO2);
        clientsDTO1.setId(null);
        assertThat(clientsDTO1).isNotEqualTo(clientsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(clientsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(clientsMapper.fromId(null)).isNull();
    }
}
