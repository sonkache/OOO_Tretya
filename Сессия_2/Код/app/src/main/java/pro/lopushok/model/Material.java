package pro.lopushok.model;

public class Material {
    private long id;
    private String name;
    private Double cost;
    private Integer packageQuantity;

    public Material() {}

    public Material(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Double getCost() { return cost; }
    public void setCost(Double cost) { this.cost = cost; }
    public Integer getPackageQuantity() { return packageQuantity; }
    public void setPackageQuantity(Integer packageQuantity) { this.packageQuantity = packageQuantity; }

    @Override public String toString() { return name; }
}
