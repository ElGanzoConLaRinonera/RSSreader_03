package cz.uhk.fim.rssreader.gui;

import cz.uhk.fim.rssreader.model.RSSItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

public class CardView extends JPanel {

    public static final int ITEM_WIDTH = 100;
    public static final int HEIGHT = 1;
    public static final int COMPONENT_WIDTH = 100;

    final String startHtml = "<html><p style='width: "+COMPONENT_WIDTH +" px'>";
    final String endHtml = "</p></html>";

    private Color bgColor;
    private Color textColor;

    public CardView(RSSItem item){
        setLayout(new WrapLayout());
        setSize(ITEM_WIDTH,HEIGHT);
        setTitle(item.getTitle());
        setDescription(item.getDescription());
        setAdditionalInfo(String.format("%s - %s", item.getAuthor(),item.getPubDate()));
        setBackground(getRandomBgColor(item.getTitle()));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2){
                    new DetailFrame(item);
                }

            }
        });
//        addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                if(SwingUtilities.isRightMouseButton(e)){
//                    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
//                }
//            }
//        });

    }
    private void setTitle(String title) {
        JLabel lblTitle = new JLabel();
        lblTitle.setFont(new Font("Courier",Font.BOLD,11));
        lblTitle.setSize(COMPONENT_WIDTH,HEIGHT);
        lblTitle.setText(title);
        lblTitle.setText(String.format("%s%s%s",startHtml, title, endHtml));
        lblTitle.setForeground(textColor);
        add(lblTitle);
    }

    private void setAdditionalInfo(String info) {
        JLabel lblAddition = new JLabel();
        lblAddition.setFont(new Font("Courier",Font.ITALIC,10));
        lblAddition.setSize(COMPONENT_WIDTH,HEIGHT);
        lblAddition.setText(info);
        lblAddition.setText(String.format("%s%s%s",startHtml, info, endHtml));
        lblAddition.setForeground(Color.LIGHT_GRAY);
        add(lblAddition);
    }

    private void setDescription(String description) {
        JLabel lblDescription = new JLabel();
        lblDescription.setFont(new Font("Courier",Font.PLAIN,12));
        lblDescription.setSize(COMPONENT_WIDTH,HEIGHT);
        lblDescription.setText(description);
        lblDescription.setText(String.format("%s%s%s",startHtml, description, endHtml));
        lblDescription.setForeground(textColor);
        add(lblDescription);
    }

    private Color getRandomBgColor(String title) {
        int length = title.length();
        String[] parts = new String[3];
        int[] colors = new int[3];
        parts[0] = title.substring(0,length / 3);
        parts[1] = title.substring(length / 3, 2 * (length/3));
        parts[2] = title.substring(2*(length/3),length);

        colors[0] = Math.abs(parts[0].hashCode() / 10000000);
        colors[1] = Math.abs(parts[1].hashCode() / 10000000);
        colors[2] = Math.abs(parts[2].hashCode() / 10000000);

        Color color = new Color(colors[0],colors[1],colors[2]);
        setInverseTextColor(color);
        return color;
    }

    private void setInverseTextColor(Color bgColor){
        textColor = new Color (255-bgColor.getRed(),255-bgColor.getGreen(),255-bgColor.getBlue());

    }


}
