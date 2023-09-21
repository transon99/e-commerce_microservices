package com.eastgate.serviceregisty.configserver.productservice.controller;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.eastgate.serviceregisty.configserver.productservice.controller.CategoryController;
import com.eastgate.serviceregisty.configserver.productservice.controller.CustomUtils;
import com.eastgate.serviceregisty.configserver.productservice.dto.CategoryDto;
import com.eastgate.serviceregisty.configserver.productservice.entity.Category;
import com.eastgate.serviceregisty.configserver.productservice.mapper.CategoryMapper;
import com.eastgate.serviceregisty.configserver.productservice.service.CategoryService;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;

@Transactional
public class CategoryControllerTest {

    private static final String ENDPOINT_URL = "/api/category";

    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private CategoryService categoryService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(categoryController)
                .build();
    }

    @Test
    public void findAllByPage() throws Exception {
        Page<CategoryDto> page = new PageImpl<>(Collections.singletonList(CategoryBuilder.getDto()));

        Mockito.when(categoryService.findByCondition(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(page);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content", Matchers.hasSize(1)));

        Mockito.verify(categoryService, Mockito.times(1))
                .findByCondition(ArgumentMatchers.any(), ArgumentMatchers.any());
        Mockito.verifyNoMoreInteractions(categoryService);
    }

    @Test
    public void getById() throws Exception {
        Mockito.when(categoryService.findById(ArgumentMatchers.anyLong()))
                .thenReturn(CategoryBuilder.getDto());

        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL + "/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)));

        Mockito.verify(categoryService, Mockito.times(1)).findById("1");
        Mockito.verifyNoMoreInteractions(categoryService);
    }

    @Test
    public void save() throws Exception {
        Mockito.when(categoryService.save(ArgumentMatchers.any(CategoryDto.class)))
                .thenReturn(CategoryBuilder.getDto());

        mockMvc.perform(
                        MockMvcRequestBuilders.post(ENDPOINT_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(CustomUtils.asJsonString(CategoryBuilder.getDto())))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        Mockito.verify(categoryService, Mockito.times(1)).save(ArgumentMatchers.any(CategoryDto.class));
        Mockito.verifyNoMoreInteractions(categoryService);
    }

    @Test
    public void update() throws Exception {
        Mockito.when(categoryService.update(ArgumentMatchers.any(), ArgumentMatchers.anyLong()))
                .thenReturn(CategoryBuilder.getDto());

        mockMvc.perform(
                        MockMvcRequestBuilders.put(ENDPOINT_URL + "/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(CustomUtils.asJsonString(CategoryBuilder.getDto())))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(categoryService, Mockito.times(1))
                .update(ArgumentMatchers.any(CategoryDto.class), ArgumentMatchers.anyLong());
        Mockito.verifyNoMoreInteractions(categoryService);
    }

    @Test
    public void delete() throws Exception {
        Mockito.doNothing().when(categoryService).deleteById(ArgumentMatchers.anyLong());

        mockMvc.perform(
                        MockMvcRequestBuilders.delete(ENDPOINT_URL + "/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(CustomUtils.asJsonString(CategoryBuilder.getIds())))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(categoryService, Mockito.times(1))
                .deleteById(Mockito.anyLong());
        Mockito.verifyNoMoreInteractions(categoryService);
    }

}
