package Domain;

public class SalesmanDTO {
    private String salesmanID;
    private Double value;

    public String getSalesmanID() {
        return salesmanID;
    }

    public void setSalesmanID(String salesmanID) {
        this.salesmanID = salesmanID;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public SalesmanDTO(String salesmanID, Double value) {
        this.salesmanID = salesmanID;
        this.value = value;
    }

    @Override
    public String toString() {
        return "SalesmanDTO{" +
                "salesmanID='" + salesmanID + '\'' +
                ", value=" + value +
                '}';
    }
}
