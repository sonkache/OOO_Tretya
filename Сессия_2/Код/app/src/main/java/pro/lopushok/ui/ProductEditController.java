package pro.lopushok.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pro.lopushok.dao.ProductDao;
import pro.lopushok.model.Material;
import pro.lopushok.model.Product;
import pro.lopushok.model.ProductMaterial;
import pro.lopushok.model.ProductType;
import pro.lopushok.ui.net.HttpMultipartUploader;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

public class ProductEditController {
    @FXML private ImageView logo;
    @FXML private Label title;
    @FXML private ComboBox<ProductType> typeCombo;
    @FXML private TextField nameField;
    @FXML private TextField articleField;
    @FXML private TextField minPriceField;
    @FXML private TextField peopleField;
    @FXML private TextField workshopField;
    @FXML private TextArea descArea;
    @FXML private TextField imageField;
    @FXML private Button browseBtn;
    @FXML private Button addMaterialBtn;
    @FXML private Button removeMaterialBtn;
    @FXML private TableView<ProductMaterial> materialsTable;
    @FXML private TableColumn<ProductMaterial, String> colMaterial;
    @FXML private TableColumn<ProductMaterial, String> colQty;
    @FXML private Button saveBtn;
    @FXML private Button deleteBtn;
    @FXML private Button cancelBtn;
    @FXML private Label errorLabel;

    private final ProductDao dao = new ProductDao();
    private final ObservableList<ProductMaterial> materials = FXCollections.observableArrayList();
    private Long editingId;
    private boolean saved;

    private String fullImagePath;
    private boolean imageChanged;
    private String originalImageName;

    @FXML
    public void initialize() {
        try { logo.setImage(new Image(getClass().getResourceAsStream("/Лопушок.png"))); } catch (Exception ignored) {}
        if (logo.getImage()==null) { try { logo.setImage(new Image(getClass().getResourceAsStream("/picture.png"))); } catch (Exception ignored) {} }

        typeCombo.getItems().setAll(dao.allTypes());

        materialsTable.setItems(materials);
        materialsTable.setPlaceholder(new Label("Нет данных"));
        materialsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        materialsTable.setTableMenuButtonVisible(false);
        colMaterial.setCellValueFactory(cd -> new javafx.beans.property.SimpleStringProperty(cd.getValue().getMaterialName()));
        colQty.setCellValueFactory(cd -> new javafx.beans.property.SimpleStringProperty(new DecimalFormat("#.###").format(cd.getValue().getQuantity())));
        materialsTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        materialsTable.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> removeMaterialBtn.setDisable(nv == null));

