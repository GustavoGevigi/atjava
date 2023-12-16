import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import DTO.UsuarioDTOInput;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegrationTest {
    private static final int HTTP_OK = 200;
    private static final int HTTP_CREATED = 201;
    @Test
    public void testInserirUsuario() {

        try {
            URL randomUserApiUrl = new URL("https://randomuser.me/api/");
            HttpURLConnection randomUserConnection = (HttpURLConnection) randomUserApiUrl.openConnection();
            randomUserConnection.setRequestMethod("GET");

            int randomUserResponseCode = randomUserConnection.getResponseCode();
            assertEquals(HTTP_OK, randomUserResponseCode);

            BufferedReader randomUserReader = new BufferedReader(new InputStreamReader(randomUserConnection.getInputStream()));
            StringBuilder randomUserContent = new StringBuilder();
            String randomUserInputLine;

            while ((randomUserInputLine = randomUserReader.readLine()) != null) {
                randomUserContent.append(randomUserInputLine);
            }

            randomUserReader.close();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode randomUserJson = objectMapper.readTree(randomUserContent.toString());
            JsonNode userNode = randomUserJson.at("/results/0");

            URL apiUrl = new URL("http://localhost:4567/usuarios");
            HttpURLConnection apiConnection = (HttpURLConnection) apiUrl.openConnection();
            apiConnection.setRequestMethod("POST");
            apiConnection.setRequestProperty("Content-Type", "application/json");
            apiConnection.setDoOutput(true);

            UsuarioDTOInput usuarioDTOInput = new UsuarioDTOInput();
            usuarioDTOInput.setNome(userNode.at("/name/first").asText());
            usuarioDTOInput.setSenha("senha_aleatoria");

            objectMapper.writeValue(apiConnection.getOutputStream(), usuarioDTOInput);

            int apiResponseCode = apiConnection.getResponseCode();
            assertEquals(HTTP_CREATED, apiResponseCode);

            System.out.println("Resultado: Integração realizada com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}