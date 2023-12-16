import DTO.UsuarioDTOInput;
import org.junit.jupiter.api.Test;
import Service.UsuarioService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServiceTest {

    @Test
    public void testInserirUsuario() {
        UsuarioService usuarioService = new UsuarioService();

        UsuarioDTOInput usuarioDTOInput = new UsuarioDTOInput();
        usuarioDTOInput.setId(1);
        usuarioDTOInput.setNome("Gustavo");
        usuarioDTOInput.setSenha("SenhaMuitoCriativa");

        usuarioService.inserirUsuario(usuarioDTOInput);

        assertEquals(1, usuarioService.listarUsuarios().size());
        System.out.println("Resultado: Inserção realizada com sucesso!");
    }
}