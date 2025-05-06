package dsegovia.desafio.service.impl;

import dsegovia.desafio.entity.EstadoTarea;
import dsegovia.desafio.entity.Rol;
import dsegovia.desafio.entity.Tarea;
import dsegovia.desafio.entity.Usuario;
import dsegovia.desafio.exception.AccesoDenegadoException;
import dsegovia.desafio.exception.NegocioException;
import dsegovia.desafio.exception.RecursoNoEncontradoException;
import dsegovia.desafio.repository.EstadoTareaRepository;
import dsegovia.desafio.repository.TareaRepository;
import dsegovia.desafio.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TareaServiceImplTest {

    @Mock
    private TareaRepository tareaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private EstadoTareaRepository estadoRepository;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private TareaServiceImpl tareaService;

    private Usuario usuario;
    private Usuario adminUsuario;
    private EstadoTarea estadoTarea;
    private Tarea tarea;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUsername("user");
        Set<Rol> roles = new HashSet<>();
        Rol rol = new Rol();
        rol.setNombre("ROLE_USER");
        roles.add(rol);
        usuario.setRoles(roles);

        adminUsuario = new Usuario();
        adminUsuario.setId(2L);
        adminUsuario.setUsername("admin");
        Set<Rol> adminRoles = new HashSet<>();
        Rol adminRol = new Rol();
        adminRol.setNombre("ROLE_ADMIN");
        adminRoles.add(adminRol);
        adminUsuario.setRoles(adminRoles);

        estadoTarea = new EstadoTarea();
        estadoTarea.setId(1L);
        estadoTarea.setNombre("PENDIENTE");

        tarea = new Tarea();
        tarea.setId(1L);
        tarea.setTitulo("Tarea de prueba");
        tarea.setDescripcion("Descripción de prueba");
        tarea.setEstado(estadoTarea);
        tarea.setOwner(usuario);
        tarea.setPrioridad(Tarea.Prioridad.MEDIA);
        tarea.setFechaCreacion(LocalDateTime.now());
        tarea.setFechaVencimiento(LocalDateTime.now().plusDays(7));
        tarea.setEtiquetas("test,prueba");

        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void crearTarea_ConEstadoValido_CreaCorrectamente() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("user");
        when(usuarioRepository.findByUsername("user")).thenReturn(Optional.of(usuario));
        when(estadoRepository.findById(1L)).thenReturn(Optional.of(estadoTarea));
        when(tareaRepository.save(any(Tarea.class))).thenReturn(tarea);

        Tarea nuevaTarea = new Tarea();
        nuevaTarea.setTitulo("Nueva tarea");
        nuevaTarea.setDescripcion("Nueva descripción");
        EstadoTarea estado = new EstadoTarea();
        estado.setId(1L);
        nuevaTarea.setEstado(estado);

        Tarea resultado = tareaService.crearTarea(nuevaTarea);

        assertEquals(tarea, resultado);
        verify(tareaRepository).save(any(Tarea.class));
    }

    @Test
    void crearTarea_SinEstado_LanzaExcepcion() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("user");
        when(usuarioRepository.findByUsername("user")).thenReturn(Optional.of(usuario));

        Tarea nuevaTarea = new Tarea();
        nuevaTarea.setTitulo("Nueva tarea");
        nuevaTarea.setDescripcion("Nueva descripción");

        assertThrows(NegocioException.class, () -> tareaService.crearTarea(nuevaTarea));
    }

    @Test
    void crearTarea_ConEstadoInvalido_LanzaExcepcion() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("user");
        when(usuarioRepository.findByUsername("user")).thenReturn(Optional.of(usuario));
        when(estadoRepository.findById(99L)).thenReturn(Optional.empty());

        Tarea nuevaTarea = new Tarea();
        nuevaTarea.setTitulo("Nueva tarea");
        nuevaTarea.setDescripcion("Nueva descripción");
        EstadoTarea estado = new EstadoTarea();
        estado.setId(99L);
        nuevaTarea.setEstado(estado);

        assertThrows(RecursoNoEncontradoException.class, () -> tareaService.crearTarea(nuevaTarea));
    }

    @Test
    void actualizarTarea_SiendoPropietario_ActualizaCorrectamente() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("user");
        when(usuarioRepository.findByUsername("user")).thenReturn(Optional.of(usuario));
        when(tareaRepository.findById(1L)).thenReturn(Optional.of(tarea));
        when(estadoRepository.findById(2L)).thenReturn(Optional.of(new EstadoTarea()));
        when(tareaRepository.save(any(Tarea.class))).thenReturn(tarea);

        Tarea tareaActualizada = new Tarea();
        tareaActualizada.setTitulo("Tarea actualizada");
        tareaActualizada.setDescripcion("Descripción actualizada");
        EstadoTarea nuevoEstado = new EstadoTarea();
        nuevoEstado.setId(2L);
        tareaActualizada.setEstado(nuevoEstado);
        tareaActualizada.setPrioridad(Tarea.Prioridad.ALTA);
        tareaActualizada.setEtiquetas("nuevo,tags");

        Tarea resultado = tareaService.actualizarTarea(1L, tareaActualizada);

        assertEquals(tarea, resultado);
        verify(tareaRepository).save(any(Tarea.class));
    }

    @Test
    void actualizarTarea_SinPermisos_LanzaExcepcion() {
        Usuario otroUsuario = new Usuario();
        otroUsuario.setId(3L);
        otroUsuario.setUsername("otro");
        Set<Rol> roles = new HashSet<>();
        Rol rol = new Rol();
        rol.setNombre("ROLE_USER");
        roles.add(rol);
        otroUsuario.setRoles(roles);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("otro");
        when(usuarioRepository.findByUsername("otro")).thenReturn(Optional.of(otroUsuario));
        when(tareaRepository.findById(1L)).thenReturn(Optional.of(tarea));

        Tarea tareaActualizada = new Tarea();
        tareaActualizada.setTitulo("Tarea actualizada");

        assertThrows(AccesoDenegadoException.class, () -> tareaService.actualizarTarea(1L, tareaActualizada));
    }

    @Test
    void eliminarTarea_SiendoPropietario_EliminaCorrectamente() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("user");
        when(usuarioRepository.findByUsername("user")).thenReturn(Optional.of(usuario));
        when(tareaRepository.findById(1L)).thenReturn(Optional.of(tarea));

        tareaService.eliminarTarea(1L);

        verify(tareaRepository).delete(tarea);
    }

    @Test
    void eliminarTarea_SinPermisos_LanzaExcepcion() {
        Usuario otroUsuario = new Usuario();
        otroUsuario.setId(3L);
        otroUsuario.setUsername("otro");
        Set<Rol> roles = new HashSet<>();
        Rol rol = new Rol();
        rol.setNombre("ROLE_USER");
        roles.add(rol);
        otroUsuario.setRoles(roles);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("otro");
        when(usuarioRepository.findByUsername("otro")).thenReturn(Optional.of(otroUsuario));
        when(tareaRepository.findById(1L)).thenReturn(Optional.of(tarea));

        assertThrows(AccesoDenegadoException.class, () -> tareaService.eliminarTarea(1L));
    }

    @Test
    void obtenerTareaById_DevuelveTarea() {
        when(tareaRepository.findById(1L)).thenReturn(Optional.of(tarea));

        Optional<Tarea> resultado = tareaService.obtenerTareaById(1L);

        assertTrue(resultado.isPresent());
        assertEquals(tarea, resultado.get());
    }

    @Test
    void tienePermiso_SiendoPropietario_DevuelveTrue() {
        boolean resultado = tareaService.tienePermiso(tarea, usuario);

        assertTrue(resultado);
    }

    @Test
    void tienePermiso_SiendoAdmin_DevuelveTrue() {
        boolean resultado = tareaService.tienePermiso(tarea, adminUsuario);

        assertTrue(resultado);
    }

    @Test
    void tienePermiso_SinPermisos_DevuelveFalse() {
        Usuario otroUsuario = new Usuario();
        otroUsuario.setId(3L);
        Set<Rol> roles = new HashSet<>();
        Rol rol = new Rol();
        rol.setNombre("ROLE_USER");
        roles.add(rol);
        otroUsuario.setRoles(roles);

        boolean resultado = tareaService.tienePermiso(tarea, otroUsuario);

        assertFalse(resultado);
    }

    @Test
    void obtenerTareasFiltradas_UsuarioNormal_DevuelveSusTareas() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("user");
        when(usuarioRepository.findByUsername("user")).thenReturn(Optional.of(usuario));
        when(tareaRepository.findByOwnerId(1L)).thenReturn(Collections.singletonList(tarea));

        List<Tarea> resultado = tareaService.obtenerTareasFiltradas(null, null, null);

        assertEquals(1, resultado.size());
        assertEquals(tarea, resultado.get(0));
    }

    @Test
    void obtenerTareasFiltradas_Admin_DevuelveTodasLasTareas() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("admin");
        when(usuarioRepository.findByUsername("admin")).thenReturn(Optional.of(adminUsuario));
        when(tareaRepository.findAll()).thenReturn(Collections.singletonList(tarea));

        List<Tarea> resultado = tareaService.obtenerTareasFiltradas(null, null, null);

        assertEquals(1, resultado.size());
        assertEquals(tarea, resultado.get(0));
    }

    @Test
    void obtenerTareasFiltradas_ConFiltros_DevuelveTareasFiltradas() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("user");
        when(usuarioRepository.findByUsername("user")).thenReturn(Optional.of(usuario));
        when(tareaRepository.findByOwnerId(1L)).thenReturn(Collections.singletonList(tarea));

        List<Tarea> resultado = tareaService.obtenerTareasFiltradas(1L, Tarea.Prioridad.MEDIA, "test");

        assertEquals(1, resultado.size());
        assertEquals(tarea, resultado.get(0));
    }

    @Test
    void obtenerUsuarioActual_DevuelveUsuario() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("user");
        when(usuarioRepository.findByUsername("user")).thenReturn(Optional.of(usuario));

        Usuario resultado = tareaService.obtenerUsuarioActual();

        assertEquals(usuario, resultado);
    }
}