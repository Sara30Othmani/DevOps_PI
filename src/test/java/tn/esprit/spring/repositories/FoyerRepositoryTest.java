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
import tn.esprit.spring.DAO.Entities.Universite;
import tn.esprit.spring.DAO.Repositories.FoyerRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class FoyerRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private FoyerRepository foyerRepository;

    private Foyer foyer1;
    private Foyer foyer2;
    private Universite universite1;
    private Bloc bloc1;
    private Chambre chambre1;

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

        universite1 = Universite.builder()
                .nomUniversite("Universite Test")
                .adresse("Adresse Test")
                .foyer(foyer1)
                .build();
        entityManager.persistAndFlush(universite1);

        bloc1 = Bloc.builder()
                .nomBloc("Bloc A")
                .capaciteBloc(50L)
                .foyer(foyer1)
                .build();
        entityManager.persistAndFlush(bloc1);

        chambre1 = Chambre.builder()
                .numeroChambre(101L)
                .typeC(TypeChambre.SIMPLE)
                .bloc(bloc1)
                .build();
        entityManager.persistAndFlush(chambre1);
    }

    @Test
    @DisplayName("Should find foyer by ID")
    void findById() {
        Optional<Foyer> found = foyerRepository.findById(foyer1.getIdFoyer());
        assertThat(found).isPresent();
        assertThat(found.get().getNomFoyer()).isEqualTo("Foyer Test 1");
    }

    @Test
    @DisplayName("Should save new foyer")
    void save() {
        Foyer newFoyer = Foyer.builder()
                .nomFoyer("Foyer Test 3")
                .capaciteFoyer(200L)
                .build();

        Foyer saved = foyerRepository.save(newFoyer);
        assertThat(saved.getIdFoyer()).isNotNull();
        assertThat(saved.getNomFoyer()).isEqualTo("Foyer Test 3");
    }

    @Test
    @DisplayName("Should find foyer by nom")
    void findByNomFoyer() {
        Foyer found = foyerRepository.findByNomFoyer("Foyer Test 1");
        assertThat(found).isNotNull();
        assertThat(found.getNomFoyer()).isEqualTo("Foyer Test 1");
    }

    @Test
    @DisplayName("Should find foyers with capacite greater than")
    void findByCapaciteFoyerGreaterThan() {
        List<Foyer> found = foyerRepository.findByCapaciteFoyerGreaterThan(120);
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getCapaciteFoyer()).isEqualTo(150L);
    }

    @Test
    @DisplayName("Should find foyers with capacite less than")
    void findByCapaciteFoyerLessThan() {
        List<Foyer> found = foyerRepository.findByCapaciteFoyerLessThan(120);
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getCapaciteFoyer()).isEqualTo(100L);
    }

    @Test
    @DisplayName("Should find foyers with capacite between")
    void findByCapaciteFoyerBetween() {
        List<Foyer> found = foyerRepository.findByCapaciteFoyerBetween(80, 160);
        assertThat(found).hasSize(2);
    }

    @Test
    @DisplayName("Should find foyer by universite nom")
    void findByUniversiteNomUniversite() {
        Foyer found = foyerRepository.findByUniversiteNomUniversite("Universite Test");
        assertThat(found).isNotNull();
        assertThat(found.getNomFoyer()).isEqualTo("Foyer Test 1");
    }

    @Test
    @DisplayName("Should find foyers by bloc chambres type")
    void getByBlocsChambresTypeC() {
        List<Foyer> found = foyerRepository.getByBlocsChambresTypeC(TypeChambre.SIMPLE);
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getNomFoyer()).isEqualTo("Foyer Test 1");
    }

    @Test
    @DisplayName("Should find foyers by bloc nom using JPQL")
    void find() {
        List<Foyer> found = foyerRepository.find("Bloc A");
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getNomFoyer()).isEqualTo("Foyer Test 1");
    }

    @Test
    @DisplayName("Should find all foyers")
    void findAll() {
        List<Foyer> allFoyers = foyerRepository.findAll();
        assertThat(allFoyers).hasSize(2);
    }

    @Test
    @DisplayName("Should delete foyer by id")
    void deleteById() {
        foyerRepository.deleteById(foyer1.getIdFoyer());
        Optional<Foyer> found = foyerRepository.findById(foyer1.getIdFoyer());
        assertThat(found).isEmpty();
    }
} 