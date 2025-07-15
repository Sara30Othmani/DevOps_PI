package tn.esprit.spring.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tn.esprit.spring.DAO.Entities.Foyer;
import tn.esprit.spring.DAO.Entities.Universite;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UniversiteTest {

    @Test
    @DisplayName("Should create universite with no-args constructor")
    void noArgsConstructor() {
        Universite universite = new Universite();
        assertThat(universite).isNotNull();
        assertThat(universite.getIdUniversite()).isEqualTo(0);
        assertThat(universite.getNomUniversite()).isNull();
        assertThat(universite.getAdresse()).isNull();
        assertThat(universite.getFoyer()).isNull();
    }

    @Test
    @DisplayName("Should create universite with all-args constructor")
    void allArgsConstructor() {
        Foyer foyer = new Foyer();
        Universite universite = new Universite(1L, "Uni A", "Address A", foyer);
        assertThat(universite.getIdUniversite()).isEqualTo(1L);
        assertThat(universite.getNomUniversite()).isEqualTo("Uni A");
        assertThat(universite.getAdresse()).isEqualTo("Address A");
        assertThat(universite.getFoyer()).isEqualTo(foyer);
    }

    @Test
    @DisplayName("Should set and get universite properties")
    void settersAndGetters() {
        Universite universite = new Universite();
        Foyer foyer = new Foyer();
        universite.setIdUniversite(1L);
        universite.setNomUniversite("Uni A");
        universite.setAdresse("Address A");
        universite.setFoyer(foyer);
        assertThat(universite.getIdUniversite()).isEqualTo(1L);
        assertThat(universite.getNomUniversite()).isEqualTo("Uni A");
        assertThat(universite.getAdresse()).isEqualTo("Address A");
        assertThat(universite.getFoyer()).isEqualTo(foyer);
    }

    @Test
    @DisplayName("Should test universite equality")
    void equals() {
        Universite u1 = new Universite(1L, "Uni A", "Address A", null);
        Universite u2 = new Universite(1L, "Uni A", "Address A", null);
        // Lombok's default equals uses object identity, so these should not be equal
        assertThat(u1).isNotEqualTo(u2);
        // Same object should be equal to itself
        assertThat(u1).isEqualTo(u1);
    }

    @Test
    @DisplayName("Should test universite hash code")
    void hashCodeTest() {
        Universite u1 = new Universite(1L, "Uni A", "Address A", null);
        Universite u2 = new Universite(1L, "Uni A", "Address A", null);
        // Lombok's default hashCode uses object identity, so these should have different hash codes
        assertThat(u1.hashCode()).isNotEqualTo(u2.hashCode());
        // Same object should have same hash code
        assertThat(u1.hashCode()).isEqualTo(u1.hashCode());
    }

    @Test
    @DisplayName("Should test universite toString")
    void toStringTest() {
        Universite universite = new Universite(1L, "Uni A", "Address A", null);
        String toString = universite.toString();
        assertThat(toString).contains("Universite");
        // Lombok's default toString includes class name and hash code, not field values
        assertThat(toString).contains("@");
    }

    @Test
    @DisplayName("Should test universite with null values")
    void withNullValues() {
        Universite universite = new Universite(1L, null, null, null);
        assertThat(universite).isNotNull();
        assertThat(universite.getIdUniversite()).isEqualTo(1L);
        assertThat(universite.getNomUniversite()).isNull();
        assertThat(universite.getAdresse()).isNull();
        assertThat(universite.getFoyer()).isNull();
    }

    @Test
    @DisplayName("Should test universite with long name and address")
    void withLongNameAndAddress() {
        Universite universite = new Universite();
        String longName = "This is a very long university name that might exceed normal limits";
        String longAddress = "1234 Long Address Street, City, Country, 1234567890";
        universite.setNomUniversite(longName);
        universite.setAdresse(longAddress);
        assertThat(universite.getNomUniversite()).isEqualTo(longName);
        assertThat(universite.getAdresse()).isEqualTo(longAddress);
    }

    @Test
    @DisplayName("Should test universite with special characters in name and address")
    void withSpecialCharacters() {
        Universite universite = new Universite();
        String specialName = "Uni@#$%^&*()_+-=[]{}|;':\",./<>?";
        String specialAddress = "Addr@#$%^&*()_+-=[]{}|;':\",./<>?";
        universite.setNomUniversite(specialName);
        universite.setAdresse(specialAddress);
        assertThat(universite.getNomUniversite()).isEqualTo(specialName);
        assertThat(universite.getAdresse()).isEqualTo(specialAddress);
    }

    @Test
    @DisplayName("Should test universite foyer relationship")
    void foyerRelationship() {
        Universite universite = new Universite();
        Foyer foyer = new Foyer();
        foyer.setIdFoyer(1L);
        foyer.setNomFoyer("Foyer A");
        universite.setFoyer(foyer);
        assertThat(universite.getFoyer()).isEqualTo(foyer);
        assertThat(universite.getFoyer().getIdFoyer()).isEqualTo(1L);
        assertThat(universite.getFoyer().getNomFoyer()).isEqualTo("Foyer A");
    }

    @Test
    @DisplayName("Should test universite with zero id")
    void withZeroId() {
        Universite universite = new Universite();
        universite.setIdUniversite(0L);
        assertThat(universite.getIdUniversite()).isEqualTo(0L);
    }

    @Test
    @DisplayName("Should test universite with negative id")
    void withNegativeId() {
        Universite universite = new Universite();
        universite.setIdUniversite(-1L);
        assertThat(universite.getIdUniversite()).isEqualTo(-1L);
    }
} 