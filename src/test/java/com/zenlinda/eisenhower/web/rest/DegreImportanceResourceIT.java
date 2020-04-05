package com.zenlinda.eisenhower.web.rest;

import com.zenlinda.eisenhower.EisenhowerApp;
import com.zenlinda.eisenhower.domain.DegreImportance;
import com.zenlinda.eisenhower.repository.DegreImportanceRepository;
import com.zenlinda.eisenhower.service.DegreImportanceService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DegreImportanceResource} REST controller.
 */
@SpringBootTest(classes = EisenhowerApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class DegreImportanceResourceIT {

    private static final Integer DEFAULT_ORDRE = 1;
    private static final Integer UPDATED_ORDRE = 2;

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private DegreImportanceRepository degreImportanceRepository;

    @Autowired
    private DegreImportanceService degreImportanceService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDegreImportanceMockMvc;

    private DegreImportance degreImportance;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DegreImportance createEntity(EntityManager em) {
        DegreImportance degreImportance = new DegreImportance()
            .ordre(DEFAULT_ORDRE)
            .libelle(DEFAULT_LIBELLE);
        return degreImportance;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DegreImportance createUpdatedEntity(EntityManager em) {
        DegreImportance degreImportance = new DegreImportance()
            .ordre(UPDATED_ORDRE)
            .libelle(UPDATED_LIBELLE);
        return degreImportance;
    }

    @BeforeEach
    public void initTest() {
        degreImportance = createEntity(em);
    }

    @Test
    @Transactional
    public void createDegreImportance() throws Exception {
        int databaseSizeBeforeCreate = degreImportanceRepository.findAll().size();

        // Create the DegreImportance
        restDegreImportanceMockMvc.perform(post("/api/degre-importances").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(degreImportance)))
            .andExpect(status().isCreated());

        // Validate the DegreImportance in the database
        List<DegreImportance> degreImportanceList = degreImportanceRepository.findAll();
        assertThat(degreImportanceList).hasSize(databaseSizeBeforeCreate + 1);
        DegreImportance testDegreImportance = degreImportanceList.get(degreImportanceList.size() - 1);
        assertThat(testDegreImportance.getOrdre()).isEqualTo(DEFAULT_ORDRE);
        assertThat(testDegreImportance.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    public void createDegreImportanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = degreImportanceRepository.findAll().size();

        // Create the DegreImportance with an existing ID
        degreImportance.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDegreImportanceMockMvc.perform(post("/api/degre-importances").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(degreImportance)))
            .andExpect(status().isBadRequest());

        // Validate the DegreImportance in the database
        List<DegreImportance> degreImportanceList = degreImportanceRepository.findAll();
        assertThat(degreImportanceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkOrdreIsRequired() throws Exception {
        int databaseSizeBeforeTest = degreImportanceRepository.findAll().size();
        // set the field null
        degreImportance.setOrdre(null);

        // Create the DegreImportance, which fails.

        restDegreImportanceMockMvc.perform(post("/api/degre-importances").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(degreImportance)))
            .andExpect(status().isBadRequest());

        List<DegreImportance> degreImportanceList = degreImportanceRepository.findAll();
        assertThat(degreImportanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = degreImportanceRepository.findAll().size();
        // set the field null
        degreImportance.setLibelle(null);

        // Create the DegreImportance, which fails.

        restDegreImportanceMockMvc.perform(post("/api/degre-importances").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(degreImportance)))
            .andExpect(status().isBadRequest());

        List<DegreImportance> degreImportanceList = degreImportanceRepository.findAll();
        assertThat(degreImportanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDegreImportances() throws Exception {
        // Initialize the database
        degreImportanceRepository.saveAndFlush(degreImportance);

        // Get all the degreImportanceList
        restDegreImportanceMockMvc.perform(get("/api/degre-importances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(degreImportance.getId().intValue())))
            .andExpect(jsonPath("$.[*].ordre").value(hasItem(DEFAULT_ORDRE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));
    }
    
    @Test
    @Transactional
    public void getDegreImportance() throws Exception {
        // Initialize the database
        degreImportanceRepository.saveAndFlush(degreImportance);

        // Get the degreImportance
        restDegreImportanceMockMvc.perform(get("/api/degre-importances/{id}", degreImportance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(degreImportance.getId().intValue()))
            .andExpect(jsonPath("$.ordre").value(DEFAULT_ORDRE))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE));
    }

    @Test
    @Transactional
    public void getNonExistingDegreImportance() throws Exception {
        // Get the degreImportance
        restDegreImportanceMockMvc.perform(get("/api/degre-importances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDegreImportance() throws Exception {
        // Initialize the database
        degreImportanceService.save(degreImportance);

        int databaseSizeBeforeUpdate = degreImportanceRepository.findAll().size();

        // Update the degreImportance
        DegreImportance updatedDegreImportance = degreImportanceRepository.findById(degreImportance.getId()).get();
        // Disconnect from session so that the updates on updatedDegreImportance are not directly saved in db
        em.detach(updatedDegreImportance);
        updatedDegreImportance
            .ordre(UPDATED_ORDRE)
            .libelle(UPDATED_LIBELLE);

        restDegreImportanceMockMvc.perform(put("/api/degre-importances").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDegreImportance)))
            .andExpect(status().isOk());

        // Validate the DegreImportance in the database
        List<DegreImportance> degreImportanceList = degreImportanceRepository.findAll();
        assertThat(degreImportanceList).hasSize(databaseSizeBeforeUpdate);
        DegreImportance testDegreImportance = degreImportanceList.get(degreImportanceList.size() - 1);
        assertThat(testDegreImportance.getOrdre()).isEqualTo(UPDATED_ORDRE);
        assertThat(testDegreImportance.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void updateNonExistingDegreImportance() throws Exception {
        int databaseSizeBeforeUpdate = degreImportanceRepository.findAll().size();

        // Create the DegreImportance

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDegreImportanceMockMvc.perform(put("/api/degre-importances").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(degreImportance)))
            .andExpect(status().isBadRequest());

        // Validate the DegreImportance in the database
        List<DegreImportance> degreImportanceList = degreImportanceRepository.findAll();
        assertThat(degreImportanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDegreImportance() throws Exception {
        // Initialize the database
        degreImportanceService.save(degreImportance);

        int databaseSizeBeforeDelete = degreImportanceRepository.findAll().size();

        // Delete the degreImportance
        restDegreImportanceMockMvc.perform(delete("/api/degre-importances/{id}", degreImportance.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DegreImportance> degreImportanceList = degreImportanceRepository.findAll();
        assertThat(degreImportanceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
