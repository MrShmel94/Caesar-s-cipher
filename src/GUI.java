
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GUI extends JFrame {
    private static JFrame gui = new GUI();
    private static JButton encrypt = new JButton("Encrypt");
    private static JButton decrypt = new JButton("Decrypt");
    private static JButton hack = new JButton("Hack");
    public static JPanel panel = new JPanel();
    private static JLabel label = new JLabel("Please click one of the buttons below", SwingConstants.CENTER);


    public GUI (){
        super("Caesars");
        this.setVisible(true);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width / 2 - 250, dimension.height / 2 - 250, 500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) throws Exception {
        //GUI gui = new GUI();
        gui.add(panel);
        panel.setLayout(new GridLayout(5,3,2,2));
        panel.add(label, BorderLayout.CENTER);
        panel.add(encrypt);
        panel.add(decrypt);
        panel.add(hack);
        panel.setVisible(true);
        encrypt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                String fileNameEncrypt = JOptionPane.showInputDialog("Please copy link to file which you want Encrypt");
                String fileNameSave = JOptionPane.showInputDialog("Please come up with name file for save");
                int key = Integer.parseInt(JOptionPane.showInputDialog("Please write key"));
                CaesarsСipher.encryptDecipher("Encrypt", fileNameEncrypt, key, fileNameSave);
                int exit = JOptionPane.showConfirmDialog(new JScrollPane(panel), "Do you want to do something else?", "Question", JOptionPane.YES_NO_OPTION);
                if (exit == 1){
                    System.exit(0);
                }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel,"Error, please retry");
                }
            }
        });
        decrypt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String fileNameEncrypt = JOptionPane.showInputDialog("Please copy link to file which you want Decrypt");
                    String fileNameSave = JOptionPane.showInputDialog("Please come up with name file for save");
                    int key = Integer.parseInt(JOptionPane.showInputDialog("Please write key"));
                    CaesarsСipher.encryptDecipher("Decipher", fileNameEncrypt, key, fileNameSave);
                    int exit = JOptionPane.showConfirmDialog(panel, "Do you want to do something else?", "Question", JOptionPane.YES_NO_OPTION);
                    if (exit == 1){
                        System.exit(0);
                    }
                }catch (Exception eq){
                    JOptionPane.showMessageDialog(panel,"Error, please retry");
                }
            }
        });
        hack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myDialog dialog = new myDialog();
                dialog.setVisible(true);
            }
        });
    }

    static class Hack extends JPanel {
        BufferedImage image;

        public void paintComponent (Graphics g){
                try {
                    image = ImageIO.read(new File("/Users/mrshmel/Documents/GitHub/Caesar-s-cipher/src/anonymous.jpeg"));
                } catch (IOException e) {
                }
                g.drawImage(image, -50,-10, this);
        }
    }

    static class myDialog extends JDialog {

         static JButton bruteForce = new JButton("BruteForce");
         static JButton analysis = new JButton("Analysis");
         static Hack panelHack = new Hack();

        public myDialog() {
            super(gui, "Hack", false);
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Dimension dimension = toolkit.getScreenSize();
            this.setBounds(dimension.width / 2 - 250, dimension.height / 2 - 250, 500, 500);
            this.add(panelHack);
            this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            panelHack.setLayout(new GridLayout(3, 3, 2, 2));
            label.setForeground(Color.RED);
            panelHack.add(label);
            bruteForce.setForeground(Color.RED);
            panelHack.add(bruteForce);
            analysis.setForeground(Color.RED);
            panelHack.add(analysis);
            bruteForce.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try{
                        String fileNameHack = JOptionPane.showInputDialog("Please copy link to file which you want Hack");
                        String fileNameSave = JOptionPane.showInputDialog("Please come up with name file for save");
                        CaesarsСipher.breakingInto("BruteForce", fileNameHack, fileNameSave, null);
                        int exit = JOptionPane.showConfirmDialog(panelHack, "Do you want to do something else?", "Question", JOptionPane.YES_NO_OPTION);
                        if (exit == 1){
                            dispose();
                        }
                }catch (Exception ea){
                        JOptionPane.showMessageDialog(panelHack,"Error, please retry");
                    }
                }
            });
            analysis.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                    String fileNameHack = JOptionPane.showInputDialog("Please copy link to file which you want Hack");
                    String fileNameSave = JOptionPane.showInputDialog("Please come up with name file for save");
                    String fileNameAnalysis = JOptionPane.showInputDialog("Please copy link to file for additional analysis");
                    CaesarsСipher.breakingInto("StaticAnalysis", fileNameHack, fileNameSave, fileNameAnalysis);
                    int exit = JOptionPane.showConfirmDialog(panelHack, "Do you want to do something else?", "Question", JOptionPane.YES_NO_OPTION);
                    if (exit == 1){
                        dispose();
                    }
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(panelHack,"Error, please retry");
                    }
                }
            });
        }
    }
}
