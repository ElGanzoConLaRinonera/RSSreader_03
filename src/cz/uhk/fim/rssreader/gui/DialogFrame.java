package cz.uhk.fim.rssreader.gui;

import cz.uhk.fim.rssreader.model.RSSItem;
import cz.uhk.fim.rssreader.model.RSSSource;
import cz.uhk.fim.rssreader.utils.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DialogFrame extends JFrame {
    JLabel lblName = new JLabel("Name: ");
    JLabel lblLink = new JLabel("Link: ");

    JTextField txtName = new JTextField();
    JTextField txtLink = new JTextField();

    JButton btnConfirmDialog = new JButton("Confirm");
    JButton btnAbortDialog = new JButton("Abort");


    public DialogFrame() {
        init();
    }

    private void init() {
        setTitle("Add");
        setSize(400, 300);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        initContentUI();
    }

    private void initContentUI() {
        txtName.setPreferredSize(new Dimension(80,22));
        txtLink.setPreferredSize(new Dimension(180, 22));

        JPanel main = new JPanel(new BorderLayout());
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel content = new JPanel(new FlowLayout(FlowLayout.LEFT));

        main.add(content,BorderLayout.NORTH);
        main.add(buttons,BorderLayout.SOUTH);

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
                if(validateIt()) {
                    List<RSSSource> sources = new ArrayList<>();
                    List<RSSSource> load = new ArrayList<>();

                    load = FileUtils.loadSource();
                    for (int i = 0; i < load.size(); i++) {
                        sources.add(new RSSSource(load.get(i).getName(), load.get(i).getSource()));
                    }
                    sources.add(new RSSSource(txtName.getText(), txtLink.getText()));
                    FileUtils.saveSource(sources);
                    txtName.setText("");
                    txtLink.setText("");
                }
            }

        });
        btnConfirmDialog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(validateIt()) {
                    List<RSSSource> sources = new ArrayList<>();
                    List<RSSSource> load = new ArrayList<>();

                    load = FileUtils.loadSource();
                    for (int i = 0; i < load.size(); i++) {
                        sources.add(new RSSSource(load.get(i).getName(), load.get(i).getSource()));
                    }
                    sources.add(new RSSSource(txtName.getText(), txtLink.getText()));
                    FileUtils.saveSource(sources);
                    txtName.setText("");
                    txtLink.setText("");
                }
            }
        });

    }
    private boolean validateIt() {
        if (txtName.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(
                    null, "Fill the Name", "ErrorMsg", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (txtLink.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(
                    null, "Fill the Link", "ErrorMsg", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}




