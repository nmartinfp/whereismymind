package org.academiadecodigo.mindblowers.client;

import javafx.scene.control.Button;

/**
 * Created by codecadet on 14/07/17.
 */
public enum Buttons {
    //BUTTON1(new Button(), "btn1"),
    BUTTON2(new Button(), "btn2"),
    BUTTON3(new Button(), "btn3"),
    BUTTON4(new Button(), "btn4"),
    BUTTON5(new Button(), "btn5"),
    BUTTON6(new Button(), "btn1Alt"),
    BUTTON7(new Button(), "btn2Alt"),
    BUTTON8(new Button(), "btn3Alt"),
    BUTTON9(new Button(), "btn4Alt"),
    BUTTON10(new Button(), "btn5Alt");

    private Button btn;
    private String btnId;

    Buttons(Button btn, String btnId) {
        this.btn = btn;
        this.btnId = btnId;
    }

    public Button getBtn() {
        return btn;
    }

    public String getBtnId() {
        return btnId;
    }

    static public Button getBtnById(String s) {
        for (Buttons btn : Buttons.values()) {
            if (btn.getBtnId().equals(s)) {
                return btn.getBtn();
            }
        }
        return null;
    }

    public static String findIdByButton(Button btn) {
        for (Buttons b : Buttons.values()) {
            if (b.getBtn() == btn) {
                return b.getBtnId();
            }
        }
        return null;
    }

    public static void setId(Button b, String s) {
        for (Buttons btns : Buttons.values()) {
            if(btns.getBtn() == b) {
                btns.getBtn().setId(s);
            }
        }
    }
}
