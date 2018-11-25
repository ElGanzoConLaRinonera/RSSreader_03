package cz.uhk.fim.rssreader.gui;

import cz.uhk.fim.rssreader.model.RSSItem;
import cz.uhk.fim.rssreader.model.RSSList;
import cz.uhk.fim.rssreader.model.RSSSource;
import cz.uhk.fim.rssreader.utils.FileUtils;
import cz.uhk.fim.rssreader.utils.RSSParser;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame {

    private static final String VALIDATION_TYPE = "VALIDATION_TYPE";
    private static final String IO_LOAD_TYPE = "IO_LOAD_TYPE";
    private static final String IO_SAVE_TYPE = "IO_SAVE_TYPE";

    private JLabel lblErrorMessage;
    private JTextField txtPathField;
    private RSSList rssList;
    private DialogFrame dlgFrame;
    private EditFrame edFrame;
    private int indexCombo;
    List<RSSSource>load = new ArrayList<>();

    public MainFrame() {
        init();
    }

    private void init() {
        setTitle("RSS Reader");
        setSize(800, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initContentUI();
    }

    private void initContentUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel controlPanel = new JPanel(new BorderLayout());
        //tlačítka Add, Edit, Remove budou ve FlowLayoutu
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        //zarovnání buttonů zleva doprava tím, co je v závorce.

        JButton btnLoad = new JButton("Load");
        txtPathField = new JTextField();
        JButton btnSave = new JButton("Save");
        lblErrorMessage = new JLabel();
        lblErrorMessage.setForeground(Color.RED);
        lblErrorMessage.setHorizontalAlignment(SwingConstants.CENTER);
        JButton btnAdd = new JButton("Add");
        JButton btnRemove = new JButton("Remove");
        JButton btnEdit = new JButton("Edit");
        JButton btnRefresh = new JButton("Refresh List");

        JComboBox comboSource = new JComboBox();
        load = FileUtils.loadSource();
        if(load.size()>0){
            //provést pouze pokud se v seznamu cfg něco nachází
            for (int i = 0; i < load.size(); i++) {
                comboSource.addItem(load.get(i).getName());
            }
            comboSource.setSelectedIndex(0);
        }

        buttonsPanel.add(btnAdd);
        buttonsPanel.add(btnEdit);
        buttonsPanel.add(btnRemove);
        buttonsPanel.add(btnRefresh);

        controlPanel.add(btnLoad, BorderLayout.WEST);
        controlPanel.add(comboSource,BorderLayout.CENTER);
        controlPanel.add(btnSave, BorderLayout.EAST);
        controlPanel.add(lblErrorMessage, BorderLayout.SOUTH);

        mainPanel.add(controlPanel,BorderLayout.NORTH);
        mainPanel.add(buttonsPanel);

        add(mainPanel, BorderLayout.NORTH);

        JPanel content = new JPanel(new WrapLayout());

        add(new JScrollPane(content), BorderLayout.CENTER);
        try {
            if(load.size()>0) {
                rssList = new RSSParser().getParsedRSS(load.get(0).getSource());
                for (RSSItem item : rssList.getAllItems()) {
                    CardView view = new CardView(item);
                    view.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            if (SwingUtilities.isLeftMouseButton(e)) {
                                if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
                                    new DetailFrame(item).setVisible(true);
                                }
                            }
                        }
                    });
                    content.add(view);
                    content.updateUI();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        btnLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                content.removeAll();
                try {
                    if(load.size()>0) {
                        int indexCombo = comboSource.getSelectedIndex();
                     // rssList = new RSSParser().getParsedRSS("https://www.zive.cz/rss/sc-47/");
                     //   String pokus = load.get(indexCombo).getSource();
                      rssList = new RSSParser().getParsedRSS(load.get(indexCombo).getSource());
                       // rssList = new RSSParser().getParsedRSS(pokus);
                        for(RSSItem item : rssList.getAllItems()) {
                            CardView view = new CardView(item);
                            view.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                if(SwingUtilities.isLeftMouseButton(e)) {
                                    if(e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
                                        new DetailFrame(item).setVisible(true);
                                    }
                                }
                            }
                            });
                            content.add(view);
                            content.updateUI();
                        }
                    }
                } catch (IOException | SAXException | ParserConfigurationException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dlgFrame = new DialogFrame();
            }
        });
        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                indexCombo = comboSource.getSelectedIndex();
                edFrame = new EditFrame(indexCombo);
                edFrame.txtName.setText(load.get(indexCombo).getName());
                edFrame.txtLink.setText(load.get(indexCombo).getSource());
            }
        });
        btnRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //odstranit vybrany prvek ze seznamu
                indexCombo = comboSource.getSelectedIndex();
                List<RSSSource> sources = new ArrayList<>();
                load.remove(indexCombo);
                for (int i = 0; i < load.size(); i++) {
                    sources.add(new RSSSource(load.get(i).getName(), load.get(i).getSource()));
                }
                FileUtils.saveSource(sources);
                if(load.size()>0){
                    comboSource.removeAllItems();
                    for (int i = 0; i < load.size(); i++) {
                        comboSource.addItem(load.get(i).getName());
                    }
                    comboSource.setSelectedIndex(0);
                }
            }
        });

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<RSSSource> sources = new ArrayList<>();
                sources.add(new RSSSource("Živě.cz", "https://www.zive.cz/rss/sc-47/"));
                sources.add(new RSSSource("asdsadas", "httpssadsadz/rss/sc-47/"));
                sources.add(new RSSSource("12SD2", "hsadsadz/rss/sc-47/"));
                sources.add(new RSSSource("AS1ěě+ě+ --- -sad -", "asdsad/"));
                FileUtils.saveSource(sources);
            }
        });
        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                load = FileUtils.loadSource();
                if(load.size()>0){
                    comboSource.removeAllItems();
                    //provést pouze pokud se v seznamu cfg něco nachází
                    for (int i = 0; i < load.size(); i++) {
                        comboSource.addItem(load.get(i).getName());
                    }
                    comboSource.setSelectedIndex(0);
                }
            }
        });
    }

    private void showErrorMessage(String type) {
        String message;
        switch(type){
            case VALIDATION_TYPE:
                message = "Zadávací pole nemůže být prázdné!";
                break;
            case IO_LOAD_TYPE:
                message = "Chyba při načítání souboru!";
                break;
            case IO_SAVE_TYPE:
                message = "Chyba při ukládání souboru!";
                break;
            default:
                message = "Bůh ví, co se stalo";
                break;
        }
        lblErrorMessage.setText(message);
        lblErrorMessage.setVisible(true);
    }

    private boolean validateInput(){
        lblErrorMessage.setVisible(false);
            if(txtPathField.getText().trim().isEmpty()) {
            showErrorMessage(VALIDATION_TYPE);
            return false;
        }
        return true;
    }
    //TODO - dialog: - 2 fieldy (název, link) - pro oba validace (validateInput - upravit metodu pro potřeby kódu)
    //      - validace polí "název" a "link" na přítomnost středníku (replaceAll(";", "");)
    //      - přidat ComboBox pro výběr zdroje feedu - pouze název feedu (bez linku)
    // - tlačítko "Load" - volitelně - buď automatická změna při výběru v ComboBoxu nebo výběr v ComboBoxu a pak Load
    // - aplikace bude fungovat jak pro lokální soubor, tak pro online feed z internetu
    // - aplikace v žádném případě nespadne na hubu - otestovat a ošetřit
    // - funkční ukládání a načítání konfigurace
    // - při spuštění aplikace se automaticky načte první záznam z konfigurace
//          - pokud konfigurace existuje nebo není prázdná -> nutno kontrolovat
}
