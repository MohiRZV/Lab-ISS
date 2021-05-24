package Domain;

public class ClientDTO {
    private Long clientID;
    private Double value;

    public Long getClientID() {
        return clientID;
    }

    public void setClientID(Long clientID) {
        this.clientID = clientID;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public ClientDTO(Long clientID, Double value) {
        this.clientID = clientID;
        this.value = value;
    }

    @Override
    public String toString() {
        return "ClientDTO{" +
                "clientID=" + clientID +
                ", value=" + value +
                '}';
    }
}
