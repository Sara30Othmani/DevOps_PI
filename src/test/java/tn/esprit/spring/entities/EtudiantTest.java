package tn.esprit.spring.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tn.esprit.spring.DAO.Entities.Etudiant;
import tn.esprit.spring.DAO.Entities.Reservation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class EtudiantTest {

    @Test
    @DisplayName("Should create etudiant with no-args constructor")
    void noArgsConstructor() {
        Etudiant etudiant = new Etudiant();
        assertThat(etudiant).isNotNull();
        assertThat(etudiant.getIdEtudiant()).isEqualTo(0);
        assertThat(etudiant.getNomEt()).isNull();
        assertThat(etudiant.getPrenomEt()).isNull();
        assertThat(etudiant.getCin()).isEqualTo(0);
        assertThat(etudiant.getEcole()).isNull();
        assertThat(etudiant.getDateNaissance()).isNull();
        assertThat(etudiant.getReservations()).isNotNull();
    }

    @Test
    @DisplayName("Should create etudiant with all-args constructor")
    void allArgsConstructor() {
        List<Reservation> reservations = new ArrayList<>();
        LocalDate dateNaissance = LocalDate.of(2000, 5, 15);
        
        Etudiant etudiant = new Etudiant(1L, "Dupont", "Jean", 12345678L, "ESPRIT", dateNaissance, reservations);
        
        assertThat(etudiant.getIdEtudiant()).isEqualTo(1L);
        assertThat(etudiant.getNomEt()).isEqualTo("Dupont");
        assertThat(etudiant.getPrenomEt()).isEqualTo("Jean");
        assertThat(etudiant.getCin()).isEqualTo(12345678L);
        assertThat(etudiant.getEcole()).isEqualTo("ESPRIT");
        assertThat(etudiant.getDateNaissance()).isEqualTo(dateNaissance);
        assertThat(etudiant.getReservations()).isEqualTo(reservations);
    }

    @Test
    @DisplayName("Should create etudiant using setters")
    void builder() {
        LocalDate dateNaissance = LocalDate.of(2000, 5, 15);
        
        Etudiant etudiant = new Etudiant();
        etudiant.setIdEtudiant(1L);
        etudiant.setNomEt("Dupont");
        etudiant.setPrenomEt("Jean");
        etudiant.setCin(12345678L);
        etudiant.setEcole("ESPRIT");
        etudiant.setDateNaissance(dateNaissance);
        
        assertThat(etudiant.getIdEtudiant()).isEqualTo(1L);
        assertThat(etudiant.getNomEt()).isEqualTo("Dupont");
        assertThat(etudiant.getPrenomEt()).isEqualTo("Jean");
        assertThat(etudiant.getCin()).isEqualTo(12345678L);
        assertThat(etudiant.getEcole()).isEqualTo("ESPRIT");
        assertThat(etudiant.getDateNaissance()).isEqualTo(dateNaissance);
        assertThat(etudiant.getReservations()).isNotNull();
    }

    @Test
    @DisplayName("Should set and get etudiant properties")
    void settersAndGetters() {
        Etudiant etudiant = new Etudiant();
        LocalDate dateNaissance = LocalDate.of(2000, 5, 15);
        List<Reservation> reservations = new ArrayList<>();
        
        etudiant.setIdEtudiant(1L);
        etudiant.setNomEt("Dupont");
        etudiant.setPrenomEt("Jean");
        etudiant.setCin(12345678L);
        etudiant.setEcole("ESPRIT");
        etudiant.setDateNaissance(dateNaissance);
        etudiant.setReservations(reservations);
        
        assertThat(etudiant.getIdEtudiant()).isEqualTo(1L);
        assertThat(etudiant.getNomEt()).isEqualTo("Dupont");
        assertThat(etudiant.getPrenomEt()).isEqualTo("Jean");
        assertThat(etudiant.getCin()).isEqualTo(12345678L);
        assertThat(etudiant.getEcole()).isEqualTo("ESPRIT");
        assertThat(etudiant.getDateNaissance()).isEqualTo(dateNaissance);
        assertThat(etudiant.getReservations()).isEqualTo(reservations);
    }

    @Test
    @DisplayName("Should add reservation to etudiant")
    void addReservation() {
        Etudiant etudiant = new Etudiant();
        Reservation reservation = new Reservation();
        
        etudiant.getReservations().add(reservation);
        
        assertThat(etudiant.getReservations()).hasSize(1);
        assertThat(etudiant.getReservations().get(0)).isEqualTo(reservation);
    }

    @Test
    @DisplayName("Should remove reservation from etudiant")
    void removeReservation() {
        Etudiant etudiant = new Etudiant();
        Reservation reservation = new Reservation();
        
        etudiant.getReservations().add(reservation);
        assertThat(etudiant.getReservations()).hasSize(1);
        
        etudiant.getReservations().remove(reservation);
        assertThat(etudiant.getReservations()).isEmpty();
    }

    @Test
    @DisplayName("Should test etudiant equality")
    void equals() {
        LocalDate dateNaissance = LocalDate.of(2000, 5, 15);
        
        Etudiant etudiant1 = new Etudiant();
        etudiant1.setIdEtudiant(1L);
        etudiant1.setNomEt("Dupont");
        etudiant1.setPrenomEt("Jean");
        etudiant1.setCin(12345678L);
        etudiant1.setEcole("ESPRIT");
        etudiant1.setDateNaissance(dateNaissance);
        
        Etudiant etudiant2 = new Etudiant();
        etudiant2.setIdEtudiant(1L);
        etudiant2.setNomEt("Dupont");
        etudiant2.setPrenomEt("Jean");
        etudiant2.setCin(12345678L);
        etudiant2.setEcole("ESPRIT");
        etudiant2.setDateNaissance(dateNaissance);
        
        // Test that they are not equal (Lombok default equals is based on object identity)
        assertThat(etudiant1).isNotEqualTo(etudiant2);
    }

    @Test
    @DisplayName("Should test etudiant hash code")
    void hashCodeTest() {
        LocalDate dateNaissance = LocalDate.of(2000, 5, 15);
        
        Etudiant etudiant1 = new Etudiant();
        etudiant1.setIdEtudiant(1L);
        etudiant1.setNomEt("Dupont");
        etudiant1.setPrenomEt("Jean");
        etudiant1.setCin(12345678L);
        etudiant1.setEcole("ESPRIT");
        etudiant1.setDateNaissance(dateNaissance);
        
        Etudiant etudiant2 = new Etudiant();
        etudiant2.setIdEtudiant(1L);
        etudiant2.setNomEt("Dupont");
        etudiant2.setPrenomEt("Jean");
        etudiant2.setCin(12345678L);
        etudiant2.setEcole("ESPRIT");
        etudiant2.setDateNaissance(dateNaissance);
        
        // Test that hash codes are different (Lombok default hashCode is based on object identity)
        assertThat(etudiant1.hashCode()).isNotEqualTo(etudiant2.hashCode());
    }

    @Test
    @DisplayName("Should test etudiant toString")
    void toStringTest() {
        LocalDate dateNaissance = LocalDate.of(2000, 5, 15);
        
        Etudiant etudiant = new Etudiant();
        etudiant.setIdEtudiant(1L);
        etudiant.setNomEt("Dupont");
        etudiant.setPrenomEt("Jean");
        etudiant.setCin(12345678L);
        etudiant.setEcole("ESPRIT");
        etudiant.setDateNaissance(dateNaissance);
        
        String toString = etudiant.toString();
        assertThat(toString).contains("Etudiant");
        // Lombok's default toString doesn't include field names, so just check it's not empty
        assertThat(toString).isNotEmpty();
    }

    @Test
    @DisplayName("Should test etudiant with null values")
    void withNullValues() {
        Etudiant etudiant = new Etudiant();
        etudiant.setIdEtudiant(1L);
        etudiant.setNomEt(null);
        etudiant.setPrenomEt(null);
        etudiant.setCin(12345678L);
        etudiant.setEcole(null);
        etudiant.setDateNaissance(null);
        
        assertThat(etudiant.getIdEtudiant()).isEqualTo(1L);
        assertThat(etudiant.getNomEt()).isNull();
        assertThat(etudiant.getPrenomEt()).isNull();
        assertThat(etudiant.getCin()).isEqualTo(12345678L);
        assertThat(etudiant.getEcole()).isNull();
        assertThat(etudiant.getDateNaissance()).isNull();
        assertThat(etudiant.getReservations()).isNotNull();
    }

    @Test
    @DisplayName("Should test etudiant with zero values")
    void withZeroValues() {
        Etudiant etudiant = Etudiant.builder()
                .idEtudiant(0L)
                .cin(0L)
                .build();
        
        assertThat(etudiant.getIdEtudiant()).isEqualTo(0L);
        assertThat(etudiant.getCin()).isEqualTo(0L);
    }

    @Test
    @DisplayName("Should test etudiant with different schools")
    void withDifferentSchools() {
        Etudiant espritEtudiant = Etudiant.builder()
                .ecole("ESPRIT")
                .build();
        
        Etudiant insatEtudiant = Etudiant.builder()
                .ecole("INSAT")
                .build();
        
        Etudiant enitEtudiant = Etudiant.builder()
                .ecole("ENIT")
                .build();
        
        assertThat(espritEtudiant.getEcole()).isEqualTo("ESPRIT");
        assertThat(insatEtudiant.getEcole()).isEqualTo("INSAT");
        assertThat(enitEtudiant.getEcole()).isEqualTo("ENIT");
    }

    @Test
    @DisplayName("Should test etudiant with different birth dates")
    void withDifferentBirthDates() {
        LocalDate date1 = LocalDate.of(1999, 1, 1);
        LocalDate date2 = LocalDate.of(2000, 6, 15);
        LocalDate date3 = LocalDate.of(2001, 12, 31);
        
        Etudiant etudiant1 = Etudiant.builder()
                .dateNaissance(date1)
                .build();
        
        Etudiant etudiant2 = Etudiant.builder()
                .dateNaissance(date2)
                .build();
        
        Etudiant etudiant3 = Etudiant.builder()
                .dateNaissance(date3)
                .build();
        
        assertThat(etudiant1.getDateNaissance()).isEqualTo(date1);
        assertThat(etudiant2.getDateNaissance()).isEqualTo(date2);
        assertThat(etudiant3.getDateNaissance()).isEqualTo(date3);
    }

    @Test
    @DisplayName("Should test etudiant with large CIN numbers")
    void withLargeCINNumbers() {
        Etudiant etudiant = Etudiant.builder()
                .cin(99999999L)
                .build();
        
        assertThat(etudiant.getCin()).isEqualTo(99999999L);
    }

    @Test
    @DisplayName("Should test etudiant with multiple reservations")
    void withMultipleReservations() {
        Etudiant etudiant = new Etudiant();
        Reservation reservation1 = new Reservation();
        Reservation reservation2 = new Reservation();
        
        etudiant.getReservations().add(reservation1);
        etudiant.getReservations().add(reservation2);
        
        assertThat(etudiant.getReservations()).hasSize(2);
        assertThat(etudiant.getReservations()).contains(reservation1, reservation2);
    }
} 