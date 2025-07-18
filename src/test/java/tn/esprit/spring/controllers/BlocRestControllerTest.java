package tn.esprit.spring.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.spring.DAO.Entities.Bloc;
import tn.esprit.spring.Services.Bloc.IBlocService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(controllers = tn.esprit.spring.RestControllers.BlocRestController.class)
public class BlocRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IBlocService blocService;

    @Autowired
    private ObjectMapper objectMapper;

    private Bloc bloc;

    @BeforeEach
    void setUp() {
        bloc = new Bloc();
        bloc.setIdBloc(1L);
        bloc.setNomBloc("BlocA");
    }

    @Test
    void testAddOrUpdate() throws Exception {
        when(blocService.addOrUpdate(any(Bloc.class))).thenReturn(bloc);
        mockMvc.perform(post("/bloc/addOrUpdate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bloc)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idBloc").value(1L))
                .andExpect(jsonPath("$.nomBloc").value("BlocA"));
    }

    @Test
    void testFindAll() throws Exception {
        when(blocService.findAll()).thenReturn(Collections.singletonList(bloc));
        mockMvc.perform(get("/bloc/findAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idBloc").value(1L));
    }

    @Test
    void testFindById() throws Exception {
        when(blocService.findById(1L)).thenReturn(bloc);
        mockMvc.perform(get("/bloc/findById?id=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idBloc").value(1L));
    }

    @Test
    void testDelete() throws Exception {
        doNothing().when(blocService).delete(any(Bloc.class));
        mockMvc.perform(delete("/bloc/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bloc)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteById() throws Exception {
        doNothing().when(blocService).deleteById(1L);
        mockMvc.perform(delete("/bloc/deleteById?id=1"))
                .andExpect(status().isOk());
    }

    @Test
    void testAffecterChambresABloc() throws Exception {
        List<Long> nums = Arrays.asList(1L, 2L);
        when(blocService.affecterChambresABloc(anyList(), anyString())).thenReturn(bloc);
        mockMvc.perform(put("/bloc/affecterChambresABloc")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nums))
                .param("nomBloc", "BlocA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idBloc").value(1L));
    }

    @Test
    void testAffecterBlocAFoyer() throws Exception {
        when(blocService.affecterBlocAFoyer(anyString(), anyString())).thenReturn(bloc);
        mockMvc.perform(put("/bloc/affecterBlocAFoyer")
                .param("nomBloc", "BlocA")
                .param("nomFoyer", "FoyerA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idBloc").value(1L));
    }

    @Test
    void testAffecterBlocAFoyer2() throws Exception {
        when(blocService.affecterBlocAFoyer(anyString(), anyString())).thenReturn(bloc);
        mockMvc.perform(put("/bloc/affecterBlocAFoyer2/FoyerA/BlocA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idBloc").value(1L));
    }

    @Test
    void testAjouterBlocEtSesChambres() throws Exception {
        when(blocService.ajouterBlocEtSesChambres(any(Bloc.class))).thenReturn(bloc);
        mockMvc.perform(post("/bloc/ajouterBlocEtSesChambres")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bloc)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idBloc").value(1L));
    }

    @Test
    void testAjouterBlocEtAffecterAFoyer() throws Exception {
        when(blocService.ajouterBlocEtAffecterAFoyer(any(Bloc.class), anyString())).thenReturn(bloc);
        mockMvc.perform(post("/bloc/ajouterBlocEtAffecterAFoyer/FoyerA")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bloc)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idBloc").value(1L));
    }
} 