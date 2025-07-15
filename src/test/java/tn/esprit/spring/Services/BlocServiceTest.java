package tn.esprit.spring.Services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.DAO.Entities.Bloc;
import tn.esprit.spring.DAO.Repositories.BlocRepository;
import tn.esprit.spring.DAO.Repositories.ChambreRepository;
import tn.esprit.spring.DAO.Repositories.FoyerRepository;
import tn.esprit.spring.Services.Bloc.BlocService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BlocServiceTest {

    @Mock
    private BlocRepository blocRepository;

    @Mock
    private ChambreRepository chambreRepository;

    @Mock
    private FoyerRepository foyerRepository;

    @InjectMocks
    private BlocService blocService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddOrUpdate() {
        Bloc bloc = new Bloc();
        bloc.setNomBloc("TestBloc");
        bloc.setChambres(new java.util.ArrayList<>());

        when(blocRepository.save(any(Bloc.class))).thenReturn(bloc);

        Bloc result = blocService.addOrUpdate(bloc);

        assertNotNull(result);
        assertEquals("TestBloc", result.getNomBloc());
        verify(blocRepository, times(1)).save(bloc);
    }

    @Test
    void testFindAll() {
        Bloc bloc1 = new Bloc();
        bloc1.setIdBloc(1L);
        bloc1.setNomBloc("Bloc1");
        Bloc bloc2 = new Bloc();
        bloc2.setIdBloc(2L);
        bloc2.setNomBloc("Bloc2");
        List<Bloc> blocList = Arrays.asList(bloc1, bloc2);

        when(blocRepository.findAll()).thenReturn(blocList);

        List<Bloc> result = blocService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(blocRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        long blocId = 1L;
        Bloc bloc = new Bloc();
        bloc.setIdBloc(blocId);
        bloc.setNomBloc("TestBloc");

        when(blocRepository.findById(blocId)).thenReturn(Optional.of(bloc));

        Bloc result = blocService.findById(blocId);

        assertNotNull(result);
        assertEquals(blocId, result.getIdBloc());
        assertEquals("TestBloc", result.getNomBloc());
        verify(blocRepository, times(1)).findById(blocId);
    }

    @Test
    void testDeleteById() {
        long blocId = 1L;
        Bloc bloc = new Bloc();
        bloc.setIdBloc(blocId);
        bloc.setChambres(new java.util.ArrayList<>());
        when(blocRepository.findById(blocId)).thenReturn(Optional.of(bloc));
        doNothing().when(chambreRepository).deleteAll(anyList());
        doNothing().when(blocRepository).delete(bloc);

        blocService.deleteById(blocId);

        verify(blocRepository, times(1)).findById(blocId);
        verify(chambreRepository, times(1)).deleteAll(anyList());
        verify(blocRepository, times(1)).delete(bloc);
    }

    @Test
    void testDelete() {
        Bloc bloc = new Bloc();
        bloc.setIdBloc(1L);
        bloc.setChambres(new java.util.ArrayList<>());
        doNothing().when(chambreRepository).deleteAll(anyList());
        doNothing().when(blocRepository).delete(bloc);

        blocService.delete(bloc);

        verify(chambreRepository, times(1)).deleteAll(anyList());
        verify(blocRepository, times(1)).delete(bloc);
    }
} 