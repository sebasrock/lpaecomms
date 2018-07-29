package co.bassan.lpaecomms.web.rest;

import co.bassan.lpaecomms.LpaecommsApp;

import co.bassan.lpaecomms.domain.InvoiceItems;
import co.bassan.lpaecomms.repository.InvoiceItemsRepository;
import co.bassan.lpaecomms.service.InvoiceItemsService;
import co.bassan.lpaecomms.service.dto.InvoiceItemsDTO;
import co.bassan.lpaecomms.service.mapper.InvoiceItemsMapper;
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
 * Test class for the InvoiceItemsResource REST controller.
 *
 * @see InvoiceItemsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LpaecommsApp.class)
public class InvoiceItemsResourceIntTest {

    private static final String DEFAULT_INVITEM_NO = "AAAAAAAAAA";
    private static final String UPDATED_INVITEM_NO = "BBBBBBBBBB";

    private static final String DEFAULT_INVITEM_QTY = "AAAAAAAAAA";
    private static final String UPDATED_INVITEM_QTY = "BBBBBBBBBB";

    @Autowired
    private InvoiceItemsRepository invoiceItemsRepository;


    @Autowired
    private InvoiceItemsMapper invoiceItemsMapper;
    

    @Autowired
    private InvoiceItemsService invoiceItemsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInvoiceItemsMockMvc;

    private InvoiceItems invoiceItems;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InvoiceItemsResource invoiceItemsResource = new InvoiceItemsResource(invoiceItemsService);
        this.restInvoiceItemsMockMvc = MockMvcBuilders.standaloneSetup(invoiceItemsResource)
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
    public static InvoiceItems createEntity(EntityManager em) {
        InvoiceItems invoiceItems = new InvoiceItems()
            .invitemNo(DEFAULT_INVITEM_NO)
            .invitemQty(DEFAULT_INVITEM_QTY);
        return invoiceItems;
    }

    @Before
    public void initTest() {
        invoiceItems = createEntity(em);
    }

