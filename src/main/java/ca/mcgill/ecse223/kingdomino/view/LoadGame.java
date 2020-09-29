package ca.mcgill.ecse223.kingdomino.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.Controller;


public class LoadGame implements ActionListener {


    // Labels and other fields

    private JLabel title;
    private JList<Save> saveList;
    private DefaultListModel<Save> saves = new DefaultListModel<Save>();

    public LoadGame() {


        //setting frame
        JFrame frame = new JFrame();
        title= new JLabel("LOAD A GAME");

        //setting buttons
        JButton loadGame = new JButton("Load Game");
        JButton importSave = new JButton("Import Game");

        saveList = new JList<Save>(saves);
        loadGame.addActionListener(this);
        updateSaveList();

        title.setFont(new Font("Slab Serif", Font.BOLD, 60));
        title.setForeground(Color.RED);
        //Set fonts


        //top panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBorder(BorderFactory.createEmptyBorder(25, 100, 50, 50));
        titlePanel.setBackground(Color.cyan);
        titlePanel.setLayout(new GridLayout(0, 1));
        titlePanel.add(title);
        titlePanel.add(loadGame);
        titlePanel.add(importSave);
        titlePanel.add(new JScrollPane(saveList));

        //set button attributes
        loadGame.setBackground(Color.YELLOW);
        loadGame.setOpaque(true);
        loadGame.setBorderPainted(false);
        loadGame.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent args) {
                if(loadGame.isEnabled()) {
                    System.out.println(Controller.loadGame(((Save)saveList.getSelectedValue()).path));
                    frame.dispose();
                    new KingDominoView();
                }
            }
        });

        importSave.setBackground(Color.YELLOW);
        importSave.setOpaque(true);
        importSave.setBorderPainted(false);
        importSave.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent args) {
                if(importSave.isEnabled()) {
                    JFileChooser fileChooser = new JFileChooser();
                    JDialog dialog = new JDialog();

                    fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
                    int result = fileChooser.showOpenDialog(dialog);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = fileChooser.getSelectedFile();
                        System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                        try {
                            Files.write(Paths.get("bin/saves.dat"), ("\n" + selectedFile.getPath()).getBytes(), StandardOpenOption.APPEND);
                        }catch (IOException e) {
                            //exception handling left as an exercise for the reader
                        }
                    }else{
                        System.out.println("Cancelled");
                    }

                    updateSaveList();

                }
            }
        });


        //add panels in specific spots
        frame.add(titlePanel, BorderLayout.NORTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("KINGDOMINO");
        frame.setSize(1280,800);
        frame.setVisible(true);

    }

    void updateSaveList() {

        saves.clear();

        try {
            BufferedReader br = new BufferedReader(new FileReader("bin/saves.dat"));
            String line = "";
            while ((line = br.readLine()) != null) {
                saves.addElement(new Save("game", line));
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new java.lang.IllegalArgumentException(
                    "Error occured while trying to read saves" + e.getMessage());
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
    }


    public class Save{
        String name;
        String path;

        public Save(String _name, String _path) {
            name = _name;
            path = _path;
        }

        @Override
        public String toString() {
            return name + "    " + path;
        }
    }
}