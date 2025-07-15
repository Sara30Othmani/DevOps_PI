package tn.esprit.spring.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tn.esprit.spring.DAO.Entities.Chambre;
import tn.esprit.spring.DAO.Entities.Etudiant;
import tn.esprit.spring.DAO.Entities.Reservation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationTest {

    @Test
    @DisplayName("Should create reservation with no-args constructor")
    void noArgsConstructor() {
        Reservation reservation = new Reservation();
        assertThat(reservation).isNotNull();
        assertThat(reservation.getIdReservation()).isNull();
        assertThat(reservation.getAnneeUniversitaire()).isNull();
        assertThat(reservation.isEstValide()).isFalse();
        assertThat(reservation.getEtudiants()).isNotNull();
    }

    @Test
    @DisplayName("Should create reservation with all-args constructor")
    void allArgsConstructor() {
        List<Etudiant> etudiants = new ArrayList<>();
        
        Reservation reservation = new Reservation("RES001", LocalDate.of(2024, 1, 1), true, etudiants);
        
        assertThat(reservation.getIdReservation()).isEqualTo("RES001");
        assertThat(reservation.getAnneeUniversitaire()).isEqualTo(LocalDate.of(2024, 1, 1));
        assertThat(reservation.isEstValide()).isTrue();
        assertThat(reservation.getEtudiants()).isEqualTo(etudiants);
    }

    @Test
    @DisplayName("Should set and get reservation properties")
    void settersAndGetters() {
        Reservation reservation = new Reservation();
        List<Etudiant> etudiants = new ArrayList<>();
        
        reservation.setIdReservation("RES001");
        reservation.setAnneeUniversitaire(LocalDate.of(2024, 1, 1));
        reservation.setEstValide(true);
        reservation.setEtudiants(etudiants);
        
        assertThat(reservation.getIdReservation()).isEqualTo("RES001");
        assertThat(reservation.getAnneeUniversitaire()).isEqualTo(LocalDate.of(2024, 1, 1));
        assertThat(reservation.isEstValide()).isTrue();
        assertThat(reservation.getEtudiants()).isEqualTo(etudiants);
    }

    @Test
    @DisplayName("Should add etudiant to reservation")
    void addEtudiant() {
        Reservation reservation = new Reservation();
        Etudiant etudiant = new Etudiant();
        
        reservation.getEtudiants().add(etudiant);
        
        assertThat(reservation.getEtudiants()).hasSize(1);
        assertThat(reservation.getEtudiants().get(0)).isEqualTo(etudiant);
    }

    @Test
    @DisplayName("Should remove etudiant from reservation")
    void removeEtudiant() {
        Reservation reservation = new Reservation();
        Etudiant etudiant = new Etudiant();
        
        reservation.getEtudiants().add(etudiant);
        assertThat(reservation.getEtudiants()).hasSize(1);
        
        reservation.getEtudiants().remove(etudiant);
        assertThat(reservation.getEtudiants()).isEmpty();
    }

    @Test
    @DisplayName("Should test reservation equality")
    void equals() {
        Reservation reservation1 = new Reservation("RES001", LocalDate.of(2024, 1, 1), true, new ArrayList<>());
        Reservation reservation2 = new Reservation("RES001", LocalDate.of(2024, 1, 1), true, new ArrayList<>());
        
        // Lombok's default equals uses object identity, so these should not be equal
        assertThat(reservation1).isNotEqualTo(reservation2);
        
        // Same object should be equal to itself
        assertThat(reservation1).isEqualTo(reservation1);
    }

    @Test
    @DisplayName("Should test reservation hash code")
    void hashCodeTest() {
        Reservation reservation1 = new Reservation("RES001", LocalDate.of(2024, 1, 1), true, new ArrayList<>());
        Reservation reservation2 = new Reservation("RES001", LocalDate.of(2024, 1, 1), true, new ArrayList<>());
        
        // Lombok's default hashCode uses object identity, so these should have different hash codes
        assertThat(reservation1.hashCode()).isNotEqualTo(reservation2.hashCode());
        
        // Same object should have same hash code
        assertThat(reservation1.hashCode()).isEqualTo(reservation1.hashCode());
    }

    @Test
    @DisplayName("Should test reservation toString")
    void toStringTest() {
        Reservation reservation = new Reservation("RES001", LocalDate.of(2024, 1, 1), true, new ArrayList<>());
        
        String toString = reservation.toString();
        assertThat(toString).contains("Reservation");
        // Lombok's default toString includes class name and hash code, not field values
        assertThat(toString).contains("@");
    }

    @Test
    @DisplayName("Should test reservation with null values")
    void withNullValues() {
        Reservation reservation = new Reservation(null, null, false, new ArrayList<>());
        
        assertThat(reservation).isNotNull();
        assertThat(reservation.getIdReservation()).isNull();
        assertThat(reservation.getAnneeUniversitaire()).isNull();
        assertThat(reservation.isEstValide()).isFalse();
        assertThat(reservation.getEtudiants()).isNotNull();
    }

    @Test
    @DisplayName("Should test reservation with empty etudiants list")
    void withEmptyEtudiants() {
        Reservation reservation = new Reservation();
        assertThat(reservation.getEtudiants()).isNotNull();
        assertThat(reservation.getEtudiants()).isEmpty();
    }

    @Test
    @DisplayName("Should test reservation with multiple etudiants")
    void withMultipleEtudiants() {
        Reservation reservation = new Reservation();
        Etudiant etudiant1 = new Etudiant();
        Etudiant etudiant2 = new Etudiant();
        
        reservation.getEtudiants().add(etudiant1);
        reservation.getEtudiants().add(etudiant2);
        
        assertThat(reservation.getEtudiants()).hasSize(2);
        assertThat(reservation.getEtudiants()).contains(etudiant1, etudiant2);
    }

    @Test
    @DisplayName("Should test reservation validation status")
    void validationStatus() {
        Reservation validReservation = new Reservation();
        validReservation.setEstValide(true);
        
        Reservation invalidReservation = new Reservation();
        invalidReservation.setEstValide(false);
        
        assertThat(validReservation.isEstValide()).isTrue();
        assertThat(invalidReservation.isEstValide()).isFalse();
    }

    @Test
    @DisplayName("Should test reservation with different dates")
    void withDifferentDates() {
        LocalDate date1 = LocalDate.of(2024, 1, 1);
        LocalDate date2 = LocalDate.of(2024, 9, 1);
        
        Reservation reservation1 = new Reservation();
        reservation1.setAnneeUniversitaire(date1);
        
        Reservation reservation2 = new Reservation();
        reservation2.setAnneeUniversitaire(date2);
        
        assertThat(reservation1.getAnneeUniversitaire()).isEqualTo(date1);
        assertThat(reservation2.getAnneeUniversitaire()).isEqualTo(date2);
    }

    @Test
    @DisplayName("Should test reservation with long id")
    void withLongId() {
        String longId = "RESERVATION_2024_2025_VERY_LONG_IDENTIFIER";
        Reservation reservation = new Reservation();
        reservation.setIdReservation(longId);
        
        assertThat(reservation.getIdReservation()).isEqualTo(longId);
    }

    @Test
    @DisplayName("Should test reservation with special characters in id")
    void withSpecialCharactersInId() {
        String specialId = "RES@#$%^&*()_+-=[]{}|;':\",./<>?";
        Reservation reservation = new Reservation();
        reservation.setIdReservation(specialId);
        
        assertThat(reservation.getIdReservation()).isEqualTo(specialId);
    }

    @Test
    @DisplayName("Should test reservation with future date")
    void withFutureDate() {
        LocalDate futureDate = LocalDate.now().plusYears(1);
        Reservation reservation = new Reservation();
        reservation.setAnneeUniversitaire(futureDate);
        
        assertThat(reservation.getAnneeUniversitaire()).isEqualTo(futureDate);
    }

    @Test
    @DisplayName("Should test reservation with past date")
    void withPastDate() {
        LocalDate pastDate = LocalDate.now().minusYears(1);
        Reservation reservation = new Reservation();
        reservation.setAnneeUniversitaire(pastDate);
        
        assertThat(reservation.getAnneeUniversitaire()).isEqualTo(pastDate);
    }
} 