    @Test
    @Transactional
    public void createInvoiceItems() throws Exception {
        int databaseSizeBeforeCreate = invoiceItemsRepository.findAll().size();

        // Create the InvoiceItems
        InvoiceItemsDTO invoiceItemsDTO = invoiceItemsMapper.toDto(invoiceItems);
        restInvoiceItemsMockMvc.perform(post("/api/invoice-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoiceItemsDTO)))
            .andExpect(status().isCreated());

        // Validate the InvoiceItems in the database
        List<InvoiceItems> invoiceItemsList = invoiceItemsRepository.findAll();
        assertThat(invoiceItemsList).hasSize(databaseSizeBeforeCreate + 1);
        InvoiceItems testInvoiceItems = invoiceItemsList.get(invoiceItemsList.size() - 1);
        assertThat(testInvoiceItems.getInvitemNo()).isEqualTo(DEFAULT_INVITEM_NO);
        assertThat(testInvoiceItems.getInvitemQty()).isEqualTo(DEFAULT_INVITEM_QTY);
    }

    @Test
    @Transactional
    public void createInvoiceItemsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = invoiceItemsRepository.findAll().size();

        // Create the InvoiceItems with an existing ID
        invoiceItems.setId(1L);
        InvoiceItemsDTO invoiceItemsDTO = invoiceItemsMapper.toDto(invoiceItems);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvoiceItemsMockMvc.perform(post("/api/invoice-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoiceItemsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InvoiceItems in the database
        List<InvoiceItems> invoiceItemsList = invoiceItemsRepository.findAll();
        assertThat(invoiceItemsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkInvitemNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = invoiceItemsRepository.findAll().size();
        // set the field null
        invoiceItems.setInvitemNo(null);

        // Create the InvoiceItems, which fails.
        InvoiceItemsDTO invoiceItemsDTO = invoiceItemsMapper.toDto(invoiceItems);

        restInvoiceItemsMockMvc.perform(post("/api/invoice-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoiceItemsDTO)))
            .andExpect(status().isBadRequest());

        List<InvoiceItems> invoiceItemsList = invoiceItemsRepository.findAll();
        assertThat(invoiceItemsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInvitemQtyIsRequired() throws Exception {
        int databaseSizeBeforeTest = invoiceItemsRepository.findAll().size();
        // set the field null
        invoiceItems.setInvitemQty(null);

        // Create the InvoiceItems, which fails.
        InvoiceItemsDTO invoiceItemsDTO = invoiceItemsMapper.toDto(invoiceItems);

        restInvoiceItemsMockMvc.perform(post("/api/invoice-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoiceItemsDTO)))
            .andExpect(status().isBadRequest());

        List<InvoiceItems> invoiceItemsList = invoiceItemsRepository.findAll();
        assertThat(invoiceItemsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInvoiceItems() throws Exception {
        // Initialize the database
        invoiceItemsRepository.saveAndFlush(invoiceItems);

        // Get all the invoiceItemsList
        restInvoiceItemsMockMvc.perform(get("/api/invoice-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoiceItems.getId().intValue())))
            .andExpect(jsonPath("$.[*].invitemNo").value(hasItem(DEFAULT_INVITEM_NO.toString())))
            .andExpect(jsonPath("$.[*].invitemQty").value(hasItem(DEFAULT_INVITEM_QTY.toString())));
    }
    

    @Test
    @Transactional
    public void getInvoiceItems() throws Exception {
        // Initialize the database
        invoiceItemsRepository.saveAndFlush(invoiceItems);

        // Get the invoiceItems
        restInvoiceItemsMockMvc.perform(get("/api/invoice-items/{id}", invoiceItems.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(invoiceItems.getId().intValue()))
            .andExpect(jsonPath("$.invitemNo").value(DEFAULT_INVITEM_NO.toString()))
            .andExpect(jsonPath("$.invitemQty").value(DEFAULT_INVITEM_QTY.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingInvoiceItems() throws Exception {
        // Get the invoiceItems
        restInvoiceItemsMockMvc.perform(get("/api/invoice-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvoiceItems() throws Exception {
        // Initialize the database
        invoiceItemsRepository.saveAndFlush(invoiceItems);

        int databaseSizeBeforeUpdate = invoiceItemsRepository.findAll().size();

        // Update the invoiceItems
        InvoiceItems updatedInvoiceItems = invoiceItemsRepository.findById(invoiceItems.getId()).get();
        // Disconnect from session so that the updates on updatedInvoiceItems are not directly saved in db
        em.detach(updatedInvoiceItems);
        updatedInvoiceItems
            .invitemNo(UPDATED_INVITEM_NO)
            .invitemQty(UPDATED_INVITEM_QTY);
        InvoiceItemsDTO invoiceItemsDTO = invoiceItemsMapper.toDto(updatedInvoiceItems);

        restInvoiceItemsMockMvc.perform(put("/api/invoice-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoiceItemsDTO)))
            .andExpect(status().isOk());

        // Validate the InvoiceItems in the database
        List<InvoiceItems> invoiceItemsList = invoiceItemsRepository.findAll();
        assertThat(invoiceItemsList).hasSize(databaseSizeBeforeUpdate);
        InvoiceItems testInvoiceItems = invoiceItemsList.get(invoiceItemsList.size() - 1);
        assertThat(testInvoiceItems.getInvitemNo()).isEqualTo(UPDATED_INVITEM_NO);
        assertThat(testInvoiceItems.getInvitemQty()).isEqualTo(UPDATED_INVITEM_QTY);
    }

    @Test
    @Transactional
    public void updateNonExistingInvoiceItems() throws Exception {
        int databaseSizeBeforeUpdate = invoiceItemsRepository.findAll().size();

        // Create the InvoiceItems
        InvoiceItemsDTO invoiceItemsDTO = invoiceItemsMapper.toDto(invoiceItems);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInvoiceItemsMockMvc.perform(put("/api/invoice-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoiceItemsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InvoiceItems in the database
        List<InvoiceItems> invoiceItemsList = invoiceItemsRepository.findAll();
        assertThat(invoiceItemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInvoiceItems() throws Exception {
        // Initialize the database
        invoiceItemsRepository.saveAndFlush(invoiceItems);

        int databaseSizeBeforeDelete = invoiceItemsRepository.findAll().size();

        // Get the invoiceItems
        restInvoiceItemsMockMvc.perform(delete("/api/invoice-items/{id}", invoiceItems.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<InvoiceItems> invoiceItemsList = invoiceItemsRepository.findAll();
        assertThat(invoiceItemsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InvoiceItems.class);
        InvoiceItems invoiceItems1 = new InvoiceItems();
        invoiceItems1.setId(1L);
        InvoiceItems invoiceItems2 = new InvoiceItems();
        invoiceItems2.setId(invoiceItems1.getId());
        assertThat(invoiceItems1).isEqualTo(invoiceItems2);
        invoiceItems2.setId(2L);
        assertThat(invoiceItems1).isNotEqualTo(invoiceItems2);
        invoiceItems1.setId(null);
        assertThat(invoiceItems1).isNotEqualTo(invoiceItems2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InvoiceItemsDTO.class);
        InvoiceItemsDTO invoiceItemsDTO1 = new InvoiceItemsDTO();
        invoiceItemsDTO1.setId(1L);
        InvoiceItemsDTO invoiceItemsDTO2 = new InvoiceItemsDTO();
        assertThat(invoiceItemsDTO1).isNotEqualTo(invoiceItemsDTO2);
        invoiceItemsDTO2.setId(invoiceItemsDTO1.getId());
        assertThat(invoiceItemsDTO1).isEqualTo(invoiceItemsDTO2);
        invoiceItemsDTO2.setId(2L);
        assertThat(invoiceItemsDTO1).isNotEqualTo(invoiceItemsDTO2);
        invoiceItemsDTO1.setId(null);
        assertThat(invoiceItemsDTO1).isNotEqualTo(invoiceItemsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(invoiceItemsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(invoiceItemsMapper.fromId(null)).isNull();
    }
}
