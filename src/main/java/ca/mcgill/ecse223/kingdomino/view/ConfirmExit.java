package ca.mcgill.ecse223.kingdomino.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfirmExit {

    JLabel text = new JLabel("Are you sure you want to exit?");
    JButton yes = new JButton("Yes");
    JButton menu = new JButton("Exit To Menu");
    JButton no = new JButton("No");
    JFrame frame = new JFrame();
    JFrame main;

    public ConfirmExit(JFrame j) {
        GroupLayout layout = new GroupLayout(frame.getContentPane());
        frame.setLayout(layout);

        main = j;

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGap(20)
                .addComponent(text)
                        .addGap(20)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(yes)
                        .addComponent(menu)
                        .addComponent(no)
                ));
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGap(20)
                .addComponent(text)
                        .addGap(20)
                .addGroup(layout.createParallelGroup()
                .addComponent(yes)
                        .addComponent(menu)
                .addComponent(no))
        );

        frame.setVisible(true);
        frame.setSize(300,200);
        refresh();
    }

    private void refresh() {
        yes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                yesClicked(e);
            }
        });

        no.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                noClicked(e);
            }
        });

        menu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuClicked(e);
            }
        });
    }

    public void yesClicked(ActionEvent e) {
        System.exit(0);
    }

    public void noClicked(ActionEvent e) {
        frame.dispose();
    }

    public void menuClicked(ActionEvent e) {
        main.dispose();
        frame.dispose();
        new UserUint();
    }
}
