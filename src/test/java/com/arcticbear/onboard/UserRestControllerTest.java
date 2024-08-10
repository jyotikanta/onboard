package com.arcticbear.onboard;

import com.arcticbear.onboard.entity.User;
import com.arcticbear.onboard.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest(CustomerRestController.class)
class UserRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser
    public void register_ShouldReturnCreatedStatus() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setEmail("ps@example.com");
        user.setMobile("7854236985");
        user.setPassword("password123");

        //Mockito.doNothing().when(customerService).save(Mockito.any(Customer.class));
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"ps@example.com\", \"mobile\":\"7854236985\", \"password\":\"password123\"}"))
                .andExpect(status().isCreated());
    }

}