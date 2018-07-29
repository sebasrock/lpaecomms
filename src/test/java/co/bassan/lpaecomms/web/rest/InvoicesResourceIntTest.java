package co.bassan.lpaecomms.web.rest;

import co.bassan.lpaecomms.LpaecommsApp;

import co.bassan.lpaecomms.domain.Invoices;
import co.bassan.lpaecomms.repository.InvoicesRepository;
import co.bassan.lpaecomms.service.InvoicesService;
import co.bassan.lpaecomms.service.dto.InvoicesDTO;
import co.bassan.lpaecomms.service.mapper.InvoicesMapper;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static co.bassan.lpaecomms.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the InvoicesResource REST controller.
 *
 * @see InvoicesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LpaecommsApp.class)
public class InvoicesResourceIntTest {

    private static final String DEFAULT_INV_NO = "AAAAAAAAAA";
    private static final String UPDATED_INV_NO = "BBBBBBBBBB";

    private static final Instant DEFAULT_INV_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INV_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Float DEFAULT_INV_AMOUNT = 1F;
    private static final Float UPDATED_INV_AMOUNT = 2F;

    private static final Boolean DEFAULT_INV_STATUS = false;
    private static final Boolean UPDATED_INV_STATUS = true;

    @Autowired
    private InvoicesRepository invoicesRepository;


    @Autowired
    private InvoicesMapper invoicesMapper;
    

    @Autowired
    private InvoicesService invoicesService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInvoicesMockMvc;

    private Invoices invoices;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InvoicesResource invoicesResource = new InvoicesResource(invoicesService);
        this.restInvoicesMockMvc = MockMvcBuilders.standaloneSetup(invoicesResource)
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
    public static Invoices createEntity(EntityManager em) {
        Invoices invoices = new Invoices()
            .invNo(DEFAULT_INV_NO)
            .invDate(DEFAULT_INV_DATE)
            .invAmount(DEFAULT_INV_AMOUNT)
            .invStatus(DEFAULT_INV_STATUS);
        return invoices;
    }

    @Before
    public void initTest() {
        invoices = createEntity(em);
    }

