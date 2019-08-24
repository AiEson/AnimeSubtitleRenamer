package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Value {
    public static String rule1 = "";
    public static String rule2 = "";
    public static Controller controller;
    public static ObservableList<String> listVideo2 = FXCollections.observableArrayList();
    public static ObservableList<String> listAss2 = FXCollections.observableArrayList();
    public static List<String> jiNum = new ArrayList<String>();
    public static int ci = 0;
    public static List<File> assFile = new ArrayList<>();
    public static List<String> realVideo = new ArrayList<>();
    public static Stage stageAddRule;
    public static File localeFile;
    public static Stage mainPrimaryStage;
}
