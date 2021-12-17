package com.company;

import com.mysql.cj.AppendingBatchVisitor;

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.table.*;
import java.sql.*;
import java.lang.*;
import java.awt.event.*;
import java.util.Vector;
import java.util.*;

class Page_2 extends Component implements ActionListener {

    private JFrame fr;
    Container c;
    private JTextArea ta;
    private JLabel title, c1, c2, c3, total, item, date, quantity, mess,bg;
    private JComboBox box1, d, m, y;
    private JTextField qtf;
    private JButton add, cfo;
    JTable j;
    JMenu menu1, menu2;
    JMenuItem p1, p2, p3, o1, o2;
    DefaultTableModel model;

    String pnm;
    String finDis;
    String column[] = {"Product ID", "Product Name", "Date", "Quantity", "Price", "Total"};
    private String[] options = {"NONE", "Eraser", "Geometry_Box", "Notebook", "Pen", "Pencil"};
    private String dates[] = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
    private String months[] = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    private String years[] = {"2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025"};

    Connection connection1;
    Statement statement1;
    ResultSet resultSet1;
    PreparedStatement pst;

    String url = "jdbc:mysql://localhost:3306/mini_project";
    String username = "root";
    String password = "Manavdoshi31";

    Page_2() {
        fr = new JFrame("Ordering");
        fr.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        c = fr.getContentPane();
        c.setLayout(null);

       bg = new JLabel(new ImageIcon("Images/img3.jpg"));
        bg.setBounds(0,0,890,680);
        c.add(bg);

        // menu bar code
        JMenuBar mb = new JMenuBar();
        menu1 = new JMenu("Pages");
        menu2 = new JMenu("Help");

        p1 = new JMenuItem("Main Page");
        p1.addActionListener(this);

        p2 = new JMenuItem("Inventory");
        p2.addActionListener(this);

        p3 = new JMenuItem("Reset Page");
        p3.addActionListener(this);

        menu1.add(p1);
        menu1.add(p2);
        menu1.add(p3);

        o1 = new JMenuItem("Contact Us");
        o2 = new JMenuItem("About Us");
        menu2.add(o1);
        menu2.add(o2);

        mb.add(menu1);
        mb.add(menu2);
        fr.setJMenuBar(mb);

        title = new JLabel("Stock Ordering");
        title.setFont(new Font("Cambria", Font.PLAIN, 30));
        title.setSize(350, 50);
        title.setLocation(325, 63);
        bg.add(title);

        item = new JLabel("Item : ");
        item.setFont(new Font("Cambria", Font.PLAIN, 19 ));
        item.setSize(97, 20);
        item.setLocation(100, 380);
        item.setBackground(Color.blue);
        bg.add(item);

        date = new JLabel("Date : ");
        date.setFont(new Font("Cambria", Font.PLAIN, 19));
        date.setSize(97, 20);
        date.setLocation(100, 419);
        bg.add(date);

        d = new JComboBox(dates);
        d.setFont(new Font("Arial", Font.PLAIN, 16));
        d.setSize(50, 24);
        d.setLocation(165, 419);
        bg.add(d);

        m = new JComboBox(months);
        m.setFont(new Font("Arial", Font.PLAIN, 16));
        m.setSize(80, 24);
        m.setLocation(225, 419);
        bg.add(m);

        y = new JComboBox(years);
        y.setFont(new Font("Arial", Font.PLAIN, 16));
        y.setSize(80, 24);
        y.setLocation(315, 419);
        bg.add(y);

        quantity = new JLabel("Quantity : ");
        quantity.setFont(new Font("Cambria", Font.PLAIN, 19));
        quantity.setSize(97, 20);
        quantity.setLocation(100, 458);
        bg.add(quantity);

        qtf = new JTextField();
        qtf.setFont(new Font("Arial", Font.PLAIN, 16));
        qtf.setSize(193, 23);
        qtf.setLocation(200, 458);
        bg.add(qtf);

        add = new JButton("Add");
        add.setFont(new Font("Cambria", Font.PLAIN, 17));
        add.setSize(90, 21);
        add.setLocation(110, 505);
        add.addActionListener(this);
        bg.add(add);

        cfo = new JButton("Confirm Order");
        cfo.setFont(new Font("Cambria", Font.PLAIN, 17));
        cfo.setSize(160, 21);
        cfo.setLocation(570, 390);
        cfo.addActionListener(this);
        bg.add(cfo);

        mess = new JLabel("");
        mess.setFont(new Font("Cambria", Font.BOLD, 17));
        mess.setSize(270, 20);
        mess.setLocation(560, 430);
        bg.add(mess);

        fr.setVisible(true);
        fr.setSize(890, 680);
        fr.setLayout(null);
        fr.setResizable(false);

        // table code
        model = new DefaultTableModel();
        model.setColumnIdentifiers(column);

        j = new JTable(model);
        j.setModel(model);
        j.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        j.setFillsViewportHeight(true);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        j.setDefaultRenderer(String.class, centerRenderer);

        JScrollPane sp = new JScrollPane(j);
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        bg.add(sp);
        sp.setBounds(90, 130, 680, 230);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection1 = DriverManager.getConnection(url, username, password);

            statement1 = connection1.createStatement();

            resultSet1 = statement1.executeQuery("select product_type from stock_ordered");

            Vector v = new Vector();

            while (resultSet1.next()) {
                pnm = resultSet1.getString(1);
                v.add(pnm);
            }

            box1 = new JComboBox(options);
            box1.setBounds(165, 380, 228, 25);
            box1.setFont(new Font("Cambria", Font.PLAIN, 17));
            c.add(box1);
            bg.add(box1);

            statement1.close();
            resultSet1.close();
        } catch (Exception ea) {
            System.out.println(ea);
        }