    @Test
    @Transactional
    public void createInvoices() throws Exception {
        int databaseSizeBeforeCreate = invoicesRepository.findAll().size();

        // Create the Invoices
        InvoicesDTO invoicesDTO = invoicesMapper.toDto(invoices);
        restInvoicesMockMvc.perform(post("/api/invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoicesDTO)))
            .andExpect(status().isCreated());

        // Validate the Invoices in the database
        List<Invoices> invoicesList = invoicesRepository.findAll();
        assertThat(invoicesList).hasSize(databaseSizeBeforeCreate + 1);
        Invoices testInvoices = invoicesList.get(invoicesList.size() - 1);
        assertThat(testInvoices.getInvNo()).isEqualTo(DEFAULT_INV_NO);
        assertThat(testInvoices.getInvDate()).isEqualTo(DEFAULT_INV_DATE);
        assertThat(testInvoices.getInvAmount()).isEqualTo(DEFAULT_INV_AMOUNT);
        assertThat(testInvoices.isInvStatus()).isEqualTo(DEFAULT_INV_STATUS);
    }

    @Test
    @Transactional
    public void createInvoicesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = invoicesRepository.findAll().size();

        // Create the Invoices with an existing ID
        invoices.setId(1L);
        InvoicesDTO invoicesDTO = invoicesMapper.toDto(invoices);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvoicesMockMvc.perform(post("/api/invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoicesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Invoices in the database
        List<Invoices> invoicesList = invoicesRepository.findAll();
        assertThat(invoicesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkInvNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = invoicesRepository.findAll().size();
        // set the field null
        invoices.setInvNo(null);

        // Create the Invoices, which fails.
        InvoicesDTO invoicesDTO = invoicesMapper.toDto(invoices);

        restInvoicesMockMvc.perform(post("/api/invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoicesDTO)))
            .andExpect(status().isBadRequest());

        List<Invoices> invoicesList = invoicesRepository.findAll();
        assertThat(invoicesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInvDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = invoicesRepository.findAll().size();
        // set the field null
        invoices.setInvDate(null);

        // Create the Invoices, which fails.
        InvoicesDTO invoicesDTO = invoicesMapper.toDto(invoices);

        restInvoicesMockMvc.perform(post("/api/invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoicesDTO)))
            .andExpect(status().isBadRequest());

        List<Invoices> invoicesList = invoicesRepository.findAll();
        assertThat(invoicesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInvAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = invoicesRepository.findAll().size();
        // set the field null
        invoices.setInvAmount(null);

        // Create the Invoices, which fails.
        InvoicesDTO invoicesDTO = invoicesMapper.toDto(invoices);

        restInvoicesMockMvc.perform(post("/api/invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoicesDTO)))
            .andExpect(status().isBadRequest());

        List<Invoices> invoicesList = invoicesRepository.findAll();
        assertThat(invoicesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInvoices() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList
        restInvoicesMockMvc.perform(get("/api/invoices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoices.getId().intValue())))
            .andExpect(jsonPath("$.[*].invNo").value(hasItem(DEFAULT_INV_NO.toString())))
            .andExpect(jsonPath("$.[*].invDate").value(hasItem(DEFAULT_INV_DATE.toString())))
            .andExpect(jsonPath("$.[*].invAmount").value(hasItem(DEFAULT_INV_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].invStatus").value(hasItem(DEFAULT_INV_STATUS.booleanValue())));
    }
    

    @Test
    @Transactional
    public void getInvoices() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get the invoices
        restInvoicesMockMvc.perform(get("/api/invoices/{id}", invoices.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(invoices.getId().intValue()))
            .andExpect(jsonPath("$.invNo").value(DEFAULT_INV_NO.toString()))
            .andExpect(jsonPath("$.invDate").value(DEFAULT_INV_DATE.toString()))
            .andExpect(jsonPath("$.invAmount").value(DEFAULT_INV_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.invStatus").value(DEFAULT_INV_STATUS.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingInvoices() throws Exception {
        // Get the invoices
        restInvoicesMockMvc.perform(get("/api/invoices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvoices() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        int databaseSizeBeforeUpdate = invoicesRepository.findAll().size();

        // Update the invoices
        Invoices updatedInvoices = invoicesRepository.findById(invoices.getId()).get();
        // Disconnect from session so that the updates on updatedInvoices are not directly saved in db
        em.detach(updatedInvoices);
        updatedInvoices
            .invNo(UPDATED_INV_NO)
            .invDate(UPDATED_INV_DATE)
            .invAmount(UPDATED_INV_AMOUNT)
            .invStatus(UPDATED_INV_STATUS);
        InvoicesDTO invoicesDTO = invoicesMapper.toDto(updatedInvoices);

        restInvoicesMockMvc.perform(put("/api/invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoicesDTO)))
            .andExpect(status().isOk());

        // Validate the Invoices in the database
        List<Invoices> invoicesList = invoicesRepository.findAll();
        assertThat(invoicesList).hasSize(databaseSizeBeforeUpdate);
        Invoices testInvoices = invoicesList.get(invoicesList.size() - 1);
        assertThat(testInvoices.getInvNo()).isEqualTo(UPDATED_INV_NO);
        assertThat(testInvoices.getInvDate()).isEqualTo(UPDATED_INV_DATE);
        assertThat(testInvoices.getInvAmount()).isEqualTo(UPDATED_INV_AMOUNT);
        assertThat(testInvoices.isInvStatus()).isEqualTo(UPDATED_INV_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingInvoices() throws Exception {
        int databaseSizeBeforeUpdate = invoicesRepository.findAll().size();

        // Create the Invoices
        InvoicesDTO invoicesDTO = invoicesMapper.toDto(invoices);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInvoicesMockMvc.perform(put("/api/invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoicesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Invoices in the database
        List<Invoices> invoicesList = invoicesRepository.findAll();
        assertThat(invoicesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInvoices() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        int databaseSizeBeforeDelete = invoicesRepository.findAll().size();

        // Get the invoices
        restInvoicesMockMvc.perform(delete("/api/invoices/{id}", invoices.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Invoices> invoicesList = invoicesRepository.findAll();
        assertThat(invoicesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Invoices.class);
        Invoices invoices1 = new Invoices();
        invoices1.setId(1L);
        Invoices invoices2 = new Invoices();
        invoices2.setId(invoices1.getId());
        assertThat(invoices1).isEqualTo(invoices2);
        invoices2.setId(2L);
        assertThat(invoices1).isNotEqualTo(invoices2);
        invoices1.setId(null);
        assertThat(invoices1).isNotEqualTo(invoices2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InvoicesDTO.class);
        InvoicesDTO invoicesDTO1 = new InvoicesDTO();
        invoicesDTO1.setId(1L);
        InvoicesDTO invoicesDTO2 = new InvoicesDTO();
        assertThat(invoicesDTO1).isNotEqualTo(invoicesDTO2);
        invoicesDTO2.setId(invoicesDTO1.getId());
        assertThat(invoicesDTO1).isEqualTo(invoicesDTO2);
        invoicesDTO2.setId(2L);
        assertThat(invoicesDTO1).isNotEqualTo(invoicesDTO2);
        invoicesDTO1.setId(null);
        assertThat(invoicesDTO1).isNotEqualTo(invoicesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(invoicesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(invoicesMapper.fromId(null)).isNull();
    }
}
