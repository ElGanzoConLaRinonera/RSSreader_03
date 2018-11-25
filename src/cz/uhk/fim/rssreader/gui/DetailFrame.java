//package cz.uhk.fim.rssreader.gui;
//
//import cz.uhk.fim.rssreader.model.RSSItem;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//
//public class DetailFrame extends JFrame {
//    public RSSItem item;
//
//    public DetailFrame(RSSItem item) {
//    this.item = item;
//
//    addMouseListener(new MouseAdapter() {
//        @Override
//        public void mouseClicked(MouseEvent e) {
//            if(SwingUtilities.isRightMouseButton(e)){
//                setVisible(false);
//
//            }
//        }
//    });
//        init();
//    }
//    private void init() {
//        setSize(400, 300);
//        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        setLocationRelativeTo(null);
//        setUndecorated(true);
//        setVisible(true);
//        initContentUI();
//    }
//    private void initContentUI() {
//        JPanel content = new JPanel(new BorderLayout());
//        content.add(new CardView(item));
//        add(content);
//        }
//
//    }
package cz.uhk.fim.rssreader.gui;

import cz.uhk.fim.rssreader.model.RSSItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DetailFrame extends JFrame {
    private static final int COMPONENT_WIDTH = 250;
    private static final int HEIGHT = 1;

    final String startHtml = "<html><p style = 'width:"+ COMPONENT_WIDTH + "px'>";
    final String endHtml = "</p></html>";

    public DetailFrame(RSSItem item){
        setLayout(new WrapLayout());
        setSize(300, HEIGHT);
        setMinimumSize(new Dimension(300, 300));
        setLocationRelativeTo(null);
        setUndecorated(true);
        setRightMouseAction();

        setDetailTitle(item.getTitle());
        setDescription(item.getDescription());
        setLink(item.getLink());
        setInfo(String.format("%s-%s", item.getPubDate(), item.getAuthor()));
    }

    private void setRightMouseAction() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON3) {
                    dispose();
                }
            }
        });
    }

    private void setDetailTitle(String title){
        JLabel lblTitle = new JLabel();
        lblTitle.setSize(COMPONENT_WIDTH, HEIGHT);
        lblTitle.setFont(new Font(Font.SERIF, Font.BOLD,12));
        lblTitle.setText(String.format("%s%s%s", startHtml, title, endHtml));
        add(lblTitle);
    }
    private void setDescription(String description){
        JLabel lblDescription = new JLabel();
        lblDescription.setSize(COMPONENT_WIDTH, HEIGHT);
        lblDescription.setFont(new Font(Font.SERIF, Font.PLAIN,11));
        lblDescription.setText(String.format("%s%s%s", startHtml, description, endHtml));
        add(lblDescription);
    }
    private void setLink(String link){
        JLabel lblLink = new JLabel();
        lblLink.setSize(COMPONENT_WIDTH, HEIGHT);
        lblLink.setFont(new Font(Font.SERIF,Font.PLAIN,10));
        lblLink.setText(String.format("%s%s%s", startHtml, link, endHtml));
        add(lblLink);
    }
    private void setInfo(String format) {
        JLabel lblFormat = new JLabel();
        lblFormat.setSize(COMPONENT_WIDTH, HEIGHT);
        lblFormat.setFont(new Font(Font.SERIF, Font.ITALIC,10));
        lblFormat.setText(String.format("%s%s%s", startHtml, format, endHtml));
        add(lblFormat);
    }
}

