package pro.lopushok.ui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pro.lopushok.dao.ProductDao;
import pro.lopushok.model.Product;
import pro.lopushok.model.ProductType;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.*;
import java.util.Locale;
import java.util.Optional;

public class ProductListController {
    @FXML private ImageView logo;
    @FXML private TextField searchField;
    @FXML private ComboBox<ProductType> typeFilter;
    @FXML private ComboBox<String> sortCombo;
    @FXML private ToggleButton ascToggle;
    @FXML private VBox cardsPane;
    @FXML private Button prevBtn;
    @FXML private Button nextBtn;
    @FXML private ScrollPane listScroll;
    @FXML private Button calcBtn;
    @FXML private Button addBtn;
    @FXML private Button editBtn;
    @FXML private Button deleteBtn;
    @FXML private Button massSetBtn;
    @FXML private Label selectionLabel;
    @FXML private HBox pageBar;

    private final ProductDao dao = new ProductDao();
    private int pageSize = 20;
    private int currentPageIndex = 0;
    private int totalPages = 1;
    private Product selectedProduct;
    private HBox selectedCard;
    private final LinkedHashMap<Long, Product> selectedMap = new LinkedHashMap<>();
    private static Stage editStage;

    @FXML
    public void initialize() {
        try { logo.setImage(new Image(getClass().getResourceAsStream("/Лопушок.png"))); } catch (Exception ignored) {}
        if (logo.getImage()==null) { try { logo.setImage(new Image(getClass().getResourceAsStream("/picture.png"))); } catch (Exception ignored) {} }
        typeFilter.getItems().setAll(dao.typesForFilter());
        typeFilter.getSelectionModel().selectFirst();
        sortCombo.getItems().addAll("Наименование", "Номер цеха", "Мин. цена для агента");
        sortCombo.getSelectionModel().selectFirst();
        ascToggle.setSelected(true);
        ascToggle.setPrefWidth(44);
        ascToggle.setMinWidth(44);
        updateArrow();
        calcBtn.setMinWidth(Region.USE_PREF_SIZE);
        addBtn.setMinWidth(Region.USE_PREF_SIZE);
        editBtn.setMinWidth(Region.USE_PREF_SIZE);
        deleteBtn.setMinWidth(Region.USE_PREF_SIZE);
        massSetBtn.setMinWidth(Region.USE_PREF_SIZE);
        selectionLabel.setMinWidth(Region.USE_PREF_SIZE);
        typeFilter.setMinWidth(Region.USE_PREF_SIZE);
        sortCombo.setMinWidth(Region.USE_PREF_SIZE);
        prevBtn.setMinWidth(Region.USE_PREF_SIZE);
        nextBtn.setMinWidth(Region.USE_PREF_SIZE);
        searchField.textProperty().addListener((obs,o,n)->debouncedRefresh());
        typeFilter.valueProperty().addListener((obs,o,n)->refresh());
        sortCombo.valueProperty().addListener((obs,o,n)->refresh());
        ascToggle.selectedProperty().addListener((obs,o,n)->{ updateArrow(); refresh(); });
        prevBtn.setOnAction(e->goPrev());
        nextBtn.setOnAction(e->goNext());
        calcBtn.setOnAction(e->openCalc());
        addBtn.setOnAction(e->onAdd());
        editBtn.setOnAction(e->onEdit());
        deleteBtn.setOnAction(e->onDelete());
        massSetBtn.setOnAction(e->onMassSet());
        Platform.runLater(() -> {
            Scene scene = cardsPane.getScene();
            if (scene != null) {
                scene.getAccelerators().put(new KeyCodeCombination(KeyCode.DELETE), this::onDelete);
                scene.getAccelerators().put(new KeyCodeCombination(KeyCode.ENTER), this::onEdit);
                scene.getAccelerators().put(new KeyCodeCombination(KeyCode.F, KeyCombination.CONTROL_DOWN), () -> searchField.requestFocus());
                scene.getRoot().requestFocus();
            }
        });
        updateSelectionUi();
        refresh();
    }

