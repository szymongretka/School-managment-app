package pl.school.management.model.api.response;

import java.util.List;

public class SettlementResponse {

    private String parentFirstName;
    private String parentLastName;
    private Float totalFees;
    private List<ChildFee> feeList;

    public String getParentFirstName() {
        return parentFirstName;
    }

    public void setParentFirstName(String parentFirstName) {
        this.parentFirstName = parentFirstName;
    }

    public String getParentLastName() {
        return parentLastName;
    }

    public void setParentLastName(String parentLastName) {
        this.parentLastName = parentLastName;
    }

    public Float getTotalFees() {
        return totalFees;
    }

    public void setTotalFees(Float totalFees) {
        this.totalFees = totalFees;
    }

    public List<ChildFee> getFeeList() {
        return feeList;
    }

    public void setFeeList(List<ChildFee> feeList) {
        this.feeList = feeList;
    }

}
