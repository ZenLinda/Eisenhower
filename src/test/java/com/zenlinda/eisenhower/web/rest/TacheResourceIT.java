package com.zenlinda.eisenhower.web.rest;

import com.zenlinda.eisenhower.EisenhowerApp;
import com.zenlinda.eisenhower.domain.Tache;
import com.zenlinda.eisenhower.repository.TacheRepository;
import com.zenlinda.eisenhower.service.TacheService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TacheResource} REST controller.
 */
@SpringBootTest(classes = EisenhowerApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class TacheResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRITPION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRITPION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_IDEALE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_IDEALE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_ALERTE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ALERTE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_DELAI = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DELAI = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private TacheRepository tacheRepository;

    @Autowired
    private TacheService tacheService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTacheMockMvc;

    private Tache tache;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tache createEntity(EntityManager em) {
        Tache tache = new Tache()
            .libelle(DEFAULT_LIBELLE)
            .descritpion(DEFAULT_DESCRITPION)
            .dateIdeale(DEFAULT_DATE_IDEALE)
            .dateAlerte(DEFAULT_DATE_ALERTE)
            .dateDelai(DEFAULT_DATE_DELAI);
        return tache;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tache createUpdatedEntity(EntityManager em) {
        Tache tache = new Tache()
            .libelle(UPDATED_LIBELLE)
            .descritpion(UPDATED_DESCRITPION)
            .dateIdeale(UPDATED_DATE_IDEALE)
            .dateAlerte(UPDATED_DATE_ALERTE)
            .dateDelai(UPDATED_DATE_DELAI);
        return tache;
    }

    @BeforeEach
    public void initTest() {
        tache = createEntity(em);
    }

    @Test
    @Transactional
    public void createTache() throws Exception {
        int databaseSizeBeforeCreate = tacheRepository.findAll().size();

        // Create the Tache
        restTacheMockMvc.perform(post("/api/taches").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tache)))
            .andExpect(status().isCreated());

        // Validate the Tache in the database
        List<Tache> tacheList = tacheRepository.findAll();
        assertThat(tacheList).hasSize(databaseSizeBeforeCreate + 1);
        Tache testTache = tacheList.get(tacheList.size() - 1);
        assertThat(testTache.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testTache.getDescritpion()).isEqualTo(DEFAULT_DESCRITPION);
        assertThat(testTache.getDateIdeale()).isEqualTo(DEFAULT_DATE_IDEALE);
        assertThat(testTache.getDateAlerte()).isEqualTo(DEFAULT_DATE_ALERTE);
        assertThat(testTache.getDateDelai()).isEqualTo(DEFAULT_DATE_DELAI);
    }

    @Test
    @Transactional
    public void createTacheWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tacheRepository.findAll().size();

        // Create the Tache with an existing ID
        tache.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTacheMockMvc.perform(post("/api/taches").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tache)))
            .andExpect(status().isBadRequest());

        // Validate the Tache in the database
        List<Tache> tacheList = tacheRepository.findAll();
        assertThat(tacheList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = tacheRepository.findAll().size();
        // set the field null
        tache.setLibelle(null);

        // Create the Tache, which fails.

        restTacheMockMvc.perform(post("/api/taches").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tache)))
            .andExpect(status().isBadRequest());

        List<Tache> tacheList = tacheRepository.findAll();
        assertThat(tacheList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTaches() throws Exception {
        // Initialize the database
        tacheRepository.saveAndFlush(tache);

        // Get all the tacheList
        restTacheMockMvc.perform(get("/api/taches?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tache.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].descritpion").value(hasItem(DEFAULT_DESCRITPION)))
            .andExpect(jsonPath("$.[*].dateIdeale").value(hasItem(DEFAULT_DATE_IDEALE.toString())))
            .andExpect(jsonPath("$.[*].dateAlerte").value(hasItem(DEFAULT_DATE_ALERTE.toString())))
            .andExpect(jsonPath("$.[*].dateDelai").value(hasItem(DEFAULT_DATE_DELAI.toString())));
    }
    
    @Test
    @Transactional
    public void getTache() throws Exception {
        // Initialize the database
        tacheRepository.saveAndFlush(tache);

        // Get the tache
        restTacheMockMvc.perform(get("/api/taches/{id}", tache.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tache.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.descritpion").value(DEFAULT_DESCRITPION))
            .andExpect(jsonPath("$.dateIdeale").value(DEFAULT_DATE_IDEALE.toString()))
            .andExpect(jsonPath("$.dateAlerte").value(DEFAULT_DATE_ALERTE.toString()))
            .andExpect(jsonPath("$.dateDelai").value(DEFAULT_DATE_DELAI.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTache() throws Exception {
        // Get the tache
        restTacheMockMvc.perform(get("/api/taches/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTache() throws Exception {
        // Initialize the database
        tacheService.save(tache);

        int databaseSizeBeforeUpdate = tacheRepository.findAll().size();

        // Update the tache
        Tache updatedTache = tacheRepository.findById(tache.getId()).get();
        // Disconnect from session so that the updates on updatedTache are not directly saved in db
        em.detach(updatedTache);
        updatedTache
            .libelle(UPDATED_LIBELLE)
            .descritpion(UPDATED_DESCRITPION)
            .dateIdeale(UPDATED_DATE_IDEALE)
            .dateAlerte(UPDATED_DATE_ALERTE)
            .dateDelai(UPDATED_DATE_DELAI);

        restTacheMockMvc.perform(put("/api/taches").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTache)))
            .andExpect(status().isOk());

        // Validate the Tache in the database
        List<Tache> tacheList = tacheRepository.findAll();
        assertThat(tacheList).hasSize(databaseSizeBeforeUpdate);
        Tache testTache = tacheList.get(tacheList.size() - 1);
        assertThat(testTache.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testTache.getDescritpion()).isEqualTo(UPDATED_DESCRITPION);
        assertThat(testTache.getDateIdeale()).isEqualTo(UPDATED_DATE_IDEALE);
        assertThat(testTache.getDateAlerte()).isEqualTo(UPDATED_DATE_ALERTE);
        assertThat(testTache.getDateDelai()).isEqualTo(UPDATED_DATE_DELAI);
    }

    @Test
    @Transactional
    public void updateNonExistingTache() throws Exception {
        int databaseSizeBeforeUpdate = tacheRepository.findAll().size();

        // Create the Tache

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTacheMockMvc.perform(put("/api/taches").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tache)))
            .andExpect(status().isBadRequest());

        // Validate the Tache in the database
        List<Tache> tacheList = tacheRepository.findAll();
        assertThat(tacheList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTache() throws Exception {
        // Initialize the database
        tacheService.save(tache);

        int databaseSizeBeforeDelete = tacheRepository.findAll().size();

        // Delete the tache
        restTacheMockMvc.perform(delete("/api/taches/{id}", tache.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tache> tacheList = tacheRepository.findAll();
        assertThat(tacheList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
