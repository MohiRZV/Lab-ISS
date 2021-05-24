package Domain;

public class Customer extends Entity<Long>{
    private String name;
    private String company;
    private Long id;
    public Customer(Long id, String name, String company) {
        super(id);
        this.name = name;
        this.company = company;
    }

    public Customer() {
        super(0L);
    }

    public Long getId(){
        return super.getId();
    }
    public void setId(Long id){
        super.setId(id);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public String toString() {
            return name + ", company: " + company;
    }
}
