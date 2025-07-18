package tn.esprit.spring.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.spring.DAO.Entities.Etudiant;
import tn.esprit.spring.Services.Etudiant.IEtudiantService;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = tn.esprit.spring.RestControllers.EtudiantRestController.class)
public class EtudiantRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IEtudiantService etudiantService;

    @Autowired
    private ObjectMapper objectMapper;

    private Etudiant etudiant;

    @BeforeEach
    void setUp() {
        etudiant = new Etudiant();
        etudiant.setIdEtudiant(1L);
        etudiant.setNomEt("Doe");
        etudiant.setPrenomEt("John");
        etudiant.setCin(12345678L);
    }

    @Test
    void testAddOrUpdate() throws Exception {
        when(etudiantService.addOrUpdate(any(Etudiant.class))).thenReturn(etudiant);
        mockMvc.perform(post("/etudiant/addOrUpdate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(etudiant)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idEtudiant").value(1L))
                .andExpect(jsonPath("$.nomEt").value("Doe"));
    }

    @Test
    void testFindAll() throws Exception {
        when(etudiantService.findAll()).thenReturn(Collections.singletonList(etudiant));
        mockMvc.perform(get("/etudiant/findAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idEtudiant").value(1L));
    }

    @Test
    void testFindById() throws Exception {
        when(etudiantService.findById(1L)).thenReturn(etudiant);
        mockMvc.perform(get("/etudiant/findById?id=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idEtudiant").value(1L));
    }

    @Test
    void testDelete() throws Exception {
        doNothing().when(etudiantService).delete(any(Etudiant.class));
        mockMvc.perform(delete("/etudiant/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(etudiant)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteById() throws Exception {
        doNothing().when(etudiantService).deleteById(1L);
        mockMvc.perform(delete("/etudiant/deleteById?id=1"))
                .andExpect(status().isOk());
    }

    @Test
    void testSelectJPQL() throws Exception {
        when(etudiantService.selectJPQL("Doe")).thenReturn(Collections.singletonList(etudiant));
        mockMvc.perform(get("/etudiant/selectJPQL")
                .param("nom", "Doe"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idEtudiant").value(1L));
    }
} 