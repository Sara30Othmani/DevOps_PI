package tn.esprit.spring.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import tn.esprit.spring.DAO.Entities.Bloc;
import tn.esprit.spring.DAO.Entities.Chambre;
import tn.esprit.spring.DAO.Entities.Foyer;
import tn.esprit.spring.DAO.Entities.TypeChambre;
import tn.esprit.spring.DAO.Repositories.BlocRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class BlocRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BlocRepository blocRepository;

    private Bloc bloc1;
    private Bloc bloc2;
    private Foyer foyer1;
    private Foyer foyer2;

    @BeforeEach
    void setUp() {
        // Create test data
        foyer1 = Foyer.builder()
                .nomFoyer("Foyer Test 1")
                .capaciteFoyer(100L)
                .build();
        entityManager.persistAndFlush(foyer1);

        foyer2 = Foyer.builder()
                .nomFoyer("Foyer Test 2")
                .capaciteFoyer(150L)
                .build();
        entityManager.persistAndFlush(foyer2);

        bloc1 = Bloc.builder()
                .nomBloc("Bloc A")
                .capaciteBloc(50L)
                .foyer(foyer1)
                .build();
        entityManager.persistAndFlush(bloc1);

        bloc2 = Bloc.builder()
                .nomBloc("Bloc B")
                .capaciteBloc(75L)
                .foyer(foyer2)
                .build();
        entityManager.persistAndFlush(bloc2);
    }

    @Test
    @DisplayName("Should find bloc by ID")
    void findById() {
        Optional<Bloc> found = blocRepository.findById(bloc1.getIdBloc());
        assertThat(found).isPresent();
        assertThat(found.get().getNomBloc()).isEqualTo("Bloc A");
    }

    @Test
    @DisplayName("Should save new bloc")
    void save() {
        Bloc newBloc = Bloc.builder()
                .nomBloc("Bloc C")
                .capaciteBloc(60L)
                .foyer(foyer1)
                .build();

        Bloc saved = blocRepository.save(newBloc);
        assertThat(saved.getIdBloc()).isNotNull();
        assertThat(saved.getNomBloc()).isEqualTo("Bloc C");
    }

    @Test
    @DisplayName("Should find bloc by nom using JPQL1")
    void selectByNomBJPQL1() {
        Bloc found = blocRepository.selectByNomBJPQL1("Bloc A");
        assertThat(found).isNotNull();
        assertThat(found.getNomBloc()).isEqualTo("Bloc A");
    }

    @Test
    @DisplayName("Should find bloc by nom using JPQL2")
    void selectByNomBJPQL2() {
        Bloc found = blocRepository.selectByNomBJPQL2("Bloc A");
        assertThat(found).isNotNull();
        assertThat(found.getNomBloc()).isEqualTo("Bloc A");
    }

    @Test
    @DisplayName("Should find bloc by nom using SQL1")
    void selectByNomBSQL1() {
        Bloc found = blocRepository.selectByNomBSQL1("Bloc A");
        assertThat(found).isNotNull();
        assertThat(found.getNomBloc()).isEqualTo("Bloc A");
    }

    @Test
    @DisplayName("Should find bloc by nom using SQL2")
    void selectByNomBSQL2() {
        Bloc found = blocRepository.selectByNomBSQL2("Bloc A");
        assertThat(found).isNotNull();
        assertThat(found.getNomBloc()).isEqualTo("Bloc A");
    }

    @Test
    @DisplayName("Should find blocs by type chambre")
    void selectBlocsByTypeChambreJPQL() {
        // Create a chambre with type for bloc1
        Chambre chambre = Chambre.builder()
                .numeroChambre(101L)
                .typeC(TypeChambre.SIMPLE)
                .bloc(bloc1)
                .build();
        entityManager.persistAndFlush(chambre);

        List<Bloc> found = blocRepository.selectBlocsByTypeChambreJPQL(TypeChambre.SIMPLE);
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getNomBloc()).isEqualTo("Bloc A");
    }

    @Test
    @DisplayName("Should find bloc by nom using getByNomBloc")
    void getByNomBloc() {
        List<Bloc> found = blocRepository.getByNomBloc("Bloc A");
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getNomBloc()).isEqualTo("Bloc A");
    }

    @Test
    @DisplayName("Should find bloc by nom using findByNomBloc")
    void findByNomBloc() {
        Bloc found = blocRepository.findByNomBloc("Bloc A");
        assertThat(found).isNotNull();
        assertThat(found.getNomBloc()).isEqualTo("Bloc A");
    }

    @Test
    @DisplayName("Should find blocs by capacite using getByCapaciteBloc")
    void getByCapaciteBloc() {
        List<Bloc> found = blocRepository.getByCapaciteBloc(50L);
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getCapaciteBloc()).isEqualTo(50L);
    }

    @Test
    @DisplayName("Should find bloc by capacite using findByCapaciteBloc")
    void findByCapaciteBloc() {
        Bloc found = blocRepository.findByCapaciteBloc(50L);
        assertThat(found).isNotNull();
        assertThat(found.getCapaciteBloc()).isEqualTo(50L);
    }

    @Test
    @DisplayName("Should find blocs by nom and capacite")
    void getByNomBlocAndCapaciteBloc() {
        List<Bloc> found = blocRepository.getByNomBlocAndCapaciteBloc("Bloc A", 50L);
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getNomBloc()).isEqualTo("Bloc A");
        assertThat(found.get(0).getCapaciteBloc()).isEqualTo(50L);
    }

    @Test
    @DisplayName("Should find blocs by nom ignoring case")
    void findByNomBlocIgnoreCase() {
        List<Bloc> found = blocRepository.findByNomBlocIgnoreCase("bloc a");
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getNomBloc()).isEqualTo("Bloc A");
    }

    @Test
    @DisplayName("Should find blocs with capacite greater than")
    void findByCapaciteBlocGreaterThan() {
        List<Bloc> found = blocRepository.findByCapaciteBlocGreaterThan(60L);
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getCapaciteBloc()).isEqualTo(75L);
    }

    @Test
    @DisplayName("Should find blocs by nom like")
    void findByNomBlocLike() {
        List<Bloc> found = blocRepository.findByNomBlocLike("%A%");
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getNomBloc()).isEqualTo("Bloc A");
    }

    @Test
    @DisplayName("Should find blocs by nom or capacite")
    void getByNomBlocOrCapaciteBloc() {
        List<Bloc> found = blocRepository.getByNomBlocOrCapaciteBloc("Bloc A", 75L);
        assertThat(found).hasSize(2);
    }

    @Test
    @DisplayName("Should find blocs by foyer id")
    void findByFoyerIdFoyer() {
        List<Bloc> found = blocRepository.findByFoyerIdFoyer(foyer1.getIdFoyer());
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getFoyer().getIdFoyer()).isEqualTo(foyer1.getIdFoyer());
    }

    @Test
    @DisplayName("Should find blocs by foyer id using JPQL")
    void findByFoyerIdFoyerJPQL() {
        List<Bloc> found = blocRepository.findByFoyerIdFoyerJPQL(foyer1.getIdFoyer());
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getFoyer().getIdFoyer()).isEqualTo(foyer1.getIdFoyer());
    }

    @Test
    @DisplayName("Should find all blocs")
    void findAll() {
        List<Bloc> allBlocs = blocRepository.findAll();
        assertThat(allBlocs).hasSize(2);
    }

    @Test
    @DisplayName("Should delete bloc by id")
    void deleteById() {
        blocRepository.deleteById(bloc1.getIdBloc());
        Optional<Bloc> found = blocRepository.findById(bloc1.getIdBloc());
        assertThat(found).isEmpty();
    }
} 