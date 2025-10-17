package pro.lopushok.model;

public class Product {
    private long id;
    private long productTypeId;
    private String article;
    private String name;
    private String typeName;
    private Integer workshopNumber;
    private Long workshopId;
    private double minAgentPrice;
    private Integer peopleRequired;
    private Integer width;
    private Integer length;
    private String description;
    private String imagePath;
    private double materialCost;
    private String materialsSummary;
    private boolean notSoldLastMonth;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public long getProductTypeId() { return productTypeId; }
    public void setProductTypeId(long productTypeId) { this.productTypeId = productTypeId; }
    public String getArticle() { return article; }
    public void setArticle(String article) { this.article = article; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getTypeName() { return typeName; }
    public void setTypeName(String typeName) { this.typeName = typeName; }
    public Integer getWorkshopNumber() { return workshopNumber; }
    public void setWorkshopNumber(Integer workshopNumber) { this.workshopNumber = workshopNumber; }
    public Long getWorkshopId() { return workshopId; }
    public void setWorkshopId(Long workshopId) { this.workshopId = workshopId; }
    public double getMinAgentPrice() { return minAgentPrice; }
    public void setMinAgentPrice(double minAgentPrice) { this.minAgentPrice = minAgentPrice; }
    public Integer getPeopleRequired() { return peopleRequired; }
    public void setPeopleRequired(Integer peopleRequired) { this.peopleRequired = peopleRequired; }
    public Integer getWidth() { return width; }
    public void setWidth(Integer width) { this.width = width; }
    public Integer getLength() { return length; }
    public void setLength(Integer length) { this.length = length; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
    public double getMaterialCost() { return materialCost; }
    public void setMaterialCost(double materialCost) { this.materialCost = materialCost; }
    public String getMaterialsSummary() { return materialsSummary; }
    public void setMaterialsSummary(String materialsSummary) { this.materialsSummary = materialsSummary; }
    public boolean isNotSoldLastMonth() { return notSoldLastMonth; }
    public void setNotSoldLastMonth(boolean notSoldLastMonth) { this.notSoldLastMonth = notSoldLastMonth; }
}
