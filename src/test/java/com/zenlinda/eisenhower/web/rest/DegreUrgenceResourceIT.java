package com.zenlinda.eisenhower.web.rest;

import com.zenlinda.eisenhower.EisenhowerApp;
import com.zenlinda.eisenhower.domain.DegreUrgence;
import com.zenlinda.eisenhower.repository.DegreUrgenceRepository;
import com.zenlinda.eisenhower.service.DegreUrgenceService;

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
 * Integration tests for the {@link DegreUrgenceResource} REST controller.
 */
@SpringBootTest(classes = EisenhowerApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class DegreUrgenceResourceIT {

    private static final Integer DEFAULT_ORDRE = 1;
    private static final Integer UPDATED_ORDRE = 2;

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private DegreUrgenceRepository degreUrgenceRepository;

    @Autowired
    private DegreUrgenceService degreUrgenceService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDegreUrgenceMockMvc;

    private DegreUrgence degreUrgence;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DegreUrgence createEntity(EntityManager em) {
        DegreUrgence degreUrgence = new DegreUrgence()
            .ordre(DEFAULT_ORDRE)
            .libelle(DEFAULT_LIBELLE);
        return degreUrgence;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DegreUrgence createUpdatedEntity(EntityManager em) {
        DegreUrgence degreUrgence = new DegreUrgence()
            .ordre(UPDATED_ORDRE)
            .libelle(UPDATED_LIBELLE);
        return degreUrgence;
    }

    @BeforeEach
    public void initTest() {
        degreUrgence = createEntity(em);
    }

    @Test
    @Transactional
    public void createDegreUrgence() throws Exception {
        int databaseSizeBeforeCreate = degreUrgenceRepository.findAll().size();

        // Create the DegreUrgence
        restDegreUrgenceMockMvc.perform(post("/api/degre-urgences").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(degreUrgence)))
            .andExpect(status().isCreated());

        // Validate the DegreUrgence in the database
        List<DegreUrgence> degreUrgenceList = degreUrgenceRepository.findAll();
        assertThat(degreUrgenceList).hasSize(databaseSizeBeforeCreate + 1);
        DegreUrgence testDegreUrgence = degreUrgenceList.get(degreUrgenceList.size() - 1);
        assertThat(testDegreUrgence.getOrdre()).isEqualTo(DEFAULT_ORDRE);
        assertThat(testDegreUrgence.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    public void createDegreUrgenceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = degreUrgenceRepository.findAll().size();

        // Create the DegreUrgence with an existing ID
        degreUrgence.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDegreUrgenceMockMvc.perform(post("/api/degre-urgences").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(degreUrgence)))
            .andExpect(status().isBadRequest());

        // Validate the DegreUrgence in the database
        List<DegreUrgence> degreUrgenceList = degreUrgenceRepository.findAll();
        assertThat(degreUrgenceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkOrdreIsRequired() throws Exception {
        int databaseSizeBeforeTest = degreUrgenceRepository.findAll().size();
        // set the field null
        degreUrgence.setOrdre(null);

        // Create the DegreUrgence, which fails.

        restDegreUrgenceMockMvc.perform(post("/api/degre-urgences").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(degreUrgence)))
            .andExpect(status().isBadRequest());

        List<DegreUrgence> degreUrgenceList = degreUrgenceRepository.findAll();
        assertThat(degreUrgenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = degreUrgenceRepository.findAll().size();
        // set the field null
        degreUrgence.setLibelle(null);

        // Create the DegreUrgence, which fails.

        restDegreUrgenceMockMvc.perform(post("/api/degre-urgences").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(degreUrgence)))
            .andExpect(status().isBadRequest());

        List<DegreUrgence> degreUrgenceList = degreUrgenceRepository.findAll();
        assertThat(degreUrgenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDegreUrgences() throws Exception {
        // Initialize the database
        degreUrgenceRepository.saveAndFlush(degreUrgence);

        // Get all the degreUrgenceList
        restDegreUrgenceMockMvc.perform(get("/api/degre-urgences?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(degreUrgence.getId().intValue())))
            .andExpect(jsonPath("$.[*].ordre").value(hasItem(DEFAULT_ORDRE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));
    }
    
    @Test
    @Transactional
    public void getDegreUrgence() throws Exception {
        // Initialize the database
        degreUrgenceRepository.saveAndFlush(degreUrgence);

        // Get the degreUrgence
        restDegreUrgenceMockMvc.perform(get("/api/degre-urgences/{id}", degreUrgence.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(degreUrgence.getId().intValue()))
            .andExpect(jsonPath("$.ordre").value(DEFAULT_ORDRE))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE));
    }

    @Test
    @Transactional
    public void getNonExistingDegreUrgence() throws Exception {
        // Get the degreUrgence
        restDegreUrgenceMockMvc.perform(get("/api/degre-urgences/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDegreUrgence() throws Exception {
        // Initialize the database
        degreUrgenceService.save(degreUrgence);

        int databaseSizeBeforeUpdate = degreUrgenceRepository.findAll().size();

        // Update the degreUrgence
        DegreUrgence updatedDegreUrgence = degreUrgenceRepository.findById(degreUrgence.getId()).get();
        // Disconnect from session so that the updates on updatedDegreUrgence are not directly saved in db
        em.detach(updatedDegreUrgence);
        updatedDegreUrgence
            .ordre(UPDATED_ORDRE)
            .libelle(UPDATED_LIBELLE);

        restDegreUrgenceMockMvc.perform(put("/api/degre-urgences").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDegreUrgence)))
            .andExpect(status().isOk());

        // Validate the DegreUrgence in the database
        List<DegreUrgence> degreUrgenceList = degreUrgenceRepository.findAll();
        assertThat(degreUrgenceList).hasSize(databaseSizeBeforeUpdate);
        DegreUrgence testDegreUrgence = degreUrgenceList.get(degreUrgenceList.size() - 1);
        assertThat(testDegreUrgence.getOrdre()).isEqualTo(UPDATED_ORDRE);
        assertThat(testDegreUrgence.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void updateNonExistingDegreUrgence() throws Exception {
        int databaseSizeBeforeUpdate = degreUrgenceRepository.findAll().size();

        // Create the DegreUrgence

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDegreUrgenceMockMvc.perform(put("/api/degre-urgences").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(degreUrgence)))
            .andExpect(status().isBadRequest());

        // Validate the DegreUrgence in the database
        List<DegreUrgence> degreUrgenceList = degreUrgenceRepository.findAll();
        assertThat(degreUrgenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDegreUrgence() throws Exception {
        // Initialize the database
        degreUrgenceService.save(degreUrgence);

        int databaseSizeBeforeDelete = degreUrgenceRepository.findAll().size();

        // Delete the degreUrgence
        restDegreUrgenceMockMvc.perform(delete("/api/degre-urgences/{id}", degreUrgence.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DegreUrgence> degreUrgenceList = degreUrgenceRepository.findAll();
        assertThat(degreUrgenceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
