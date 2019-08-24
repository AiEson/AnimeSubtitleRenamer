package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends Application {


    ObservableList<String> listVideo = FXCollections.observableArrayList();
    ObservableList<String> listAss = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) throws Exception {
        File localFile = new File(".").getAbsoluteFile();
        Value.localeFile = localFile;
        Value.mainPrimaryStage = primaryStage;
        File[] fileList = localFile.listFiles();
        boolean haveVideo = false, haveAss = false;
        if (fileList != null) {
            for (File i : fileList) {
                String name = i.getName();
                if (Alg.isVideoFile(name)) {
                    haveVideo = true;
                }
                if (Alg.isAssFile(name)) {
                    haveAss = true;
                }
            }
        }
        if (haveAss && haveVideo) {
            Value.listVideo2 = FXCollections.observableArrayList();
            Value.listAss2 = FXCollections.observableArrayList();
            Value.jiNum = new ArrayList<String>();
            Value.assFile = new ArrayList<>();
            Value.realVideo = new ArrayList<>();
            Value.ci = 0;
//            System.out.println("双击了条目");
            listVideo = FXCollections.observableArrayList();
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File file = localFile;
            File[] fileListt = file.listFiles();
            System.out.println(file.toString());
            for (int i = 0; i < fileListt.length; i++) {
                if (Alg.isVideoFile(fileListt[i].toString())) {
                    listVideo.add(fileListt[i].getName());
                }

            }
            listAss = FXCollections.observableArrayList();
            DirectoryChooser directoryChooser2 = new DirectoryChooser();
            File file2 = localFile;
            System.out.println(file2.toString());
            File[] fileList2 = file2.listFiles();
            for (int i = 0; i < fileList2.length; i++) {
                if (Alg.isAssFile(fileList2[i].toString())) {
                    listAss.add(fileList2[i].getName());
                }
            }

            if (listVideo.size() != 0 && listAss.size() != 0) {
                Alg.autoGetRule(listVideo, (listVideo.size() + "").length());
                for (int i = 0; i < listVideo.size(); i++) {
                    String all = listVideo.get(i);
                    Value.listVideo2.add("文件：" + all);
                    Value.jiNum.add(Alg.subString(all, Value.rule1, Value.rule2));
                }
                Alg.autoGetRule(listAss, (listAss.size() + "").length());
                Value.ci = 520;
                addRuleWindo();
                Value.stageAddRule.setOnHidden(e -> {
                    System.out.println("正在应用重命名");
                    try {
                        List<String> listAss3 = new ArrayList<>();
                        for (int i = 0; i < listAss.size(); i++) {
                            if (listAss.get(i).indexOf(Value.rule2) != -1)
                                listAss3.add(listAss.get(i));
                        }
                        for (int i = 0; i < listAss3.size(); i++) {
                            String all = listAss3.get(i);
                            if (Value.jiNum.size() == i)
                                break;
                            if (Value.jiNum.get(i).equals(Alg.subString(all, Value.rule1, Value.rule2))) {
                                Value.listAss2.add(Value.listVideo2.get(i) + " ---> 字幕：" + all + " 集数：" + Value.jiNum.get(i));
                                Value.assFile.add(new File(file2 + File.separator + all));
                                Value.realVideo.add(Value.listVideo2.get(i).replace("文件：", ""));
                            } else
                                Value.listAss2.add(Value.listVideo2.get(i) + " 字幕：未成功匹配");
                        }
                        {
                            boolean isSuc = true;
                            try {
                                for (int i = 0; i < Value.realVideo.size(); i++) {
                                    String videoBeg = Value.realVideo.get(i).replace(Alg.getEnd(Value.realVideo.get(i)), "");
                                    String assFileName = videoBeg + Alg.getEnd(Value.assFile.get(i).getName());
                                    Value.assFile.get(i).renameTo(new File(file.toString() + File.separator + assFileName));
                                }
                            } catch (Exception ea) {
                                isSuc = false;
                                {
                                    ea.printStackTrace();
                                    primaryStage.getIcons().add(new Image("draw/WvTLogo.png"));
                                    URL location = getClass().getResource("RootLayout.fxml");
                                    FXMLLoader fxmlLoader = new FXMLLoader();
                                    fxmlLoader.setLocation(location);
                                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                                    Parent root = null;
                                    try {
                                        root = fxmlLoader.load();
                                    } catch (IOException ex) {
                                        ex.printStackTrace();
                                    }
                                    primaryStage.setTitle("番剧批量重命名-By 青铜火机和电吹风 ver 0.3");
                                    primaryStage.setScene(new Scene(root, 800, 450));
                                    primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                                        @Override
                                        public void handle(WindowEvent event) {
                                            Platform.exit();
                                            System.exit(0);
                                        }
                                    });
                                    primaryStage.show();
                                    Controller controller = fxmlLoader.getController();
                                    controller.initialize();
                                    primaryStage.setOnCloseRequest(event -> new Controller().exitButton());
                                    final ChangeListener<Number> listener = new ChangeListener<Number>() {
                                        final Timer timer = new Timer(); // uses a timer to call your resize method
                                        TimerTask task = null; // task to execute after defined delay
                                        final long delayTime = 200; // delay that has to pass in order to consider an operation done

                                        @Override
                                        public void changed(ObservableValue<? extends Number> observable, Number oldValue, final Number newValue) {
                                            if (task != null) { // there was already a task scheduled from the previous operation ...
                                                task.cancel(); // cancel it, we have a new size to consider
                                            }

                                            task = new TimerTask() // create new task that calls your resize operation
                                            {
                                                @Override
                                                public void run() {
                                                    // here you can place your resize code
//                        System.out.println("resize to " + primaryStage.getWidth() + " " + primaryStage.getHeight());
//                        controller.setListViewLW(primaryStage.getWidth()*0.5, primaryStage.getHeight());
//                        controller.setListViewRW(primaryStage.getWidth()*0.5, primaryStage.getHeight());
                                                }

                                            };
                                            // schedule new task
                                            timer.schedule(task, delayTime);
                                        }
                                    };

// finally we have to register the listener
                                    primaryStage.widthProperty().addListener(listener);
                                    primaryStage.heightProperty().addListener(listener);
                                }
                            }
                        }
                    } catch (Exception eee) {
                        eee.printStackTrace();
                        primaryStage.getIcons().add(new Image("draw/WvTLogo.png"));
                        URL location = getClass().getResource("RootLayout.fxml");
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(location);
                        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                        Parent root = null;
                        try {
                            root = fxmlLoader.load();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        primaryStage.setTitle("番剧批量重命名-By 青铜火机和电吹风 ver 0.3");
                        primaryStage.setScene(new Scene(root, 800, 450));
                        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                            @Override
                            public void handle(WindowEvent event) {
                                Platform.exit();
                                System.exit(0);
                            }
                        });
                        primaryStage.show();
                        Controller controller = fxmlLoader.getController();
                        controller.initialize();
                        primaryStage.setOnCloseRequest(event -> new Controller().exitButton());
                        final ChangeListener<Number> listener = new ChangeListener<Number>() {
                            final Timer timer = new Timer(); // uses a timer to call your resize method
                            TimerTask task = null; // task to execute after defined delay
                            final long delayTime = 200; // delay that has to pass in order to consider an operation done

                            @Override
                            public void changed(ObservableValue<? extends Number> observable, Number oldValue, final Number newValue) {
                                if (task != null) { // there was already a task scheduled from the previous operation ...
                                    task.cancel(); // cancel it, we have a new size to consider
                                }

                                task = new TimerTask() // create new task that calls your resize operation
                                {
                                    @Override
                                    public void run() {
                                        // here you can place your resize code
//                        System.out.println("resize to " + primaryStage.getWidth() + " " + primaryStage.getHeight());
//                        controller.setListViewLW(primaryStage.getWidth()*0.5, primaryStage.getHeight());
//                        controller.setListViewRW(primaryStage.getWidth()*0.5, primaryStage.getHeight());
                                    }

                                };
                                // schedule new task
                                timer.schedule(task, delayTime);
                            }
                        };

// finally we have to register the listener
                        primaryStage.widthProperty().addListener(listener);
                        primaryStage.heightProperty().addListener(listener);
                    }
                });
            }

        } else {
            primaryStage.getIcons().add(new Image("draw/WvTLogo.png"));
            URL location = getClass().getResource("RootLayout.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(location);
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
            Parent root = fxmlLoader.load();
            primaryStage.setTitle("番剧批量重命名-By 青铜火机和电吹风 ver 0.3");
            primaryStage.setScene(new Scene(root, 800, 450));
            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    Platform.exit();
                    System.exit(0);
                }
            });
            primaryStage.show();
            Controller controller = fxmlLoader.getController();
            controller.initialize();
            primaryStage.setOnCloseRequest(event -> new Controller().exitButton());
            final ChangeListener<Number> listener = new ChangeListener<Number>() {
                final Timer timer = new Timer(); // uses a timer to call your resize method
                TimerTask task = null; // task to execute after defined delay
                final long delayTime = 200; // delay that has to pass in order to consider an operation done

                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, final Number newValue) {
                    if (task != null) { // there was already a task scheduled from the previous operation ...
                        task.cancel(); // cancel it, we have a new size to consider
                    }

                    task = new TimerTask() // create new task that calls your resize operation
                    {
                        @Override
                        public void run() {
                            // here you can place your resize code
//                        System.out.println("resize to " + primaryStage.getWidth() + " " + primaryStage.getHeight());
//                        controller.setListViewLW(primaryStage.getWidth()*0.5, primaryStage.getHeight());
//                        controller.setListViewRW(primaryStage.getWidth()*0.5, primaryStage.getHeight());
                        }

                    };
                    // schedule new task
                    timer.schedule(task, delayTime);
                }
            };

// finally we have to register the listener
            primaryStage.widthProperty().addListener(listener);
            primaryStage.heightProperty().addListener(listener);
        }
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
        Stage stageAddRule = new Stage();
        stageAddRule.setTitle("请添加字幕规则");
        stageAddRule.setScene(scene);
        stageAddRule.getIcons().add(new Image("draw/WvTLogo.png"));
        Value.stageAddRule = stageAddRule;
        stageAddRule.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
