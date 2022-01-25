
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;

public class GUI extends JFrame {
    private static JButton encrypt = new JButton("Encrypt");
    private static JButton decrypt = new JButton("Decrypt");
    private static JButton hack = new JButton("Hack");
    private static JPanel panel = new JPanel();
    private static JLabel label = new JLabel("Please click one of the buttons below");


    public GUI (){
        super("Caesars");
        this.setVisible(true);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width / 2 - 250, dimension.height / 2 - 250, 500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) throws Exception {
        GUI gui = new GUI();
        gui.add(panel);
        panel.setLayout(new GridLayout(5,3,2,2));
        panel.add(label);
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
                int exit = JOptionPane.showConfirmDialog(panel, "Do you want to do something else?", "Question", JOptionPane.YES_NO_OPTION);
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
    }
}
