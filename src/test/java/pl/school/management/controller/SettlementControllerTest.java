package pl.school.management.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.school.management.model.api.response.SettlementResponse;
import pl.school.management.service.SettlementService;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(SettlementController.class)
class SettlementControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SettlementService settlementService;

    @Test
    void schoolSettlement() throws Exception {
        SettlementResponse settlementResponse = new SettlementResponse();
        settlementResponse.setParentFirstName("Jacob");
        Mockito.when(settlementService.getSettlementForSchool(anyLong(), anyInt()))
                .thenReturn(List.of(settlementResponse));

        mvc.perform(get("/api/settlement/school/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].parentFirstName", is("Jacob")));
    }

    @Test
    void parentSettlement() throws Exception {
        SettlementResponse settlementResponse = new SettlementResponse();
        settlementResponse.setParentFirstName("Jacob");
        Mockito.when(settlementService.getSettlementForParent(anyLong(), anyLong(), anyInt()))
                .thenReturn(settlementResponse);

        mvc.perform(get("/api/settlement/parent/1/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.parentFirstName", is("Jacob")));
    }

}