    private void updateArrow() { ascToggle.setText(ascToggle.isSelected() ? "▲" : "▼"); }
    private void goPrev() { setPage(Math.max(0, currentPageIndex-1)); }
    private void goNext() { setPage(Math.min(totalPages-1, currentPageIndex+1)); }

    private void setPage(int index) {
        currentPageIndex = Math.max(0, Math.min(index, totalPages-1));
        loadPage(currentPageIndex);
        buildPageBar();
        listScroll.setVvalue(0);
    }

    private void refresh() {
        String s = searchField.getText();
        Long typeId = typeFilter.getValue()==null?null:typeFilter.getValue().getId();
        int total = dao.totalCount(s, typeId);
        totalPages = Math.max(1,(int)Math.ceil(total/(double)pageSize));
        if (currentPageIndex > totalPages-1) currentPageIndex = totalPages-1;
        if (currentPageIndex < 0) currentPageIndex = 0;
        loadPage(currentPageIndex);
        buildPageBar();
        selectedProduct = null;
        selectedCard = null;
        calcBtn.setDisable(true);
        editBtn.setDisable(true);
        deleteBtn.setDisable(true);
    }

    private void debouncedRefresh() { Platform.runLater(this::refresh); }

    private void loadPage(int pageIndex) {
        String s = searchField.getText();
        Long typeId = typeFilter.getValue()==null?null:typeFilter.getValue().getId();
        String sortKey = switch (sortCombo.getValue()) {
            case "Номер цеха" -> "workshop";
            case "Мин. цена для агента" -> "price";
            default -> "name";
        };
        boolean asc = ascToggle.isSelected();
        List<Product> items = dao.page(s, typeId!=null && typeId==0?null:typeId, sortKey, asc, pageIndex, pageSize);
        cardsPane.getChildren().clear();
        for (Product p : items) cardsPane.getChildren().add(buildCard(p));
        updateSelectionUi();
    }

    private Pane buildCard(Product p) {
        HBox root = new HBox(16);
        root.setMaxWidth(Double.MAX_VALUE);
        root.prefWidthProperty().bind(listScroll.widthProperty().subtract(36));
        String style = "card";
        if (p.isNotSoldLastMonth()) style = style + " card-warn";
        root.getStyleClass().addAll(style.split("\\s+"));
        CheckBox cb = new CheckBox();
        cb.setSelected(selectedMap.containsKey(p.getId()));
        cb.selectedProperty().addListener((o,ov,nv)->{
            if (nv) selectedMap.put(p.getId(), p); else selectedMap.remove(p.getId());
            updateSelectionUi();
        });
        ImageView iv = new ImageView(resolveImage(p.getImagePath()));
        iv.setFitWidth(160);
        iv.setFitHeight(120);
        iv.setPreserveRatio(false);
        iv.setSmooth(true);
        VBox center = new VBox(6);
        Label title = new Label((p.getTypeName()==null?"Тип продукта":p.getTypeName()) + " | " + p.getName());
        title.getStyleClass().add("card-title");
        Label article = new Label("Артикул: " + (p.getArticle()==null?"—":p.getArticle()));
        String mats = p.getMaterialsSummary()==null || p.getMaterialsSummary().isBlank() ? "нет данных" : p.getMaterialsSummary();
        Label materials = new Label("Материалы: " + materialsPreview(mats));
        center.getChildren().addAll(title, article, materials);
        VBox right = new VBox();
        right.setMinWidth(160);
        right.setAlignment(javafx.geometry.Pos.TOP_RIGHT);
        NumberFormat nf = NumberFormat.getNumberInstance(new Locale("ru","RU"));
        Label cost = new Label("Стоимость: " + nf.format(p.getMaterialCost()));
        cost.setStyle("-fx-font-weight:bold;");
        Label minPrice = new Label("Мин. цена для агента: " + nf.format(p.getMinAgentPrice()));
        right.getChildren().addAll(cost, minPrice);
        right.setFillWidth(true);
        cost.setWrapText(true);
        minPrice.setWrapText(true);
        cost.setMaxWidth(Double.MAX_VALUE);
        minPrice.setMaxWidth(Double.MAX_VALUE);
        cost.setTextOverrun(javafx.scene.control.OverrunStyle.CLIP);
        minPrice.setTextOverrun(javafx.scene.control.OverrunStyle.CLIP);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        root.getChildren().addAll(cb, iv, center, spacer, right);
        root.setOnMouseClicked(e -> {
            if (selectedCard != null) selectedCard.getStyleClass().remove("card-selected");
            selectedCard = root;
            selectedProduct = p;
            if (!root.getStyleClass().contains("card-selected")) root.getStyleClass().add("card-selected");
            calcBtn.setDisable(false);
            editBtn.setDisable(false);
            deleteBtn.setDisable(false);
            if (e.getClickCount() == 2) onEdit();
        });
        return root;
    }

