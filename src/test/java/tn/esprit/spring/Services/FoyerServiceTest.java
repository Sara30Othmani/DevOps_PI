package tn.esprit.spring.Services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.DAO.Entities.Bloc;
import tn.esprit.spring.DAO.Entities.Foyer;
import tn.esprit.spring.DAO.Entities.Universite;
import tn.esprit.spring.DAO.Repositories.BlocRepository;
import tn.esprit.spring.DAO.Repositories.FoyerRepository;
import tn.esprit.spring.DAO.Repositories.UniversiteRepository;
import tn.esprit.spring.Services.Foyer.FoyerService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FoyerServiceTest {

    @Mock
    private FoyerRepository foyerRepository;

    @Mock
    private UniversiteRepository universiteRepository;

    @Mock
    private BlocRepository blocRepository;

    @InjectMocks
    private FoyerService foyerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddOrUpdate() {
        Foyer foyer = new Foyer();
        foyer.setIdFoyer(1L);
        foyer.setNomFoyer("Foyer A");
        foyer.setCapaciteFoyer(100L);

        when(foyerRepository.save(any(Foyer.class))).thenReturn(foyer);

        Foyer result = foyerService.addOrUpdate(foyer);

        assertNotNull(result);
        assertEquals(1L, result.getIdFoyer());
        assertEquals("Foyer A", result.getNomFoyer());
        assertEquals(100L, result.getCapaciteFoyer());
        verify(foyerRepository, times(1)).save(foyer);
    }

    @Test
    void testFindAll() {
        Foyer foyer1 = new Foyer();
        foyer1.setIdFoyer(1L);
        foyer1.setNomFoyer("Foyer A");

        Foyer foyer2 = new Foyer();
        foyer2.setIdFoyer(2L);
        foyer2.setNomFoyer("Foyer B");

        List<Foyer> foyerList = Arrays.asList(foyer1, foyer2);

        when(foyerRepository.findAll()).thenReturn(foyerList);

        List<Foyer> result = foyerService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(foyerRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        long foyerId = 1L;
        Foyer foyer = new Foyer();
        foyer.setIdFoyer(foyerId);
        foyer.setNomFoyer("Foyer A");
        foyer.setCapaciteFoyer(100L);

        when(foyerRepository.findById(foyerId)).thenReturn(Optional.of(foyer));

        Foyer result = foyerService.findById(foyerId);

        assertNotNull(result);
        assertEquals(foyerId, result.getIdFoyer());
        assertEquals("Foyer A", result.getNomFoyer());
        verify(foyerRepository, times(1)).findById(foyerId);
    }

    @Test
    void testDeleteById() {
        long foyerId = 1L;
        doNothing().when(foyerRepository).deleteById(foyerId);

        foyerService.deleteById(foyerId);

        verify(foyerRepository, times(1)).deleteById(foyerId);
    }

    @Test
    void testDelete() {
        Foyer foyer = new Foyer();
        foyer.setIdFoyer(1L);
        foyer.setNomFoyer("Foyer A");

        doNothing().when(foyerRepository).delete(foyer);

        foyerService.delete(foyer);

        verify(foyerRepository, times(1)).delete(foyer);
    }

    @Test
    void testAffecterFoyerAUniversiteByNom() {
        long idFoyer = 1L;
        String nomUniversite = "Universite A";

        Foyer foyer = new Foyer();
        foyer.setIdFoyer(idFoyer);
        foyer.setNomFoyer("Foyer A");

        Universite universite = new Universite();
        universite.setIdUniversite(1L);
        universite.setNomUniversite(nomUniversite);

        when(foyerRepository.findById(idFoyer)).thenReturn(Optional.of(foyer));
        when(universiteRepository.findByNomUniversite(nomUniversite)).thenReturn(universite);
        when(universiteRepository.save(any(Universite.class))).thenReturn(universite);

        Universite result = foyerService.affecterFoyerAUniversite(idFoyer, nomUniversite);

        assertNotNull(result);
        assertEquals(foyer, result.getFoyer());
        verify(foyerRepository, times(1)).findById(idFoyer);
        verify(universiteRepository, times(1)).findByNomUniversite(nomUniversite);
        verify(universiteRepository, times(1)).save(universite);
    }

    @Test
    void testAjouterFoyerEtAffecterAUniversite() {
        long idUniversite = 1L;
        Foyer foyer = new Foyer();
        foyer.setIdFoyer(1L);
        foyer.setNomFoyer("Foyer A");

        Bloc bloc1 = new Bloc();
        bloc1.setIdBloc(1L);
        bloc1.setNomBloc("Bloc A");

        Bloc bloc2 = new Bloc();
        bloc2.setIdBloc(2L);
        bloc2.setNomBloc("Bloc B");

        List<Bloc> blocs = Arrays.asList(bloc1, bloc2);
        foyer.setBlocs(blocs);

        Universite universite = new Universite();
        universite.setIdUniversite(idUniversite);
        universite.setNomUniversite("Universite A");

        when(foyerRepository.save(any(Foyer.class))).thenReturn(foyer);
        when(universiteRepository.findById(idUniversite)).thenReturn(Optional.of(universite));
        when(blocRepository.save(any(Bloc.class))).thenReturn(bloc1, bloc2);
        when(universiteRepository.save(any(Universite.class))).thenReturn(universite);

        Foyer result = foyerService.ajouterFoyerEtAffecterAUniversite(foyer, idUniversite);

        assertNotNull(result);
        verify(foyerRepository, times(1)).save(foyer);
        verify(universiteRepository, times(1)).findById(idUniversite);
        verify(blocRepository, times(2)).save(any(Bloc.class));
        verify(universiteRepository, times(1)).save(universite);
    }

    @Test
    void testAjoutFoyerEtBlocs() {
        Foyer foyer = new Foyer();
        foyer.setIdFoyer(1L);
        foyer.setNomFoyer("Foyer A");

        Bloc bloc1 = new Bloc();
        bloc1.setIdBloc(1L);
        bloc1.setNomBloc("Bloc A");

        Bloc bloc2 = new Bloc();
        bloc2.setIdBloc(2L);
        bloc2.setNomBloc("Bloc B");

        List<Bloc> blocs = Arrays.asList(bloc1, bloc2);
        foyer.setBlocs(blocs);

        when(foyerRepository.save(any(Foyer.class))).thenReturn(foyer);
        when(blocRepository.save(any(Bloc.class))).thenReturn(bloc1, bloc2);

        Foyer result = foyerService.ajoutFoyerEtBlocs(foyer);

        assertNotNull(result);
        assertEquals(1L, result.getIdFoyer());
        assertEquals("Foyer A", result.getNomFoyer());
        verify(foyerRepository, times(1)).save(foyer);
        verify(blocRepository, times(2)).save(any(Bloc.class));
    }

    @Test
    void testAffecterFoyerAUniversiteByIds() {
        long idFoyer = 1L;
        long idUniversite = 1L;

        Foyer foyer = new Foyer();
        foyer.setIdFoyer(idFoyer);
        foyer.setNomFoyer("Foyer A");

        Universite universite = new Universite();
        universite.setIdUniversite(idUniversite);
        universite.setNomUniversite("Universite A");

        when(foyerRepository.findById(idFoyer)).thenReturn(Optional.of(foyer));
        when(universiteRepository.findById(idUniversite)).thenReturn(Optional.of(universite));
        when(universiteRepository.save(any(Universite.class))).thenReturn(universite);

        Universite result = foyerService.affecterFoyerAUniversite(idFoyer, idUniversite);

        assertNotNull(result);
        assertEquals(foyer, result.getFoyer());
        verify(foyerRepository, times(1)).findById(idFoyer);
        verify(universiteRepository, times(1)).findById(idUniversite);
        verify(universiteRepository, times(1)).save(universite);
    }

    @Test
    void testDesaffecterFoyerAUniversite() {
        long idUniversite = 1L;

        Universite universite = new Universite();
        universite.setIdUniversite(idUniversite);
        universite.setNomUniversite("Universite A");
        universite.setFoyer(new Foyer());

        when(universiteRepository.findById(idUniversite)).thenReturn(Optional.of(universite));
        when(universiteRepository.save(any(Universite.class))).thenReturn(universite);

        Universite result = foyerService.desaffecterFoyerAUniversite(idUniversite);

        assertNotNull(result);
        assertNull(result.getFoyer());
        verify(universiteRepository, times(1)).findById(idUniversite);
        verify(universiteRepository, times(1)).save(universite);
    }

    @Test
    void testAjouterFoyerEtAffecterAUniversiteWithEmptyBlocs() {
        long idUniversite = 1L;
        Foyer foyer = new Foyer();
        foyer.setIdFoyer(1L);
        foyer.setNomFoyer("Foyer A");
        foyer.setBlocs(null); // Test with null blocs

        Universite universite = new Universite();
        universite.setIdUniversite(idUniversite);
        universite.setNomUniversite("Universite A");

        when(foyerRepository.save(any(Foyer.class))).thenReturn(foyer);
        when(universiteRepository.findById(idUniversite)).thenReturn(Optional.of(universite));
        when(universiteRepository.save(any(Universite.class))).thenReturn(universite);

        Foyer result = foyerService.ajouterFoyerEtAffecterAUniversite(foyer, idUniversite);

        assertNotNull(result);
        verify(foyerRepository, times(1)).save(foyer);
        verify(universiteRepository, times(1)).findById(idUniversite);
        verify(universiteRepository, times(1)).save(universite);
        // Should not call blocRepository.save when blocs is null
        verify(blocRepository, never()).save(any(Bloc.class));
    }

    @Test
    void testAjoutFoyerEtBlocsWithEmptyBlocs() {
        Foyer foyer = new Foyer();
        foyer.setIdFoyer(1L);
        foyer.setNomFoyer("Foyer A");
        foyer.setBlocs(null); // Test with null blocs

        when(foyerRepository.save(any(Foyer.class))).thenReturn(foyer);

        Foyer result = foyerService.ajoutFoyerEtBlocs(foyer);

        assertNotNull(result);
        assertEquals(1L, result.getIdFoyer());
        assertEquals("Foyer A", result.getNomFoyer());
        verify(foyerRepository, times(1)).save(foyer);
        // Should not call blocRepository.save when blocs is null
        verify(blocRepository, never()).save(any(Bloc.class));
    }
} 