package tn.esprit.spring.Services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.DAO.Entities.Etudiant;
import tn.esprit.spring.DAO.Entities.Reservation;
import tn.esprit.spring.DAO.Repositories.EtudiantRepository;
import tn.esprit.spring.DAO.Repositories.ReservationRepository;
import tn.esprit.spring.Services.Etudiant.EtudiantService;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EtudiantServiceTest {

    @Mock
    private EtudiantRepository etudiantRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private EtudiantService etudiantService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddOrUpdate() {
        Etudiant etudiant = new Etudiant();
        etudiant.setIdEtudiant(1L);
        etudiant.setNomEt("Doe");
        etudiant.setPrenomEt("John");
        etudiant.setCin(12345678L);

        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(etudiant);

        Etudiant result = etudiantService.addOrUpdate(etudiant);

        assertNotNull(result);
        assertEquals(1L, result.getIdEtudiant());
        assertEquals("Doe", result.getNomEt());
        assertEquals("John", result.getPrenomEt());
        assertEquals(12345678L, result.getCin());
        verify(etudiantRepository, times(1)).save(etudiant);
    }

    @Test
    void testFindAll() {
        Etudiant etudiant1 = new Etudiant();
        etudiant1.setIdEtudiant(1L);
        etudiant1.setNomEt("Doe");
        etudiant1.setPrenomEt("John");

        Etudiant etudiant2 = new Etudiant();
        etudiant2.setIdEtudiant(2L);
        etudiant2.setNomEt("Smith");
        etudiant2.setPrenomEt("Jane");

        List<Etudiant> etudiantList = Arrays.asList(etudiant1, etudiant2);

        when(etudiantRepository.findAll()).thenReturn(etudiantList);

        List<Etudiant> result = etudiantService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(etudiantRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        long etudiantId = 1L;
        Etudiant etudiant = new Etudiant();
        etudiant.setIdEtudiant(etudiantId);
        etudiant.setNomEt("Doe");
        etudiant.setPrenomEt("John");
        etudiant.setCin(12345678L);

        when(etudiantRepository.findById(etudiantId)).thenReturn(Optional.of(etudiant));

        Etudiant result = etudiantService.findById(etudiantId);

        assertNotNull(result);
        assertEquals(etudiantId, result.getIdEtudiant());
        assertEquals("Doe", result.getNomEt());
        assertEquals("John", result.getPrenomEt());
        verify(etudiantRepository, times(1)).findById(etudiantId);
    }

    @Test
    void testDeleteById() {
        long etudiantId = 1L;
        doNothing().when(etudiantRepository).deleteById(etudiantId);

        etudiantService.deleteById(etudiantId);

        verify(etudiantRepository, times(1)).deleteById(etudiantId);
    }

    @Test
    void testDelete() {
        Etudiant etudiant = new Etudiant();
        etudiant.setIdEtudiant(1L);
        etudiant.setNomEt("Doe");
        etudiant.setPrenomEt("John");

        doNothing().when(etudiantRepository).delete(etudiant);

        etudiantService.delete(etudiant);

        verify(etudiantRepository, times(1)).delete(etudiant);
    }

    @Test
    void testSelectJPQL() {
        String nom = "Doe";
        Etudiant etudiant1 = new Etudiant();
        etudiant1.setIdEtudiant(1L);
        etudiant1.setNomEt("Doe");
        etudiant1.setPrenomEt("John");

        Etudiant etudiant2 = new Etudiant();
        etudiant2.setIdEtudiant(2L);
        etudiant2.setNomEt("Doe");
        etudiant2.setPrenomEt("Jane");

        List<Etudiant> etudiantList = Arrays.asList(etudiant1, etudiant2);

        when(etudiantRepository.selectJPQL(nom)).thenReturn(etudiantList);

        List<Etudiant> result = etudiantService.selectJPQL(nom);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(etudiantRepository, times(1)).selectJPQL(nom);
    }

    @Test
    void testAffecterReservationAEtudiant() {
        String idR = "RES001";
        String nomE = "Doe";
        String prenomE = "John";

        Reservation reservation = new Reservation();
        reservation.setIdReservation(idR);

        Etudiant etudiant = new Etudiant();
        etudiant.setIdEtudiant(1L);
        etudiant.setNomEt(nomE);
        etudiant.setPrenomEt(prenomE);
        etudiant.setReservations(new ArrayList<>());

        when(reservationRepository.findById(idR)).thenReturn(Optional.of(reservation));
        when(etudiantRepository.getByNomEtAndPrenomEt(nomE, prenomE)).thenReturn(etudiant);
        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(etudiant);

        etudiantService.affecterReservationAEtudiant(idR, nomE, prenomE);

        verify(reservationRepository, times(1)).findById(idR);
        verify(etudiantRepository, times(1)).getByNomEtAndPrenomEt(nomE, prenomE);
        verify(etudiantRepository, times(1)).save(etudiant);
        assertTrue(etudiant.getReservations().contains(reservation));
    }

    @Test
    void testDesaffecterReservationAEtudiant() {
        String idR = "RES001";
        String nomE = "Doe";
        String prenomE = "John";

        Reservation reservation = new Reservation();
        reservation.setIdReservation(idR);

        Etudiant etudiant = new Etudiant();
        etudiant.setIdEtudiant(1L);
        etudiant.setNomEt(nomE);
        etudiant.setPrenomEt(prenomE);
        etudiant.setReservations(new ArrayList<>());
        etudiant.getReservations().add(reservation);

        when(reservationRepository.findById(idR)).thenReturn(Optional.of(reservation));
        when(etudiantRepository.getByNomEtAndPrenomEt(nomE, prenomE)).thenReturn(etudiant);
        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(etudiant);

        etudiantService.desaffecterReservationAEtudiant(idR, nomE, prenomE);

        verify(reservationRepository, times(1)).findById(idR);
        verify(etudiantRepository, times(1)).getByNomEtAndPrenomEt(nomE, prenomE);
        verify(etudiantRepository, times(1)).save(etudiant);
        assertFalse(etudiant.getReservations().contains(reservation));
    }

    @Test
    void testAffecterReservationAEtudiantWithEmptyReservations() {
        String idR = "RES001";
        String nomE = "Doe";
        String prenomE = "John";

        Reservation reservation = new Reservation();
        reservation.setIdReservation(idR);

        Etudiant etudiant = new Etudiant();
        etudiant.setIdEtudiant(1L);
        etudiant.setNomEt(nomE);
        etudiant.setPrenomEt(prenomE);
        // Initialize with null to test the scenario
        etudiant.setReservations(null);

        when(reservationRepository.findById(idR)).thenReturn(Optional.of(reservation));
        when(etudiantRepository.getByNomEtAndPrenomEt(nomE, prenomE)).thenReturn(etudiant);
        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(etudiant);

        // This should handle the null case gracefully
        etudiantService.affecterReservationAEtudiant(idR, nomE, prenomE);

        verify(reservationRepository, times(1)).findById(idR);
        verify(etudiantRepository, times(1)).getByNomEtAndPrenomEt(nomE, prenomE);
        verify(etudiantRepository, times(1)).save(etudiant);
    }

    @Test
    void testDesaffecterReservationAEtudiantWithEmptyReservations() {
        String idR = "RES001";
        String nomE = "Doe";
        String prenomE = "John";

        Reservation reservation = new Reservation();
        reservation.setIdReservation(idR);

        Etudiant etudiant = new Etudiant();
        etudiant.setIdEtudiant(1L);
        etudiant.setNomEt(nomE);
        etudiant.setPrenomEt(prenomE);
        // Initialize with null to test the scenario
        etudiant.setReservations(null);

        when(reservationRepository.findById(idR)).thenReturn(Optional.of(reservation));
        when(etudiantRepository.getByNomEtAndPrenomEt(nomE, prenomE)).thenReturn(etudiant);
        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(etudiant);

        // This should handle the null case gracefully
        etudiantService.desaffecterReservationAEtudiant(idR, nomE, prenomE);

        verify(reservationRepository, times(1)).findById(idR);
        verify(etudiantRepository, times(1)).getByNomEtAndPrenomEt(nomE, prenomE);
        verify(etudiantRepository, times(1)).save(etudiant);
    }
} 