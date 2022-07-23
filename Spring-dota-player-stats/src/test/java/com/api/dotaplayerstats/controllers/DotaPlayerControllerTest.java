package com.api.dotaplayerstats.controllers;

import com.api.dotaplayerstats.builders.DotaPlayerBuilder;
import com.api.dotaplayerstats.models.DotaPlayerModel;
import com.api.dotaplayerstats.services.DotaPlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Optional;

import static com.api.dotaplayerstats.helpers.toJsonStringHelper.toJsonString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class DotaPlayerControllerTest {

    private MockMvc mockMvc;

    @Mock
    DotaPlayerService dotaPlayerService;

    @InjectMocks
    DotaPlayerController dotaPlayerController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(dotaPlayerController).build();
    }

    @Test
    void whenPostIsReceivedThenDotaPlayerIsCreated() throws Exception {

        // given
        DotaPlayerModel dotaPlayerModel = new DotaPlayerBuilder().build();

        // when
        Mockito.when(dotaPlayerService.existsByDotaPlayerAlias(dotaPlayerModel.getDotaPlayerAlias()))
                .thenReturn(false);
        Mockito.when(dotaPlayerService.save(Mockito.any(DotaPlayerModel.class)))
                .thenReturn(dotaPlayerModel);

        // then
        ResultActions result = mockMvc.perform(post("/dota-player")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJsonString(dotaPlayerModel)));

        result.andExpect(status().isCreated());
        assertEquals(toJsonString(dotaPlayerModel),
                    result.andReturn().getResponse().getContentAsString());
    }

    @Test
    void whenPostAliasIsAlreadyInUseThenConflictIsReturned() throws Exception {

        // given
        DotaPlayerModel dotaPlayerModel = new DotaPlayerBuilder().build();

        // when
        Mockito.when(dotaPlayerService.existsByDotaPlayerAlias(dotaPlayerModel.getDotaPlayerAlias()))
                .thenReturn(true);

        // then
        ResultActions result = mockMvc.perform(post("/dota-player")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJsonString(dotaPlayerModel)));

        result.andExpect(status().isConflict());
        assertEquals("Dota Player Alias already registered.",
                    result.andReturn().getResponse().getContentAsString());
    }

    @Test
    void whenGetAllIsCalledThenAListOfDotaPlayerIsReturned() throws Exception {

        // given
        var dotaPlayerModelList = new ArrayList<DotaPlayerModel>();
        var dotaPlayerModel1 = new DotaPlayerBuilder().build();
        var dotaPlayerModel2 = new DotaPlayerBuilder().build();
        dotaPlayerModel2.setId(2L);
        dotaPlayerModelList.add(dotaPlayerModel1);
        dotaPlayerModelList.add(dotaPlayerModel2);

        // when
        Mockito.when(dotaPlayerService.findAll()).thenReturn(dotaPlayerModelList);

        //then
        ResultActions result = mockMvc.perform(get("/dota-player"));

        result.andExpect(status().isOk());
        assertEquals(toJsonString(dotaPlayerModelList),
                    result.andReturn().getResponse().getContentAsString());
    }

    @Test
    void whenGetOneIsCalledThenDotaPlayerIsReturned() throws Exception {

        // given
        var dotaPlayerModel = new DotaPlayerBuilder().build();

        // when
        Mockito.when(dotaPlayerService.findByDotaPlayerAlias(Mockito.any()))
                .thenReturn(Optional.ofNullable(dotaPlayerModel));

        // then
        ResultActions result = mockMvc.perform(get("/dota-player/ana"));
        String resultBody = result.andReturn().getResponse().getContentAsString();

        result.andExpect(status().isOk());
        assertEquals(toJsonString(dotaPlayerModel), resultBody);
    }

    @Test
    void whenGetOneDotaPlayerAliasDoesNotExistsThenReturnNotFound() throws Exception {
        // given

        // when
        Mockito.when(dotaPlayerService.findByDotaPlayerAlias("ana"))
                .thenReturn(Optional.empty());

        // then
        ResultActions result = mockMvc.perform(get("/dota-player/ana"));

        result.andExpect(status().isNotFound());
    }

    @Test
    void whenPutIsReceivedThenDotaPlayerIsUpdated() throws Exception {

        // given
        var originalDotaPlayerModel = new DotaPlayerBuilder().build();
        var updatedDotaPlayerModel = new DotaPlayerModel();
        updatedDotaPlayerModel.setId(2L);
        updatedDotaPlayerModel.setDotaPlayerAlias("miracle-");
        updatedDotaPlayerModel.setDotaPlayerName("Amer Al-Barkawi");
        updatedDotaPlayerModel.setDotaPlayerRole("Carry");

        // when
        Mockito.when(dotaPlayerService.findByDotaPlayerAlias("ana"))
                .thenReturn(Optional.ofNullable(originalDotaPlayerModel));
        Mockito.when(dotaPlayerService.save(Mockito.any(DotaPlayerModel.class)))
                .thenReturn(updatedDotaPlayerModel);

        // then
        ResultActions result = mockMvc.perform(put("/dota-player/ana")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJsonString(updatedDotaPlayerModel)));
        String resultBody = result.andReturn().getResponse().getContentAsString();

        result.andExpect(status().isOk());
        assertEquals(toJsonString(updatedDotaPlayerModel), resultBody);
    }

    @Test
    void whenPutDotaPlayerAliasIsInvalidThenReturnNotFound() throws Exception {

        // given
        var dotaPlayerModel = new DotaPlayerBuilder().build();

        // when
        Mockito.when(dotaPlayerService.findByDotaPlayerAlias("miracle-"))
                .thenReturn(Optional.empty());

        // then
        ResultActions result = mockMvc.perform(put("/dota-player/miracle-")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJsonString(dotaPlayerModel)));
        String resultBody = result.andReturn().getResponse().getContentAsString();

        result.andExpect(status().isNotFound());
        assertEquals("Dota Player Alias not found.", resultBody);
    }

    @Test
    void whenDeleteThenReturnOk() throws Exception {

        // given
        var dotaPlayerModel = new DotaPlayerBuilder().build();

        // when
        Mockito.when(dotaPlayerService.findByDotaPlayerAlias("ana"))
                .thenReturn(Optional.ofNullable(dotaPlayerModel));

        // then
        ResultActions result = mockMvc.perform(delete("/dota-player/ana"));

        result.andExpect(status().isOk());
    }

    @Test
    void whenDeleteDotaPlayerAliasDoesNotExistsThenReturnNotFound() throws Exception {

        // given

        // when
        Mockito.when(dotaPlayerService.findByDotaPlayerAlias("ana"))
                .thenReturn(Optional.empty());

        // then
        ResultActions result = mockMvc.perform(delete("/dota-player/ana"));

        result.andExpect(status().isNotFound());
    }
}
