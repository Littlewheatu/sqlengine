package com.zzu.sqlconfig.controller;

import com.zzu.sqlconfig.service.ExcuteSqlService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.LinkedList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
public class ExcuteSqlControllerTest {

    @MockBean
    private ExcuteSqlService excuteSqlService;

    @Test
    public void getSqlTest(){
        when(excuteSqlService.getSql("")).thenReturn(new LinkedList<>());
    }
}
