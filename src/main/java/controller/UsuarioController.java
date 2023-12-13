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

        Spark.port(4567);
        inicializarEndpoints();
    }

    private void inicializarEndpoints() {
        Spark.get("/usuarios", this::listarUsuarios);

        Spark.get("/usuarios/:id", this::buscarUsuarioPorId);
        Spark.delete("/usuarios/:id", this::excluirUsuarioPorId);

        Spark.post("/usuarios", this::inserirNovoUsuario);

        Spark.put("/usuarios", this::atualizarUsuario);
    }

    private String listarUsuarios(Request req, Response res) {
        try {
            res.type("application/json");
            res.status(200);

            return "Lógica para listar usuários aqui...";
        } catch (Exception e) {
            res.status(500);
            return "Erro ao processar a requisição.";
        }
    }

    private String buscarUsuarioPorId(Request req, Response res) {
        try {
            int id = Integer.parseInt(req.params(":id"));

            UsuarioDTOOutput usuarioEncontrado = usuarioService.buscarUsuarioPorId(id);

            if (usuarioEncontrado != null) {
                String jsonResponse = objectMapper.writeValueAsString(usuarioEncontrado);

                res.type("application/json");
                res.status(200);

                return jsonResponse;
            } else {
                res.status(404);
                return "Usuário não encontrado.";
            }
        } catch (NumberFormatException e) {
            res.status(400);
            return "ID inválido.";
        } catch (Exception e) {
            res.status(500);
            return "Erro ao processar a requisição.";
        }
    }

    private String excluirUsuarioPorId(Request req, Response res) {
        try {
            int id = Integer.parseInt(req.params(":id"));
            usuarioService.excluirUsuario(id);

            res.status(204);
            return "";
        } catch (NumberFormatException e) {
            res.status(400);
            return "ID inválido.";
        } catch (Exception e) {
            res.status(500);
            return "Erro ao processar a requisição.";
        }
    }

    private String inserirNovoUsuario(Request req, Response res) {
        try {
            UsuarioDTOInput novoUsuarioDTO = objectMapper.readValue(req.body(), UsuarioDTOInput.class);

            usuarioService.inserirUsuario(novoUsuarioDTO);

            res.status(201);
            return "Usuário inserido com sucesso.";
        } catch (IOException e) {
            res.status(400);
            return "Erro ao processar o corpo da requisição.";
        } catch (Exception e) {
            res.status(500);
            return "Erro ao processar a requisição.";
        }
    }

    private String atualizarUsuario(Request req, Response res) {
        try {
            UsuarioDTOInput usuarioAtualizadoDTO = objectMapper.readValue(req.body(), UsuarioDTOInput.class);


            usuarioService.atualizarUsuario(usuarioAtualizadoDTO);

            res.status(204); 
            return "";
        } catch (IOException e) {
            res.status(400);
            return "Erro ao processar o corpo da requisição.";
        } catch (Exception e) {
            res.status(500);
            return "Erro ao processar a requisição.";
        }
    }

    public void respostasRequisicoes() {
        Spark.awaitStop();
    }

    public static void main(String[] args) {
        UsuarioController usuarioController = new UsuarioController();
        usuarioController.respostasRequisicoes();
    }
}
