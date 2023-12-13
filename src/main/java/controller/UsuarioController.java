package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.UsuarioDTOInput;
import service.UsuarioService;
import spark.Request;
import spark.Response;
import spark.Spark;

import dto.UsuarioDTOOutput;

import java.io.IOException;
import java.util.Optional;

public class UsuarioController {
    private UsuarioService usuarioService;
    private ObjectMapper objectMapper;

    public UsuarioController() {
        this.usuarioService = new UsuarioService();
        this.objectMapper = new ObjectMapper();

        // Inicializar Spark
        Spark.port(4567); // Porta padrão, você pode alterar conforme necessário
        inicializarEndpoints();
    }

    private void inicializarEndpoints() {
        // Endpoint para listar usuários
        Spark.get("/usuarios", this::listarUsuarios);

        // Endpoint para buscar um usuário por ID
        Spark.get("/usuarios/:id", this::buscarUsuarioPorId);

        // Endpoint para excluir um usuário por ID
        Spark.delete("/usuarios/:id", this::excluirUsuarioPorId);

        // Endpoint para inserir um novo usuário
        Spark.post("/usuarios", this::inserirNovoUsuario);

        // Endpoint para atualizar um usuário
        Spark.put("/usuarios", this::atualizarUsuario);
    }

    private String listarUsuarios(Request req, Response res) {
        try {
            res.type("application/json");
            res.status(200);

            // Adapte conforme necessário para a sua implementação
            return "Lógica para listar usuários aqui...";
        } catch (Exception e) {
            res.status(500);
            return "Erro ao processar a requisição.";
        }
    }

    private String buscarUsuarioPorId(Request req, Response res) {
        try {
            // Obter o parâmetro :id da URI
            int id = Integer.parseInt(req.params(":id"));

            // Chamar o método buscarUsuarioPorId do UsuarioService
            UsuarioDTOOutput usuarioEncontrado = usuarioService.buscarUsuarioPorId(id);

            // Verificar se o usuário foi encontrado
            if (usuarioEncontrado != null) {
                // Converter o objeto retornado em JSON
                String jsonResponse = objectMapper.writeValueAsString(usuarioEncontrado);

                // Definir o tipo de conteúdo e o código de resposta
                res.type("application/json");
                res.status(200);

                // Retornar o JSON como resposta
                return jsonResponse;
            } else {
                // Usuário não encontrado, definir o código de resposta adequado
                res.status(404);
                return "Usuário não encontrado.";
            }
        } catch (NumberFormatException e) {
            // Se o parâmetro :id não for um número válido
            res.status(400);
            return "ID inválido.";
        } catch (Exception e) {
            res.status(500);
            return "Erro ao processar a requisição.";
        }
    }

    private String excluirUsuarioPorId(Request req, Response res) {
        try {
            // Obter o parâmetro :id da URI
            int id = Integer.parseInt(req.params(":id"));

            // Chamar o método excluirUsuario do UsuarioService
            usuarioService.excluirUsuario(id);

            // Definir o código de resposta adequado
            res.status(204); // No Content
            return "";
        } catch (NumberFormatException e) {
            // Se o parâmetro :id não for um número válido
            res.status(400);
            return "ID inválido.";
        } catch (Exception e) {
            res.status(500);
            return "Erro ao processar a requisição.";
        }
    }

    private String inserirNovoUsuario(Request req, Response res) {
        try {
            // Converter o JSON recebido no corpo da requisição para UsuarioDTOInput
            UsuarioDTOInput novoUsuarioDTO = objectMapper.readValue(req.body(), UsuarioDTOInput.class);

            // Chamar o método inserirUsuario do UsuarioService
            usuarioService.inserirUsuario(novoUsuarioDTO);

            // Definir o código de resposta adequado
            res.status(201); // Created
            return "Usuário inserido com sucesso.";
        } catch (IOException e) {
            // Erro ao converter o JSON para UsuarioDTOInput
            res.status(400);
            return "Erro ao processar o corpo da requisição.";
        } catch (Exception e) {
            res.status(500);
            return "Erro ao processar a requisição.";
        }
    }

    private String atualizarUsuario(Request req, Response res) {
        try {
            // Converter o JSON recebido no corpo da requisição para UsuarioDTOInput
            UsuarioDTOInput usuarioAtualizadoDTO = objectMapper.readValue(req.body(), UsuarioDTOInput.class);

            // Chamar o método atualizarUsuario do UsuarioService
            usuarioService.atualizarUsuario(usuarioAtualizadoDTO);

            // Definir o código de resposta adequado
            res.status(204); // No Content
            return "";
        } catch (IOException e) {
            // Erro ao converter o JSON para UsuarioDTOInput
            res.status(400);
            return "Erro ao processar o corpo da requisição.";
        } catch (Exception e) {
            res.status(500);
            return "Erro ao processar a requisição.";
        }
    }

    public void respostasRequisicoes() {
        // Este método agora inicia o servidor Spark e aguarda requisições
        Spark.awaitStop();
    }

    public static void main(String[] args) {
        UsuarioController usuarioController = new UsuarioController();
        usuarioController.respostasRequisicoes();
    }
}
