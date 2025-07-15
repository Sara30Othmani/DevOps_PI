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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tn.esprit.spring.DAO.Entities.Reservation;
import tn.esprit.spring.RestControllers.ReservationRestController;
import tn.esprit.spring.Services.Reservation.IReservationService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = ReservationRestController.class)
class ReservationRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IReservationService reservationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /reservation/addOrUpdate should add or update a reservation")
    void addOrUpdate() throws Exception {
        Reservation reservation = Reservation.builder()
                .idReservation("1")
                .estValide(true)
                .build();

        Mockito.when(reservationService.addOrUpdate(any(Reservation.class)))
                .thenReturn(reservation);

        mockMvc.perform(post("/reservation/addOrUpdate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservation)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idReservation").value("1"))
                .andExpect(jsonPath("$.estValide").value(true));
    }

    @Test
    @DisplayName("GET /reservation/findAll should return all reservations")
    void findAll() throws Exception {
        List<Reservation> reservations = Arrays.asList(
                Reservation.builder().idReservation("1").build(),
                Reservation.builder().idReservation("2").build()
        );

        Mockito.when(reservationService.findAll()).thenReturn(reservations);

        mockMvc.perform(get("/reservation/findAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idReservation").value("1"))
                .andExpect(jsonPath("$[1].idReservation").value("2"));
    }

    @Test
    @DisplayName("GET /reservation/findById should return reservation by ID")
    void findById() throws Exception {
        Reservation reservation = Reservation.builder().idReservation("1").build();

        Mockito.when(reservationService.findById("1")).thenReturn(reservation);

        mockMvc.perform(get("/reservation/findById")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idReservation").value("1"));
    }

    @Test
    @DisplayName("DELETE /reservation/deleteById/{id} should delete reservation by ID")
    void deleteReservationById() throws Exception {
        Mockito.doNothing().when(reservationService).deleteById("1");

        mockMvc.perform(MockMvcRequestBuilders.delete("/reservation/deleteById/1"))
                .andExpect(status().isOk());

        verify(reservationService).deleteById("1");
    }

    @Test
    @DisplayName("DELETE /reservation/delete should delete reservation by body")
    void deleteReservationByBody() throws Exception {
        Reservation reservation = Reservation.builder().idReservation("1").build();

        Mockito.doNothing().when(reservationService).delete(any(Reservation.class));

        mockMvc.perform(MockMvcRequestBuilders.delete("/reservation/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservation)))
                .andExpect(status().isOk());

        verify(reservationService).delete(Mockito.argThat(arg ->
                arg != null && "1".equals(arg.getIdReservation())));
    }

    @Test
    @DisplayName("POST /reservation/ajouterReservationEtAssignerAChambreEtAEtudiant should assign reservation")
    void ajouterReservationEtAssignerAChambreEtAEtudiant() throws Exception {
        Reservation reservation = Reservation.builder().idReservation("1").build();

        Mockito.when(reservationService.ajouterReservationEtAssignerAChambreEtAEtudiant(1L, 123L))
                .thenReturn(reservation);

        mockMvc.perform(post("/reservation/ajouterReservationEtAssignerAChambreEtAEtudiant")
                        .param("numChambre", "1")
                        .param("cin", "123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idReservation").value("1"));
    }

    @Test
    @DisplayName("GET /reservation/getReservationParAnneeUniversitaire should return count")
    void getReservationParAnneeUniversitaire() throws Exception {
        Mockito.when(reservationService.getReservationParAnneeUniversitaire(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(5L);

        mockMvc.perform(get("/reservation/getReservationParAnneeUniversitaire")
                        .param("debutAnnee", "2023-01-01")
                        .param("finAnnee", "2023-12-31"))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }

    @Test
    @DisplayName("DELETE /reservation/annulerReservation should cancel reservation by cinEtudiant")
    void cancelReservation() throws Exception {
        Mockito.when(reservationService.annulerReservation(123L)).thenReturn("Annulée");

        mockMvc.perform(MockMvcRequestBuilders.delete("/reservation/annulerReservation")
                        .param("cinEtudiant", "123"))
                .andExpect(status().isOk())
                .andExpect(content().string("Annulée"));
    }
}
