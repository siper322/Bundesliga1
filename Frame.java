import javax.swing.*;



public class Frame2 extends JFrame {
    private JPanel mainPanel;
    private JTable table1;
    private JPanel mainpanel;
    private JPanel panel2;
    private JPanel panel1;
    private JComboBox comboBox1;
    private JButton dodajWynikButton;
    private JPanel panel3;
    private JButton edytujWynikButton;
    private JComboBox comboBox2;
    private JButton pokazTabeleButton;



    public Frame2() {

        add(mainpanel);
        setSize(500, 300);
        setTitle("Bundesliga 2018/2019");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocation(500, 250);


        Connector newContentPane = new Connector();
        newContentPane.setOpaque(true); //content panes must be opaque
        setContentPane(newContentPane);

    }
}
