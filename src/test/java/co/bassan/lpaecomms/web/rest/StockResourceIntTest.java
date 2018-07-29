package co.bassan.lpaecomms.web.rest;

import co.bassan.lpaecomms.LpaecommsApp;

import co.bassan.lpaecomms.domain.Stock;
import co.bassan.lpaecomms.repository.StockRepository;
import co.bassan.lpaecomms.service.StockService;
import co.bassan.lpaecomms.service.dto.StockDTO;
import co.bassan.lpaecomms.service.mapper.StockMapper;
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
 * Test class for the StockResource REST controller.
 *
 * @see StockResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LpaecommsApp.class)
public class StockResourceIntTest {

    private static final String DEFAULT_STOCK_ID = "AAAAAAAAAA";
    private static final String UPDATED_STOCK_ID = "BBBBBBBBBB";

    private static final String DEFAULT_STOCK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_STOCK_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_STOCK_DESC = "AAAAAAAAAA";
    private static final String UPDATED_STOCK_DESC = "BBBBBBBBBB";

    private static final String DEFAULT_STOCK_ON_HAND = "AAAAAAAAAA";
    private static final String UPDATED_STOCK_ON_HAND = "BBBBBBBBBB";

    private static final Float DEFAULT_STOCK_PRICE = 1F;
    private static final Float UPDATED_STOCK_PRICE = 2F;

    private static final Boolean DEFAULT_STOCK_STATUS = false;
    private static final Boolean UPDATED_STOCK_STATUS = true;

    @Autowired
    private StockRepository stockRepository;


    @Autowired
    private StockMapper stockMapper;
    

    @Autowired
    private StockService stockService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restStockMockMvc;

    private Stock stock;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StockResource stockResource = new StockResource(stockService);
        this.restStockMockMvc = MockMvcBuilders.standaloneSetup(stockResource)
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
    public static Stock createEntity(EntityManager em) {
        Stock stock = new Stock()
            .stockID(DEFAULT_STOCK_ID)
            .stockName(DEFAULT_STOCK_NAME)
            .stockDesc(DEFAULT_STOCK_DESC)
            .stockOnHand(DEFAULT_STOCK_ON_HAND)
            .stockPrice(DEFAULT_STOCK_PRICE)
            .stockStatus(DEFAULT_STOCK_STATUS);
        return stock;
    }

    @Before
    public void initTest() {
        stock = createEntity(em);
    }

