package tn.esprit.spring.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.spring.DAO.Entities.Foyer;
import tn.esprit.spring.DAO.Entities.Universite;
import tn.esprit.spring.Services.Foyer.IFoyerService;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = tn.esprit.spring.RestControllers.FoyerRestController.class)
public class FoyerRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IFoyerService foyerService;

    @Autowired
    private ObjectMapper objectMapper;

    private Foyer foyer;
    private Universite universite;

    @BeforeEach
    void setUp() {
        foyer = new Foyer();
        foyer.setIdFoyer(1L);
        foyer.setNomFoyer("FoyerA");
        universite = new Universite();
        universite.setIdUniversite(2L);
        universite.setNomUniversite("UniA");
    }

    @Test
    void testAddOrUpdate() throws Exception {
        when(foyerService.addOrUpdate(any(Foyer.class))).thenReturn(foyer);
        mockMvc.perform(post("/foyer/addOrUpdate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(foyer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idFoyer").value(1L))
                .andExpect(jsonPath("$.nomFoyer").value("FoyerA"));
    }

    @Test
    void testFindAll() throws Exception {
        when(foyerService.findAll()).thenReturn(Collections.singletonList(foyer));
        mockMvc.perform(get("/foyer/findAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idFoyer").value(1L));
    }

    @Test
    void testFindById() throws Exception {
        when(foyerService.findById(1L)).thenReturn(foyer);
        mockMvc.perform(get("/foyer/findById?id=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idFoyer").value(1L));
    }

    @Test
    void testDelete() throws Exception {
        doNothing().when(foyerService).delete(any(Foyer.class));
        mockMvc.perform(delete("/foyer/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(foyer)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteById() throws Exception {
        doNothing().when(foyerService).deleteById(1L);
        mockMvc.perform(delete("/foyer/deleteById?id=1"))
                .andExpect(status().isOk());
    }

    @Test
    void testAffecterFoyerAUniversiteByRequestParam() throws Exception {
        when(foyerService.affecterFoyerAUniversite(1L, "UniA")).thenReturn(universite);
        mockMvc.perform(put("/foyer/affecterFoyerAUniversite")
                .param("idFoyer", "1")
                .param("nomUniversite", "UniA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUniversite").value(2L));
    }

    @Test
    void testDesaffecterFoyerAUniversite() throws Exception {
        when(foyerService.desaffecterFoyerAUniversite(2L)).thenReturn(universite);
        mockMvc.perform(put("/foyer/desaffecterFoyerAUniversite")
                .param("idUniversite", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUniversite").value(2L));
    }

    @Test
    void testAjouterFoyerEtAffecterAUniversite() throws Exception {
        when(foyerService.ajouterFoyerEtAffecterAUniversite(any(Foyer.class), eq(2L))).thenReturn(foyer);
        mockMvc.perform(post("/foyer/ajouterFoyerEtAffecterAUniversite")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(foyer))
                .param("idUniversite", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idFoyer").value(1L));
    }

    @Test
    void testAffecterFoyerAUniversiteByPathVariable() throws Exception {
        when(foyerService.affecterFoyerAUniversite(1L, 2L)).thenReturn(universite);
        mockMvc.perform(put("/foyer/affecterFoyerAUniversite/1/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUniversite").value(2L));
    }
} 