package com.rytech.usuario.business;

import com.rytech.usuario.business.converter.UsuarioConverter;
import com.rytech.usuario.business.dto.in.EnderecoDTORequest;
import com.rytech.usuario.business.dto.in.TelefoneDTORequest;
import com.rytech.usuario.business.dto.in.UsuarioDTORequest;
import com.rytech.usuario.business.dto.out.EnderecoDTOResponse;
import com.rytech.usuario.business.dto.out.TelefoneDTOResponse;
import com.rytech.usuario.business.dto.out.UsuarioDTOResponse;
import com.rytech.usuario.infrastructure.entity.Endereco;
import com.rytech.usuario.infrastructure.entity.Telefone;
import com.rytech.usuario.infrastructure.entity.Usuario;
import com.rytech.usuario.infrastructure.exceptions.ConflictException;
import com.rytech.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.rytech.usuario.infrastructure.exceptions.UnauthorizedException;
import com.rytech.usuario.infrastructure.repository.EnderecoRepository;
import com.rytech.usuario.infrastructure.repository.TelefoneRepository;
import com.rytech.usuario.infrastructure.repository.UsuarioRepository;
import com.rytech.usuario.infrastructure.security.JwtUtil;
import com.rytech.usuario.business.dto.in.EnderecoDTORequestFixture;
import com.rytech.usuario.business.dto.in.TelefoneDTORequestFixture;
import com.rytech.usuario.business.dto.in.UsuarioDTORequestFixture;
import com.rytech.usuario.business.dto.out.EnderecoDTOResponseFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {


    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private EnderecoRepository enderecoRepository;

    @Mock
    private TelefoneRepository telefoneRepository;

    @Mock
    private UsuarioConverter usuarioConverter;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    Usuario usuarioEntity;

    Endereco enderecoEntity;

    Telefone telefoneEntity;

    UsuarioDTORequest usuarioDTORequest;

    TelefoneDTORequest telefoneDTORequest;

    EnderecoDTORequest enderecoDTORequest;

    UsuarioDTOResponse usuarioDTOResponse;

    EnderecoDTOResponse enderecoDTOResponse;

    TelefoneDTOResponse telefoneDTOResponse;


    @BeforeEach
    public void setUp() {
        enderecoEntity = Endereco.builder()
                .rua("Teste Unitário")
                .numero("12345")
                .cep("44340000")
                .cidade("São Paulo")
                .complemento("Complemento")
                .estado("SP")
                .build();
        telefoneEntity = Telefone.builder()
                .numero("34243526")
                .ddd("71")
                .build();
        usuarioEntity = Usuario.builder()
                .nome("rytechh")
                .email("rytechh21@gmail.com")
                .senha("1234")
                .enderecos(List.of(enderecoEntity))
                .telefones(List.of(telefoneEntity))
                .build();
        telefoneDTORequest = TelefoneDTORequestFixture.build("34243526", "71");
        enderecoDTORequest = EnderecoDTORequestFixture.build("Teste Unitário", "12345",
                "Complemento", "São Paulo", "SP", "44340000");
        usuarioDTORequest = UsuarioDTORequestFixture.build("rytechh", "rytechh21@gmail.com", "1234",
                List.of(enderecoDTORequest), List.of(telefoneDTORequest));
        enderecoDTOResponse = EnderecoDTOResponseFixture.build(2109L, "Teste Unitário", "12345",
                "Complemento", "São Paulo", "SP", "44340000");
    }


    @Test
    void testLoginSuccess() {
        // Dado que o usuario está registrado no sistema GIVEN

        // Quando o usuario tenta fazer login WHEN

        // Então o sistema deve conceder acesso ao usuario THEN
    }

    @Test
    void deveSalvarUsuarioComSucesso() {
        when(usuarioRepository.existsByEmail(any())).thenReturn(false);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioEntity);
        when(usuarioConverter.paraUsuario(any())).thenReturn(usuarioEntity);
        when(usuarioConverter.paraUsuarioDTO(any())).thenReturn(usuarioDTOResponse);
        when(passwordEncoder.encode(any())).thenReturn("1234");

        UsuarioDTOResponse dtoResponse = usuarioService.salvaUsuario(usuarioDTORequest);

        assertEquals(usuarioDTOResponse, dtoResponse);

        verify(usuarioRepository).existsByEmail(any());
        verify(usuarioRepository).save(any(Usuario.class));
        verifyNoMoreInteractions(usuarioRepository);
    }

    @Test
    void deveLancarExcecaoEmailJaExiste() {
        when(usuarioRepository.existsByEmail(any())).thenReturn(true);

        assertThrows(ConflictException.class, ()
                -> usuarioService.salvaUsuario(usuarioDTORequest));

        verify(usuarioRepository).existsByEmail(any());
        verifyNoMoreInteractions(usuarioRepository);
    }

    @Test
    void deveAutenticarUsuarioComSucesso() {
        Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.getName()).thenReturn("rytechh@gmail.com");
        when(jwtUtil.generateToken(any())).thenReturn("token-fake");

        String resultado = usuarioService.autenticarUsuario(usuarioDTORequest);

        assertEquals("Bearer token-fake", resultado);

    }

    @Test
    void deveLancarExcecaoEmailExistente() {
        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Inválido "));

        assertThrows(UnauthorizedException.class, () ->
                usuarioService.autenticarUsuario(usuarioDTORequest));

    }

    @Test
    void deveBuscarUsuarioPorEmail() {
        when(usuarioRepository.findByEmail(any())).thenReturn(Optional.of(usuarioEntity));
        when(usuarioConverter.paraUsuarioDTO(any())).thenReturn(usuarioDTOResponse);

        UsuarioDTOResponse result = usuarioService.buscarUsuarioPorEmail("rytechh21@gmail.com");

        assertEquals(usuarioDTOResponse, result);
        verify(usuarioRepository).findByEmail(any());

    }

    @Test
    void deveLancarExcecaoEmailNaoEncontrado() {
        when(usuarioRepository.findByEmail(any())).
                thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> usuarioService.buscarUsuarioPorEmail("Não encontrado"));

        verify(usuarioRepository).findByEmail(any());
    }

    @Test
    void deveDeletarUsuarioPorEmail() {
        usuarioService.deletaUsuarioPorEmail("rytechh21@gmail.com");

        verify(usuarioRepository).deleteByEmail(any());
        verifyNoMoreInteractions(usuarioRepository);
    }

    @Test
    void deveAtualizarDadosUsuario() {
        when(jwtUtil.extrairEmailToken(any())).thenReturn("rytechh21@gmail.com");
        when(usuarioRepository.findByEmail(any())).thenReturn(Optional.of(usuarioEntity));
        when(usuarioConverter.updateUsuario(any(), any())).thenReturn(usuarioEntity);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioEntity);
        when(usuarioConverter.paraUsuarioDTO(any())).thenReturn(usuarioDTOResponse);

        UsuarioDTOResponse result = usuarioService.atualizaDadosUsuario("Bearer token-fake", usuarioDTORequest);

        assertEquals(usuarioDTOResponse, result);
        verify(usuarioRepository).findByEmail(any());
        verify(usuarioRepository).save(any(Usuario.class));
        verifyNoMoreInteractions(usuarioRepository);
    }


    @Test
    void deveLancarExcecaoAoAtualizarUsuarioNaoEncontrado() {
        when(jwtUtil.extrairEmailToken(any())).thenReturn("naocadastrado@gmail.com");
        when(usuarioRepository.findByEmail(any())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> usuarioService.atualizaDadosUsuario("Bearer token-fake", usuarioDTORequest));

        verify(usuarioRepository).findByEmail(any());
        verifyNoMoreInteractions(usuarioRepository);

    }

    @Test
    void deveAtualizarEnderecoComSucesso() {
        when(enderecoRepository.findById(any())).thenReturn(Optional.of(enderecoEntity));
        when(usuarioConverter.paraEnderecoDTO(any())).thenReturn(enderecoDTOResponse);
        when(usuarioConverter.updateEndereco(any(), any())).thenReturn(enderecoEntity);
        when(enderecoRepository.save(any(Endereco.class))).thenReturn(enderecoEntity);

        EnderecoDTOResponse atualizaEndereco = usuarioService.atualizaEndereco(2109L, enderecoDTORequest);

        assertEquals(enderecoDTOResponse, atualizaEndereco);
        verify(enderecoRepository).findById(any());
        verify(enderecoRepository).save(any(Endereco.class));
        verifyNoMoreInteractions(enderecoRepository);
    }

    @Test
    void deveLancarExcecaoAoAtualizarEnderecoNaoEncontrado() {
        when(enderecoRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> usuarioService.atualizaEndereco(2109L, enderecoDTORequest));

        verify(enderecoRepository).findById(any());
        verifyNoMoreInteractions(enderecoRepository);
    }

    @Test
    void deveAtualizarTelefoneComSucesso() {
        when(telefoneRepository.findById(any())).thenReturn(Optional.of(telefoneEntity));
        when(usuarioConverter.updateTelefone(any(), any())).thenReturn(telefoneEntity);
        when(usuarioConverter.paraTelefoneDTO(any())).thenReturn(telefoneDTOResponse);
        when(telefoneRepository.save(any(Telefone.class))).thenReturn(telefoneEntity);

        TelefoneDTOResponse result = usuarioService.atualizaTelefone(1410L, telefoneDTORequest);

        assertEquals(telefoneDTOResponse, result);
        verify(telefoneRepository).findById(any());
        verify(telefoneRepository).save(any(Telefone.class));
        verifyNoMoreInteractions(telefoneRepository);
    }

    @Test
    void deveLancarExcecaoAoAtualizarTelefoneNaoEncontrado() {
        when(telefoneRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> usuarioService.atualizaTelefone(1410L, telefoneDTORequest));

        verify(telefoneRepository).findById(any());
        verifyNoMoreInteractions(telefoneRepository);

    }

    @Test
    void deveCadastrarEnderecoComSucesso() {
        when(usuarioRepository.findByEmail(any())).thenReturn(Optional.of(usuarioEntity));
        when(usuarioConverter.paraEnderecoEntity(any(), any())).thenReturn(enderecoEntity);
        when(usuarioConverter.paraEnderecoDTO(any())).thenReturn(enderecoDTOResponse);
        when(jwtUtil.extrairEmailToken(any())).thenReturn("raian21@gmail.com");
        when(enderecoRepository.save(any(Endereco.class))).thenReturn(enderecoEntity);

        EnderecoDTOResponse result = usuarioService.cadastraEndereco("Bearer token-fake", enderecoDTORequest);

        assertEquals(enderecoDTOResponse, result);
        verify(usuarioRepository).findByEmail(any());
        verify(enderecoRepository).save(any(Endereco.class));

    }

    @Test
    void deveLancarExcecaoAoCadastrarEnderecoUsuarioNaoEncontrado() {
        when(jwtUtil.extrairEmailToken(any())).thenReturn("naocadastrado@gmail.com");
        when(usuarioRepository.findByEmail(any())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> usuarioService.cadastraEndereco("Bearer token-fake", enderecoDTORequest));

        verify(usuarioRepository).findByEmail(any());
        verifyNoMoreInteractions(usuarioRepository);

    }

    @Test
    void deveCadastrarTelefoneComSucesso() {
        when(usuarioRepository.findByEmail(any())).thenReturn(Optional.of(usuarioEntity));
        when(jwtUtil.extrairEmailToken(any())).thenReturn("rytechh21@gmail.com");
        when(telefoneRepository.save(any(Telefone.class))).thenReturn(telefoneEntity);
        when(usuarioConverter.paraTelefoneDTO(any())).thenReturn(telefoneDTOResponse);
        when(usuarioConverter.paraTelefoneEntity(any(), any())).thenReturn(telefoneEntity);


        TelefoneDTOResponse result = usuarioService.cadastraTelefone("Bearer token-fake", telefoneDTORequest);

        assertEquals(telefoneDTOResponse, result);
        verify(usuarioRepository).findByEmail(any());
        verify(telefoneRepository).save(any(Telefone.class));
    }

    @Test
    void deveLancarExcecaoAoCadastrarTelefoneNaoEncontrado() {
        when(jwtUtil.extrairEmailToken(any())).thenReturn("naocadastrado@gmail.com");
        when(usuarioRepository.findByEmail(any())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> usuarioService.cadastraTelefone("Bearer token-fake", telefoneDTORequest));

        verify(usuarioRepository).findByEmail(any());
        verifyNoMoreInteractions(usuarioRepository);
    }
}
