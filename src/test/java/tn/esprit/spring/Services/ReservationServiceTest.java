package tn.esprit.spring.Services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.DAO.Entities.Bloc;
import tn.esprit.spring.DAO.Entities.Chambre;
import tn.esprit.spring.DAO.Entities.Etudiant;
import tn.esprit.spring.DAO.Entities.Reservation;
import tn.esprit.spring.DAO.Entities.TypeChambre;
import tn.esprit.spring.DAO.Repositories.ChambreRepository;
import tn.esprit.spring.DAO.Repositories.EtudiantRepository;
import tn.esprit.spring.DAO.Repositories.ReservationRepository;
import tn.esprit.spring.Services.Reservation.ReservationService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private ChambreRepository chambreRepository;

    @Mock
    private EtudiantRepository etudiantRepository;

    @InjectMocks
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddOrUpdate() {
        Reservation reservation = new Reservation();
        reservation.setIdReservation("RES001");
        reservation.setEstValide(true);
        reservation.setAnneeUniversitaire(LocalDate.now());

        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        Reservation result = reservationService.addOrUpdate(reservation);

        assertNotNull(result);
        assertEquals("RES001", result.getIdReservation());
        assertTrue(result.isEstValide());
        verify(reservationRepository, times(1)).save(reservation);
    }

    @Test
    void testFindAll() {
        Reservation reservation1 = new Reservation();
        reservation1.setIdReservation("RES001");
        reservation1.setEstValide(true);

        Reservation reservation2 = new Reservation();
        reservation2.setIdReservation("RES002");
        reservation2.setEstValide(false);

        List<Reservation> reservationList = Arrays.asList(reservation1, reservation2);

        when(reservationRepository.findAll()).thenReturn(reservationList);

        List<Reservation> result = reservationService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(reservationRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        String reservationId = "RES001";
        Reservation reservation = new Reservation();
        reservation.setIdReservation(reservationId);
        reservation.setEstValide(true);
        reservation.setAnneeUniversitaire(LocalDate.now());

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));

        Reservation result = reservationService.findById(reservationId);

        assertNotNull(result);
        assertEquals(reservationId, result.getIdReservation());
        assertTrue(result.isEstValide());
        verify(reservationRepository, times(1)).findById(reservationId);
    }

    @Test
    void testDeleteById() {
        String reservationId = "RES001";
        doNothing().when(reservationRepository).deleteById(reservationId);

        reservationService.deleteById(reservationId);

        verify(reservationRepository, times(1)).deleteById(reservationId);
    }

    @Test
    void testDelete() {
        Reservation reservation = new Reservation();
        reservation.setIdReservation("RES001");
        reservation.setEstValide(true);

        doNothing().when(reservationRepository).delete(reservation);

        reservationService.delete(reservation);

        verify(reservationRepository, times(1)).delete(reservation);
    }

    @Test
    void testGetDateDebutAU() {
        LocalDate result = reservationService.getDateDebutAU();
        assertNotNull(result);
        // The method should return a date in September
        assertEquals(9, result.getMonthValue());
    }

    @Test
    void testGetDateFinAU() {
        LocalDate result = reservationService.getDateFinAU();
        assertNotNull(result);
        // The method should return a date in June
        assertEquals(6, result.getMonthValue());
    }

    @Test
    void testAjouterReservationEtAssignerAChambreEtAEtudiant_Success() {
        Long numChambre = 101L;
        long cin = 12345678L;

        Bloc bloc = new Bloc();
        bloc.setNomBloc("BlocA");

        Chambre chambre = new Chambre();
        chambre.setIdChambre(1L);
        chambre.setNumeroChambre(numChambre);
        chambre.setTypeC(TypeChambre.SIMPLE);
        chambre.setBloc(bloc);
        chambre.setReservations(new ArrayList<>());

        Etudiant etudiant = new Etudiant();
        etudiant.setIdEtudiant(1L);
        etudiant.setCin(cin);
        etudiant.setNomEt("Doe");
        etudiant.setPrenomEt("John");

        when(chambreRepository.findByNumeroChambre(numChambre)).thenReturn(chambre);
        when(etudiantRepository.findByCin(cin)).thenReturn(etudiant);
        when(chambreRepository.countReservationsByIdChambreAndReservationsAnneeUniversitaireBetween(
                anyLong(), any(LocalDate.class), any(LocalDate.class))).thenReturn(0);
        when(reservationRepository.save(any(Reservation.class))).thenReturn(new Reservation());
        when(chambreRepository.save(any(Chambre.class))).thenReturn(chambre);

        Reservation result = reservationService.ajouterReservationEtAssignerAChambreEtAEtudiant(numChambre, cin);

        assertNotNull(result);
        verify(chambreRepository, times(1)).findByNumeroChambre(numChambre);
        verify(etudiantRepository, times(1)).findByCin(cin);
        verify(reservationRepository, times(1)).save(any(Reservation.class));
        verify(chambreRepository, times(1)).save(any(Chambre.class));
    }

    @Test
    void testAjouterReservationEtAssignerAChambreEtAEtudiant_ChambreFull() {
        Long numChambre = 101L;
        long cin = 12345678L;

        Bloc bloc = new Bloc();
        bloc.setNomBloc("BlocA");

        Chambre chambre = new Chambre();
        chambre.setIdChambre(1L);
        chambre.setNumeroChambre(numChambre);
        chambre.setTypeC(TypeChambre.SIMPLE);
        chambre.setBloc(bloc);

        Etudiant etudiant = new Etudiant();
        etudiant.setIdEtudiant(1L);
        etudiant.setCin(cin);

        when(chambreRepository.findByNumeroChambre(numChambre)).thenReturn(chambre);
        when(etudiantRepository.findByCin(cin)).thenReturn(etudiant);
        when(chambreRepository.countReservationsByIdChambreAndReservationsAnneeUniversitaireBetween(
                anyLong(), any(LocalDate.class), any(LocalDate.class))).thenReturn(1);

        Reservation result = reservationService.ajouterReservationEtAssignerAChambreEtAEtudiant(numChambre, cin);

        assertNull(result);
        verify(chambreRepository, times(1)).findByNumeroChambre(numChambre);
        verify(etudiantRepository, times(1)).findByCin(cin);
        verify(reservationRepository, never()).save(any(Reservation.class));
    }

    @Test
    void testGetReservationParAnneeUniversitaire() {
        LocalDate debutAnnee = LocalDate.of(2023, 9, 15);
        LocalDate finAnnee = LocalDate.of(2024, 6, 30);
        int expectedCount = 10;

        when(reservationRepository.countByAnneeUniversitaireBetween(debutAnnee, finAnnee)).thenReturn(expectedCount);

        long result = reservationService.getReservationParAnneeUniversitaire(debutAnnee, finAnnee);

        assertEquals(expectedCount, result);
        verify(reservationRepository, times(1)).countByAnneeUniversitaireBetween(debutAnnee, finAnnee);
    }

    @Test
    void testAnnulerReservation() {
        long cinEtudiant = 12345678L;
        String reservationId = "RES001";

        Reservation reservation = new Reservation();
        reservation.setIdReservation(reservationId);
        reservation.setEstValide(true);

        Chambre chambre = new Chambre();
        chambre.setIdChambre(1L);
        chambre.setReservations(new ArrayList<>());
        chambre.getReservations().add(reservation);

        when(reservationRepository.findByEtudiantsCinAndEstValide(cinEtudiant, true)).thenReturn(reservation);
        when(chambreRepository.findByReservationsIdReservation(reservationId)).thenReturn(chambre);
        when(chambreRepository.save(any(Chambre.class))).thenReturn(chambre);
        doNothing().when(reservationRepository).delete(reservation);

        String result = reservationService.annulerReservation(cinEtudiant);

        assertNotNull(result);
        assertTrue(result.contains("annulée avec succés"));
        verify(reservationRepository, times(1)).findByEtudiantsCinAndEstValide(cinEtudiant, true);
        verify(chambreRepository, times(1)).findByReservationsIdReservation(reservationId);
        verify(chambreRepository, times(1)).save(chambre);
        verify(reservationRepository, times(1)).delete(reservation);
    }

    @Test
    void testAffectReservationAChambre() {
        String idRes = "RES001";
        long idChambre = 1L;

        Reservation reservation = new Reservation();
        reservation.setIdReservation(idRes);

        Chambre chambre = new Chambre();
        chambre.setIdChambre(idChambre);
        chambre.setReservations(new ArrayList<>());

        when(reservationRepository.findById(idRes)).thenReturn(Optional.of(reservation));
        when(chambreRepository.findById(idChambre)).thenReturn(Optional.of(chambre));
        when(chambreRepository.save(any(Chambre.class))).thenReturn(chambre);

        reservationService.affectReservationAChambre(idRes, idChambre);

        verify(reservationRepository, times(1)).findById(idRes);
        verify(chambreRepository, times(1)).findById(idChambre);
        verify(chambreRepository, times(1)).save(chambre);
        assertTrue(chambre.getReservations().contains(reservation));
    }

    @Test
    void testDeaffectReservationAChambre() {
        String idRes = "RES001";
        long idChambre = 1L;

        Reservation reservation = new Reservation();
        reservation.setIdReservation(idRes);

        Chambre chambre = new Chambre();
        chambre.setIdChambre(idChambre);
        chambre.setReservations(new ArrayList<>());
        chambre.getReservations().add(reservation);

        when(reservationRepository.findById(idRes)).thenReturn(Optional.of(reservation));
        when(chambreRepository.findById(idChambre)).thenReturn(Optional.of(chambre));
        when(chambreRepository.save(any(Chambre.class))).thenReturn(chambre);

        reservationService.deaffectReservationAChambre(idRes, idChambre);

        verify(reservationRepository, times(1)).findById(idRes);
        verify(chambreRepository, times(1)).findById(idChambre);
        verify(chambreRepository, times(1)).save(chambre);
        assertFalse(chambre.getReservations().contains(reservation));
    }

    @Test
    void testAnnulerReservations() {
        Reservation reservation1 = new Reservation();
        reservation1.setIdReservation("RES001");
        reservation1.setEstValide(true);

        Reservation reservation2 = new Reservation();
        reservation2.setIdReservation("RES002");
        reservation2.setEstValide(true);

        List<Reservation> reservations = Arrays.asList(reservation1, reservation2);

        when(reservationRepository.findByEstValideAndAnneeUniversitaireBetween(
                any(Boolean.class), any(LocalDate.class), any(LocalDate.class))).thenReturn(reservations);
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation1, reservation2);

        reservationService.annulerReservations();

        verify(reservationRepository, times(1)).findByEstValideAndAnneeUniversitaireBetween(
                any(Boolean.class), any(LocalDate.class), any(LocalDate.class));
        verify(reservationRepository, times(2)).save(any(Reservation.class));
        assertFalse(reservation1.isEstValide());
        assertFalse(reservation2.isEstValide());
    }

    @Test
    void testAjouterReservationEtAssignerAChambreEtAEtudiant_DoubleChambre() {
        Long numChambre = 101L;
        long cin = 12345678L;

        Bloc bloc = new Bloc();
        bloc.setNomBloc("BlocA");

        Chambre chambre = new Chambre();
        chambre.setIdChambre(1L);
        chambre.setNumeroChambre(numChambre);
        chambre.setTypeC(TypeChambre.DOUBLE);
        chambre.setBloc(bloc);
        chambre.setReservations(new ArrayList<>());

        Etudiant etudiant = new Etudiant();
        etudiant.setIdEtudiant(1L);
        etudiant.setCin(cin);

        when(chambreRepository.findByNumeroChambre(numChambre)).thenReturn(chambre);
        when(etudiantRepository.findByCin(cin)).thenReturn(etudiant);
        when(chambreRepository.countReservationsByIdChambreAndReservationsAnneeUniversitaireBetween(
                anyLong(), any(LocalDate.class), any(LocalDate.class))).thenReturn(1);
        when(reservationRepository.save(any(Reservation.class))).thenReturn(new Reservation());
        when(chambreRepository.save(any(Chambre.class))).thenReturn(chambre);

        Reservation result = reservationService.ajouterReservationEtAssignerAChambreEtAEtudiant(numChambre, cin);

        assertNotNull(result);
        verify(chambreRepository, times(1)).findByNumeroChambre(numChambre);
        verify(etudiantRepository, times(1)).findByCin(cin);
        verify(reservationRepository, times(1)).save(any(Reservation.class));
    }

    @Test
    void testAjouterReservationEtAssignerAChambreEtAEtudiant_TripleChambre() {
        Long numChambre = 101L;
        long cin = 12345678L;

        Bloc bloc = new Bloc();
        bloc.setNomBloc("BlocA");

        Chambre chambre = new Chambre();
        chambre.setIdChambre(1L);
        chambre.setNumeroChambre(numChambre);
        chambre.setTypeC(TypeChambre.TRIPLE);
        chambre.setBloc(bloc);
        chambre.setReservations(new ArrayList<>());

        Etudiant etudiant = new Etudiant();
        etudiant.setIdEtudiant(1L);
        etudiant.setCin(cin);

        when(chambreRepository.findByNumeroChambre(numChambre)).thenReturn(chambre);
        when(etudiantRepository.findByCin(cin)).thenReturn(etudiant);
        when(chambreRepository.countReservationsByIdChambreAndReservationsAnneeUniversitaireBetween(
                anyLong(), any(LocalDate.class), any(LocalDate.class))).thenReturn(2);
        when(reservationRepository.save(any(Reservation.class))).thenReturn(new Reservation());
        when(chambreRepository.save(any(Chambre.class))).thenReturn(chambre);

        Reservation result = reservationService.ajouterReservationEtAssignerAChambreEtAEtudiant(numChambre, cin);

        assertNotNull(result);
        verify(chambreRepository, times(1)).findByNumeroChambre(numChambre);
        verify(etudiantRepository, times(1)).findByCin(cin);
        verify(reservationRepository, times(1)).save(any(Reservation.class));
    }
} 