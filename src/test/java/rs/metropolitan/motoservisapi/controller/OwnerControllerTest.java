package rs.metropolitan.motoservisapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import rs.metropolitan.motoservisapi.model.Owner;
import rs.metropolitan.motoservisapi.service.OwnerService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class OwnerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OwnerService ownerService;

    private Owner owner;

    @BeforeEach
    void setUp() {
        owner = new Owner(1L, "Milos", "Pavlovic", "0641234567", "milos@email.com");
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void findAll_shouldReturnListOfOwners() throws Exception {
        when(ownerService.findAll()).thenReturn(List.of(owner));

        mockMvc.perform(get("/api/owners")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Milos"))
                .andExpect(jsonPath("$[0].lastName").value("Pavlovic"));
    }

    @Test
    @WithMockUser(authorities = "ROLE_USER")
    void findAll_shouldReturnOwners_forUserRole() throws Exception {
        when(ownerService.findAll()).thenReturn(List.of(owner));

        mockMvc.perform(get("/api/owners")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void findById_shouldReturnOwner_whenExists() throws Exception {
        when(ownerService.findById(1L)).thenReturn(owner);

        mockMvc.perform(get("/api/owners/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Milos"))
                .andExpect(jsonPath("$.email").value("milos@email.com"));
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void save_shouldCreateOwner_whenAdmin() throws Exception {
        when(ownerService.save(any(Owner.class))).thenReturn(owner);

        mockMvc.perform(post("/api/owners")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(owner)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Milos"));
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void update_shouldUpdateOwner_whenAdmin() throws Exception {
        Owner updated = new Owner(1L, "Nikola", "Nikolic", "069123", "nikola@email.com");
        when(ownerService.update(eq(1L), any(Owner.class))).thenReturn(updated);

        mockMvc.perform(put("/api/owners/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Nikola"));
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void delete_shouldReturn204_whenAdmin() throws Exception {
        doNothing().when(ownerService).deleteById(1L);

        mockMvc.perform(delete("/api/owners/1")
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    void findAll_shouldReturn401_whenNotAuthenticated() throws Exception {
        mockMvc.perform(get("/api/owners")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden()); // 403 umesto 401
    }
}
