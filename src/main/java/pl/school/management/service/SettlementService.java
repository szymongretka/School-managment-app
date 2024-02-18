package pl.school.management.service;

import pl.school.management.model.api.response.SettlementResponse;

import java.time.Month;
import java.util.List;

public interface SettlementService {

    List<SettlementResponse> getSettlementForSchool(Long schoolId, int month);

    SettlementResponse getSettlementForParent(Long schoolId, Long parentId, int month);

}
