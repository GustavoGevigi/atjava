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
        // Criar um objeto UsuarioDTOInput para inserção
        UsuarioDTOInput novoUsuarioDTO = new UsuarioDTOInput();
        novoUsuarioDTO.setNome("Novo Usuário");
        novoUsuarioDTO.setSenha("senha123");

        // Executar o método inserir
        usuarioService.inserirUsuario(novoUsuarioDTO);

        // Validar a inserção verificando o tamanho da lista após a inserção
        assertEquals(1, usuarioService.listarUsuarios().size());
    }
}
