package com.rytech.usuario.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rytech.usuario.business.UsuarioService;
import com.rytech.usuario.business.ViaCepService;
import com.rytech.usuario.business.dto.in.EnderecoDTORequest;
import com.rytech.usuario.business.dto.in.TelefoneDTORequest;
import com.rytech.usuario.business.dto.in.UsuarioDTORequest;
import com.rytech.usuario.business.dto.out.EnderecoDTOResponse;
import com.rytech.usuario.business.dto.out.TelefoneDTOResponse;
import com.rytech.usuario.business.dto.out.UsuarioDTOResponse;
import com.rytech.usuario.infrastructure.clients.ViaCepDTO;
import com.rytech.usuario.business.dto.in.EnderecoDTORequestFixture;
import com.rytech.usuario.business.dto.in.TelefoneDTORequestFixture;
import com.rytech.usuario.business.dto.in.UsuarioDTORequestFixture;
import com.rytech.usuario.business.dto.out.EnderecoDTOResponseFixture;
import com.rytech.usuario.business.dto.out.TelefoneDTOResponseFixture;
import com.rytech.usuario.business.dto.out.UsuarioDTOResponseFixture;
import com.rytech.usuario.infrastructure.clients.ViaCepDTOFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UsuarioControllerTests {

    @InjectMocks
    UsuarioController usuarioController;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private ViaCepService viaCepService;

    MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private String url;

    UsuarioDTORequest usuarioDTORequest;

    EnderecoDTORequest enderecoDTORequest;

    TelefoneDTORequest telefoneDTORequest;

    UsuarioDTOResponse usuarioDTOResponse;

    EnderecoDTOResponse enderecoDTOResponse;

    TelefoneDTOResponse telefoneDTOResponse;

    private ViaCepDTO viaCepDTO;

    private String json;

    private String enderecoJson;

    private String telefoneJson;


    @BeforeEach
    public void setup() throws JsonProcessingException {
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).alwaysDo(print()).build();
        url = "/usuario/login";
        viaCepDTO = ViaCepDTOFixture.build("44340000", "Rua das Flores",
                "Apto 101", "A", "Centro", "Salvador", "BA",
                "Salvador", "Nordeste", "3550308", "1004", "75", "7107");
        telefoneDTORequest = TelefoneDTORequestFixture.build("34243526", "71");
        enderecoDTORequest = EnderecoDTORequestFixture.build("Teste Unitário", "12345",
                "Complemento", "São Paulo", "SP", "44340000");
        usuarioDTORequest = UsuarioDTORequestFixture.build("rytechh", "rytechh21@gmail.com", "1234",
                List.of(enderecoDTORequest), List.of(telefoneDTORequest));
        json = objectMapper.writeValueAsString(usuarioDTORequest);
        enderecoJson = objectMapper.writeValueAsString(enderecoDTORequest);
        telefoneJson = objectMapper.writeValueAsString(telefoneDTORequest);
        telefoneDTOResponse = TelefoneDTOResponseFixture.build(21312L, "34243526", "71");
        enderecoDTOResponse = EnderecoDTOResponseFixture.build(2109L, "Teste Unitário", "12345",
                "Complemento", "São Paulo", "SP", "44340000");
        usuarioDTOResponse = UsuarioDTOResponseFixture.build("rytechh", "rytechh21@gmail.com", "1234",
                List.of(enderecoDTOResponse), List.of(telefoneDTOResponse));
    }

    @Test
    void deveSalvarDadosDeUsuarioComSucesso() throws Exception {
        when(usuarioService.salvaUsuario(any())).thenReturn(usuarioDTOResponse);

        mockMvc.perform(post("/usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isOk());

        verify(usuarioService).salvaUsuario(any());
        verifyNoMoreInteractions(usuarioService);

    }


    @Test
    void naoDeveSalvarDadosDeUsuarioCasoJsonNullo() throws Exception {
        mockMvc.perform(post("/usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());

        verifyNoInteractions(usuarioService);
    }

    @Test
    void deveAutenticarUsuarioComSucesso() throws Exception {
        when(usuarioService.autenticarUsuario(any())).thenReturn("Bearer token-fake");

        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isOk());

        verify(usuarioService).autenticarUsuario(any());
        verifyNoMoreInteractions(usuarioService);
    }

    @Test
    void naoDeveAutenticarUsuarioCasoJsonNullo() throws Exception {
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());

        verifyNoInteractions(usuarioService);
    }


    @Test
    void deveBuscarUsuarioPorEmailComSucesso() throws Exception {
        when(usuarioService.buscarUsuarioPorEmail("email@teste.com")).thenReturn(usuarioDTOResponse);

        mockMvc.perform(get("/usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("email", "email@teste.com")
        ).andExpect(status().isOk());

        verify(usuarioService).buscarUsuarioPorEmail("email@teste.com");
        verifyNoMoreInteractions(usuarioService);
    }

    @Test
    void naoDeveBuscarUsuarioPorEmailCasoJsonNulo() throws Exception {
        mockMvc.perform(get("/usuario")
                .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(status().isBadRequest());

        verifyNoInteractions(usuarioService);
    }

    @Test
    void deveDeletarUsuarioPorEmailComSucesso() throws Exception {

        mockMvc.perform(delete("/usuario/rytechh21@gmail.com")
                .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(status().isOk());

        verify(usuarioService).deletaUsuarioPorEmail("rytechh21@gmail.com");
        verifyNoMoreInteractions(usuarioService);
    }

    @Test
    void deveAtualizarDadosDeUsuarioComSucesso() throws Exception {
        when(usuarioService.atualizaDadosUsuario(eq("Bearer token-fake"),
                any())).thenReturn(usuarioDTOResponse);

        mockMvc.perform(put("/usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
                .header("Authorization", "Bearer token-fake")
        ).andExpect(status().isOk());

        verify(usuarioService).atualizaDadosUsuario(eq("Bearer token-fake"), any());
        verifyNoMoreInteractions(usuarioService);
    }

    @Test
    void naoDeveAtualizarDadosDeUsuarioCasoJsonNulo() throws Exception {

        mockMvc.perform(put("/usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer token-fake")
        ).andExpect(status().isBadRequest());

        verifyNoInteractions(usuarioService);
    }

    @Test
    void deveAtualizarEnderecoComSucesso() throws Exception {
        when(usuarioService.atualizaEndereco(eq(2109L), any())).thenReturn(enderecoDTOResponse);

        mockMvc.perform(put("/usuario/endereco")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
                .param("id", "2109")
        ).andExpect(status().isOk());

        verify(usuarioService).atualizaEndereco(eq(2109L), any());
        verifyNoMoreInteractions(usuarioService);
    }

    @Test
    void naoDeveAtualizarEnderecoSemBody() throws Exception {

        mockMvc.perform(put("/usuario/endereco")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("id", "2109")
        ).andExpect(status().isBadRequest());

        verifyNoInteractions(usuarioService);

    }

    @Test
    void naoDeveAtualizarEnderecoSemId() throws Exception {

        mockMvc.perform(put("/usuario/endereco")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(enderecoJson)
        ).andExpect(status().isBadRequest());

        verifyNoInteractions(usuarioService);
    }

    @Test
    void deveAtualizarTelefoneComSucesso() throws Exception {
        when(usuarioService.atualizaTelefone(eq(1410L),
                any())).thenReturn(telefoneDTOResponse);

        mockMvc.perform(put("/usuario/telefone")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(telefoneJson)
                .param("id", "1410")
        ).andExpect(status().isOk());

        verify(usuarioService).atualizaTelefone(eq(1410L), any());
        verifyNoMoreInteractions(usuarioService);
    }

    @Test
    void naoDeveAtualizarTelefoneSemBody() throws Exception {

        mockMvc.perform(put("/usuario/telefone")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("id", "1410")
        ).andExpect(status().isBadRequest());

        verifyNoInteractions(usuarioService);
    }

    @Test
    void naoDeveAtualizarTelefoneSemId() throws Exception {

        mockMvc.perform(put("/usuario/telefone")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(telefoneJson)
        ).andExpect(status().isBadRequest());

        verifyNoInteractions(usuarioService);
    }

    @Test
    void deveCadastrarEnderecoComSucesso() throws Exception {
        when(usuarioService.cadastraEndereco(eq("Bearer token-fake"),
                any())).thenReturn(enderecoDTOResponse);

        mockMvc.perform(post("/usuario/endereco")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(enderecoJson)
                .header("Authorization", "Bearer token-fake")
        ).andExpect(status().isOk());

        verify(usuarioService).cadastraEndereco(eq("Bearer token-fake"), any());
        verifyNoMoreInteractions(usuarioService);
    }

    @Test
    void naoDeveCadastrarEnderecoSemBody() throws Exception {

        mockMvc.perform(post("/usuario/endereco")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer token-fake")
        ).andExpect(status().isBadRequest());

        verifyNoInteractions(usuarioService);
    }

    @Test
    void naoDeveCadastrarEnderecoSemHeader() throws Exception {

        mockMvc.perform(post("/usuario/endereco")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(enderecoJson)
        ).andExpect(status().isBadRequest());

        verifyNoInteractions(usuarioService);
    }

    @Test
    void deveCadastrarTelefoneComSucesso() throws Exception {
        when(usuarioService.cadastraTelefone(eq("Bearer token-fake"),
                any())).thenReturn(telefoneDTOResponse);

        mockMvc.perform(post("/usuario/telefone")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(telefoneJson)
                .header("Authorization", "Bearer token-fake")
        ).andExpect(status().isOk());

        verify(usuarioService).cadastraTelefone(eq("Bearer token-fake"), any());
        verifyNoMoreInteractions(usuarioService);
    }

    @Test
    void naoDeveCadastrarTelefoneSemBody() throws Exception {

        mockMvc.perform(post("/usuario/telefone")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer token-fake")
        ).andExpect(status().isBadRequest());

        verifyNoInteractions(usuarioService);
    }

    @Test
    void naoDeveCadastrarTelefoneSemHeader() throws Exception {

        mockMvc.perform(post("/usuario/telefone")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(telefoneJson)
        ).andExpect(status().isBadRequest());

        verifyNoInteractions(usuarioService);
    }

    @Test
    void deveBuscarDadosViaCepComSucesso() throws Exception {
        when(viaCepService.buscaDadosEndereco("44340000")).thenReturn(viaCepDTO);

        mockMvc.perform(get("/usuario/endereco/44340000")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        verify(viaCepService).buscaDadosEndereco("44340000");
        verifyNoMoreInteractions(viaCepService);
    }

    @Test
    void naoDeveBuscarDadosSemCep() throws Exception {

        mockMvc.perform(get("/usuario/endereco")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isMethodNotAllowed());

        verifyNoInteractions(viaCepService);
    }
}
