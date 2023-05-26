package view;

import model.PlayerColor;

import javax.swing.*;
import java.awt.*;

public  class allcomponent extends JComponent {
    private PlayerColor owner;

    private boolean selected;

    public allcomponent() {

    }
    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        Graphics2D g2 = (Graphics2D) g;
//        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        Font font = new Font("楷体", Font.PLAIN, getWidth() / 2);
//        g2.setFont(font);
//        g2.setColor(owner.getColor());
//        g2.drawString("象", getWidth() / 4, getHeight() * 5 / 8); // FIXME: Use library to find the correct offset.
//        if (isSelected()) { // Highlights the model if selected.
//            g.setColor(Color.RED);
//            g.drawOval(0, 0, getWidth() , getHeight());
//        }
    }
}
