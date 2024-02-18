package pl.school.management.model.api.response;

public class ChildFee {

    private Long childId;
    private String childName;
    private Float totalAmount;
    private Integer totalTime;

    public ChildFee(Long childId, String childName, Float totalAmount, Integer totalTime) {
        this.childId = childId;
        this.childName = childName;
        this.totalAmount = totalAmount;
        this.totalTime = totalTime;
    }

    public Long getChildId() {
        return childId;
    }

    public void setChildId(Long childId) {
        this.childId = childId;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public Float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Integer totalTime) {
        this.totalTime = totalTime;
    }

    public void incrementTotalHours(int hours) {
        this.totalTime = totalTime + hours;
    }

    public void incrementTotalAmount(float amount) {
        this.totalAmount = totalAmount + amount;
    }

}
