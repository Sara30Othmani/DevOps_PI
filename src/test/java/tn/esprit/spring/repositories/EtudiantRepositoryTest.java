package tn.esprit.spring.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import tn.esprit.spring.DAO.Entities.Etudiant;
import tn.esprit.spring.DAO.Repositories.EtudiantRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class EtudiantRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EtudiantRepository etudiantRepository;

    private Etudiant etudiant1;
    private Etudiant etudiant2;
    private Etudiant etudiant3;

    @BeforeEach
    void setUp() {
        etudiant1 = Etudiant.builder()
                .nomEt("Dupont")
                .prenomEt("Jean")
                .cin(12345678L)
                .ecole("ESPRIT")
                .dateNaissance(LocalDate.of(2000, 5, 15))
                .build();
        entityManager.persistAndFlush(etudiant1);

        etudiant2 = Etudiant.builder()
                .nomEt("Martin")
                .prenomEt("Marie")
                .cin(87654321L)
                .ecole("ESPRIT")
                .dateNaissance(LocalDate.of(2001, 8, 20))
                .build();
        entityManager.persistAndFlush(etudiant2);

        etudiant3 = Etudiant.builder()
                .nomEt("Dupont")
                .prenomEt("Pierre")
                .cin(11223344L)
                .ecole("INSAT")
                .dateNaissance(LocalDate.of(1999, 3, 10))
                .build();
        entityManager.persistAndFlush(etudiant3);
    }

    @Test
    @DisplayName("Should find etudiant by ID")
    void findById() {
        Optional<Etudiant> found = etudiantRepository.findById(etudiant1.getIdEtudiant());
        assertThat(found).isPresent();
        assertThat(found.get().getNomEt()).isEqualTo("Dupont");
    }

    @Test
    @DisplayName("Should save new etudiant")
    void save() {
        Etudiant newEtudiant = Etudiant.builder()
                .nomEt("Nouveau")
                .prenomEt("Etudiant")
                .cin(99999999L)
                .ecole("ESPRIT")
                .dateNaissance(LocalDate.of(2002, 1, 1))
                .build();

        Etudiant saved = etudiantRepository.save(newEtudiant);
        assertThat(saved.getIdEtudiant()).isNotNull();
        assertThat(saved.getNomEt()).isEqualTo("Nouveau");
    }

    @Test
    @DisplayName("Should find etudiants by nom")
    void findByNomEt() {
        List<Etudiant> found = etudiantRepository.findByNomEt("Dupont");
        assertThat(found).hasSize(2);
        assertThat(found.get(0).getNomEt()).isEqualTo("Dupont");
    }

    @Test
    @DisplayName("Should find etudiants by nom using JPQL")
    void selectJPQL() {
        List<Etudiant> found = etudiantRepository.selectJPQL("Dupont");
        assertThat(found).hasSize(2);
        assertThat(found.get(0).getNomEt()).isEqualTo("Dupont");
    }

    @Test
    @DisplayName("Should find etudiants by nom using SQL")
    void selectSQL() {
        List<Etudiant> found = etudiantRepository.selectSQL("Dupont");
        assertThat(found).hasSize(2);
        assertThat(found.get(0).getNomEt()).isEqualTo("Dupont");
    }

    @Test
    @DisplayName("Should find etudiant by nom and prenom")
    void findByNomEtAndPrenomEt() {
        List<Etudiant> found = etudiantRepository.findByNomEtAndPrenomEt("Dupont", "Jean");
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getNomEt()).isEqualTo("Dupont");
        assertThat(found.get(0).getPrenomEt()).isEqualTo("Jean");
    }

    @Test
    @DisplayName("Should find etudiant by nom and prenom using JPQL")
    void select2JPQL() {
        List<Etudiant> found = etudiantRepository.select2JPQL("Dupont", "Jean");
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getNomEt()).isEqualTo("Dupont");
        assertThat(found.get(0).getPrenomEt()).isEqualTo("Jean");
    }

    @Test
    @DisplayName("Should find etudiant by nom and prenom using SQL")
    void select2SQL() {
        List<Etudiant> found = etudiantRepository.select2SQL("Jean", "Dupont");
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getNomEt()).isEqualTo("Dupont");
        assertThat(found.get(0).getPrenomEt()).isEqualTo("Jean");
    }

    @Test
    @DisplayName("Should get etudiant by nom and prenom")
    void getByNomEtAndPrenomEt() {
        Etudiant found = etudiantRepository.getByNomEtAndPrenomEt("Dupont", "Jean");
        assertThat(found).isNotNull();
        assertThat(found.getNomEt()).isEqualTo("Dupont");
        assertThat(found.getPrenomEt()).isEqualTo("Jean");
    }

    @Test
    @DisplayName("Should find etudiants by nom or prenom")
    void findByNomEtOrPrenomEt() {
        List<Etudiant> found = etudiantRepository.findByNomEtOrPrenomEt("Dupont", "Marie");
        assertThat(found).hasSize(3); // Dupont (2) + Marie (1)
    }

    @Test
    @DisplayName("Should find etudiants with id greater than")
    void findByIdEtudiantGreaterThan() {
        List<Etudiant> found = etudiantRepository.findByIdEtudiantGreaterThan(etudiant1.getIdEtudiant());
        assertThat(found).hasSize(2);
    }

    @Test
    @DisplayName("Should find etudiants with id less than")
    void findByIdEtudiantLessThan() {
        List<Etudiant> found = etudiantRepository.findByIdEtudiantLessThan(etudiant3.getIdEtudiant());
        assertThat(found).hasSize(2);
    }

    @Test
    @DisplayName("Should find etudiants by date naissance between")
    void getByDateNaissanceBetween() {
        LocalDate start = LocalDate.of(2000, 1, 1);
        LocalDate end = LocalDate.of(2001, 12, 31);
        List<Etudiant> found = etudiantRepository.getByDateNaissanceBetween(start, end);
        assertThat(found).hasSize(2);
    }

    @Test
    @DisplayName("Should find etudiants by nom like")
    void getByNomEtLike() {
        List<Etudiant> found = etudiantRepository.getByNomEtLike("%Dup%");
        assertThat(found).hasSize(2);
    }

    @Test
    @DisplayName("Should find etudiants by nom containing")
    void getByNomEtContaining() {
        List<Etudiant> found = etudiantRepository.getByNomEtContaining("Dup");
        assertThat(found).hasSize(2);
    }

    @Test
    @DisplayName("Should find etudiants by nom contains")
    void getByNomEtContains() {
        List<Etudiant> found = etudiantRepository.getByNomEtContains("Dup");
        assertThat(found).hasSize(2);
    }

    @Test
    @DisplayName("Should find etudiants by nom ignore case")
    void getByNomEtIgnoreCase() {
        List<Etudiant> found = etudiantRepository.getByNomEtIgnoreCase("dupont");
        assertThat(found).hasSize(2);
    }

    @Test
    @DisplayName("Should find etudiants with nom not null")
    void getByNomEtNotNull() {
        List<Etudiant> found = etudiantRepository.getByNomEtNotNull();
        assertThat(found).hasSize(3);
    }

    @Test
    @DisplayName("Should find etudiant by cin")
    void findByCin() {
        Etudiant found = etudiantRepository.findByCin(12345678L);
        assertThat(found).isNotNull();
        assertThat(found.getCin()).isEqualTo(12345678L);
    }

    @Test
    @DisplayName("Should find etudiants by nom like (alternative method)")
    void findByNomEtLike() {
        List<Etudiant> found = etudiantRepository.findByNomEtLike("%Dup%");
        assertThat(found).hasSize(2);
    }

    @Test
    @DisplayName("Should find etudiants by nom contains (alternative method)")
    void findByNomEtContains() {
        List<Etudiant> found = etudiantRepository.findByNomEtContains("Dup");
        assertThat(found).hasSize(2);
    }

    @Test
    @DisplayName("Should find etudiants by nom containing (alternative method)")
    void findByNomEtContaining() {
        List<Etudiant> found = etudiantRepository.findByNomEtContaining("Dup");
        assertThat(found).hasSize(2);
    }

    @Test
    @DisplayName("Should find all etudiants")
    void findAll() {
        List<Etudiant> allEtudiants = etudiantRepository.findAll();
        assertThat(allEtudiants).hasSize(3);
    }

    @Test
    @DisplayName("Should delete etudiant by id")
    void deleteById() {
        etudiantRepository.deleteById(etudiant1.getIdEtudiant());
        Optional<Etudiant> found = etudiantRepository.findById(etudiant1.getIdEtudiant());
        assertThat(found).isEmpty();
    }
} 