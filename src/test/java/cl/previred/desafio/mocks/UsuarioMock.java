package cl.previred.desafio.mocks;

import cl.previred.desafio.domain.models.Usuario;

public class UsuarioMock {
    public static Usuario usuario1=Usuario.builder()
                                    .username("admin")
                                    .id(1L)
                                    .role("ADMIN")
                                    .enabled(true)
                                    .build();
    public static Usuario usuario2=Usuario.builder()
                                    .username("user")
                                    .id(2L)
                                    .role("USER")
                                    .enabled(true)
                                    .build();

}
