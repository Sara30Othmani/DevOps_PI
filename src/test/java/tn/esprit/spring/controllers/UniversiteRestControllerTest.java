package tn.esprit.spring.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.spring.DAO.Entities.Foyer;
import tn.esprit.spring.DAO.Entities.Universite;
import tn.esprit.spring.Services.Universite.IUniversiteService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = tn.esprit.spring.RestControllers.UniversiteRestController.class)
class UniversiteRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUniversiteService universiteService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /universite/addOrUpdate should add or update a universite")
    void addOrUpdate() throws Exception {
        Universite universite = Universite.builder().idUniversite(1L).nomUniversite("Test University").build();
        Mockito.when(universiteService.addOrUpdate(any(Universite.class))).thenReturn(universite);

        mockMvc.perform(post("/universite/addOrUpdate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(universite)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUniversite").value(1L))
                .andExpect(jsonPath("$.nomUniversite").value("Test University"));
    }

    @Test
    @DisplayName("GET /universite/findAll should return all universites")
    void findAll() throws Exception {
        List<Universite> universites = Arrays.asList(
                Universite.builder().idUniversite(1L).nomUniversite("U1").build(),
                Universite.builder().idUniversite(2L).nomUniversite("U2").build()
        );
        Mockito.when(universiteService.findAll()).thenReturn(universites);

        mockMvc.perform(get("/universite/findAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idUniversite").value(1L))
                .andExpect(jsonPath("$[1].idUniversite").value(2L));
    }

    @Test
    @DisplayName("GET /universite/findById should return universite by id")
    void findById() throws Exception {
        Universite universite = Universite.builder().idUniversite(1L).nomUniversite("U1").build();
        Mockito.when(universiteService.findById(1L)).thenReturn(universite);

        mockMvc.perform(get("/universite/findById").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUniversite").value(1L))
                .andExpect(jsonPath("$.nomUniversite").value("U1"));
    }

    @Test
    @DisplayName("DELETE /universite/delete should delete universite by body")
    void deleteByBody() throws Exception {
        Universite universite = Universite.builder().idUniversite(1L).build();
        Mockito.doNothing().when(universiteService).delete(any(Universite.class));
        mockMvc.perform(delete("/universite/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(universite)))
                .andExpect(status().isOk());
        Mockito.verify(universiteService).delete(Mockito.argThat(arg -> arg != null && arg.getIdUniversite() == 1L));
    }

    @Test
    @DisplayName("DELETE /universite/deleteById should delete universite by id")
    void deleteById() throws Exception {
        Mockito.doNothing().when(universiteService).deleteById(1L);
        mockMvc.perform(delete("/universite/deleteById").param("id", "1"))
                .andExpect(status().isOk());
        Mockito.verify(universiteService).deleteById(1L);
    }

    @Test
    @DisplayName("POST /universite/ajouterUniversiteEtSonFoyer should add universite and its foyer")
    void ajouterUniversiteEtSonFoyer() throws Exception {
        Foyer foyer = Foyer.builder().idFoyer(1L).nomFoyer("Foyer1").build();
        Universite universite = Universite.builder().idUniversite(1L).nomUniversite("U1").foyer(foyer).build();
        Mockito.when(universiteService.ajouterUniversiteEtSonFoyer(any(Universite.class))).thenReturn(universite);

        mockMvc.perform(post("/universite/ajouterUniversiteEtSonFoyer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(universite)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUniversite").value(1L))
                .andExpect(jsonPath("$.foyer.idFoyer").value(1L))
                .andExpect(jsonPath("$.foyer.nomFoyer").value("Foyer1"));
    }
} 