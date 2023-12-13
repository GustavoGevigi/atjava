package service;

import dto.UsuarioDTOInput;
import dto.UsuarioDTOOutput;
import model.Usuario;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UsuarioService {
    private List<Usuario> listaUsuarios;
    private ModelMapper modelMapper;

    public UsuarioService() {
        this.listaUsuarios = new ArrayList<>();
        this.modelMapper = new ModelMapper();
    }

    // Método para listar todos os usuários
    public List<UsuarioDTOOutput> listarUsuarios() {
        return listaUsuarios.stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioDTOOutput.class))
                .collect(Collectors.toList());
    }

    // Método para inserir um novo usuário a partir de um objeto UsuarioDTOInput
    public void inserirUsuario(UsuarioDTOInput usuarioDTOInput) {
        Usuario usuario = modelMapper.map(usuarioDTOInput, Usuario.class);
        listaUsuarios.add(usuario);
    }

    // Método para alterar um usuário existente a partir de um objeto UsuarioDTOInput
    public void alterarUsuario(UsuarioDTOInput usuarioDTOInput) {
        Optional<Usuario> usuarioExistente = listaUsuarios.stream()
                .filter(u -> u.getId() == usuarioDTOInput.getId())
                .findFirst();

        usuarioExistente.ifPresent(u -> {
            modelMapper.map(usuarioDTOInput, u);
            // Alternativamente, você poderia mapear manualmente os campos, por exemplo:
            // u.setNome(usuarioDTOInput.getNome());
            // u.setSenha(usuarioDTOInput.getSenha());
        });
    }

    // Método para atualizar um usuário a partir de um objeto UsuarioDTOInput
// Método para atualizar um usuário a partir de um objeto UsuarioDTOInput
    public void atualizarUsuario(UsuarioDTOInput usuarioDTO) {
        // Verificar se o usuário com o ID fornecido existe na lista
        UsuarioDTOOutput usuarioExistente = buscarUsuarioPorId(usuarioDTO.getId());

        if (usuarioExistente != null) {
            // Atualizar os dados do usuário existente com base no DTO de entrada
            usuarioExistente.setId(usuarioDTO.getId());
            usuarioExistente.setNome(usuarioDTO.getNome());
            usuarioExistente.setSenha(usuarioDTO.getSenha());
        } else {
            // Se o usuário não existe, pode lançar uma exceção ou tratar conforme necessário
            throw new RuntimeException("Usuário não encontrado para atualização.");
        }
    }

    // Método para buscar um usuário por ID e converter para UsuarioDTOOutput
    public UsuarioDTOOutput buscarUsuarioPorId(int id) {
        Optional<Usuario> usuarioExistente = listaUsuarios.stream()
                .filter(u -> u.getId() == id)
                .findFirst();

        return usuarioExistente.map(usuario -> modelMapper.map(usuario, UsuarioDTOOutput.class))
                .orElse(null);
    }

    // Método para excluir um usuário por ID
    public void excluirUsuario(int id) {
        listaUsuarios.removeIf(u -> u.getId() == id);
    }
}
