package Controller;

import DTO.UsuarioDTOInput;
import Service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import static spark.Spark.*;

public class UsuarioController {
    private UsuarioService usuarioService;
    private ObjectMapper objectMapper;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
        this.objectMapper = new ObjectMapper();
    }

    public void respostasRequisicoes() {
        // Endpoint para listar usuários
        get("/usuarios", (req, res) -> {
            res.type("application/json");

            try {
                var usuarios = usuarioService.listarUsuarios();
                String json = objectMapper.writeValueAsString(usuarios);
                res.status(200);
                return json;
            } catch (Exception e) {
                res.status(500);
                return "Erro ao processar a requisição";
            }
        });

        get("/usuarios/:id", (req, res) -> {
            res.type("application/json");

            try {
                int id = Integer.parseInt(req.params(":id"));
                var usuarioOptional = usuarioService.buscarUsuarioPorId(id);

                if (usuarioOptional.isPresent()) {
                    String json = objectMapper.writeValueAsString(usuarioOptional.get());
                    res.status(200);
                    return json;
                } else {
                    res.status(404);
                    return "Usuário não encontrado";
                }
            } catch (NumberFormatException e) {
                res.status(400);
                return "ID inválido";
            } catch (Exception e) {
                res.status(500);
                return "Erro ao processar a requisição";
            }
        });

        delete("/usuarios/:id", (req, res) -> {
            res.type("application/json");

            try {
                int id = Integer.parseInt(req.params(":id"));
                usuarioService.excluirUsuario(id);
                res.status(204);
                return "";
            } catch (NumberFormatException e) {
                res.status(400);
                return "ID inválido";
            } catch (Exception e) {
                res.status(500);
                return "Erro ao processar a requisição";
            }
        });

        post("/usuarios", (req, res) -> {
            res.type("application/json");

            try {
                UsuarioDTOInput usuarioDTOInput = objectMapper.readValue(req.body(), UsuarioDTOInput.class);
                usuarioService.inserirUsuario(usuarioDTOInput);
                res.status(201);
                return "Usuário inserido com sucesso";
            } catch (Exception e) {
                res.status(500);
                return "Erro ao processar a requisição";
            }
        });

        put("/usuarios", (req, res) -> {
            res.type("application/json");

            try {
                UsuarioDTOInput usuarioDTOInput = objectMapper.readValue(req.body(), UsuarioDTOInput.class);
                usuarioService.alterarUsuario(usuarioDTOInput);
                res.status(200);
                return "Usuário atualizado com sucesso";
            } catch (Exception e) {
                res.status(500);
                return "Erro ao processar a requisição";
            }
        });
    }

    public static void main(String[] args) {
        UsuarioService usuarioService = new UsuarioService();
        UsuarioController usuarioController = new UsuarioController(usuarioService);

        port(4567);
        usuarioController.respostasRequisicoes();
    }
}
