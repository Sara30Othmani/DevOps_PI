package tn.esprit.spring.Services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.DAO.Entities.Bloc;
import tn.esprit.spring.DAO.Entities.Chambre;
import tn.esprit.spring.DAO.Entities.TypeChambre;
import tn.esprit.spring.DAO.Repositories.BlocRepository;
import tn.esprit.spring.DAO.Repositories.ChambreRepository;
import tn.esprit.spring.Services.Chambre.ChambreService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ChambreServiceTest {

    @Mock
    private ChambreRepository chambreRepository;

    @Mock
    private BlocRepository blocRepository;

    @InjectMocks
    private ChambreService chambreService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddOrUpdate() {
        Chambre chambre = new Chambre();
        chambre.setNumeroChambre(101L);
        chambre.setTypeC(TypeChambre.SIMPLE);

        when(chambreRepository.save(any(Chambre.class))).thenReturn(chambre);

        Chambre result = chambreService.addOrUpdate(chambre);

        assertNotNull(result);
        assertEquals(101L, result.getNumeroChambre());
        assertEquals(TypeChambre.SIMPLE, result.getTypeC());
        verify(chambreRepository, times(1)).save(chambre);
    }

    @Test
    void testFindAll() {
        Chambre chambre1 = new Chambre();
        chambre1.setIdChambre(1L);
        chambre1.setNumeroChambre(101L);
        chambre1.setTypeC(TypeChambre.SIMPLE);

        Chambre chambre2 = new Chambre();
        chambre2.setIdChambre(2L);
        chambre2.setNumeroChambre(102L);
        chambre2.setTypeC(TypeChambre.DOUBLE);

        List<Chambre> chambreList = Arrays.asList(chambre1, chambre2);

        when(chambreRepository.findAll()).thenReturn(chambreList);

        List<Chambre> result = chambreService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(chambreRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        long chambreId = 1L;
        Chambre chambre = new Chambre();
        chambre.setIdChambre(chambreId);
        chambre.setNumeroChambre(101L);
        chambre.setTypeC(TypeChambre.SIMPLE);

        when(chambreRepository.findById(chambreId)).thenReturn(Optional.of(chambre));

        Chambre result = chambreService.findById(chambreId);

        assertNotNull(result);
        assertEquals(chambreId, result.getIdChambre());
        assertEquals(101L, result.getNumeroChambre());
        verify(chambreRepository, times(1)).findById(chambreId);
    }

    @Test
    void testDeleteById() {
        long chambreId = 1L;
        doNothing().when(chambreRepository).deleteById(chambreId);

        chambreService.deleteById(chambreId);

        verify(chambreRepository, times(1)).deleteById(chambreId);
    }

    @Test
    void testDelete() {
        Chambre chambre = new Chambre();
        chambre.setIdChambre(1L);
        chambre.setNumeroChambre(101L);

        doNothing().when(chambreRepository).delete(chambre);

        chambreService.delete(chambre);

        verify(chambreRepository, times(1)).delete(chambre);
    }

    @Test
    void testGetChambresParNomBloc() {
        String nomBloc = "BlocA";
        Chambre chambre1 = new Chambre();
        chambre1.setIdChambre(1L);
        chambre1.setNumeroChambre(101L);

        Chambre chambre2 = new Chambre();
        chambre2.setIdChambre(2L);
        chambre2.setNumeroChambre(102L);

        List<Chambre> chambreList = Arrays.asList(chambre1, chambre2);

        when(chambreRepository.findByBlocNomBloc(nomBloc)).thenReturn(chambreList);

        List<Chambre> result = chambreService.getChambresParNomBloc(nomBloc);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(chambreRepository, times(1)).findByBlocNomBloc(nomBloc);
    }

    @Test
    void testNbChambreParTypeEtBloc() {
        TypeChambre type = TypeChambre.SIMPLE;
        long idBloc = 1L;

        Bloc bloc = new Bloc();
        bloc.setIdBloc(idBloc);

        Chambre chambre1 = new Chambre();
        chambre1.setTypeC(TypeChambre.SIMPLE);
        chambre1.setBloc(bloc);

        Chambre chambre2 = new Chambre();
        chambre2.setTypeC(TypeChambre.DOUBLE);
        chambre2.setBloc(bloc);

        Chambre chambre3 = new Chambre();
        chambre3.setTypeC(TypeChambre.SIMPLE);
        chambre3.setBloc(bloc);

        List<Chambre> allChambres = Arrays.asList(chambre1, chambre2, chambre3);

        when(chambreRepository.findAll()).thenReturn(allChambres);

        long result = chambreService.nbChambreParTypeEtBloc(type, idBloc);

        assertEquals(2, result);
        verify(chambreRepository, times(1)).findAll();
    }

    @Test
    void testGetChambresParNomBlocJava() {
        String nomBloc = "BlocA";
        Bloc bloc = new Bloc();
        bloc.setNomBloc(nomBloc);

        Chambre chambre1 = new Chambre();
        chambre1.setIdChambre(1L);
        chambre1.setNumeroChambre(101L);

        Chambre chambre2 = new Chambre();
        chambre2.setIdChambre(2L);
        chambre2.setNumeroChambre(102L);

        List<Chambre> chambres = Arrays.asList(chambre1, chambre2);
        bloc.setChambres(chambres);

        when(blocRepository.findByNomBloc(nomBloc)).thenReturn(bloc);

        List<Chambre> result = chambreService.getChambresParNomBlocJava(nomBloc);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(blocRepository, times(1)).findByNomBloc(nomBloc);
    }

    @Test
    void testGetChambresParNomBlocKeyWord() {
        String nomBloc = "BlocA";
        Chambre chambre1 = new Chambre();
        chambre1.setIdChambre(1L);
        chambre1.setNumeroChambre(101L);

        Chambre chambre2 = new Chambre();
        chambre2.setIdChambre(2L);
        chambre2.setNumeroChambre(102L);

        List<Chambre> chambreList = Arrays.asList(chambre1, chambre2);

        when(chambreRepository.findByBlocNomBloc(nomBloc)).thenReturn(chambreList);

        List<Chambre> result = chambreService.getChambresParNomBlocKeyWord(nomBloc);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(chambreRepository, times(1)).findByBlocNomBloc(nomBloc);
    }

    @Test
    void testGetChambresParNomBlocJPQL() {
        String nomBloc = "BlocA";
        Chambre chambre1 = new Chambre();
        chambre1.setIdChambre(1L);
        chambre1.setNumeroChambre(101L);

        Chambre chambre2 = new Chambre();
        chambre2.setIdChambre(2L);
        chambre2.setNumeroChambre(102L);

        List<Chambre> chambreList = Arrays.asList(chambre1, chambre2);

        when(chambreRepository.getChambresParNomBlocJPQL(nomBloc)).thenReturn(chambreList);

        List<Chambre> result = chambreService.getChambresParNomBlocJPQL(nomBloc);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(chambreRepository, times(1)).getChambresParNomBlocJPQL(nomBloc);
    }

    @Test
    void testGetChambresParNomBlocSQL() {
        String nomBloc = "BlocA";
        Chambre chambre1 = new Chambre();
        chambre1.setIdChambre(1L);
        chambre1.setNumeroChambre(101L);

        Chambre chambre2 = new Chambre();
        chambre2.setIdChambre(2L);
        chambre2.setNumeroChambre(102L);

        List<Chambre> chambreList = Arrays.asList(chambre1, chambre2);

        when(chambreRepository.getChambresParNomBlocSQL(nomBloc)).thenReturn(chambreList);

        List<Chambre> result = chambreService.getChambresParNomBlocSQL(nomBloc);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(chambreRepository, times(1)).getChambresParNomBlocSQL(nomBloc);
    }

    @Test
    void testPourcentageChambreParTypeChambre() {
        when(chambreRepository.count()).thenReturn(100L);
        when(chambreRepository.countChambreByTypeC(TypeChambre.SIMPLE)).thenReturn(40L);
        when(chambreRepository.countChambreByTypeC(TypeChambre.DOUBLE)).thenReturn(35L);
        when(chambreRepository.countChambreByTypeC(TypeChambre.TRIPLE)).thenReturn(25L);

        // This method logs the results, so we just verify the repository calls
        chambreService.pourcentageChambreParTypeChambre();

        verify(chambreRepository, times(1)).count();
        verify(chambreRepository, times(1)).countChambreByTypeC(TypeChambre.SIMPLE);
        verify(chambreRepository, times(1)).countChambreByTypeC(TypeChambre.DOUBLE);
        verify(chambreRepository, times(1)).countChambreByTypeC(TypeChambre.TRIPLE);
    }
} 