        box1.setOpaque(true);
        fr.setVisible(true);
        fr.setBackground(Color.lightGray);
        fr.setSize(890, 680);
        fr.setLayout(null);
    }

    public void actionPerformed(ActionEvent e) {

        String  ff = null;
        // to verify that all fields are entered
        if (e.getSource() == add) {
            if (box1.getSelectedItem().equals("") || d.getSelectedItem().equals("") || m.getSelectedItem().equals("") || y.getSelectedItem().equals("") || qtf.getText().equals("")) {
                String text = "Please Enter All Fields!";
                String title = "Alert Message";
                int optionType = JOptionPane.OK_CANCEL_OPTION;
                int result = JOptionPane.showConfirmDialog(null, text, title, optionType);
                if (result == JOptionPane.OK_OPTION) ;
            }
            else {
                finDis = (String) box1.getSelectedItem(); //copying the selected item
                System.out.println(finDis);

                String prno = " ";
                String ptyp = " ";
                String cost_p = " ";

                String dd = (String) d.getSelectedItem();
                String mm = (String) m.getSelectedItem();
                String yy = (String) y.getSelectedItem();
                ff = yy + "-" + mm + "-" + dd;

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");

                    connection1 = DriverManager.getConnection(url, username, password);

                    pst = connection1.prepareStatement("select * from stock_ordered where Product_type = '" + finDis + "' ");
                    resultSet1 = pst.executeQuery();

                    int i = 0;

                    if (resultSet1.next()) {
                        prno = resultSet1.getString("Product_no");
                        ptyp = resultSet1.getString("Product_type");
                        cost_p = resultSet1.getString("Cost_price");

                        float num = Float.parseFloat(qtf.getText());
                        float tot = num * Float.parseFloat(cost_p);

                        int qt = Integer.parseInt(qtf.getText());

                        model.addRow(new Object[]{prno, ptyp, ff, qt, cost_p, tot});
                        i++;
                    }
                    if (i < 1) {
                        JOptionPane.showMessageDialog(null, "No Record Found", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    if (i == 1) {
                        System.out.println(i + " Record Found");
                    } else {
                        System.out.println(i + " Records Found");
                    }
                    statement1.close();
                    resultSet1.close();
                }
                catch (Exception ea) {
                    JOptionPane.showMessageDialog(null, ea.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                //successfully added message
                JOptionPane.showMessageDialog(fr, "Item Added Successfully..!");

                String def = "";
                d.setSelectedIndex(0);
                m.setSelectedIndex(0);
                y.setSelectedIndex(0);
                qtf.setText(def);
            }
        }

        if (e.getSource() == cfo) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");

                connection1 = DriverManager.getConnection(url, username, password);
                statement1 = connection1.createStatement();

                int rocount = j.getRowCount();
                System.out.println(rocount);

                for (int i = 0; i < rocount; i++) {
                    String tbl_qt = j.getValueAt(i, 3).toString();
                    String p_typ = j.getValueAt(i, 1).toString();
                    String date = j.getValueAt(i,2).toString();

                    pst = connection1.prepareStatement("select quantity from stock_ordered  WHERE Product_type = '" + p_typ + "'");
                    resultSet1 = pst.executeQuery();

                    if (resultSet1.next()) {
                        String database_qt = resultSet1.getString("Quantity");

                        float final_update_qt = Float.parseFloat(tbl_qt) + Float.parseFloat(database_qt);
                        System.out.println(tbl_qt);
                        System.out.println(final_update_qt);

                        statement1.executeUpdate("UPDATE Stock_ordered SET Quantity = ('" + final_update_qt + "') , Date_of_order = ('"+date+"') WHERE Product_type = '" + p_typ + "'");
                    }
                }
                JOptionPane.showMessageDialog(null, " Order Placed. \nThank You!");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.out.println(ex);
            }
            mess.setText("Order Placed Successfully !");
        }

        if (e.getSource() == p1) {
            new Page_1();
        }
        if (e.getSource() == p2) {
            new Page_3();
        }
        if (e.getSource() == p3) {
            model.setRowCount(0);
        }
    }

    static class Page_3 extends JFrame implements ActionListener {

        Connection connection1;
        Statement statement1, statement2;
        ResultSet resultSet1, resultSet2;
        PreparedStatement pst;

        String url = "jdbc:mysql://localhost:3306/mini_project";
        String username = "root";
        String password = "Manavdoshi31";

        private JFrame fr;
        Container c;
        JTextField txt1, txt2, txt3;
        JLabel title, MostSold, total, ls,bg3;
        JMenu mm1, mm2;
        JMenuItem h1, h2, h3, k1, k2;
        JButton e,show;
        JTable jt;
        
        int[] profit = new int[5];
        int sum=0;

        int[] cp = new int[5]; // Cost Price
        int[] sp = new int[5]; // Selling Price
        int[] qs=new int[5]; //Quantity sold
        int[] qo=new int[5]; //Quantity order
        int[] ql=new int[5]; //Quantity left
        ArrayList<String> ar = new ArrayList<String>();

        Page_3() {
            //setting up frame
            fr = new JFrame("Inventory ");
            fr.setFont(new Font("Cambria", Font.PLAIN, 15));
            fr.setResizable(false);
            fr.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            fr.setBackground(Color.lightGray);

            c = fr.getContentPane();
            c.setLayout(null);

            c = fr.getContentPane();
            c.setLayout(null);
            c.setBackground(Color.lightGray);

            bg3 = new JLabel(new ImageIcon("Images/img3.jpg"));
            bg3.setBounds(0,0,890,680);
            c.add(bg3);

            //setting menu bar
            JMenuBar mb = new JMenuBar();
            mm1 = new JMenu("Pages");
            mm2 = new JMenu("Help");

            h1 = new JMenuItem("Main Page ");
            h1.addActionListener(this);

            h2 = new JMenuItem("Orders");
            h2.addActionListener(this);

            h3 = new JMenuItem("Reset Page");
            h3.addActionListener(this);

            mm1.add(h1);
            mm1.add(h2);
            mm1.add(h3);
            k1 = new JMenuItem("Contact Us");
            k2 = new JMenuItem("About Us");
            mm2.add(k1);
            mm2.add(k2);

            mb.add(mm1);
            mb.add(mm2);
            fr.setJMenuBar(mb);

            title = new JLabel("Stock Analysis");
            title.setFont(new Font("Cambria", Font.PLAIN, 30));
            title.setSize(250, 50);//350
            title.setLocation(325, 63);
            c.add(title);
            bg3.add(title);

            //most sold label
            MostSold = new JLabel("Most Sold Product: ");
            MostSold.setFont(new Font("Cambria", Font.PLAIN, 19));
            MostSold.setSize(200, 150);
            MostSold.setLocation(105, 358);
            c.add(MostSold);
            bg3.add(MostSold);

            txt1 = new JTextField();
            txt1.setFont(new Font("Arial", Font.PLAIN, 16));
            txt1.setSize(300, 23);
            txt1.setLocation(365, 425);
            txt1.setText("");
            txt1.setEditable(false);
            c.add(txt1);

            show = new JButton("Show Analysis");
            show.setFont(new Font("Cambria", Font.PLAIN, 17));
            show.setSize(160, 21);
            show.setLocation(580, 285);
            show.addActionListener(this);
            c.add(show);
            bg3.add(show);

            //exit button
            e = new JButton("Exit");
            e.setFont(new Font("Cambria", Font.PLAIN, 17));
            e.setSize(160, 21);
            e.setLocation(365, 525);
            e.addActionListener(this);
            bg3.add(e);
            c.add(e);

            //profit
            try {
                Class.forName("com.mysql.cj.jdbc.Driver"); //driver class
                Connection connection = DriverManager.getConnection(url, username, password);
                Statement statement = connection.createStatement();
                String q[] = {" select * from stock_ordered;", "select * from stock_sold;"};

                //int count=0;
                for (int i = 0; i < q.length; i++) {
                    ResultSet rs = statement.executeQuery(q[i]);
                    int count = 0;
                    while (rs.next()) {
                        if (i == 0) {
                            cp[count] = rs.getInt("Cost_price");
                            qo[count]=rs.getInt("Quantity");

                        } else if (i == 1) {
                            sp[count] = rs.getInt("Sell_price");
                            qs[count]=rs.getInt("Quantity_sold");
                        }
                        count++;
                    }
                }

                //loop counting qt left and profit
                int[] profit = new int[5];
                for (int k = 0; k < 5; k++) {
                    profit[k] =( sp[k] - cp[k])*(qs[k]);
                    sum=sum+profit[k];
                    ql[k]=( qo[k] - qs[k]);
                }

                //total profit earned
                total = new JLabel("Total Profit Earned: ");
                total.setFont(new Font("Cambria", Font.PLAIN, 19));
                total.setSize(250, 150);
                total.setLocation(105, 268);
                c.add(total);
                bg3.add(total);

                txt2 = new JTextField();
                txt2.setFont(new Font("Arial", Font.PLAIN, 16));
                txt2.setSize(300, 23);
                txt2.setLocation(365, 332);
                txt2.setEditable(false);
                c.add(txt2);
                bg3.add(txt2);

                //stock availability
                String pencil,pen,eraser,notebook,geo_box;
                //pencil
                if(ql[0]<=(0.25*qo[0]) && ql[0]!=0)
                {
                    pencil="Stock low ";
                    ar.add("pencil");
                }
                else if(ql[0]<=0 )
                {
                    pencil="Out of stock ";
                }
                else {
                    pencil="Stock available";
                }

                //pen
                if(ql[1]<=(0.25*qo[1]) && ql[1]!=0)
                {
                    pen="Stock low";
                    ar.add("pen");
                }
                else if(ql[1]<=0 )
                {
                    pen="Out of stock ";
                }
                else{
                    pen="Stock available";
                }
                //eraser
                if(ql[2]<=(0.25*qo[2]) && ql[2]!=0)
                {
                    eraser="Stock low ";
                    ar.add("eraser");
                }
                else if(ql[2]<=0 )
                {
                    eraser="Out of stock ";
                }
                else{
                    eraser="Stock available";
                }
                //notebook
                if(ql[3]<=(0.25*qo[3]) && ql[3]!=0)
                {
                    notebook="Stock low ";
                    ar.add("notebook");
                }
                else if(ql[3]<=0 )
                {
                    notebook="Out of stock ";
                }
                else{
                    notebook="Stock available";
                }
                //geometry box
                if(ql[4]<=(0.25*qo[4]) && ql[4]!=0)
                {
                    geo_box="Stock low ";
                    ar.add("Geometry Box");
                }
                else if(ql[4]<=0 )
                {
                    geo_box="Out of stock ";
                }
                else{
                    geo_box="Stock available";
                }

                //low stock label
                ls = new JLabel("Products with low stock: ");
                ls.setFont(new Font("Cambria", Font.PLAIN, 19));
                ls.setSize(310, 150);
                ls.setLocation(105, 312);
                c.add(ls);
                bg3.add(ls);

                txt3 = new JTextField();
                txt3.setFont(new Font("Arial", Font.PLAIN, 16));
                txt3.setSize(300, 23);
                txt3.setLocation(365, 377);
                txt3.setEditable(false);
                c.add(txt3);
                bg3.add(txt3);


                //Table Code begins
                String column[] = {"Product Name", "Quantity Left ", "Profit Earned ", "Stock Availability"};
                String data[][] = {{"Pencil",String.valueOf(ql[0]), String.valueOf(profit[0]), pencil },
                        {"Pen", String.valueOf(ql[1]), String.valueOf(profit[1]), pen},
                        {"Eraser ", String.valueOf(ql[2]), String.valueOf(profit[2]), eraser}, {"Notebook", String.valueOf(ql[3]), String.valueOf(profit[3]), notebook}, {"Geometry Box", String.valueOf(ql[4]),String.valueOf(profit[4]), geo_box}};

                DefaultTableModel model = new DefaultTableModel(data, column){
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };

                jt = new
                        JTable(model);

                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(JLabel.CENTER);
                jt.setDefaultRenderer(String.class, centerRenderer);

                JScrollPane sp2 = new JScrollPane(jt);
                fr.add(sp2);
                bg3.add(sp2);
                sp2.setBounds(90, 140, 680, 115);
                //sp.setBounds(90,130,680,230);
                fr.setVisible(true);
                fr.setSize(890, 680);
                fr.setLayout(null);

                //Table code ends
                connection.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if(e.getSource()==h1)
            {
                new Page_1();
            }
            if(e.getSource()==h2)
            {
                new Page_2();
            }
            if(e.getSource()==show)
            {
                txt2.setText("" +sum);
                txt3.setText(ar.toString());

                try{
                    Class.forName("com.mysql.cj.jdbc.Driver"); //driver class
                    Connection connection = DriverManager.getConnection(url, username, password);
                    Statement statement = connection.createStatement();

                    //most sold product

                    PreparedStatement  prs = connection.prepareStatement(" select max(Quantity_sold),Product_type from stock_sold;");
                    ResultSet  rs1 = prs.executeQuery();
                    if(rs1.next()) {
                        System.out.println(rs1.getString("Product_type"));
                        txt1.setText(rs1.getString("Product_type"));
                    }
                }
                catch (Exception e10)
                {
                    System.out.println(e10);
                }
            }
        }
    }

    static class Page_1 extends Component implements ActionListener {

        JFrame frm;
        JButton b1, b2, b3, b4, b5, print;
        JTextArea bill;
        JLabel ttl, line, line1, line2, line3, bi, date2,bg1;
        JComboBox box1, d2, mo, y2;
        Container c;

        JMenu m1, m2;
        JMenuItem g1, g2, g3, a1, a2;

        public float a, b, v, d, f;

        private String dates[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
        private String months[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        private String years[] = {"2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025"};

        String click,fff, he1, he2, he3, he4, fin, ln1, ln2;

        String url = "jdbc:mysql://localhost:3306/mini_project";
        String username = "root";
        String password = "Manavdoshi31";

        Connection connection1;
        Statement statement1,st2;
        ResultSet resultSet1;
        PreparedStatement pst;

        Page_1() {
            frm = new JFrame("MAIN PAGE");
            frm.setFont(new Font("Cambria", Font.PLAIN, 15));
            frm.setResizable(false);
            frm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frm.setBackground(Color.lightGray);

            c = frm.getContentPane();
            c.setLayout(null);

            c = frm.getContentPane();
            c.setLayout(null);

            bg1 = new JLabel(new ImageIcon("Images/img3.jpg"));
            bg1.setBounds(0,0,890,680);
            c.add(bg1);

            JMenuBar mmm = new JMenuBar();
            m1 = new JMenu("Pages");
            m2 = new JMenu("Help");

            g1 = new JMenuItem("Ordering ");
            g1.addActionListener(this);

            g2 = new JMenuItem("Inventory");
            g2.addActionListener(this);

            g3 = new JMenuItem("Reset Page");
            g3.addActionListener(this);

            m1.add(g1);
            m1.add(g2);
            m1.add(g3);
            a1 = new JMenuItem("Contact Us");
            a2 = new JMenuItem("About Us");
            m2.add(a1);
            m2.add(a2);

            mmm.add(m1);
            mmm.add(m2);
            frm.setJMenuBar(mmm);

            line = new JLabel("--------------------------------------------------------------------------------------");
            line.setFont(new Font("Verdana", Font.PLAIN, 30));
            line.setSize(2200, 10);
            line.setLocation(0, 50);
            c.add(line);
            bg1.add(line);

            line1 = new JLabel("--------------------------------------------------------------------------------------");
            line1.setFont(new Font("Verdana", Font.PLAIN, 30));
            line1.setSize(2200, 10);
            line1.setLocation(0, 65);
            c.add(line1);
            bg1.add(line1);

            line2 = new JLabel("--------------------------------------------------------------------------------------");
            line2.setFont(new Font("Verdana", Font.PLAIN, 30));
            line2.setSize(2200, 10);
            line2.setLocation(0, 120);
            c.add(line2);
            bg1.add(line2);

            line3 = new JLabel("--------------------------------------------------------------------------------------");
            line3.setFont(new Font("Verdana", Font.PLAIN, 30));
            line3.setSize(2200, 10);
            line3.setLocation(0, 135);
            c.add(line3);
            bg1.add(line3);

            ttl = new JLabel("TSEC Stationary");
            ttl.setFont(new Font("Cambria", Font.PLAIN, 30));
            ttl.setSize(350, 50);
            ttl.setLocation(285, 75);
            c.add(ttl);
            bg1.add(ttl);

            b1 = new JButton("Eraser");
            b1.setFont(new Font("Cambria", Font.PLAIN, 16));
            b1.setSize(120, 35);
            b1.setLocation(55, 265);
            b1.addActionListener(this);
            c.add(b1);
            bg1.add(b1);

            b2 = new JButton("Geometry Box");
            b2.setFont(new Font("Cambria", Font.PLAIN, 16));
            b2.setSize(140, 35);
            b2.setLocation(228, 265);
            b2.addActionListener(this);
            c.add(b2);
            bg1.add(b2);

            b3 = new JButton("Notebook");
            b3.setFont(new Font("Cambria", Font.PLAIN, 16));
            b3.setSize(120, 35);
            b3.setLocation(55, 325);
            b3.addActionListener(this);
            c.add(b3);
            bg1.add(b3);

            b4 = new JButton("Pen");
            b4.setFont(new Font("Cambria", Font.PLAIN, 16));
            b4.setSize(140, 35);
            b4.setLocation(228, 325);
            b4.addActionListener(this);
            c.add(b4);
            bg1.add(b4);

            b5 = new JButton("Pencil");
            b5.setFont(new Font("Cambria", Font.PLAIN, 16));
            b5.setSize(135, 35);
            b5.setLocation(125, 387);
            b5.addActionListener(this);
            c.add(b5);
            bg1.add(b5);

            date2 = new JLabel("Date : ");
            date2.setFont(new Font("Cambria", Font.PLAIN, 19));
            date2.setSize(97, 20);
            date2.setLocation(55, 465);
            c.add(date2);
            bg1.add(date2);

            d2 = new JComboBox(dates);
            d2.setFont(new Font("Arial", Font.PLAIN, 16));
            d2.setSize(50, 24);
            d2.setLocation(120, 465);
            c.add(d2);
            bg1.add(d2);

            mo = new JComboBox(months);
            mo.setFont(new Font("Arial", Font.PLAIN, 16));
            mo.setSize(80, 24);
            mo.setLocation(180, 465);
            c.add(mo);
            bg1.add(mo);

            y2 = new JComboBox(years);
            y2.setFont(new Font("Arial", Font.PLAIN, 16));
            y2.setSize(80, 24);
            y2.setLocation(270, 465);
            c.add(y2);
            bg1.add(y2);

            bi = new JLabel("Customer Bill");
            bi.setFont(new Font("Cambria", Font.BOLD, 20));
            bi.setSize(180, 30);
            bi.setLocation(563, 190);
            c.add(bi);
            bg1.add(bi);

            bill = new JTextArea();
            bill.setFont(new Font("Cambria", Font.BOLD, 17));
            bill.setSize(370, 350);
            bill.setLocation(453, 225);
            bill.setLineWrap(true);
            bill.setEditable(false);
            c.add(bill);
            bg1.add(bill);

            he1 = "Pr Name ";
            he2 = "Qt ";
            he3 = "Price";
            he4 = "TOTAL\n";
            ln1 = "_________________________________________________________";
            ln2 = "_________________________________________________________";
            fin = ln2 + "   " + he1 + "       |    " + he2 + "       |    " + he3 + "     |    " + he4 + ln1;
            bill.append(fin);

            print = new JButton("Print Bill");
            print.setFont(new Font("Cambria", Font.PLAIN, 19));
            print.setSize(200, 35);
            print.setLocation(87, 525);
            print.addActionListener(this);
            c.add(print);
            bg1.add(print);

            frm.setVisible(true);
            frm.setSize(890, 680);
            frm.setLayout(null);
        }

        public void actionPerformed(ActionEvent e) {

            String info;
            if (e.getSource() == g1) {
                new Page_2();
            }
            if (e.getSource() == g2) {
                new Page_3();
            }

            String dd = (String) d2.getSelectedItem();
            String mm = (String) mo.getSelectedItem();
            String yy = (String) y2.getSelectedItem();
            fff = yy + "-" + mm + "-" + dd;

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");

                if (e.getSource() == b1) {
                    click = "P0003";
                } else if (e.getSource() == b2) {
                    click = "P0005";
                } else if (e.getSource() == b3) {
                    click = "P0004";
                } else if (e.getSource() == b4) {
                    click = "P0002";
                } else if (e.getSource() == b5) {
                    click = "P0001";
                }

                connection1 = DriverManager.getConnection(url, username, password);
                statement1 = connection1.createStatement();

                pst = connection1.prepareStatement("select Quantity_sold from stock_sold  WHERE Product_no = '" + click + "'");
                resultSet1 = pst.executeQuery();

                if (resultSet1.next()) {
                    if (e.getSource() == b1) {
                        String name = JOptionPane.showInputDialog(frm, "Enter the Quantity of Eraser");

                        String db_qt = resultSet1.getString("Quantity_sold");
                        int qt_left = Integer.parseInt(db_qt) + Integer.parseInt(name);
                        System.out.println(qt_left);


                        a = Float.parseFloat(name) * (float) 10.0;
                        bill.append("\n Eraser                    " + name + "           10.0" + "                 " + a);

                        statement1.execute("UPDATE Stock_sold SET Quantity_sold = ('" + qt_left + "') , Date_of_sale = '"+fff+"' WHERE Product_no = '"+click+"'");
                    }
                }
                if (e.getSource() == b2) {
                    String name = JOptionPane.showInputDialog(frm, "Enter the Quantity of Geometry Box");

                    String db_qt = resultSet1.getString("Quantity_sold");
                    int qt_left = Integer.parseInt(db_qt) + Integer.parseInt(name);
                    System.out.println(qt_left);

                    b = Float.parseFloat(name) * (float) 100.0;
                    bill.append("\n Geometry Box     " + name + "           100.0" + "               " + b);

                    statement1.execute("UPDATE Stock_sold SET Quantity_sold = ('" + qt_left + "'), Date_of_sale = '"+fff+"' WHERE Product_no = '"+click+"'");
                }

                if (e.getSource() == b3) {
                    String name = JOptionPane.showInputDialog(frm, "Enter the Quantity of Notebook");

                    String db_qt = resultSet1.getString("Quantity_sold");
                    int qt_left = Integer.parseInt(db_qt) + Integer.parseInt(name);
                    System.out.println(qt_left);

                    v = Float.parseFloat(name) * (float) 65.0;
                    bill.append("\n Notebook              " + name + "           65.0" + "                 " + v);

                    statement1.execute("UPDATE Stock_sold SET Quantity_sold = ('" + qt_left + "'), Date_of_sale = '"+fff+"' WHERE Product_no = '"+click+"'");
                }

                if (e.getSource() == b4) {
                    String name = JOptionPane.showInputDialog(frm, "Enter the Quantity of Pen");

                    String db_qt = resultSet1.getString("Quantity_sold");
                    int qt_left = Integer.parseInt(db_qt) + Integer.parseInt(name);
                    System.out.println(qt_left);

                    d = Float.parseFloat(name) * (float) 20.0;
                    bill.append("\n Pen                         " + name + "            20.0" + "                " + d);

                    statement1.execute("UPDATE Stock_sold SET Quantity_sold = ('" + qt_left + "'), Date_of_sale = '"+fff+"' WHERE Product_no = '"+click+"'");
                }

                if (e.getSource() == b5) {
                    String name = JOptionPane.showInputDialog(frm, "Enter the Quantity of Pencil");

                    String db_qt = resultSet1.getString("Quantity_sold");
                    int qt_left = Integer.parseInt(db_qt) + Integer.parseInt(name);
                    System.out.println(qt_left);

                    f = Float.parseFloat(name) * (float) 15.0;
                    bill.append("\n Pencil                    " + name + "             15.0" + "                 " + f);

                    statement1.execute("UPDATE Stock_sold SET Quantity_sold = ('" + qt_left + "'), Date_of_sale = '"+fff+"' WHERE Product_no = '"+click+"'");
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            } catch (ClassNotFoundException exception) {
                exception.printStackTrace();
            }

            if (e.getSource() == print) {
                float sum = this.b + this.a + this.f + this.d + this.v;
                System.out.println(sum);
                bill.append("\n\n\n-----------------------------------------------------------------                         TOTAL AMOUNT = " + sum);
            }
            if(e.getSource()==g3)
            {
                bill.setText(" ");
                he1 = "Pr Name ";
                he2 = "Qt ";
                he3 = "Price";
                he4 = "TOTAL\n";
                ln1 = "_________________________________________________________";
                ln2 = "_________________________________________________________";
                fin = ln2 + "   " + he1 + "       |    " + he2 + "       |    " + he3 + "     |    " + he4 + ln1;
                bill.append(fin);
                d2.setSelectedIndex(0);
                mo.setSelectedIndex(0);
                y2.setSelectedIndex(0);

            }
        }
    }
        public static void main(String[] args) {
            new Page_1();
    }
}
