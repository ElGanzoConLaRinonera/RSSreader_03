package cz.uhk.fim.rssreader.gui;

import cz.uhk.fim.rssreader.model.RSSItem;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DetailFrame extends JFrame {

    public RSSItem item;

    public DetailFrame(RSSItem item) {
    this.item = item;

    addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if(SwingUtilities.isRightMouseButton(e)){
                setVisible(false);
            }
        }
    });
        init();
    }
    private void init() {
        setSize(400, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);
        initContentUI();
    }
    private void initContentUI() {
        JPanel content = new JPanel();
        content.add(new CardView(item));
        add(content);
        }

    }

