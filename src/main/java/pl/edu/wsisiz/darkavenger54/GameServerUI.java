/*
 * Created by JFormDesigner on Fri Jun 13 19:12:58 CEST 2025
 */

package pl.edu.wsisiz.darkavenger54;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout;

/**
 * @author Yevhenii Manuilov
 */
public class GameServerUI extends JFrame {
    private GameServer gameServer;
    boolean isStarted = false;
    public GameServerUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("System Look and Feel Not Supported");
        }
        initComponents();
        this.setTitle("Server");
        this.setResizable(false);
        this.serverTextArea.setEditable(false);
        this.stopButton.setEnabled(false);
        this.setVisible(true);
    }

    private void start(ActionEvent e) {
        if (Utility.tryParsePort(portTextField.getText()))
        {
            gameServer = new GameServer(Integer.parseInt(portTextField.getText()), this);
            isStarted = true;
            gameServer.start();
            startButton.setEnabled(false);
            stopButton.setEnabled(true);
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Invalid port", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void stop(ActionEvent e) {
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
    }

    public JTextArea getServerTextArea()
    {
        return serverTextArea;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        panel1 = new JPanel();
        panel2 = new JPanel();
        portTextField = new JTextField();
        startButton = new JButton();
        stopButton = new JButton();
        scrollPane1 = new JScrollPane();
        serverTextArea = new JTextArea();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        var contentPane = getContentPane();

        //======== panel1 ========
        {

            //======== panel2 ========
            {

                //---- startButton ----
                startButton.setText("Start");
                startButton.addActionListener(e -> start(e));

                //---- stopButton ----
                stopButton.setText("Stop");
                stopButton.addActionListener(e -> stop(e));

                GroupLayout panel2Layout = new GroupLayout(panel2);
                panel2.setLayout(panel2Layout);
                panel2Layout.setHorizontalGroup(
                    panel2Layout.createParallelGroup()
                        .addGroup(panel2Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(portTextField, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(startButton, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(stopButton, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                            .addContainerGap(112, Short.MAX_VALUE))
                );
                panel2Layout.setVerticalGroup(
                    panel2Layout.createParallelGroup()
                        .addGroup(panel2Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(portTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(startButton)
                                .addComponent(stopButton))
                            .addContainerGap(26, Short.MAX_VALUE))
                );
            }

            //======== scrollPane1 ========
            {
                scrollPane1.setViewportView(serverTextArea);
            }

            GroupLayout panel1Layout = new GroupLayout(panel1);
            panel1.setLayout(panel1Layout);
            panel1Layout.setHorizontalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panel1Layout.createParallelGroup()
                            .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE)
                            .addComponent(panel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
            );
            panel1Layout.setVerticalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(panel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
                        .addContainerGap())
            );
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(panel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap())
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(panel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap())
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel panel1;
    private JPanel panel2;
    private JTextField portTextField;
    private JButton startButton;
    private JButton stopButton;
    private JScrollPane scrollPane1;
    private JTextArea serverTextArea;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
