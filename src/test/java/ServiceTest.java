import DTO.UsuarioDTOInput;
import org.junit.jupiter.api.Test;
import Service.UsuarioService;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServiceTest {

    @Test
    public void testInserirUsuario() {
        // Criar uma instância do serviço
        UsuarioService usuarioService = new UsuarioService();

        // Criar um objeto UsuarioDTOInput para inserção
        UsuarioDTOInput usuarioDTOInput = new UsuarioDTOInput();
        usuarioDTOInput.setNome("NomeTeste");
        usuarioDTOInput.setSenha("SenhaTeste");

        // Executar o método de inserção
        usuarioService.inserirUsuario(usuarioDTOInput);

        // Validar a inserção verificando o tamanho da lista
        assertEquals(1, usuarioService.listarUsuarios().size());
    }
}
