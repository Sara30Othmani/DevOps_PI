package tn.esprit.spring.Services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.DAO.Entities.Foyer;
import tn.esprit.spring.DAO.Entities.Universite;
import tn.esprit.spring.DAO.Repositories.UniversiteRepository;
import tn.esprit.spring.Services.Universite.UniversiteService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UniversiteServiceTest {

    @Mock
    private UniversiteRepository universiteRepository;

    @InjectMocks
    private UniversiteService universiteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddOrUpdate() {
        Universite universite = new Universite();
        universite.setIdUniversite(1L);
        universite.setNomUniversite("Uni A");

        when(universiteRepository.save(any(Universite.class))).thenReturn(universite);

        Universite result = universiteService.addOrUpdate(universite);

        assertNotNull(result);
        assertEquals(1L, result.getIdUniversite());
        assertEquals("Uni A", result.getNomUniversite());
        verify(universiteRepository, times(1)).save(universite);
    }

    @Test
    void testFindAll() {
        Universite u1 = new Universite();
        u1.setIdUniversite(1L);
        u1.setNomUniversite("Uni A");
        Universite u2 = new Universite();
        u2.setIdUniversite(2L);
        u2.setNomUniversite("Uni B");
        List<Universite> universites = Arrays.asList(u1, u2);

        when(universiteRepository.findAll()).thenReturn(universites);

        List<Universite> result = universiteService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(universiteRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        long id = 1L;
        Universite universite = new Universite();
        universite.setIdUniversite(id);
        universite.setNomUniversite("Uni A");

        when(universiteRepository.findById(id)).thenReturn(Optional.of(universite));

        Universite result = universiteService.findById(id);

        assertNotNull(result);
        assertEquals(id, result.getIdUniversite());
        assertEquals("Uni A", result.getNomUniversite());
        verify(universiteRepository, times(1)).findById(id);
    }

    @Test
    void testDeleteById() {
        long id = 1L;
        doNothing().when(universiteRepository).deleteById(id);

        universiteService.deleteById(id);

        verify(universiteRepository, times(1)).deleteById(id);
    }

    @Test
    void testDelete() {
        Universite universite = new Universite();
        universite.setIdUniversite(1L);
        universite.setNomUniversite("Uni A");

        doNothing().when(universiteRepository).delete(universite);

        universiteService.delete(universite);

        verify(universiteRepository, times(1)).delete(universite);
    }

    @Test
    void testAjouterUniversiteEtSonFoyer() {
        Universite universite = new Universite();
        universite.setIdUniversite(1L);
        universite.setNomUniversite("Uni A");
        Foyer foyer = new Foyer();
        foyer.setIdFoyer(10L);
        universite.setFoyer(foyer);

        when(universiteRepository.save(any(Universite.class))).thenReturn(universite);

        Universite result = universiteService.ajouterUniversiteEtSonFoyer(universite);

        assertNotNull(result);
        assertEquals(1L, result.getIdUniversite());
        assertEquals("Uni A", result.getNomUniversite());
        assertNotNull(result.getFoyer());
        assertEquals(10L, result.getFoyer().getIdFoyer());
        verify(universiteRepository, times(1)).save(universite);
    }
} 