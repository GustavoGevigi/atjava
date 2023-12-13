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

    public List<UsuarioDTOOutput> listarUsuarios() {
        return listaUsuarios.stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioDTOOutput.class))
                .collect(Collectors.toList());
    }

    public void inserirUsuario(UsuarioDTOInput usuarioDTOInput) {
        Usuario usuario = modelMapper.map(usuarioDTOInput, Usuario.class);
        listaUsuarios.add(usuario);
    }

    public void alterarUsuario(UsuarioDTOInput usuarioDTOInput) {
        Optional<Usuario> usuarioExistente = listaUsuarios.stream()
                .filter(u -> u.getId() == usuarioDTOInput.getId())
                .findFirst();

        usuarioExistente.ifPresent(u -> {
            modelMapper.map(usuarioDTOInput, u);
        });
    }

    public void atualizarUsuario(UsuarioDTOInput usuarioDTO) {
        UsuarioDTOOutput usuarioExistente = buscarUsuarioPorId(usuarioDTO.getId());

        if (usuarioExistente != null) {
            usuarioExistente.setId(usuarioDTO.getId());
            usuarioExistente.setNome(usuarioDTO.getNome());
            usuarioExistente.setSenha(usuarioDTO.getSenha());
        } else {
            throw new RuntimeException("Usuário não encontrado para atualização.");
        }
    }

    public UsuarioDTOOutput buscarUsuarioPorId(int id) {
        Optional<Usuario> usuarioExistente = listaUsuarios.stream()
                .filter(u -> u.getId() == id)
                .findFirst();

        return usuarioExistente.map(usuario -> modelMapper.map(usuario, UsuarioDTOOutput.class))
                .orElse(null);
    }

    public void excluirUsuario(int id) {
        listaUsuarios.removeIf(u -> u.getId() == id);
    }
}
