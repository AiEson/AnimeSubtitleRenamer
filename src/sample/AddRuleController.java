package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AddRuleController {
    @FXML
    private Label inLable;
    @FXML
    private TextField inFirstRule;
    @FXML
    private TextField inSecRule;
    @FXML
    private Button inRuleButton;
    @FXML
    private BorderPane inLay;

    @FXML
    public void initialize() {
        if (Value.ci == 0) {
            Alg.autoGetRule(Value.controller.listVideo, (Value.controller.listVideo.size() + "").length());
        } else if (Value.ci == 1){
            Alg.autoGetRule(Value.controller.listAss, (Value.controller.listAss.size() + "").length());
        }
        inFirstRule.setText(Value.rule1);
        inSecRule.setText(Value.rule2);

        inRuleButton.setOnAction(event -> {
            Value.ci++;
            if (Value.ci == 1) {

                Value.rule1 = inFirstRule.getText();
                Value.rule2 = inSecRule.getText();
                for (int i = 0; i < Value.controller.listVideo.size(); i++) {
                    String all = Value.controller.listVideo.get(i);
                    Value.listVideo2.add("文件：" + all);
                    Value.jiNum.add(Alg.subString(all, Value.rule1, Value.rule2));
                }
                Value.controller.addRuleWindo();
                Stage scene = (Stage) inLay.getScene().getWindow();
                scene.close();
            } else if (Value.ci == 2){
                try {
                    Value.rule1 = inFirstRule.getText();
                    Value.rule2 = inSecRule.getText();
                    List<String> listAss3 = new ArrayList<>();
                    for (int i = 0; i < Value.controller.listAss.size(); i++) {
                        if (Value.controller.listAss.get(i).indexOf(Value.rule2) != -1)
                            listAss3.add(Value.controller.listAss.get(i));
                    }
                    for (int i = 0; i < listAss3.size(); i++) {
                        String all = listAss3.get(i);
                        if (Value.jiNum.size() == i)
                            break;
                        if (Value.jiNum.get(i).equals(Alg.subString(all, Value.rule1, Value.rule2))) {
                            Value.listAss2.add(Value.listVideo2.get(i) + " ---> 字幕：" + all + " 集数：" + Value.jiNum.get(i));
                            Value.assFile.add(new File(Value.controller.file2 + File.separator + all));
                            Value.realVideo.add(Value.listVideo2.get(i).replace("文件：", ""));
                        } else
                            Value.listAss2.add(Value.listVideo2.get(i) + " 字幕：未成功匹配");
                    }
                    Value.controller.listViewL.setItems(Value.listAss2);
                    Stage scene = (Stage) inLay.getScene().getWindow();
                    scene.close();
                }catch (Exception e)
                {
                    e.printStackTrace();
                    inLable.setText("自动匹配出错，请手动输入字幕文件规则！");
                }
            } else if (Value.ci == 521)
            {
                Value.rule1 = inFirstRule.getText();
                Value.rule2 = inSecRule.getText();
                Value.ci = 520;
                Value.stageAddRule.close();
                System.exit(0);
            }

        });
    }
}
