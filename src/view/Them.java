package view;
import javax.swing.*;
public enum Them {
    sunSet(new ImageIcon("D:/src/sunSet.png")),wave(new ImageIcon("D:/src/wave.png"));
    private final ImageIcon backgroundIcon;
    Them(ImageIcon backgroundIcon) {
        this.backgroundIcon=backgroundIcon;
    }
    public ImageIcon getBackgroundIcon(){
        return backgroundIcon;
    }
}
