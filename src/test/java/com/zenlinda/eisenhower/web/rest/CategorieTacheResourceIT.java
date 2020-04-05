package com.zenlinda.eisenhower.web.rest;

import com.zenlinda.eisenhower.EisenhowerApp;
import com.zenlinda.eisenhower.domain.CategorieTache;
import com.zenlinda.eisenhower.repository.CategorieTacheRepository;
import com.zenlinda.eisenhower.service.CategorieTacheService;

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

import com.zenlinda.eisenhower.domain.enumeration.Couleur;
/**
 * Integration tests for the {@link CategorieTacheResource} REST controller.
 */
@SpringBootTest(classes = EisenhowerApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CategorieTacheResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final Couleur DEFAULT_COULEUR = Couleur.ROUGE;
    private static final Couleur UPDATED_COULEUR = Couleur.BLEU;

    @Autowired
    private CategorieTacheRepository categorieTacheRepository;

    @Autowired
    private CategorieTacheService categorieTacheService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCategorieTacheMockMvc;

    private CategorieTache categorieTache;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategorieTache createEntity(EntityManager em) {
        CategorieTache categorieTache = new CategorieTache()
            .libelle(DEFAULT_LIBELLE)
            .couleur(DEFAULT_COULEUR);
        return categorieTache;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategorieTache createUpdatedEntity(EntityManager em) {
        CategorieTache categorieTache = new CategorieTache()
            .libelle(UPDATED_LIBELLE)
            .couleur(UPDATED_COULEUR);
        return categorieTache;
    }

    @BeforeEach
    public void initTest() {
        categorieTache = createEntity(em);
    }

    @Test
    @Transactional
    public void createCategorieTache() throws Exception {
        int databaseSizeBeforeCreate = categorieTacheRepository.findAll().size();

        // Create the CategorieTache
        restCategorieTacheMockMvc.perform(post("/api/categorie-taches").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categorieTache)))
            .andExpect(status().isCreated());

        // Validate the CategorieTache in the database
        List<CategorieTache> categorieTacheList = categorieTacheRepository.findAll();
        assertThat(categorieTacheList).hasSize(databaseSizeBeforeCreate + 1);
        CategorieTache testCategorieTache = categorieTacheList.get(categorieTacheList.size() - 1);
        assertThat(testCategorieTache.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testCategorieTache.getCouleur()).isEqualTo(DEFAULT_COULEUR);
    }

    @Test
    @Transactional
    public void createCategorieTacheWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = categorieTacheRepository.findAll().size();

        // Create the CategorieTache with an existing ID
        categorieTache.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategorieTacheMockMvc.perform(post("/api/categorie-taches").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categorieTache)))
            .andExpect(status().isBadRequest());

        // Validate the CategorieTache in the database
        List<CategorieTache> categorieTacheList = categorieTacheRepository.findAll();
        assertThat(categorieTacheList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = categorieTacheRepository.findAll().size();
        // set the field null
        categorieTache.setLibelle(null);

        // Create the CategorieTache, which fails.

        restCategorieTacheMockMvc.perform(post("/api/categorie-taches").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categorieTache)))
            .andExpect(status().isBadRequest());

        List<CategorieTache> categorieTacheList = categorieTacheRepository.findAll();
        assertThat(categorieTacheList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCouleurIsRequired() throws Exception {
        int databaseSizeBeforeTest = categorieTacheRepository.findAll().size();
        // set the field null
        categorieTache.setCouleur(null);

        // Create the CategorieTache, which fails.

        restCategorieTacheMockMvc.perform(post("/api/categorie-taches").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categorieTache)))
            .andExpect(status().isBadRequest());

        List<CategorieTache> categorieTacheList = categorieTacheRepository.findAll();
        assertThat(categorieTacheList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCategorieTaches() throws Exception {
        // Initialize the database
        categorieTacheRepository.saveAndFlush(categorieTache);

        // Get all the categorieTacheList
        restCategorieTacheMockMvc.perform(get("/api/categorie-taches?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categorieTache.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].couleur").value(hasItem(DEFAULT_COULEUR.toString())));
    }
    
    @Test
    @Transactional
    public void getCategorieTache() throws Exception {
        // Initialize the database
        categorieTacheRepository.saveAndFlush(categorieTache);

        // Get the categorieTache
        restCategorieTacheMockMvc.perform(get("/api/categorie-taches/{id}", categorieTache.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(categorieTache.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.couleur").value(DEFAULT_COULEUR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCategorieTache() throws Exception {
        // Get the categorieTache
        restCategorieTacheMockMvc.perform(get("/api/categorie-taches/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCategorieTache() throws Exception {
        // Initialize the database
        categorieTacheService.save(categorieTache);

        int databaseSizeBeforeUpdate = categorieTacheRepository.findAll().size();

        // Update the categorieTache
        CategorieTache updatedCategorieTache = categorieTacheRepository.findById(categorieTache.getId()).get();
        // Disconnect from session so that the updates on updatedCategorieTache are not directly saved in db
        em.detach(updatedCategorieTache);
        updatedCategorieTache
            .libelle(UPDATED_LIBELLE)
            .couleur(UPDATED_COULEUR);

        restCategorieTacheMockMvc.perform(put("/api/categorie-taches").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCategorieTache)))
            .andExpect(status().isOk());

        // Validate the CategorieTache in the database
        List<CategorieTache> categorieTacheList = categorieTacheRepository.findAll();
        assertThat(categorieTacheList).hasSize(databaseSizeBeforeUpdate);
        CategorieTache testCategorieTache = categorieTacheList.get(categorieTacheList.size() - 1);
        assertThat(testCategorieTache.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testCategorieTache.getCouleur()).isEqualTo(UPDATED_COULEUR);
    }

    @Test
    @Transactional
    public void updateNonExistingCategorieTache() throws Exception {
        int databaseSizeBeforeUpdate = categorieTacheRepository.findAll().size();

        // Create the CategorieTache

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategorieTacheMockMvc.perform(put("/api/categorie-taches").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categorieTache)))
            .andExpect(status().isBadRequest());

        // Validate the CategorieTache in the database
        List<CategorieTache> categorieTacheList = categorieTacheRepository.findAll();
        assertThat(categorieTacheList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCategorieTache() throws Exception {
        // Initialize the database
        categorieTacheService.save(categorieTache);

        int databaseSizeBeforeDelete = categorieTacheRepository.findAll().size();

        // Delete the categorieTache
        restCategorieTacheMockMvc.perform(delete("/api/categorie-taches/{id}", categorieTache.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CategorieTache> categorieTacheList = categorieTacheRepository.findAll();
        assertThat(categorieTacheList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
