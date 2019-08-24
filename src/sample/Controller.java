package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    File file, file2;
    boolean RisChoose;
    boolean LisChoose;
    ObservableList<String> listVideo = FXCollections.observableArrayList();
    ObservableList<String> listAss = FXCollections.observableArrayList();
    @FXML
    public ListView listViewL;
    @FXML
    private Button buttonL, buttonR;
    @FXML
    private MenuBar menuBar;
    @FXML
    private ImageView imageViewAbout;
    @FXML
    private MenuItem menuItemClearPath, menuItemCloseAPP, menuItemAboutAPP;
    private Main main;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public Controller() {
    }


    //    public void exitButtonOnMouseClicked(MenuItem view) {
//        Stage stage = (Stage)view.getScene().getWindow();
//        stage.close();
//    }
    //平台退出的代码
    //platformExitButton是第二窗口的退出按钮
    public void exitButton() {
        Platform.exit();
        System.exit(0);
    }

    public void addRuleWindo() {
        URL location = getClass().getResource("AddRule.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root, 600, 400);
        Stage stage = new Stage();
        stage.setTitle("请添加规则");
        stage.setScene(scene);
        stage.getIcons().add(new Image("draw/WvTLogo.png"));
        stage.show();
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    public void initialize() {
        // Initialize the person table with the two columns.
        Value.controller = this;
        listViewL.setItems(FXCollections.observableArrayList("点击下方按钮选择文件夹，先选视频，再选字幕"));
        buttonL.setOnAction(event -> {
            boolean isSuc = true;
            try {
                for (int i = 0; i < Value.realVideo.size(); i++) {
                    String videoBeg = Value.realVideo.get(i).replace(Alg.getEnd(Value.realVideo.get(i)), "");
                    String assFileName = videoBeg + Alg.getEnd(Value.assFile.get(i).getName());
                    Value.assFile.get(i).renameTo(new File(file.toString() + File.separator + assFileName));
                }
            } catch (Exception e)
            {
                isSuc = false;
            }
            Stage secondStage = new Stage();
            Label label = new Label("操作" + (isSuc ? "成功！" : "失败")); // 放一个标签
            StackPane secondPane = new StackPane(label);
            Scene secondScene = new Scene(secondPane, 300, 200);
            secondStage.setScene(secondScene);
            secondStage.getIcons().add(new Image("draw/WvTLogo.png"));
            secondStage.show();
        });
        buttonR.setOnAction(event -> {
            Value.listVideo2 = FXCollections.observableArrayList();
            Value.listAss2 = FXCollections.observableArrayList();
            Value.jiNum = new ArrayList<String>();
            Value.assFile = new ArrayList<>();
            Value.realVideo = new ArrayList<>();
            Value.ci = 0;
//            System.out.println("双击了条目");
            listVideo = FXCollections.observableArrayList();
            DirectoryChooser directoryChooser = new DirectoryChooser();
            file = directoryChooser.showDialog(new Stage());
            File[] fileList = file.listFiles();
            System.out.println(file.toString());
            for (int i = 0; i < fileList.length; i++) {
                if (Alg.isVideoFile(fileList[i].toString())) {
                    listVideo.add(fileList[i].getName());
                }

            }
            listAss = FXCollections.observableArrayList();
            DirectoryChooser directoryChooser2 = new DirectoryChooser();
            file2 = directoryChooser2.showDialog(new Stage());
            System.out.println(file2.toString());
            File[] fileList2 = file2.listFiles();
            for (int i = 0; i < fileList2.length; i++) {
                if (Alg.isAssFile(fileList2[i].toString())) {
                    listAss.add(fileList2[i].getName());
                }
            }

            if (listVideo.size() != 0 && listAss.size() != 0) {
                addRuleWindo();
            }

        });
        menuItemClearPath.setOnAction(event -> {
            Value.listVideo2 = FXCollections.observableArrayList();
            Value.listAss2 = FXCollections.observableArrayList();
            Value.jiNum = new ArrayList<String>();
            Value.assFile = new ArrayList<>();
            Value.realVideo = new ArrayList<>();
            Value.ci = 0;
            listViewL.setItems(FXCollections.observableArrayList("点击下方按钮选择文件夹，先选视频，再选字幕"));
        });

        menuItemAboutAPP.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                URL location = getClass().getResource("AboutAPP.fxml");
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(location);
                fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                Parent root = null;
                try {
                    root = fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Scene scene = new Scene(root, 600, 400);
                Stage stage = new Stage();
                stage.setTitle("关于");
                stage.setScene(scene);
                stage.getIcons().add(new Image("draw/WvTLogo.png"));
                stage.show();
            }
        });

        menuItemCloseAPP.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                exitButton();
            }
        });

        listViewL.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {

                } else if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                    System.out.println("单击了条目");
                }
            }
        });
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(Main mainApp) {
        this.main = mainApp;
    }

    public ListView getListViewL() {
        return listViewL;
    }

}
