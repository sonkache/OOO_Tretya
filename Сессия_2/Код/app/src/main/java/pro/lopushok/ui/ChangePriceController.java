package pro.lopushok.ui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import pro.lopushok.dao.ProductDao;

import java.text.NumberFormat;
import java.util.Collection;
import java.util.Locale;

public class ChangePriceController {
    @FXML private ImageView logo;
    @FXML private Label title;
    @FXML private TextField valueField;
    @FXML private Label avgLabel;
    @FXML private Button applyBtn;
    @FXML private Button cancelBtn;

    private final ProductDao dao = new ProductDao();
    private Collection<Long> ids;
    private boolean applied;
    private double newValue;

    public void init(Collection<Long> ids) {
        this.ids = ids;
        try { logo.setImage(new Image(getClass().getResourceAsStream("/Лопушок.png"))); } catch (Exception ignored) {}
        if (logo.getImage()==null) { try { logo.setImage(new Image(getClass().getResourceAsStream("/picture.png"))); } catch (Exception ignored) {} }
        title.setText("Изменить «Мин. цену для агента»");
        double avg = dao.averageMinAgentPriceForIds(ids);
        NumberFormat nf = NumberFormat.getNumberInstance(new Locale("ru","RU"));
        nf.setMaximumFractionDigits(2);
        avgLabel.setText(nf.format(avg));
        valueField.setText(String.valueOf(avg));
        applyBtn.setOnAction(e -> onApply());
        cancelBtn.setOnAction(e -> onCancel());
    }

    private void onApply() {
        try {
            String s = valueField.getText()==null?"":valueField.getText().trim();
            if (s.isEmpty()) { show("Введите значение"); return; }
            newValue = Double.parseDouble(s);
//            if (newValue < 0) { show("Значение не может быть отрицательным"); return; }
            applied = true;
            close();
        } catch (Exception ex) {
            show("Некорректное число");
        }
    }

    private void onCancel() {
        applied = false;
        close();
    }

    private void close() {
        Stage st = (Stage) title.getScene().getWindow();
        st.close();
    }

    public boolean isApplied() {
        return applied;
    }

    public double getNewValue() {
        return newValue;
    }

    private void show(String m) {
        new Alert(Alert.AlertType.WARNING, m, ButtonType.OK).showAndWait();
    }
}
