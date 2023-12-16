package Service;

import DTO.UsuarioDTOInput;
import DTO.UsuarioDTOOutput;
import Model.Usuario;
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
        Usuario usuarioModificado = modelMapper.map(usuarioDTOInput, Usuario.class);

        Optional<Usuario> usuarioExistente = listaUsuarios.stream()
                .filter(u -> u.getId() == usuarioModificado.getId())
                .findFirst();

        usuarioExistente.ifPresent(u -> {
            u.setNome(usuarioModificado.getNome());
            u.setSenha(usuarioModificado.getSenha());
        });
    }

    public Optional<UsuarioDTOOutput> buscarUsuarioPorId(int id) {
        Optional<Usuario> usuarioExistente = listaUsuarios.stream()
                .filter(usuario -> usuario.getId() == id)
                .findFirst();

        return usuarioExistente.map(usuario -> modelMapper.map(usuario, UsuarioDTOOutput.class));
    }

    public void excluirUsuario(int id) {
        listaUsuarios.removeIf(usuario -> usuario.getId() == id);
    }
}
