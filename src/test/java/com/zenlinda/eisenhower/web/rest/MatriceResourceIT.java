package com.zenlinda.eisenhower.web.rest;

import com.zenlinda.eisenhower.EisenhowerApp;
import com.zenlinda.eisenhower.domain.Matrice;
import com.zenlinda.eisenhower.repository.MatriceRepository;
import com.zenlinda.eisenhower.service.MatriceService;

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
 * Integration tests for the {@link MatriceResource} REST controller.
 */
@SpringBootTest(classes = EisenhowerApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MatriceResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private MatriceRepository matriceRepository;

    @Autowired
    private MatriceService matriceService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMatriceMockMvc;

    private Matrice matrice;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Matrice createEntity(EntityManager em) {
        Matrice matrice = new Matrice()
            .libelle(DEFAULT_LIBELLE);
        return matrice;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Matrice createUpdatedEntity(EntityManager em) {
        Matrice matrice = new Matrice()
            .libelle(UPDATED_LIBELLE);
        return matrice;
    }

    @BeforeEach
    public void initTest() {
        matrice = createEntity(em);
    }

    @Test
    @Transactional
    public void createMatrice() throws Exception {
        int databaseSizeBeforeCreate = matriceRepository.findAll().size();

        // Create the Matrice
        restMatriceMockMvc.perform(post("/api/matrices").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(matrice)))
            .andExpect(status().isCreated());

        // Validate the Matrice in the database
        List<Matrice> matriceList = matriceRepository.findAll();
        assertThat(matriceList).hasSize(databaseSizeBeforeCreate + 1);
        Matrice testMatrice = matriceList.get(matriceList.size() - 1);
        assertThat(testMatrice.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    public void createMatriceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = matriceRepository.findAll().size();

        // Create the Matrice with an existing ID
        matrice.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMatriceMockMvc.perform(post("/api/matrices").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(matrice)))
            .andExpect(status().isBadRequest());

        // Validate the Matrice in the database
        List<Matrice> matriceList = matriceRepository.findAll();
        assertThat(matriceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = matriceRepository.findAll().size();
        // set the field null
        matrice.setLibelle(null);

        // Create the Matrice, which fails.

        restMatriceMockMvc.perform(post("/api/matrices").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(matrice)))
            .andExpect(status().isBadRequest());

        List<Matrice> matriceList = matriceRepository.findAll();
        assertThat(matriceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMatrices() throws Exception {
        // Initialize the database
        matriceRepository.saveAndFlush(matrice);

        // Get all the matriceList
        restMatriceMockMvc.perform(get("/api/matrices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matrice.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));
    }
    
    @Test
    @Transactional
    public void getMatrice() throws Exception {
        // Initialize the database
        matriceRepository.saveAndFlush(matrice);

        // Get the matrice
        restMatriceMockMvc.perform(get("/api/matrices/{id}", matrice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(matrice.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE));
    }

    @Test
    @Transactional
    public void getNonExistingMatrice() throws Exception {
        // Get the matrice
        restMatriceMockMvc.perform(get("/api/matrices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMatrice() throws Exception {
        // Initialize the database
        matriceService.save(matrice);

        int databaseSizeBeforeUpdate = matriceRepository.findAll().size();

        // Update the matrice
        Matrice updatedMatrice = matriceRepository.findById(matrice.getId()).get();
        // Disconnect from session so that the updates on updatedMatrice are not directly saved in db
        em.detach(updatedMatrice);
        updatedMatrice
            .libelle(UPDATED_LIBELLE);

        restMatriceMockMvc.perform(put("/api/matrices").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMatrice)))
            .andExpect(status().isOk());

        // Validate the Matrice in the database
        List<Matrice> matriceList = matriceRepository.findAll();
        assertThat(matriceList).hasSize(databaseSizeBeforeUpdate);
        Matrice testMatrice = matriceList.get(matriceList.size() - 1);
        assertThat(testMatrice.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void updateNonExistingMatrice() throws Exception {
        int databaseSizeBeforeUpdate = matriceRepository.findAll().size();

        // Create the Matrice

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatriceMockMvc.perform(put("/api/matrices").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(matrice)))
            .andExpect(status().isBadRequest());

        // Validate the Matrice in the database
        List<Matrice> matriceList = matriceRepository.findAll();
        assertThat(matriceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMatrice() throws Exception {
        // Initialize the database
        matriceService.save(matrice);

        int databaseSizeBeforeDelete = matriceRepository.findAll().size();

        // Delete the matrice
        restMatriceMockMvc.perform(delete("/api/matrices/{id}", matrice.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Matrice> matriceList = matriceRepository.findAll();
        assertThat(matriceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
