/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ldf.exam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ldf.exam.security.JWTUtil;
import javax.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @author ldiez
 */
//@ExtendWith(SpringExtension.class). not necessary included in @SpringBootTest from Spring Boot 2.1 ahead
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:db-test.properties")
@Sql(config = @SqlConfig(encoding = "UTF-8"), scripts = "/sql/test-degree.sql")
public class DegreeControllerTest {
    
    @Value("${jwt_cookie_name}")
    private String cookieName;   

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private JWTUtil jwtutil;


    // @WithMockUser("Consultant")
    @Test
    public void find_login_ok() throws Exception {

        String jwtCookie = jwtutil.generateToken("AliciaVerdu");
        Cookie cookie = new Cookie(cookieName,jwtCookie);
        cookie.setPath("/api");
        cookie.setMaxAge(24 * 60 * 60);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        //cookie.setSameSite("none");
        mockMvc.perform(get("/api/degree/degrees").cookie(cookie))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Ingenieria Informática")))
                .andExpect(jsonPath("$[*].name", hasSize(10)))
                .andExpect(jsonPath("$[*].name", 
                        hasItems("Ingenieria Informática"
                                ,"Bioinformática"
                                ,"Derecho"
                                ,"Ingenieria Industrial"
                                ,"Quimicas"
                                ,"Física"
                                ,"ADE"                                
                                ,"Marketing"
                                ,"Informática de Gestión"
                                ,"Informática de Sistemas")));
    }

    //@Test
    public void find_nologin_401() throws Exception {
        mockMvc.perform(get("/api/degree/degrees"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
    
//        @Test
    public void degreesByStudentId() throws Exception {
        mockMvc.perform(get("/api/degree/degreesByStudentId/2"))
                .andDo(print());

    }

}
