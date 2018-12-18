import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;


import javax.swing.table.DefaultTableModel;

public class Connector extends JPanel {
    public boolean DEBUG = false;
    private static final long serialVersionUID = 1L;

    private static ResultSet rs;


    Connection conn = null;
    Statement st = null;
    static Vector<Vector<String>> data = new Vector<Vector<String>>();

    public Connector() {
        super(new GridLayout(1, 0));


        Vector<String> columnNames = new Vector<String>();
        columnNames.add("Sum(points)");
        columnNames.add("name");


        String sqlquery = "SELECT Sum(points),name FROM "
                + "(SELECT SUM(hometeam_points) as points ,teams.team_id,name FROM scores,teams WHERE hometeam_id=teams.team_id "
                + "GROUP By team_id UNION ALL SELECT SUM(awayteam_points) as points ,teams.team_id,name FROM scores,teams "
                + "WHERE awayteam_id=teams.team_id GROUP BY team_id ) as T GROUP By name ORDER By SUM(points) DESC";


        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/bundesliga", "root", "");
            st = conn.createStatement();
            ResultSet rs = st.executeQuery(sqlquery);
            while (rs.next()) {

                Vector<String> vstring = new Vector<String>();


                vstring.add(rs.getString("Sum(points)"));
                vstring.add(rs.getString("name"));

                vstring.add("\n\n\n\n\n\n\n");

                data.add(vstring);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ex) {
                    System.out.println("Error: " + ex);
                }
            }
        }

        //  final JTable table = new JTable(data, columnNames);
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        final JTable table1 = new JTable(model);
        table1.setPreferredScrollableViewportSize(new Dimension(500, 200));
        table1.setFillsViewportHeight(true);

        if (DEBUG) {
            table1.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    printDebugData(table1);
                }
            });
        }

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table1);

        //Add the scroll pane to this panel.
        add(scrollPane);
    }
    public void printDebugData(JTable table) {
        int numRows = table.getRowCount();
        int numCols = table.getColumnCount();
        javax.swing.table.TableModel model = table.getModel();

        System.out.println("Value of data: ");
        for (int i = 0; i < numRows; i++) {
            System.out.print("    row " + i + ":");
            for (int j = 0; j < numCols; j++) {
                System.out.print("  " + model.getValueAt(i, j));
            }
            System.out.println();
        }
        System.out.println("--------------------------");
    }
}
