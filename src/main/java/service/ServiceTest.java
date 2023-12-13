package service;

import dto.UsuarioDTOInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.UsuarioService;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServiceTest {

    private UsuarioService usuarioService;

    @BeforeEach
    public void setUp() {
        usuarioService = new UsuarioService();
    }

    @Test
    public void testInserirUsuario() {
        UsuarioDTOInput novoUsuarioDTO = new UsuarioDTOInput();
        novoUsuarioDTO.setNome("Novo Usuário");
        novoUsuarioDTO.setSenha("senha123");

        usuarioService.inserirUsuario(novoUsuarioDTO);

        assertEquals(1, usuarioService.listarUsuarios().size());
    }
}
