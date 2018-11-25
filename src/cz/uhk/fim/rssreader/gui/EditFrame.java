package cz.uhk.fim.rssreader.gui;

import cz.uhk.fim.rssreader.model.RSSList;
import cz.uhk.fim.rssreader.model.RSSSource;
import cz.uhk.fim.rssreader.utils.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class EditFrame extends JFrame {
    private int indexCombo;
    JLabel lblName = new JLabel("Name: ");
    JLabel lblLink = new JLabel("Link: ");

    JTextField txtName = new JTextField();
    JTextField txtLink = new JTextField();

    JButton btnConfirmDialog = new JButton("Confirm");
    JButton btnAbortDialog = new JButton("Abort");
    List<RSSSource> load = new ArrayList<>();
    List<RSSSource> sources = new ArrayList<>();

    public EditFrame(int indexCombo) {
        this.indexCombo = indexCombo;
        init();
    }

    private void init() {
        setTitle("Edit");
        setSize(400, 300);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        initContentUI();
    }

    private void initContentUI() {
        txtName.setPreferredSize(new Dimension(80, 22));
        txtLink.setPreferredSize(new Dimension(180, 22));

        JPanel main = new JPanel(new BorderLayout());
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel content = new JPanel(new FlowLayout(FlowLayout.LEFT));

        main.add(content, BorderLayout.NORTH);
        main.add(buttons, BorderLayout.SOUTH);

        content.add(lblName);
        content.add(txtName);
        content.add(lblLink);
        content.add(txtLink);

        buttons.add(btnConfirmDialog);
        buttons.add(btnAbortDialog);

        add(main, BorderLayout.NORTH);

        btnAbortDialog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        btnConfirmDialog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                load = FileUtils.loadSource();
                load.remove(indexCombo);
                for (int i = 0; i < load.size(); i++) {
                    sources.add(new RSSSource(load.get(i).getName(), load.get(i).getSource()));
                }
                sources.add(new RSSSource(txtName.getText(), txtLink.getText()));
                FileUtils.saveSource(sources);
                txtName.setText("");
                txtLink.setText("");
            }
        });
    }
}