    @Test
    @Transactional
    public void createStock() throws Exception {
        int databaseSizeBeforeCreate = stockRepository.findAll().size();

        // Create the Stock
        StockDTO stockDTO = stockMapper.toDto(stock);
        restStockMockMvc.perform(post("/api/stocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockDTO)))
            .andExpect(status().isCreated());

        // Validate the Stock in the database
        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(databaseSizeBeforeCreate + 1);
        Stock testStock = stockList.get(stockList.size() - 1);
        assertThat(testStock.getStockID()).isEqualTo(DEFAULT_STOCK_ID);
        assertThat(testStock.getStockName()).isEqualTo(DEFAULT_STOCK_NAME);
        assertThat(testStock.getStockDesc()).isEqualTo(DEFAULT_STOCK_DESC);
        assertThat(testStock.getStockOnHand()).isEqualTo(DEFAULT_STOCK_ON_HAND);
        assertThat(testStock.getStockPrice()).isEqualTo(DEFAULT_STOCK_PRICE);
        assertThat(testStock.isStockStatus()).isEqualTo(DEFAULT_STOCK_STATUS);
    }

    @Test
    @Transactional
    public void createStockWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stockRepository.findAll().size();

        // Create the Stock with an existing ID
        stock.setId(1L);
        StockDTO stockDTO = stockMapper.toDto(stock);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStockMockMvc.perform(post("/api/stocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Stock in the database
        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkStockIDIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockRepository.findAll().size();
        // set the field null
        stock.setStockID(null);

        // Create the Stock, which fails.
        StockDTO stockDTO = stockMapper.toDto(stock);

        restStockMockMvc.perform(post("/api/stocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockDTO)))
            .andExpect(status().isBadRequest());

        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStockNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockRepository.findAll().size();
        // set the field null
        stock.setStockName(null);

        // Create the Stock, which fails.
        StockDTO stockDTO = stockMapper.toDto(stock);

        restStockMockMvc.perform(post("/api/stocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockDTO)))
            .andExpect(status().isBadRequest());

        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStockDescIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockRepository.findAll().size();
        // set the field null
        stock.setStockDesc(null);

        // Create the Stock, which fails.
        StockDTO stockDTO = stockMapper.toDto(stock);

        restStockMockMvc.perform(post("/api/stocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockDTO)))
            .andExpect(status().isBadRequest());

        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStockOnHandIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockRepository.findAll().size();
        // set the field null
        stock.setStockOnHand(null);

        // Create the Stock, which fails.
        StockDTO stockDTO = stockMapper.toDto(stock);

        restStockMockMvc.perform(post("/api/stocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockDTO)))
            .andExpect(status().isBadRequest());

        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStockPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockRepository.findAll().size();
        // set the field null
        stock.setStockPrice(null);

        // Create the Stock, which fails.
        StockDTO stockDTO = stockMapper.toDto(stock);

        restStockMockMvc.perform(post("/api/stocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockDTO)))
            .andExpect(status().isBadRequest());

        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStockStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockRepository.findAll().size();
        // set the field null
        stock.setStockStatus(null);

        // Create the Stock, which fails.
        StockDTO stockDTO = stockMapper.toDto(stock);

        restStockMockMvc.perform(post("/api/stocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockDTO)))
            .andExpect(status().isBadRequest());

        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStocks() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList
        restStockMockMvc.perform(get("/api/stocks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stock.getId().intValue())))
            .andExpect(jsonPath("$.[*].stockID").value(hasItem(DEFAULT_STOCK_ID.toString())))
            .andExpect(jsonPath("$.[*].stockName").value(hasItem(DEFAULT_STOCK_NAME.toString())))
            .andExpect(jsonPath("$.[*].stockDesc").value(hasItem(DEFAULT_STOCK_DESC.toString())))
            .andExpect(jsonPath("$.[*].stockOnHand").value(hasItem(DEFAULT_STOCK_ON_HAND.toString())))
            .andExpect(jsonPath("$.[*].stockPrice").value(hasItem(DEFAULT_STOCK_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].stockStatus").value(hasItem(DEFAULT_STOCK_STATUS.booleanValue())));
    }
    

    @Test
    @Transactional
    public void getStock() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get the stock
        restStockMockMvc.perform(get("/api/stocks/{id}", stock.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(stock.getId().intValue()))
            .andExpect(jsonPath("$.stockID").value(DEFAULT_STOCK_ID.toString()))
            .andExpect(jsonPath("$.stockName").value(DEFAULT_STOCK_NAME.toString()))
            .andExpect(jsonPath("$.stockDesc").value(DEFAULT_STOCK_DESC.toString()))
            .andExpect(jsonPath("$.stockOnHand").value(DEFAULT_STOCK_ON_HAND.toString()))
            .andExpect(jsonPath("$.stockPrice").value(DEFAULT_STOCK_PRICE.doubleValue()))
            .andExpect(jsonPath("$.stockStatus").value(DEFAULT_STOCK_STATUS.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingStock() throws Exception {
        // Get the stock
        restStockMockMvc.perform(get("/api/stocks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStock() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        int databaseSizeBeforeUpdate = stockRepository.findAll().size();

        // Update the stock
        Stock updatedStock = stockRepository.findById(stock.getId()).get();
        // Disconnect from session so that the updates on updatedStock are not directly saved in db
        em.detach(updatedStock);
        updatedStock
            .stockID(UPDATED_STOCK_ID)
            .stockName(UPDATED_STOCK_NAME)
            .stockDesc(UPDATED_STOCK_DESC)
            .stockOnHand(UPDATED_STOCK_ON_HAND)
            .stockPrice(UPDATED_STOCK_PRICE)
            .stockStatus(UPDATED_STOCK_STATUS);
        StockDTO stockDTO = stockMapper.toDto(updatedStock);

        restStockMockMvc.perform(put("/api/stocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockDTO)))
            .andExpect(status().isOk());

        // Validate the Stock in the database
        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(databaseSizeBeforeUpdate);
        Stock testStock = stockList.get(stockList.size() - 1);
        assertThat(testStock.getStockID()).isEqualTo(UPDATED_STOCK_ID);
        assertThat(testStock.getStockName()).isEqualTo(UPDATED_STOCK_NAME);
        assertThat(testStock.getStockDesc()).isEqualTo(UPDATED_STOCK_DESC);
        assertThat(testStock.getStockOnHand()).isEqualTo(UPDATED_STOCK_ON_HAND);
        assertThat(testStock.getStockPrice()).isEqualTo(UPDATED_STOCK_PRICE);
        assertThat(testStock.isStockStatus()).isEqualTo(UPDATED_STOCK_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingStock() throws Exception {
        int databaseSizeBeforeUpdate = stockRepository.findAll().size();

        // Create the Stock
        StockDTO stockDTO = stockMapper.toDto(stock);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restStockMockMvc.perform(put("/api/stocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Stock in the database
        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStock() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        int databaseSizeBeforeDelete = stockRepository.findAll().size();

        // Get the stock
        restStockMockMvc.perform(delete("/api/stocks/{id}", stock.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Stock.class);
        Stock stock1 = new Stock();
        stock1.setId(1L);
        Stock stock2 = new Stock();
        stock2.setId(stock1.getId());
        assertThat(stock1).isEqualTo(stock2);
        stock2.setId(2L);
        assertThat(stock1).isNotEqualTo(stock2);
        stock1.setId(null);
        assertThat(stock1).isNotEqualTo(stock2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockDTO.class);
        StockDTO stockDTO1 = new StockDTO();
        stockDTO1.setId(1L);
        StockDTO stockDTO2 = new StockDTO();
        assertThat(stockDTO1).isNotEqualTo(stockDTO2);
        stockDTO2.setId(stockDTO1.getId());
        assertThat(stockDTO1).isEqualTo(stockDTO2);
        stockDTO2.setId(2L);
        assertThat(stockDTO1).isNotEqualTo(stockDTO2);
        stockDTO1.setId(null);
        assertThat(stockDTO1).isNotEqualTo(stockDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(stockMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(stockMapper.fromId(null)).isNull();
    }
}