        addMaterialBtn.setOnAction(e -> onAddMaterial());
        removeMaterialBtn.setOnAction(e -> onRemoveMaterial());
        browseBtn.setOnAction(e -> onBrowse());
        saveBtn.setOnAction(e -> onSave());
        deleteBtn.setOnAction(e -> onDelete());
        cancelBtn.setOnAction(e -> close());
    }

    public void createMode() {
        editingId = null;
        title.setText("Добавление продукта");
        if (!typeCombo.getItems().isEmpty()) typeCombo.getSelectionModel().select(0);
        fullImagePath = null;
        originalImageName = null;
        imageChanged = false;
        imageField.setText("");
    }

    public void editMode(Product p) {
        editingId = p.getId();
        title.setText("Редактирование продукта");
        for (ProductType t : typeCombo.getItems()) if (t.getId() == p.getProductTypeId()) { typeCombo.getSelectionModel().select(t); break; }
        nameField.setText(p.getName());
        articleField.setText(p.getArticle());
        minPriceField.setText(p.getMinAgentPrice() == 0 ? "" : String.valueOf(p.getMinAgentPrice()));
        if (p.getPeopleRequired() != null) peopleField.setText(String.valueOf(p.getPeopleRequired()));
        if (p.getWorkshopNumber() != null) workshopField.setText(String.valueOf(p.getWorkshopNumber()));
        descArea.setText(p.getDescription());

        originalImageName = safeToNull(p.getImagePath());
        fullImagePath = originalImageName;
        imageChanged = false;
        imageField.setText(originalImageName == null ? "" : originalImageName);

        materials.setAll(dao.productMaterials(p.getId()));
    }

    public boolean isSaved() { return saved; }

    private void onBrowse() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Выбрать изображение");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Изображения", "*.png", "*.jpg", "*.jpeg", "*.bmp", "*.gif", "*.webp"));
        File f = fc.showOpenDialog(getStage());
        if (f != null) {
            imageChanged = true;
            fullImagePath = f.getAbsolutePath();
            imageField.setText(f.getName());
        }
    }

    private void onAddMaterial() {
        Dialog<ProductMaterial> dlg = new Dialog<>();
        dlg.setTitle("Добавить материал");
        ButtonType okBtn = new ButtonType("ОК", ButtonData.OK_DONE);
        ButtonType cancelBtnType = new ButtonType("Отмена", ButtonBar.ButtonData.CANCEL_CLOSE);
        dlg.getDialogPane().getButtonTypes().addAll(okBtn, cancelBtnType);
        dlg.getDialogPane().getStylesheets().add(getClass().getResource("/pro/lopushok/style.css").toExternalForm());
        ImageView ivHeader = new ImageView();
        try { ivHeader.setImage(new Image(getClass().getResourceAsStream("/Лопушок.png"))); } catch (Exception ignored) {}
        if (ivHeader.getImage()==null) { try { ivHeader.setImage(new Image(getClass().getResourceAsStream("/picture.png"))); } catch (Exception ignored) {} }
        ivHeader.setFitHeight(36);
        ivHeader.setPreserveRatio(true);
        Label lblHeader = new Label("Добавить материал");
        lblHeader.setStyle("-fx-font-size:16px;-fx-font-weight:bold;");
        HBox header = new HBox(10, ivHeader, lblHeader);
        header.getStyleClass().add("header-bar");
        TextField search = new TextField();
        search.setPromptText("Поиск материала");
        ListView<Material> results = new ListView<>();
        results.setPrefHeight(220);
        TextField qtyField = new TextField();
        qtyField.setPromptText("Количество");
        VBox content = new VBox(8, search, results, qtyField);
        content.setPrefWidth(420);
        VBox root = new VBox(8, header, content);
        dlg.getDialogPane().setContent(root);
        search.textProperty().addListener((o, ov, nv) -> {
            List<Material> list = dao.findMaterials(nv, 30);
            results.getItems().setAll(list);
        });
        results.getItems().setAll(dao.findMaterials("", 30));
        dlg.setOnShown(ev -> {
            Stage st = (Stage) dlg.getDialogPane().getScene().getWindow();
            try { st.getIcons().add(new Image(getClass().getResourceAsStream("/Лопушок.png"))); } catch (Exception ignored) {}
            try { st.getIcons().add(new Image(getClass().getResourceAsStream("/Лопushok.ico"))); } catch (Exception ignored) {}
            if (st.getIcons().isEmpty()) { try { st.getIcons().add(new Image(getClass().getResourceAsStream("/picture.png"))); } catch (Exception ignored) {} }
        });
        dlg.setResultConverter(bt -> {
            if (bt.getButtonData() == ButtonData.OK_DONE) {
                Material m = results.getSelectionModel().getSelectedItem();
                if (m == null) return null;
                try {
                    double q = Double.parseDouble(qtyField.getText().trim());
                    if (q < 0) return null;
                    return new ProductMaterial(m.getId(), m.getName(), q);
                } catch (Exception ex) { return null; }
            }
            return null;
        });
        var res = dlg.showAndWait();
        res.ifPresent(pm -> {
            int idx = -1;
            for (int i = 0; i < materials.size(); i++) if (materials.get(i).getMaterialId() == pm.getMaterialId()) { idx = i; break; }
            if (idx >= 0) materials.set(idx, pm); else materials.add(pm);
        });
    }

    private void onRemoveMaterial() {
        ProductMaterial pm = materialsTable.getSelectionModel().getSelectedItem();
        if (pm != null) materials.remove(pm);
    }

    private void onSave() {
        errorLabel.setText("");

        ProductType t = typeCombo.getValue();
        if (t == null) { errorLabel.setText("Выберите тип"); return; }
        String name = trimOrNull(nameField.getText());
        if (name == null) { errorLabel.setText("Введите наименование"); return; }
        String article = trimOrNull(articleField.getText());
        if (article == null) { errorLabel.setText("Введите артикул"); return; }
        if (dao.articleExists(article, editingId)) { errorLabel.setText("Такой артикул уже существует"); return; }
        Double minPrice = parseNonNegativeDouble(minPriceField.getText(), "Мин. цена должна быть числом ≥ 0");
        if (minPrice == null) return;
        Integer ppl = parseNullableInt(peopleField.getText(), "Количество людей должно быть целым ≥ 0"); if (ppl != null && ppl < 0) { errorLabel.setText("Количество людей ≥ 0"); return; }
        Integer workshopNum = parseNullableInt(workshopField.getText(), "Номер цеха должен быть целым ≥ 0");

        Long workshopId = dao.findWorkshopIdByNumber(workshopNum);
        if (workshopNum != null && workshopId == null) { errorLabel.setText("Цех с номером " + workshopNum + " не существует"); return; }
        final Long fWorkshopId = workshopId;

        Product p = new Product();
        if (editingId != null) p.setId(editingId);
        p.setProductTypeId(t.getId());
        p.setName(name);
        p.setArticle(article);
        p.setMinAgentPrice(minPrice);
        p.setPeopleRequired(ppl);
        p.setDescription(descArea.getText());

        setUiDisabled(true);
        errorLabel.setText("Сохранение…");

        Task<Void> saveTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                String imagePathToSave;
                if (imageChanged) {
                    String uploadUrl = HttpMultipartUploader.DEFAULT_UPLOAD_URL;
                    if (uploadUrl == null || uploadUrl.isBlank()) {
                        throw new RuntimeException("Невозможно загрузить изображение: не задан upload URL");
                    }
                    try {
                        String serverResp = HttpMultipartUploader.uploadFile(uploadUrl, new File(fullImagePath), "file");
                        if (serverResp != null && serverResp.trim().toUpperCase().startsWith("ERR:")) {
                            throw new RuntimeException("Сервер загрузки: " + serverResp.trim());
                        }
                        String nameOnly = extractFileName(serverResp);
                        if (nameOnly == null || nameOnly.isBlank()) {
                            throw new RuntimeException("Сервер вернул некорректный ответ при загрузке изображения");
                        }
                        imagePathToSave = nameOnly;
                    } catch (Exception ex) {
                        throw new RuntimeException(ex.getMessage(), ex);
                    }
                } else {
                    imagePathToSave = originalImageName;
                }

                if (imagePathToSave != null && imagePathToSave.isBlank()) imagePathToSave = null;

                p.setImagePath(imagePathToSave);
                p.setWorkshopId(fWorkshopId);

                if (editingId == null) {
                    long id = dao.insert(p);
                    dao.replaceProductMaterials(id, materials);
                } else {
                    dao.update(p);
                    dao.replaceProductMaterials(p.getId(), materials);
                }
                return null;
            }

            @Override
            protected void succeeded() {
                saved = true;
                close();
            }

            @Override
            protected void failed() {
                Throwable ex = getException();
                errorLabel.setText(ex != null && ex.getMessage() != null
                        ? ex.getMessage()
                        : "Ошибка при сохранении");
                setUiDisabled(false);
            }
        };

        Thread th = new Thread(saveTask, "product-save");
        th.setDaemon(true);
        th.start();
    }

    private void onDelete() {
        errorLabel.setText("");
        if (editingId == null) { close(); return; }
        if (dao.hasSales(editingId)) {
            errorLabel.setText("Нельзя удалить продукт: есть продажи");
            return;
        }
        ButtonType ok = new ButtonType("Удалить", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("Отмена", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Удалить выбранный продукт?", ok, cancel);
        Optional<ButtonType> res = a.showAndWait();
        if (res.isPresent() && res.get()==ok) {
            dao.delete(editingId);
            saved = true;
            close();
        }
    }

    private void setUiDisabled(boolean disabled) {
        saveBtn.setDisable(disabled);
        deleteBtn.setDisable(disabled);
        cancelBtn.setDisable(disabled);
        addMaterialBtn.setDisable(disabled);
        removeMaterialBtn.setDisable(disabled);
        browseBtn.setDisable(disabled);
        typeCombo.setDisable(disabled);
        nameField.setDisable(disabled);
        articleField.setDisable(disabled);
        minPriceField.setDisable(disabled);
        peopleField.setDisable(disabled);
        workshopField.setDisable(disabled);
        descArea.setDisable(disabled);
        materialsTable.setDisable(disabled);
    }

    private String trimOrNull(String s) { if (s == null) return null; String t = s.trim(); return t.isEmpty() ? null : t; }
    private String safeToNull(String v) { return (v == null || v.isBlank()) ? null : v; }

    private Double parseNonNegativeDouble(String s, String err) {
        try { double v = Double.parseDouble(s.trim()); if (v < 0) { errorLabel.setText(err); return null; } return v; }
        catch (Exception e) { errorLabel.setText(err); return null; }
    }
    private Integer parseNullableInt(String s, String err) {
        if (s == null || s.isBlank()) return null;
        try { return Integer.parseInt(s.trim()); }
        catch (Exception e) { errorLabel.setText(err); return null; }
    }

    private String extractFileName(String s) {
        if (s == null) return null;
        String x = s.trim();
        if (x.isEmpty()) return null;
        int q = x.indexOf('?'); if (q >= 0) x = x.substring(0, q);
        int h = x.indexOf('#'); if (h >= 0) x = x.substring(0, h);
        x = x.replace('\\', '/');
        int slash = x.lastIndexOf('/');
        String candidate = slash >= 0 ? x.substring(slash + 1) : x;
        return candidate.isBlank() ? null : candidate;
    }

    private Stage getStage() { return (Stage) title.getScene().getWindow(); }
    private void close() { getStage().close(); }
}
