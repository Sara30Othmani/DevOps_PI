package tn.esprit.spring.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tn.esprit.spring.DAO.Entities.Bloc;
import tn.esprit.spring.DAO.Entities.Foyer;
import tn.esprit.spring.DAO.Entities.Universite;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FoyerTest {

    @Test
    @DisplayName("Should create foyer with no-args constructor")
    void noArgsConstructor() {
        Foyer foyer = new Foyer();
        assertThat(foyer).isNotNull();
        assertThat(foyer.getIdFoyer()).isEqualTo(0);
        assertThat(foyer.getNomFoyer()).isNull();
        assertThat(foyer.getCapaciteFoyer()).isEqualTo(0);
        assertThat(foyer.getUniversite()).isNull();
        assertThat(foyer.getBlocs()).isNotNull();
    }

    @Test
    @DisplayName("Should create foyer with all-args constructor")
    void allArgsConstructor() {
        Universite universite = new Universite();
        List<Bloc> blocs = new ArrayList<>();
        
        Foyer foyer = new Foyer(1L, "Foyer A", 100L, universite, blocs);
        
        assertThat(foyer.getIdFoyer()).isEqualTo(1L);
        assertThat(foyer.getNomFoyer()).isEqualTo("Foyer A");
        assertThat(foyer.getCapaciteFoyer()).isEqualTo(100L);
        assertThat(foyer.getUniversite()).isEqualTo(universite);
        assertThat(foyer.getBlocs()).isEqualTo(blocs);
    }

    @Test
    @DisplayName("Should set and get foyer properties")
    void settersAndGetters() {
        Foyer foyer = new Foyer();
        Universite universite = new Universite();
        List<Bloc> blocs = new ArrayList<>();
        
        foyer.setIdFoyer(1L);
        foyer.setNomFoyer("Foyer A");
        foyer.setCapaciteFoyer(100L);
        foyer.setUniversite(universite);
        foyer.setBlocs(blocs);
        
        assertThat(foyer.getIdFoyer()).isEqualTo(1L);
        assertThat(foyer.getNomFoyer()).isEqualTo("Foyer A");
        assertThat(foyer.getCapaciteFoyer()).isEqualTo(100L);
        assertThat(foyer.getUniversite()).isEqualTo(universite);
        assertThat(foyer.getBlocs()).isEqualTo(blocs);
    }

    @Test
    @DisplayName("Should add bloc to foyer")
    void addBloc() {
        Foyer foyer = new Foyer();
        Bloc bloc = new Bloc();
        
        foyer.getBlocs().add(bloc);
        
        assertThat(foyer.getBlocs()).hasSize(1);
        assertThat(foyer.getBlocs().get(0)).isEqualTo(bloc);
    }

    @Test
    @DisplayName("Should remove bloc from foyer")
    void removeBloc() {
        Foyer foyer = new Foyer();
        Bloc bloc = new Bloc();
        
        foyer.getBlocs().add(bloc);
        assertThat(foyer.getBlocs()).hasSize(1);
        
        foyer.getBlocs().remove(bloc);
        assertThat(foyer.getBlocs()).isEmpty();
    }

    @Test
    @DisplayName("Should test foyer equality")
    void equals() {
        Foyer foyer1 = new Foyer(1L, "Foyer A", 100L, null, new ArrayList<>());
        Foyer foyer2 = new Foyer(1L, "Foyer A", 100L, null, new ArrayList<>());
        
        // Lombok's default equals uses object identity, so these should not be equal
        assertThat(foyer1).isNotEqualTo(foyer2);
        
        // Same object should be equal to itself
        assertThat(foyer1).isEqualTo(foyer1);
    }

    @Test
    @DisplayName("Should test foyer hash code")
    void hashCodeTest() {
        Foyer foyer1 = new Foyer(1L, "Foyer A", 100L, null, new ArrayList<>());
        Foyer foyer2 = new Foyer(1L, "Foyer A", 100L, null, new ArrayList<>());
        
        // Lombok's default hashCode uses object identity, so these should have different hash codes
        assertThat(foyer1.hashCode()).isNotEqualTo(foyer2.hashCode());
        
        // Same object should have same hash code
        assertThat(foyer1.hashCode()).isEqualTo(foyer1.hashCode());
    }

    @Test
    @DisplayName("Should test foyer toString")
    void toStringTest() {
        Foyer foyer = new Foyer(1L, "Foyer A", 100L, null, new ArrayList<>());
        
        String toString = foyer.toString();
        assertThat(toString).contains("Foyer");
        // Lombok's default toString includes class name and hash code, not field values
        assertThat(toString).contains("@");
    }

    @Test
    @DisplayName("Should test foyer with null values")
    void withNullValues() {
        Foyer foyer = new Foyer(1L, null, 100L, null, new ArrayList<>());
        
        assertThat(foyer).isNotNull();
        assertThat(foyer.getIdFoyer()).isEqualTo(1L);
        assertThat(foyer.getNomFoyer()).isNull();
        assertThat(foyer.getCapaciteFoyer()).isEqualTo(100L);
        assertThat(foyer.getUniversite()).isNull();
        assertThat(foyer.getBlocs()).isNotNull();
    }

    @Test
    @DisplayName("Should test foyer with empty blocs list")
    void withEmptyBlocs() {
        Foyer foyer = new Foyer();
        assertThat(foyer.getBlocs()).isNotNull();
        assertThat(foyer.getBlocs()).isEmpty();
    }

    @Test
    @DisplayName("Should test foyer with multiple blocs")
    void withMultipleBlocs() {
        Foyer foyer = new Foyer();
        Bloc bloc1 = new Bloc();
        Bloc bloc2 = new Bloc();
        
        foyer.getBlocs().add(bloc1);
        foyer.getBlocs().add(bloc2);
        
        assertThat(foyer.getBlocs()).hasSize(2);
        assertThat(foyer.getBlocs()).contains(bloc1, bloc2);
    }

    @Test
    @DisplayName("Should test foyer universite relationship")
    void universiteRelationship() {
        Foyer foyer = new Foyer();
        Universite universite = new Universite();
        universite.setIdUniversite(1L);
        universite.setNomUniversite("Universite A");
        
        foyer.setUniversite(universite);
        
        assertThat(foyer.getUniversite()).isEqualTo(universite);
        assertThat(foyer.getUniversite().getIdUniversite()).isEqualTo(1L);
        assertThat(foyer.getUniversite().getNomUniversite()).isEqualTo("Universite A");
    }

    @Test
    @DisplayName("Should test foyer with large capacity")
    void withLargeCapacity() {
        Foyer foyer = new Foyer();
        foyer.setCapaciteFoyer(10000L);
        
        assertThat(foyer.getCapaciteFoyer()).isEqualTo(10000L);
    }

    @Test
    @DisplayName("Should test foyer with zero capacity")
    void withZeroCapacity() {
        Foyer foyer = new Foyer();
        foyer.setCapaciteFoyer(0L);
        
        assertThat(foyer.getCapaciteFoyer()).isEqualTo(0L);
    }

    @Test
    @DisplayName("Should test foyer with negative capacity")
    void withNegativeCapacity() {
        Foyer foyer = new Foyer();
        foyer.setCapaciteFoyer(-100L);
        
        assertThat(foyer.getCapaciteFoyer()).isEqualTo(-100L);
    }

    @Test
    @DisplayName("Should test foyer with long name")
    void withLongName() {
        Foyer foyer = new Foyer();
        String longName = "This is a very long foyer name that might exceed normal limits";
        foyer.setNomFoyer(longName);
        
        assertThat(foyer.getNomFoyer()).isEqualTo(longName);
    }

    @Test
    @DisplayName("Should test foyer with special characters in name")
    void withSpecialCharactersInName() {
        Foyer foyer = new Foyer();
        String specialName = "Foyer@#$%^&*()_+-=[]{}|;':\",./<>?";
        foyer.setNomFoyer(specialName);
        
        assertThat(foyer.getNomFoyer()).isEqualTo(specialName);
    }
} 