package tn.esprit.spring.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tn.esprit.spring.DAO.Entities.Bloc;
import tn.esprit.spring.DAO.Entities.Chambre;
import tn.esprit.spring.DAO.Entities.Foyer;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BlocTest {

    @Test
    @DisplayName("Should create bloc with no-args constructor")
    void noArgsConstructor() {
        Bloc bloc = new Bloc();
        assertThat(bloc).isNotNull();
        assertThat(bloc.getIdBloc()).isEqualTo(0);
        assertThat(bloc.getNomBloc()).isNull();
        assertThat(bloc.getCapaciteBloc()).isEqualTo(0);
        assertThat(bloc.getFoyer()).isNull();
        assertThat(bloc.getChambres()).isNotNull();
    }

    @Test
    @DisplayName("Should create bloc with all-args constructor")
    void allArgsConstructor() {
        Foyer foyer = new Foyer();
        List<Chambre> chambres = new ArrayList<>();
        
        Bloc bloc = new Bloc(1L, "Bloc A", 50L, foyer, chambres);
        
        assertThat(bloc.getIdBloc()).isEqualTo(1L);
        assertThat(bloc.getNomBloc()).isEqualTo("Bloc A");
        assertThat(bloc.getCapaciteBloc()).isEqualTo(50L);
        assertThat(bloc.getFoyer()).isEqualTo(foyer);
        assertThat(bloc.getChambres()).isEqualTo(chambres);
    }

    @Test
    @DisplayName("Should set and get bloc properties")
    void settersAndGetters() {
        Bloc bloc = new Bloc();
        Foyer foyer = new Foyer();
        List<Chambre> chambres = new ArrayList<>();
        
        bloc.setIdBloc(1L);
        bloc.setNomBloc("Bloc A");
        bloc.setCapaciteBloc(50L);
        bloc.setFoyer(foyer);
        bloc.setChambres(chambres);
        
        assertThat(bloc.getIdBloc()).isEqualTo(1L);
        assertThat(bloc.getNomBloc()).isEqualTo("Bloc A");
        assertThat(bloc.getCapaciteBloc()).isEqualTo(50L);
        assertThat(bloc.getFoyer()).isEqualTo(foyer);
        assertThat(bloc.getChambres()).isEqualTo(chambres);
    }

    @Test
    @DisplayName("Should add chambre to bloc")
    void addChambre() {
        Bloc bloc = new Bloc();
        Chambre chambre = new Chambre();
        
        bloc.getChambres().add(chambre);
        
        assertThat(bloc.getChambres()).hasSize(1);
        assertThat(bloc.getChambres().get(0)).isEqualTo(chambre);
    }

    @Test
    @DisplayName("Should remove chambre from bloc")
    void removeChambre() {
        Bloc bloc = new Bloc();
        Chambre chambre = new Chambre();
        
        bloc.getChambres().add(chambre);
        assertThat(bloc.getChambres()).hasSize(1);
        
        bloc.getChambres().remove(chambre);
        assertThat(bloc.getChambres()).isEmpty();
    }

    @Test
    @DisplayName("Should test bloc equality")
    void equals() {
        Bloc bloc1 = new Bloc(1L, "Bloc A", 50L, null, new ArrayList<>());
        Bloc bloc2 = new Bloc(1L, "Bloc A", 50L, null, new ArrayList<>());
        
        // Lombok's default equals uses object identity, so these should not be equal
        assertThat(bloc1).isNotEqualTo(bloc2);
        
        // Same object should be equal to itself
        assertThat(bloc1).isEqualTo(bloc1);
    }

    @Test
    @DisplayName("Should test bloc hash code")
    void hashCodeTest() {
        Bloc bloc1 = new Bloc(1L, "Bloc A", 50L, null, new ArrayList<>());
        Bloc bloc2 = new Bloc(1L, "Bloc A", 50L, null, new ArrayList<>());
        
        // Lombok's default hashCode uses object identity, so these should have different hash codes
        assertThat(bloc1.hashCode()).isNotEqualTo(bloc2.hashCode());
        
        // Same object should have same hash code
        assertThat(bloc1.hashCode()).isEqualTo(bloc1.hashCode());
    }

    @Test
    @DisplayName("Should test bloc toString")
    void toStringTest() {
        Bloc bloc = new Bloc(1L, "Bloc A", 50L, null, new ArrayList<>());
        
        String toString = bloc.toString();
        assertThat(toString).contains("Bloc");
        // Lombok's default toString includes class name and hash code, not field values
        assertThat(toString).contains("@");
    }

    @Test
    @DisplayName("Should test bloc with null values")
    void withNullValues() {
        Bloc bloc = new Bloc(1L, null, 50L, null, new ArrayList<>());
        
        assertThat(bloc).isNotNull();
        assertThat(bloc.getIdBloc()).isEqualTo(1L);
        assertThat(bloc.getNomBloc()).isNull();
        assertThat(bloc.getCapaciteBloc()).isEqualTo(50L);
        assertThat(bloc.getFoyer()).isNull();
        assertThat(bloc.getChambres()).isNotNull();
    }

    @Test
    @DisplayName("Should test bloc with empty chambres list")
    void withEmptyChambres() {
        Bloc bloc = new Bloc();
        assertThat(bloc.getChambres()).isNotNull();
        assertThat(bloc.getChambres()).isEmpty();
    }

    @Test
    @DisplayName("Should test bloc with multiple chambres")
    void withMultipleChambres() {
        Bloc bloc = new Bloc();
        Chambre chambre1 = new Chambre();
        Chambre chambre2 = new Chambre();
        
        bloc.getChambres().add(chambre1);
        bloc.getChambres().add(chambre2);
        
        assertThat(bloc.getChambres()).hasSize(2);
        assertThat(bloc.getChambres()).contains(chambre1, chambre2);
    }
} 