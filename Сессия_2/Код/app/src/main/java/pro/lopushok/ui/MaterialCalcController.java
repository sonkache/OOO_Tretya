package pro.lopushok.ui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import pro.lopushok.model.Product;
import wsuniversallib.Calculation;

public class MaterialCalcController {
    @FXML private ImageView logo;
    @FXML private Label title;
    @FXML private ComboBox<String> materialType;
    @FXML private TextField countField;
    @FXML private TextField widthField;
    @FXML private TextField lengthField;
    @FXML private Button calcBtn;
    @FXML private Label result;

    private Product product;

    @FXML
    public void initialize() {
        try { logo.setImage(new Image(getClass().getResourceAsStream("/Лопушок.png"))); } catch (Exception ignored) {}
        if (logo.getImage()==null) { try { logo.setImage(new Image(getClass().getResourceAsStream("/picture.png"))); } catch (Exception ignored) {} }
        materialType.getItems().addAll("Тип 1", "Тип 2");
        materialType.getSelectionModel().selectFirst();
        calcBtn.setOnAction(e -> doCalc());
    }

    public void setProduct(Product p) {
        this.product = p;
        title.setText(p.getName());
        if (p.getWidth() != null) widthField.setText(String.valueOf(p.getWidth()));
        if (p.getLength() != null) lengthField.setText(String.valueOf(p.getLength()));
        countField.setText("1");
    }

    private void doCalc() {
        try {
            int mt = switch (materialType.getSelectionModel().getSelectedIndex()) {
                case 0 -> 1;
                case 1 -> 2;
                default -> 3;
            };
            int pt = (int) product.getProductTypeId();
            int count = Integer.parseInt(countField.getText().trim());
            float w = Float.parseFloat(widthField.getText().trim());
            float l = Float.parseFloat(lengthField.getText().trim());
            int q = Calculation.getQuantityForProduct(pt, mt, count, w, l);
            result.setText("Требуемое сырьё (ед., с учётом брака): " + q);
        } catch (Exception ex) {
            result.setText("Ошибка ввода");
        }
    }
}
