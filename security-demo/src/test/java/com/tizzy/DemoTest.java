package com.tizzy;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void whenQuerySuccess() throws Exception {
        mockMvc.perform(get("/user").contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("size", "15")
                .param("page", "1")
                .param("sort", "age,desc", "name,asc"))
                .andExpect(status().isOk())
                //https://github.com/jayway/JsonPath
                .andExpect(jsonPath("$.length()", is(3)));
    }

    @Test
    public void whenGetInfoSuccess() throws Exception {
        mockMvc.perform(get("/user/2").contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(2)));
    }

    @Test
    public void whenGetInfoFail() throws Exception {
        mockMvc.perform(get("/user/213edf").contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void whenCreateSuccess() throws Exception {

        //language=json
        String content = "{\"username\": \"tom\", \"password\": \"buzhidao\", \"age\": \"12\", \"birthday\": \"" + System.currentTimeMillis() + "\"}";
        MockHttpServletRequestBuilder builder = post("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content);
        String result = mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andReturn().getResponse().getContentAsString();

        System.out.println(result);
    }

    @Test
    public void whenCreateFail() throws Exception {
//        LocalDateTime plus = LocalDateTime.now().plus(Duration.ofDays(365));
        LocalDateTime plus = LocalDateTime.now().plus(1, ChronoUnit.YEARS);
        System.out.println(plus);
        long epochMilli = plus.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        //language=json
        String content = "{\"username\": null, \"password\": null, \"age\": \"12\", \"birthday\": \"" + epochMilli + "\"}";
        MockHttpServletRequestBuilder builder = post("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content);
        String result = mockMvc.perform(builder)
                .andExpect(status().is4xxClientError())
                .andReturn().getResponse().getContentAsString();

        System.out.println("result: " + result);
    }

    @Test
    public void whenUpdateSuccess() throws Exception {
        //language=json
        String content = "{\"username\": \"tom\", \"password\": \"buzhidao1\", \"age\": \"13\", \"birthday\": \"" + System.currentTimeMillis() + "\"}";
        MockHttpServletRequestBuilder builder = put("/user/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content);
        String result = mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.password", is("buzhidao1")))
                .andReturn().getResponse().getContentAsString();

        System.out.println(result);
    }
}
