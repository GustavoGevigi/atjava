package service;

import dto.UsuarioDTOInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.UsuarioService;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Requisito 17
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

    /*Requisito 18:

package service;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServiceTest {

    @Test
    public void testListarUsuarios() throws IOException {
        String apiUrl = "http://localhost:9013/usuarios";

        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            assertEquals(200, responseCode);

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                System.out.println("Resposta da API: Teste realizado com sucesso! " + response.toString());
            }
        } finally {
            connection.disconnect();
        }
    }
}
    
*/

/*Requisito 19: 

package service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServiceTest {

    @Test
    public void testInserirUsuario() throws IOException {
        String randomUserApiUrl = "https://randomuser.me/api/";
        String userData = fazerRequisicaoGET(randomUserApiUrl);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(userData);
        String nome = jsonNode.path("results").path(0).path("name").path("first").asText();
        String senha = jsonNode.path("results").path(0).path("login").path("password").asText();

        String apiUrl = "http://localhost:9013/usuarios";
        String requestBody = String.format("{\"nome\": \"%s\", \"senha\": \"%s\"}", nome, senha);

        HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.getOutputStream().write(requestBody.getBytes());

        int responseCode = connection.getResponseCode();
        assertEquals(200, responseCode);
    }

    private String fazerRequisicaoGET(String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            return response.toString();
        }
    }
}


*/
}
