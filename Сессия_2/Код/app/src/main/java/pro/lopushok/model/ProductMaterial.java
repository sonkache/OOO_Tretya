package pro.lopushok.model;

public class ProductMaterial {
    private long materialId;
    private String materialName;
    private double quantity;

    public ProductMaterial() {}

    public ProductMaterial(long materialId, String materialName, double quantity) {
        this.materialId = materialId;
        this.materialName = materialName;
        this.quantity = quantity;
    }

    public long getMaterialId() { return materialId; }
    public void setMaterialId(long materialId) { this.materialId = materialId; }
    public String getMaterialName() { return materialName; }
    public void setMaterialName(String materialName) { this.materialName = materialName; }
    public double getQuantity() { return quantity; }
    public void setQuantity(double quantity) { this.quantity = quantity; }
}