    private void buildPageBar() {
        pageBar.getChildren().clear();
        prevBtn.setDisable(currentPageIndex == 0);
        nextBtn.setDisable(currentPageIndex >= totalPages - 1);
        int maxShown = Math.min(totalPages, 10);
        int start = Math.max(0, currentPageIndex - 4);
        int end = Math.min(totalPages - 1, start + maxShown - 1);
        start = Math.max(0, end - maxShown + 1);
        for (int i = start; i <= end; i++) {
            Button b = new Button(String.valueOf(i + 1));
            b.getStyleClass().add("page-num");
            if (i == currentPageIndex) b.getStyleClass().add("page-num-current");
            final int idx = i;
            b.setOnAction(e -> setPage(idx));
            pageBar.getChildren().add(b);
        }
    }

    private void onMassSet() {
        if (selectedMap.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Не выбраны продукты").showAndWait();
            return;
        }
        try {
            FXMLLoader l = new FXMLLoader(getClass().getResource("/pro/lopushok/change-price.fxml"));
            Pane root = l.load();
            ChangePriceController ctrl = l.getController();
            ctrl.init(selectedMap.keySet());
            Stage st = new Stage();
            st.initModality(Modality.APPLICATION_MODAL);
            st.setTitle("Изменение цены");
            st.setScene(new Scene(root));
            addStageIcons(st);
            st.showAndWait();
            if (ctrl.isApplied()) {
                int updated = dao.bulkIncreaseMinAgentPriceValue(selectedMap.keySet(), ctrl.getNewValue());
                new Alert(Alert.AlertType.INFORMATION, "Обновлено записей: " + updated).showAndWait();
                selectedMap.clear();
                refresh();
            }
        } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, "Не удалось открыть окно изменения цены").showAndWait();
        }
    }

    private void openCalc() {
        if (selectedProduct == null) return;
        try {
            FXMLLoader l = new FXMLLoader(getClass().getResource("/pro/lopushok/material-calc.fxml"));
            Pane root = l.load();
            MaterialCalcController ctrl = l.getController();
            ctrl.setProduct(selectedProduct);
            Stage st = new Stage();
            st.initModality(Modality.APPLICATION_MODAL);
            st.setTitle("Расчёт сырья");
            st.setScene(new Scene(root));
            addStageIcons(st);
            st.showAndWait();
        } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, "Не удалось открыть окно расчёта").showAndWait();
        }
    }

    private void onAdd() { openEditWindow(null); }

    private void onEdit() {
        if (selectedProduct==null) return;
        Product fresh = dao.get(selectedProduct.getId());
        if (fresh==null) return;
        openEditWindow(fresh);
    }

    private static Stage editStageRef;
    private void openEditWindow(Product p) {
        if (editStageRef!=null && editStageRef.isShowing()) {
            editStageRef.requestFocus();
            return;
        }
        try {
            FXMLLoader l = new FXMLLoader(getClass().getResource("/pro/lopushok/product-edit.fxml"));
            Pane root = l.load();
            ProductEditController ctrl = l.getController();
            if (p==null) ctrl.createMode(); else ctrl.editMode(p);
            Stage st = new Stage();
            st.initModality(Modality.APPLICATION_MODAL);
            st.setTitle(p==null ? "Добавление продукта" : "Редактирование продукта");
            st.setScene(new Scene(root));
            addStageIcons(st);
            editStageRef = st;
            st.setOnHidden(ev -> editStageRef = null);
            st.showAndWait();
            if (ctrl.isSaved()) refresh();
        } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, "Не удалось открыть форму").showAndWait();
        }
    }

    private void onDelete() {
        if (selectedProduct==null) return;
        if (dao.hasSales(selectedProduct.getId())) {
            new Alert(Alert.AlertType.WARNING, "Нельзя удалить продукт: есть продажи").showAndWait();
            return;
        }
        ButtonType ok = new ButtonType("Удалить", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("Отмена", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Удалить выбранный продукт?", ok, cancel);
        Optional<ButtonType> res = a.showAndWait();
        if (res.isPresent() && res.get()==ok) {
            dao.delete(selectedProduct.getId());
            refresh();
        }
    }

    private void updateSelectionUi() {
        int n = selectedMap.size();
        selectionLabel.setText("Выбрано: " + n);
        massSetBtn.setDisable(n==0);
        deleteBtn.setDisable(selectedProduct==null);
        calcBtn.setDisable(selectedProduct==null);
        editBtn.setDisable(selectedProduct==null);
    }

    private String materialsPreview(String full) {
        String[] arr = full.split(",\\s*");
        if (arr.length<=3) return full;
        return arr[0]+", "+arr[1]+", "+arr[2]+", ...";
    }

    private static final String BASE_IMAGE_URL = "http://217.144.185.104:8010/images/";

    private Image resolveImage(String path) {
        // 1) Загружаем плейсхолдер один раз
        Image placeholder;
        try (InputStream is = getClass().getResourceAsStream("/picture.png")) {
            placeholder = new Image(is);
        } catch (Exception e) {
            // как крайний случай — пустая картинка 1x1
            return new Image("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR4nGNgYAAAAAMAASsJTYQAAAAASUVORK5CYII=");
        }

        // 2) Нормализуем и отбрасываем «псевдо-пути» из БД
        if (path == null) return placeholder;
        String s = path.trim();
        if (s.isEmpty()) return placeholder;
        String low = s.toLowerCase(Locale.ROOT);
        if (low.equals("не указано") || low.equals("отсутствует") || low.equals("нет") || low.equals("null")) {
            return placeholder;
        }

        try {
            Image img;

            // 3) Явные HTTP(S)
            if (low.startsWith("http://") || low.startsWith("https://")) {
                img = new Image(s, /*backgroundLoading*/ true);
                return img.isError() ? placeholder : img;
            }

            // 4) Локальный абсолютный путь
            java.nio.file.Path p = java.nio.file.Paths.get(s);
            if (java.nio.file.Files.exists(p)) {
                img = new Image(p.toUri().toString(), true);
                return img.isError() ? placeholder : img;
            }

            // 5) Имя файла -> пробуем на вашем CDN/сервере
            img = new Image(BASE_IMAGE_URL + s, true);
            return img.isError() ? placeholder : img;

        } catch (Exception ignore) {
            return placeholder;
        }
    }


    private void addStageIcons(Stage st) {
        try { st.getIcons().add(new Image(getClass().getResourceAsStream("/Лопушок.png"))); } catch (Exception ignored) {}
        try { st.getIcons().add(new Image(getClass().getResourceAsStream("/Лопушок.ico"))); } catch (Exception ignored) {}
        if (st.getIcons().isEmpty()) { try { st.getIcons().add(new Image(getClass().getResourceAsStream("/picture.png"))); } catch (Exception ignored) {} }
    }
}
