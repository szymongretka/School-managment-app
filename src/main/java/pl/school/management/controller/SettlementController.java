package pl.school.management.controller;

import org.springframework.web.bind.annotation.*;
import pl.school.management.model.api.response.SettlementResponse;
import pl.school.management.service.SettlementService;

import java.time.Month;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(path = "/api/settlement")
public class SettlementController {

    private final SettlementService settlementService;

    public SettlementController(SettlementService settlementService) {
        this.settlementService = settlementService;
    }

    @GetMapping("/school/{schoolId}")
    public List<SettlementResponse> schoolSettlement(@PathVariable Long schoolId,
                                                     @RequestParam(required = false) Month month) {
        return settlementService.getSettlementForSchool(schoolId,
                Objects.isNull(month) ? Calendar.getInstance().get(Calendar.MONTH) + 1 : month.getValue());
    }

    @GetMapping("/parent/{schoolId}/{parentId}")
    public SettlementResponse parentSettlement(@PathVariable Long schoolId,
                                               @PathVariable Long parentId,
                                               @RequestParam(required = false) Month month) {
        return settlementService.getSettlementForParent(schoolId, parentId,
                Objects.isNull(month) ? Calendar.getInstance().get(Calendar.MONTH) + 1 : month.getValue());
    }

}
