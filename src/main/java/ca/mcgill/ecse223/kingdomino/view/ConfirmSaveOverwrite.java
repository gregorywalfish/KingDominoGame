package ca.mcgill.ecse223.kingdomino.view;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfirmSaveOverwrite {

    JLabel text = new JLabel("Are you sure you want to overwrite your last save?");
    JButton yes = new JButton("Yes");
    JButton no = new JButton("No");
    JFrame frame = new JFrame();

    public ConfirmSaveOverwrite() {
        GroupLayout layout = new GroupLayout(frame.getContentPane());
        frame.setLayout(layout);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGap(20)
                .addComponent(text)
                        .addGap(20)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(yes)
                        .addGap(30)
                        .addComponent(no)
                ));
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGap(20)
                .addComponent(text)
                        .addGap(20)
                .addGroup(layout.createParallelGroup()
                .addComponent(yes)
                        .addGap(30)
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
                yesClicked();
            }
        });

        no.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                noClicked();
            }
        });
    }

    public void yesClicked() {
        Controller.saveGame(KingdominoApplication.getKingdomino().getCurrentGame(),String.format("bin/%s_%s_%s_%s_kingdomino.mov", KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(0).getUser().getName(), KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(1).getUser().getName(),KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(2).getUser().getName(),KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(3).getUser().getName()), true);
        frame.dispose();
    }

    public void noClicked() {
        frame.dispose();
    }
}
