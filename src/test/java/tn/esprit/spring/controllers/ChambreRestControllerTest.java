package tn.esprit.spring.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.spring.DAO.Entities.Chambre;
import tn.esprit.spring.DAO.Entities.TypeChambre;
import tn.esprit.spring.Services.Chambre.IChambreService;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = tn.esprit.spring.RestControllers.ChambreRestController.class)
public class ChambreRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IChambreService chambreService;

    @Autowired
    private ObjectMapper objectMapper;

    private Chambre chambre;

    @BeforeEach
    void setUp() {
        chambre = new Chambre();
        chambre.setIdChambre(1L);
        chambre.setNumeroChambre(101L);
        chambre.setTypeC(TypeChambre.SIMPLE);
    }

    @Test
    void testAddOrUpdate() throws Exception {
        when(chambreService.addOrUpdate(any(Chambre.class))).thenReturn(chambre);
        mockMvc.perform(post("/chambre/addOrUpdate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(chambre)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idChambre").value(1L))
                .andExpect(jsonPath("$.numeroChambre").value(101L));
    }

    @Test
    void testFindAll() throws Exception {
        when(chambreService.findAll()).thenReturn(Collections.singletonList(chambre));
        mockMvc.perform(get("/chambre/findAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idChambre").value(1L));
    }

    @Test
    void testFindById() throws Exception {
        when(chambreService.findById(1L)).thenReturn(chambre);
        mockMvc.perform(get("/chambre/findById?id=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idChambre").value(1L));
    }

    @Test
    void testDelete() throws Exception {
        doNothing().when(chambreService).delete(any(Chambre.class));
        mockMvc.perform(delete("/chambre/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(chambre)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteById() throws Exception {
        doNothing().when(chambreService).deleteById(1L);
        mockMvc.perform(delete("/chambre/deleteById?id=1"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetChambresParNomBloc() throws Exception {
        when(chambreService.getChambresParNomBloc("BlocA")).thenReturn(Collections.singletonList(chambre));
        mockMvc.perform(get("/chambre/getChambresParNomBloc")
                .param("nomBloc", "BlocA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idChambre").value(1L));
    }

    @Test
    void testNbChambreParTypeEtBloc() throws Exception {
        when(chambreService.nbChambreParTypeEtBloc(TypeChambre.SIMPLE, 1L)).thenReturn(5L);
        mockMvc.perform(get("/chambre/nbChambreParTypeEtBloc")
                .param("type", "SIMPLE")
                .param("idBloc", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }

    @Test
    void testGetChambresNonReserveParNomFoyerEtTypeChambre() throws Exception {
        when(chambreService.getChambresNonReserveParNomFoyerEtTypeChambre("FoyerA", TypeChambre.SIMPLE)).thenReturn(Collections.singletonList(chambre));
        mockMvc.perform(get("/chambre/getChambresNonReserveParNomFoyerEtTypeChambre")
                .param("nomFoyer", "FoyerA")
                .param("type", "SIMPLE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idChambre").value(1L));
    }
} 