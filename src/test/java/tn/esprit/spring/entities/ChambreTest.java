package tn.esprit.spring.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tn.esprit.spring.DAO.Entities.Bloc;
import tn.esprit.spring.DAO.Entities.Chambre;
import tn.esprit.spring.DAO.Entities.Reservation;
import tn.esprit.spring.DAO.Entities.TypeChambre;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ChambreTest {

    @Test
    @DisplayName("Should create chambre with no-args constructor")
    void noArgsConstructor() {
        Chambre chambre = new Chambre();
        assertThat(chambre).isNotNull();
        assertThat(chambre.getIdChambre()).isEqualTo(0);
        assertThat(chambre.getNumeroChambre()).isEqualTo(0);
        assertThat(chambre.getTypeC()).isNull();
        assertThat(chambre.getBloc()).isNull();
    }

    @Test
    @DisplayName("Should create chambre with all-args constructor")
    void allArgsConstructor() {
        Bloc bloc = new Bloc();
        List<Reservation> reservations = new ArrayList<>();
        
        Chambre chambre = new Chambre(1L, 101L, TypeChambre.SIMPLE, bloc, reservations);
        
        assertThat(chambre.getIdChambre()).isEqualTo(1L);
        assertThat(chambre.getNumeroChambre()).isEqualTo(101L);
        assertThat(chambre.getTypeC()).isEqualTo(TypeChambre.SIMPLE);
        assertThat(chambre.getBloc()).isEqualTo(bloc);
        assertThat(chambre.getReservations()).isEqualTo(reservations);
    }

    @Test
    @DisplayName("Should set and get chambre properties")
    void settersAndGetters() {
        Chambre chambre = new Chambre();
        Bloc bloc = new Bloc();
        
        chambre.setIdChambre(1L);
        chambre.setNumeroChambre(101L);
        chambre.setTypeC(TypeChambre.DOUBLE);
        chambre.setBloc(bloc);
        
        assertThat(chambre.getIdChambre()).isEqualTo(1L);
        assertThat(chambre.getNumeroChambre()).isEqualTo(101L);
        assertThat(chambre.getTypeC()).isEqualTo(TypeChambre.DOUBLE);
        assertThat(chambre.getBloc()).isEqualTo(bloc);
    }

    @Test
    @DisplayName("Should test chambre with different room types")
    void withDifferentRoomTypes() {
        Chambre simpleChambre = new Chambre();
        simpleChambre.setTypeC(TypeChambre.SIMPLE);
        
        Chambre doubleChambre = new Chambre();
        doubleChambre.setTypeC(TypeChambre.DOUBLE);
        
        Chambre tripleChambre = new Chambre();
        tripleChambre.setTypeC(TypeChambre.TRIPLE);
        
        assertThat(simpleChambre.getTypeC()).isEqualTo(TypeChambre.SIMPLE);
        assertThat(doubleChambre.getTypeC()).isEqualTo(TypeChambre.DOUBLE);
        assertThat(tripleChambre.getTypeC()).isEqualTo(TypeChambre.TRIPLE);
    }

    @Test
    @DisplayName("Should test chambre equality")
    void equals() {
        Chambre chambre1 = new Chambre(1L, 101L, TypeChambre.SIMPLE, null, new ArrayList<>());
        Chambre chambre2 = new Chambre(1L, 101L, TypeChambre.SIMPLE, null, new ArrayList<>());
        
        // Lombok's default equals uses object identity, so these should not be equal
        assertThat(chambre1).isNotEqualTo(chambre2);
        
        // Same object should be equal to itself
        assertThat(chambre1).isEqualTo(chambre1);
    }

    @Test
    @DisplayName("Should test chambre hash code")
    void hashCodeTest() {
        Chambre chambre1 = new Chambre(1L, 101L, TypeChambre.SIMPLE, null, new ArrayList<>());
        Chambre chambre2 = new Chambre(1L, 101L, TypeChambre.SIMPLE, null, new ArrayList<>());
        
        // Lombok's default hashCode uses object identity, so these should have different hash codes
        assertThat(chambre1.hashCode()).isNotEqualTo(chambre2.hashCode());
        
        // Same object should have same hash code
        assertThat(chambre1.hashCode()).isEqualTo(chambre1.hashCode());
    }

    @Test
    @DisplayName("Should test chambre toString")
    void toStringTest() {
        Chambre chambre = new Chambre(1L, 101L, TypeChambre.SIMPLE, null, new ArrayList<>());
        
        String toString = chambre.toString();
        assertThat(toString).contains("Chambre");
        // Lombok's default toString includes class name and hash code, not field values
        assertThat(toString).contains("@");
    }

    @Test
    @DisplayName("Should test chambre with null values")
    void withNullValues() {
        Chambre chambre = new Chambre(1L, 101L, null, null, new ArrayList<>());
        
        assertThat(chambre.getIdChambre()).isEqualTo(1L);
        assertThat(chambre.getNumeroChambre()).isEqualTo(101L);
        assertThat(chambre.getTypeC()).isNull();
        assertThat(chambre.getBloc()).isNull();
    }

    @Test
    @DisplayName("Should test chambre with zero values")
    void withZeroValues() {
        Chambre chambre = new Chambre(0L, 0L, null, null, new ArrayList<>());
        
        assertThat(chambre.getIdChambre()).isEqualTo(0L);
        assertThat(chambre.getNumeroChambre()).isEqualTo(0L);
    }

    @Test
    @DisplayName("Should test chambre with negative values")
    void withNegativeValues() {
        Chambre chambre = new Chambre(-1L, -101L, null, null, new ArrayList<>());
        
        assertThat(chambre.getIdChambre()).isEqualTo(-1L);
        assertThat(chambre.getNumeroChambre()).isEqualTo(-101L);
    }

    @Test
    @DisplayName("Should test chambre bloc relationship")
    void blocRelationship() {
        Bloc bloc = new Bloc();
        bloc.setIdBloc(1L);
        bloc.setNomBloc("Bloc A");
        
        Chambre chambre = new Chambre();
        chambre.setIdChambre(1L);
        chambre.setNumeroChambre(101L);
        chambre.setBloc(bloc);
        
        assertThat(chambre.getBloc()).isEqualTo(bloc);
        assertThat(chambre.getBloc().getIdBloc()).isEqualTo(1L);
        assertThat(chambre.getBloc().getNomBloc()).isEqualTo("Bloc A");
    }

    @Test
    @DisplayName("Should test chambre reservations relationship")
    void reservationsRelationship() {
        Chambre chambre = new Chambre();
        List<Reservation> reservations = new ArrayList<>();
        
        Reservation reservation1 = new Reservation();
        Reservation reservation2 = new Reservation();
        
        reservations.add(reservation1);
        reservations.add(reservation2);
        
        chambre.setReservations(reservations);
        
        assertThat(chambre.getReservations()).hasSize(2);
        assertThat(chambre.getReservations()).contains(reservation1, reservation2);
    }

    @Test
    @DisplayName("Should test chambre with large room numbers")
    void withLargeRoomNumbers() {
        Chambre chambre = new Chambre();
        chambre.setNumeroChambre(9999L);
        
        assertThat(chambre.getNumeroChambre()).isEqualTo(9999L);
    }

    @Test
    @DisplayName("Should test chambre type enum values")
    void roomTypeEnumValues() {
        assertThat(TypeChambre.SIMPLE).isNotNull();
        assertThat(TypeChambre.DOUBLE).isNotNull();
        assertThat(TypeChambre.TRIPLE).isNotNull();
        
        assertThat(TypeChambre.values()).hasSize(3);
    }
} 