package com.rytech.usuario.business.converter;

import com.rytech.usuario.business.dto.in.EnderecoDTORequest;
import com.rytech.usuario.business.dto.in.TelefoneDTORequest;
import com.rytech.usuario.business.dto.in.UsuarioDTORequest;
import com.rytech.usuario.business.dto.out.EnderecoDTOResponse;
import com.rytech.usuario.business.dto.out.TelefoneDTOResponse;
import com.rytech.usuario.business.dto.out.UsuarioDTOResponse;
import com.rytech.usuario.infrastructure.entity.Endereco;
import com.rytech.usuario.infrastructure.entity.Telefone;
import com.rytech.usuario.infrastructure.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UsuarioConverter {

    public Usuario paraUsuario(UsuarioDTORequest usuarioDTO) {
        return Usuario.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .enderecos(usuarioDTO.getEnderecos() != null ?
                        paraListaEndereco(usuarioDTO.getEnderecos()) : null)
                .telefones(usuarioDTO.getTelefones() != null ?
                        paraListaTelefone(usuarioDTO.getTelefones()) : null)
                .build();
    }

    public List<Endereco> paraListaEndereco(List<EnderecoDTORequest> enderecosDTO) {
        List<Endereco> enderecos = new ArrayList<>();
        for (EnderecoDTORequest enderecoDTO : enderecosDTO) {
            enderecos.add(paraEndereco(enderecoDTO));
        }
        return enderecos;
    }

    public Endereco paraEndereco(EnderecoDTORequest enderecoDTO) {
        return Endereco.builder()
                .rua(enderecoDTO.getRua())
                .numero(enderecoDTO.getNumero())
                .complemento(enderecoDTO.getComplemento())
                .cidade(enderecoDTO.getCidade())
                .estado(enderecoDTO.getEstado())
                .cep(enderecoDTO.getCep())
                .build();
    }

    public List<Telefone> paraListaTelefone(List<TelefoneDTORequest> telefoneDTO) {
        return telefoneDTO.stream().map(this::paraTelefone).toList();
    }

    public Telefone paraTelefone(TelefoneDTORequest telefoneDTO) {
        return Telefone.builder()
                .numero(telefoneDTO.getNumero())
                .ddd(telefoneDTO.getDdd())
                .build();
    }

    public UsuarioDTOResponse paraUsuarioDTO(Usuario usuario) {
        return UsuarioDTOResponse.builder()
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .senha(usuario.getSenha())
                .enderecos(usuario.getEnderecos() != null ?
                        paraListaEnderecoDTO(usuario.getEnderecos()) : null)
                .telefones(usuario.getTelefones() != null ?
                        paraListaTelefoneDTO(usuario.getTelefones()) : null)
                .build();
    }

    public List<EnderecoDTOResponse> paraListaEnderecoDTO(List<Endereco> enderecosDTO) {
        List<EnderecoDTOResponse> enderecos = new ArrayList<>();
        for (Endereco enderecoDTO : enderecosDTO) {
            enderecos.add(paraEnderecoDTO(enderecoDTO));
        }
        return enderecos;
    }

    public EnderecoDTOResponse paraEnderecoDTO(Endereco endereco) {
        return EnderecoDTOResponse.builder()
                .id(endereco.getId())
                .rua(endereco.getRua())
                .numero(endereco.getNumero())
                .complemento(endereco.getComplemento())
                .cidade(endereco.getCidade())
                .estado(endereco.getEstado())
                .cep(endereco.getCep())
                .build();
    }

    public List<TelefoneDTOResponse> paraListaTelefoneDTO(List<Telefone> telefones) {
        return telefones.stream().map(this::paraTelefoneDTO).toList();
    }////////

    public TelefoneDTOResponse paraTelefoneDTO(Telefone telefone) {
        return TelefoneDTOResponse.builder()
                .id(telefone.getId())
                .numero(telefone.getNumero())
                .ddd(telefone.getDdd())
                .build();
    }

    public Usuario updateUsuario(UsuarioDTORequest usuarioDTO, Usuario entity) {
        return Usuario.builder()
                .nome(usuarioDTO.getNome() != null ? usuarioDTO.getNome() : entity.getNome())
                .id(entity.getId())
                .senha(usuarioDTO.getSenha() != null ? usuarioDTO.getSenha() : entity.getSenha())
                .email(usuarioDTO.getEmail() != null ? usuarioDTO.getEmail() : entity.getEmail())
                .enderecos(entity.getEnderecos())
                .telefones(entity.getTelefones())
                .build();
    }

    public Endereco updateEndereco(EnderecoDTORequest dto, Endereco entity) {
        return Endereco.builder()
                .id(entity.getId())
                .rua(dto.getRua() != null ? dto.getRua() : entity.getRua())
                .numero(dto.getNumero() != null ? dto.getNumero() : entity.getNumero())
                .complemento(dto.getComplemento() != null ? dto.getComplemento() : entity.getComplemento())
                .cep(dto.getCep() != null ? dto.getCep() : entity.getCep())
                .cidade(dto.getCidade() != null ? dto.getCidade() : entity.getCidade())
                .estado(dto.getEstado() != null ? dto.getEstado() : entity.getEstado())
                .usuarioId(entity.getUsuarioId())
                .build();

    }

    public Telefone updateTelefone(TelefoneDTORequest dto, Telefone entity) {
        return Telefone.builder()
                .id(entity.getId())
                .ddd(dto.getDdd() != null ? dto.getDdd() : entity.getDdd())
                .numero(dto.getNumero() != null ? dto.getNumero() : entity.getNumero())
                .usuarioId(entity.getUsuarioId())
                .build();
    }

    public Endereco paraEnderecoEntity(EnderecoDTORequest dto, Long idUsuario) {
        return Endereco.builder()
                .rua(dto.getRua())
                .numero(dto.getNumero())
                .complemento(dto.getComplemento())
                .cidade(dto.getCidade())
                .estado(dto.getEstado())
                .cep(dto.getCep())
                .usuarioId(idUsuario)
                .build();
    }

    public Telefone paraTelefoneEntity(TelefoneDTORequest dto, Long idUsuario) {
        return Telefone.builder()
                .ddd(dto.getDdd())
                .numero(dto.getNumero())
                .usuarioId(idUsuario)
                .build();
    }
}
