/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qlycoffee;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HUYNH
 */
public class Bill_Form extends javax.swing.JFrame {

    /**
     * Creates new form BillForm
     */
    Vector vec;
    DefaultTableModel dtm;
    SimpleDateFormat ft = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy");
    NumberFormat n = new DecimalFormat("#,###");

    public Bill_Form(String NameEmp) throws UnknownHostException {
        initComponents();
        String x = NameEmp;
        txtguest.setEnabled(false);
        txtIDHĐ.setEnabled(false);
        txtempname.setText(x);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        clock();
        ImageIcon img = new ImageIcon("Image//coffee-tea.png");
        this.setIconImage(img.getImage());
        SetImage s = new SetImage();             
        s.setImageButton(btnPrintSave, "Image//Printer_Picture.png");
        s.setImageLabel(jLabel9, "Image//item_s11553.png");
        btnThem.setSize(20, 20);
        s.setImageButton(btnThem, "Image//addtocart.png");
        btnDel.setSize(40, 40);
        s.setImageButton(btnDel, "Image//delete.png");
        dtm = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jTable.setModel(dtm);
        Object[] obj = new Object[]{"Mã", "Tên sản phẩm", "Nhóm", "Kích thước", "Đơn giá (VNĐ)", "Số lượng (ly)", "Thành tiền"};
        dtm.setColumnIdentifiers(obj);
        Mongo mongo = new Mongo();
        DB db = mongo.getDB("QlyCofee");
        DBCollection products = db.getCollection("Product");

//        BasicDBObject projection = new BasicDBObject(new BasicDBObject("Size",new BasicDBObject("$elemMatch", new BasicDBObject("Size", "Nhỏ"))));
//        DBCursor cursor = products.find();
//        DBObject dbo1 = products.findOne();
        List searchResults = products.distinct("ProductName");
        int u = searchResults.size();
//                                System.out.println(searchResults);
//
//                        System.out.println(searchResults.get(0));
//                                                System.out.println(searchResults.get(1));
//                        System.out.println(searchResults.get(2));

        vec = new Vector();
//        int i=1;
//        while () {

//            vec.add((String) cursor.next().get("ProductName"));
//        }
        int i = 0;

        while (i < u) {
            vec.add(searchResults.get(i));
//                        System.out.println(searchResults.get(i));
            i++;
        }
        DefaultComboBoxModel dcm = new DefaultComboBoxModel(vec);
        cbproduct.setModel(dcm);
        JTextField text = (JTextField) cbproduct.getEditor().getEditorComponent();
        text.setText("");
        text.addKeyListener(new ComboListener(vec, cbproduct));
        cbproduct.requestFocusInWindow();

    }

    public void clock() {
        Thread runnable = new Thread() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Date t = new Date();
                        labeltime.setText(ft.format(t));
                        sleep(1000);
                    }
                } catch (InterruptedException e) {

                }
            }
        };
        Thread clock = new Thread(runnable);
        clock.start();
    }

    public void LoadTable() {
        try {

            Mongo mongo = new Mongo();
            DB db = mongo.getDB("QlyCofee");
//            DBCollection employees = db.getCollection("Product&Type");
//            BasicDBObject projection = new BasicDBObject("Size", new BasicDBObject("$elemMatch", new BasicDBObject("Size", (String) cbSize.getSelectedItem()))).append("ProductName", (String) cbproduct.getSelectedItem());
//            DBCursor cursor = employees.find(projection);

            DBCollection employees = db.getCollection("Product&ProductType");
//            BasicDBObject projection = new BasicDBObject("Size", new BasicDBObject("$elemMatch", new BasicDBObject("Size", (String) cbSize.getSelectedItem()))).append("ProductName", (String) cbproduct.getSelectedItem());
//            BasicDBObject projection = new BasicDBObject().append("","").append("username",1);

            DBCursor cursor = employees.find(new BasicDBObject().append("Size", (String) cbSize.getSelectedItem()).append("ProductName", (String) cbproduct.getSelectedItem()));

            DBObject dbo2 = (DBObject) employees.findOne(new BasicDBObject().append("Size", (String) cbSize.getSelectedItem()).append("ProductName", (String) cbproduct.getSelectedItem()));

            System.out.println(dbo2);
            int price, quantity, intoMoney;
            if (cursor.hasNext()) {
                vec = new Vector();
                price = (int) dbo2.get("Price");
                quantity = (int) spinnerquantity.getValue();
                intoMoney = price * quantity;
                vec.add((String) dbo2.get("IDProduct"));
                vec.add((String) dbo2.get("ProductName"));
                vec.add((String) dbo2.get("IDType"));
                vec.add((String) cbSize.getSelectedItem());
                vec.add(price);
                vec.add(spinnerquantity.getValue());
                vec.add(intoMoney);
                dtm.addRow(vec);
                JOptionPane.showMessageDialog(rootPane, "Thêm thành công");

                System.out.println(java.time.LocalDate.now());
                System.out.println(java.time.LocalTime.now());
                return;
//                
            } else {
                JOptionPane.showMessageDialog(rootPane, "Đéo có sản phẩm");
                cbproduct.requestFocusInWindow();

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Không thể kết nối đến máy chủ.");
        }
    }

    public void setText(Boolean b) {
        txtguest.setEnabled(b);

    }

    public void IntoMoney() {
        int count = jTable.getRowCount();
        int Price, TotalPrice = 0;
        for (int i = 0; i < count; i++) {
            Price = (int) jTable.getValueAt(i, 6);
            TotalPrice += Price;
        }
        txtTotal.setText(n.format(TotalPrice));
    }

    public void UpdateDiscountandPay() {
        int Discount;
        //Tính Discount        
        String TotalPrice = txtTotal.getText().replaceAll(",", "");
        Discount = (Integer.parseInt(txtdiscount1.getText()) * Integer.parseInt(TotalPrice)) / 100;
        txtdiscount2.setText(n.format(Discount));
        //Tính Pay
        int Pay = Integer.parseInt(TotalPrice) - Discount;
        txtpay.setText(n.format(Pay));
    }

    public void PrintandSave(String string) {

        int line = jTable.getRowCount();
        //Thêm bản ghi vào trong database
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/LLLL/yyyy");
            String x = java.time.LocalDate.now().format(formatter);
//            System.out.println(x);
            DateTimeFormatter uuu = DateTimeFormatter.ofPattern("HH:mm:ss");
            String y = java.time.LocalTime.now().format(uuu);
//            System.out.println(y);

            Mongo mongo = new Mongo();
            DB db = mongo.getDB("QlyCofee");
            DBCollection orders = db.getCollection("[Order]");

            BasicDBObject document = new BasicDBObject();

            document.put("IDHĐ", txtIDHĐ.getText());
            document.put("DateHĐ", x);
            document.put("TimeHĐ", y);
            document.put("userName", string);
//            BasicDBObject document2 = new BasicDBObject();

//            document.put("userName", string);
            orders.insert(document);
            JOptionPane.showMessageDialog(rootPane, "Đã tạo hóa đơn.");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Không thể kết nối đến máy chủ.");
        }
        for (int i = 0; i < line; i++) {
            try {
                Mongo mongo = new Mongo();
                DB db = mongo.getDB("QlyCofee");
                DBCollection orderdetails = db.getCollection("OrderDetail");
                BasicDBObject document = new BasicDBObject();
                document.put("IDHĐ", txtIDHĐ.getText());
                document.put("IDProduct", (String) jTable.getValueAt(i, 0));
                document.put("Quantity", (int) jTable.getValueAt(i, 5));
                orderdetails.insert(document);
            } catch (Exception e) {
            }
        }
        for (int i = 0; i < line; i++) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/LLLL/yyyy");
                String x = java.time.LocalDate.now().format(formatter);
//            System.out.println(x);
                DateTimeFormatter uuu = DateTimeFormatter.ofPattern("HH:mm:ss");
                String y = java.time.LocalTime.now().format(uuu);
//            System.out.println(y);
                Mongo mongo = new Mongo();
                DB db = mongo.getDB("QlyCofee");
                DBCollection history = db.getCollection("History");
                long cc = history.count();
//                DBCollection history = db.getCollection("History");
                DBCollection employees = db.getCollection("Product&Type");
//                BasicDBObject projection = new BasicDBObject("Size", new BasicDBObject("$elemMatch", new BasicDBObject("Size", (String) cbSize.getSelectedItem()))).append("ProductName", (String) cbproduct.getSelectedItem());
                BasicDBObject document2 = new BasicDBObject();
//                DBObject dbo2 = employees.findOne(projection);
                document2.put("STT", cc + 1);
                document2.put("IDHĐ", txtIDHĐ.getText());
                document2.put("IDProduct", (String) jTable.getValueAt(i, 0));
                document2.put("Quantity", (int) jTable.getValueAt(i, 5));
                document2.put("Price", (int) jTable.getValueAt(i, 4));
                document2.put("TimeHĐ", y);
                document2.put("DateHĐ", x);
                document2.put("username", string);
                history.insert(document2);
            } catch (Exception e) {
            }
        }

        //Kiểm tra số tiền ngày hôm nay và set lại giá trị
        String pay = txtpay.getText().replaceAll(",", "");
        try {
//            int i=1 ;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/LLLL/yyyy");
            String x = java.time.LocalDate.now().format(formatter);
            System.out.println(x);
            Mongo mongo = new Mongo();
            DB db = mongo.getDB("QlyCofee");
            DBCollection revenuee = db.getCollection("Revenue");
            long i = revenuee.count() + 1;
            DBCursor cursor = revenuee.find(new BasicDBObject().append("Date", x));
            if (cursor.hasNext()) {
                int money1 = Integer.parseInt((String) cursor.next().get("Money"));
                int money2 = Integer.parseInt(pay);
                int moneyTotal = money1 + money2;
                String m1 = String.valueOf(money1);
//                        String m2=String.valueOf(money2);
                String mt = String.valueOf(moneyTotal);
                BasicDBObject query = new BasicDBObject();
                query.put("Money", m1); // (1)

                BasicDBObject newDocument = new BasicDBObject();
                newDocument.put("Money", mt); // (2)

                BasicDBObject updateObject = new BasicDBObject();
                updateObject.put("$set", newDocument); // (3)

                revenuee.update(query, updateObject); // (4)
//                revenuee.updateOne(query, updateObject);

            } else {

//                Mongo mongo = new Mongo();
//                DB db = mongo.getDB("QlyCofee");
//                DBCollection revenuee = db.getCollection("Revenue");
                BasicDBObject revenues = new BasicDBObject();
                revenues.put("IDRevenue", i);
                revenues.put("Date", x);
                revenues.put("Money", pay);
                revenuee.insert(revenues);

            }
            JOptionPane.showMessageDialog(rootPane, "Đã cập nhật doanh thu trong ngày.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Không thể kết nối đến máy chủ.");
        }
        //Viết hóa đơn vào file.txt
        try {
            Date now = new Date();
            try (Writer bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("History//" + txtIDHĐ.getText().trim() + ".txt"), "UTF8"))) {
                bw.write("\t\t\t\tTHE HUYNH COFFEE\r\n\r\n");
                bw.write("\t\t\tTổ 4, P.Cự Khối, Q.Long Biên, Hà Nội\r\n");
                bw.write("\t\t\t\tSĐT: 0983923430\r\n\r\n");
                bw.write("\t\t\t\tHÓA ĐƠN BÁN HÀNG\r\n\r\n");
                bw.write("\tMã hóa đơn: " + txtIDHĐ.getText() + "\r\n");
                bw.write("\tThời gian: " + ft.format(now) + "\r\n");
                bw.write("\tNhân viên: " + txtempname.getText() + "\r\n");
                bw.write("\t-----------------------------------------------------------------\r\n");
                bw.write("\tMã\tKích thước\tSố lượng\tĐơn giá" + "\t" + "      Thành tiền\r\n");
                bw.write("\t-----------------------------------------------------------------\r\n");
                //Ghi sản phẩm
                int quanTotal = 0;
                for (int i = 0; i < line; i++) {
                    String id = (String) dtm.getValueAt(i, 0);
                    String name = (String) dtm.getValueAt(i, 1);
                    String size = (String) dtm.getValueAt(i, 3);
                    int price = (int) dtm.getValueAt(i, 4);
                    int quantity = (int) dtm.getValueAt(i, 5);
                    int TotalPrice = (int) dtm.getValueAt(i, 6);
                    bw.write("\t" + (i + 1) + "." + name + "\r\n");
                    bw.write("\t" + id + "\t" + size + "\t\t" + quantity + "\t\t" + price + "\t\t" + TotalPrice + "\r\n\r\n");
                    quanTotal += quantity;

                }
                int x = Integer.parseInt(txtguest.getText());
                bw.write("\t-----------------------------------------------------------------\r\n");
                bw.write("\tTổng cộng:  " + quanTotal + "\t\t\t" + txtTotal.getText() + "VNĐ\r\n");
                bw.write("\tChiết khấu: " + txtdiscount1.getText().trim() + " %\r\n");
                bw.write("\t-----------------------------------------------------------------\r\n");
                bw.write("\tThành tiền:\t\t\t" + txtpay.getText() + "VNĐ\r\n");
                bw.write("\t-----------------------------------------------------------------\r\n");
                bw.write("\tTiền khách đưa:\t\t\t" + n.format(x) + "VNĐ\r\n");
                bw.write("\tTiền trả lại:\t\t\t" + txtRepay.getText() + "VNĐ\r\n");
                bw.write("\t-----------------------------------------------------------------\r\n");
                bw.write("\tMật khẩu Wifi: huynhzip3\r\n");
                bw.write("\t-----------------------CẢM ƠN QUÝ KHÁCH--------------------------\r\n");
            }

        } catch (Exception e) {
        }
        //Mở file.txt
        Runtime run = Runtime.getRuntime();
        try {
            run.exec("notepad History//" + txtIDHĐ.getText().trim() + ".txt");
        } catch (IOException e) {
        }
        //Set lại bảng, combobox và textbox
        dtm.getDataVector().removeAllElements();
        jTable.revalidate();
        setText(false);
        txtguest.setText("0");
        txtRepay.setText("0");
        txtIDHĐ.setText("");
        txtdiscount1.setText("0");
        txtdiscount2.setText("0");
        txtTotal.setText("0");
        txtpay.setText("0");
        lbguest.setText("");

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtempname = new javax.swing.JTextField();
        cbproduct = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        spinnerquantity = new javax.swing.JSpinner();
        cbSize = new javax.swing.JComboBox<>();
        btnDel = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        btnPrintSave = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        txtdiscount1 = new javax.swing.JTextField();
        txtpay = new javax.swing.JTextField();
        txtguest = new javax.swing.JTextField();
        txtRepay = new javax.swing.JTextField();
        txtIDHĐ = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtdiscount2 = new javax.swing.JTextField();
        lbguest = new javax.swing.JLabel();
        btnThem = new javax.swing.JButton();
        labeltime = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Hóa Đơn");

        jLabel1.setFont(new java.awt.Font("Sitka Heading", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 0, 255));
        jLabel1.setText("Tên Nhân Viên:");

        jLabel2.setFont(new java.awt.Font("Sitka Heading", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 255));
        jLabel2.setText("Tên Sản Phẩm:");

        txtempname.setEditable(false);
        txtempname.setBackground(new java.awt.Color(204, 204, 204));
        txtempname.setFont(new java.awt.Font("Sitka Small", 3, 14)); // NOI18N
        txtempname.setForeground(new java.awt.Color(255, 51, 51));

        cbproduct.setEditable(true);

        jTable.setBackground(new java.awt.Color(0, 255, 0));
        jTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable.setFocusable(false);
        jTable.setSelectionBackground(new java.awt.Color(255, 0, 0));
        jScrollPane1.setViewportView(jTable);

        jLabel4.setFont(new java.awt.Font("Sitka Heading", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 255));
        jLabel4.setText("Số Lượng");

        jLabel5.setFont(new java.awt.Font("Sitka Heading", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 255));
        jLabel5.setText("Kích Thước:");

        spinnerquantity.setModel(new javax.swing.SpinnerNumberModel(1, 1, 100, 1));

        cbSize.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nhỏ", "Vừa", "Lớn" }));

        btnDel.setFont(new java.awt.Font("Sitka Subheading", 1, 24)); // NOI18N
        btnDel.setForeground(new java.awt.Color(255, 51, 51));
        btnDel.setText("Xóa");
        btnDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelActionPerformed(evt);
            }
        });

        btnPrintSave.setFont(new java.awt.Font("Sitka Subheading", 1, 24)); // NOI18N
        btnPrintSave.setForeground(new java.awt.Color(0, 204, 102));
        btnPrintSave.setText("Lưu và In");
        btnPrintSave.setEnabled(false);
        btnPrintSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintSaveActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        jLabel3.setText("Tổng cộng:");

        jLabel6.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        jLabel6.setText("Chiết khấu:");

        jLabel10.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 51, 51));
        jLabel10.setText("Mã Hóa Đơn:");

        jLabel7.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 51, 51));
        jLabel7.setText("Thành tiền:");

        jLabel8.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        jLabel8.setText("Tiền khách đưa:");

        jLabel11.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        jLabel11.setText("Tiền trả lại:");

        jLabel9.setFont(new java.awt.Font("Copperplate Gothic Light", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(102, 51, 0));
        jLabel9.setText("Thanh toán");

        txtTotal.setEditable(false);
        txtTotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtTotal.setForeground(new java.awt.Color(51, 51, 255));
        txtTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotal.setText("0");

        txtdiscount1.setEditable(false);
        txtdiscount1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtdiscount1.setForeground(new java.awt.Color(51, 51, 255));
        txtdiscount1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        txtpay.setEditable(false);
        txtpay.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtpay.setForeground(new java.awt.Color(255, 0, 0));
        txtpay.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtpay.setText("0");

        txtguest.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtguest.setForeground(new java.awt.Color(51, 51, 255));
        txtguest.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtguest.setText("0");
        txtguest.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtguestCaretUpdate(evt);
            }
        });

        txtRepay.setEditable(false);
        txtRepay.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtRepay.setForeground(new java.awt.Color(51, 51, 255));
        txtRepay.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtRepay.setText("0");

        txtIDHĐ.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtIDHĐ.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 102, 51));
        jLabel12.setText("%");

        txtdiscount2.setEditable(false);
        txtdiscount2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtdiscount2.setForeground(new java.awt.Color(51, 51, 255));
        txtdiscount2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtdiscount2.setText("0");

        lbguest.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbguest.setForeground(new java.awt.Color(255, 51, 51));
        lbguest.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(160, 160, 160)
                        .addComponent(jLabel9))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel11)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtguest)
                            .addComponent(txtRepay)
                            .addComponent(txtIDHĐ)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtdiscount1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtdiscount2, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtTotal)
                            .addComponent(txtpay))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnPrintSave, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(101, 101, 101))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(164, 164, 164)
                .addComponent(lbguest, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel9)
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtdiscount1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(txtdiscount2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(txtpay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(txtguest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbguest)
                .addGap(4, 4, 4)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtRepay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtIDHĐ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnPrintSave, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        btnThem.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        jMenuBar1.setToolTipText("");

        jMenu1.setText("Tài Khoản");

        jMenuItem1.setText("Thông tin  tài khoản");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Đổi mật khẩu");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setText("Đăng xuất");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Giới Thiệu");
        jMenu2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu2MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu2);

        jMenu3.setText("Chi tiết hóa đơn");
        jMenu3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu3MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtempname, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cbproduct, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(spinnerquantity, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbSize, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 916, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labeltime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnDel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtempname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4)
                                    .addComponent(spinnerquantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1))
                                .addGap(27, 27, 27)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cbSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5)
                                    .addComponent(cbproduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))
                                .addGap(8, 8, 8))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(97, 97, 97)
                        .addComponent(btnDel, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labeltime)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:

        int line = jTable.getRowCount();
        for (int i = 0; i < line; i++) {
            if (jTable.getValueAt(i, 1).equals(cbproduct.getSelectedItem()) && jTable.getValueAt(i, 3).equals(cbSize.getSelectedItem())) {
                int oldquan = (int) jTable.getValueAt(i, 5);
                int newquan = (int) spinnerquantity.getValue();
                int totalquan = oldquan + newquan;
                spinnerquantity.setValue(totalquan);
                dtm.removeRow(i);
                break;
            }
        }
        LoadTable();
        //Nếu khách mua từ 3 loại sản phẩm khác nhau trở lên thì giảm giá 5%
        if (line > 1) {
            txtdiscount1.setText("5");
        } else {
            txtdiscount1.setText("0");
        }
        IntoMoney();
        UpdateDiscountandPay();
        cbproduct.setSelectedIndex(-1);
        cbSize.setSelectedIndex(0);
        spinnerquantity.setValue(1);
        if (jTable.getRowCount() > 0) {
            setText(true);
        } else {
            setText(false);
        }
        String Total = txtpay.getText().replaceAll(",", "");
        int Repay = Integer.parseInt(txtguest.getText()) - Integer.parseInt(Total);
        txtRepay.setText(n.format(Repay));
        if (Repay < 0) {
            lbguest.setText("Khách chưa đưa đủ tiền.");
            btnPrintSave.setEnabled(false);
            txtIDHĐ.setEnabled(false);
        } else if (Integer.parseInt(txtguest.getText()) == 0) {
            btnPrintSave.setEnabled(false);
            txtIDHĐ.setEnabled(false);
        } else {
            btnPrintSave.setEnabled(true);
            txtIDHĐ.setEnabled(true);
        }

    }//GEN-LAST:event_btnThemActionPerformed

    private void btnDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelActionPerformed
        // TODO add your handling code here:

        dtm.removeRow(jTable.getSelectedRow());
        int line = jTable.getRowCount();
        if (line > 3) {
            txtdiscount1.setText("5");
        } else {
            txtdiscount1.setText("0");
        }
        IntoMoney();
        UpdateDiscountandPay();
        String Total = txtpay.getText().replaceAll(",", "");
        int Repay = Integer.parseInt(txtguest.getText()) - Integer.parseInt(Total);
        txtRepay.setText(n.format(Repay));
        if (jTable.getRowCount() > 0) {
            setText(true);
        } else {
            setText(false);
            txtguest.setText("0");
            txtRepay.setText("0");
            txtIDHĐ.setText("");
        }
        JOptionPane.showMessageDialog(rootPane, "Xóa thành công");

    }//GEN-LAST:event_btnDelActionPerformed

    private void txtguestCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtguestCaretUpdate
        // TODO add your handling code here:
        while (true) {
            if ((txtguest.getText().trim().equals(""))) {
                lbguest.setText("Khách hàng chưa đưa tiền.");
                return;
            } else if (!txtguest.getText().trim().matches("\\d+")) {
                lbguest.setText("Tiền có dạng số.");
                return;
            } else {
                lbguest.setText("");
                break;
            }
        }
        String Total = txtpay.getText().replaceAll(",", "");
        int Repay = Integer.parseInt(txtguest.getText()) - Integer.parseInt(Total);
        txtRepay.setText(n.format(Repay));
        if (Repay < 0) {
            lbguest.setText("Khách chưa đưa đủ tiền.");
            btnPrintSave.setEnabled(false);
            txtIDHĐ.setEnabled(false);
        } else if (Integer.parseInt(txtguest.getText()) == 0) {
            btnPrintSave.setEnabled(false);
            txtIDHĐ.setEnabled(false);

        } else {
            btnPrintSave.setEnabled(true);
            txtIDHĐ.setEnabled(true);
        }
    }//GEN-LAST:event_txtguestCaretUpdate

    private void btnPrintSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintSaveActionPerformed
        // TODO add your handling code here:
        while (true) {
            if (txtIDHĐ.getText().trim().equals("")) {
                JOptionPane.showMessageDialog(rootPane, "Mã hóa đơn không được trống.");
                txtIDHĐ.requestFocusInWindow();
                return;
            } else if (!txtIDHĐ.getText().trim().matches("HD\\d{4}")) {
                JOptionPane.showMessageDialog(rootPane, "Mã hóa đơn có dạng HDxxxx, trong đó xxxx là số nguyên.");
                txtIDHĐ.requestFocusInWindow();
                return;
            } else {
                break;
            }
        }
        try {
            Mongo mongo = new Mongo();
            DB db = mongo.getDB("QlyCofee");
            DBCollection or = db.getCollection("[Order]");
            DBCollection em = db.getCollection("Employee");

            DBCursor cursor = or.find(new BasicDBObject().append("IDHĐ", txtIDHĐ.getText().trim()));

            if (!cursor.hasNext()) {
                DBCursor cursor2 = em.find(new BasicDBObject().append("NameEmp", txtempname.getText()));

                if (cursor2.hasNext()) {
                    PrintandSave((String) cursor2.next().get("username"));

                } else {
                    JOptionPane.showMessageDialog(rootPane, "Tên nhân viên không tồn tại.");
                }

            } else {
                JOptionPane.showMessageDialog(rootPane, "Mã hóa đơn đã tồn tại, vui lòng chọn mã mới.");
                txtIDHĐ.requestFocusInWindow();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Không thể kết nối đến máy chủ.");
        }
    }//GEN-LAST:event_btnPrintSaveActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        try {
            // TODO add your handling code here:
            new Information(txtempname.getText()).setVisible(true);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Bill_Form.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        new ChangePass(txtempname.getText()).setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        int click = JOptionPane.showConfirmDialog(rootPane, "Đăng xuất ngay bây giờ?");
        if (click == 0) {
            this.setVisible(false);
            try {
                new Login_Form().setVisible(true);
            } catch (IOException ex) {
                Logger.getLogger(Bill_Form.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenu2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu2MouseClicked
        // TODO add your handling code here:
        new About().setVisible(true);
    }//GEN-LAST:event_jMenu2MouseClicked

    private void jMenu3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu3MouseClicked
        try {
            // TODO add your handling code here:
            new History().setVisible(true);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Bill_Form.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenu3MouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDel;
    private javax.swing.JButton btnPrintSave;
    private javax.swing.JButton btnThem;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbSize;
    private javax.swing.JComboBox<String> cbproduct;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable;
    private javax.swing.JLabel labeltime;
    private javax.swing.JLabel lbguest;
    private javax.swing.JSpinner spinnerquantity;
    private javax.swing.JTextField txtIDHĐ;
    private javax.swing.JTextField txtRepay;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txtdiscount1;
    private javax.swing.JTextField txtdiscount2;
    private javax.swing.JTextField txtempname;
    private javax.swing.JTextField txtguest;
    private javax.swing.JTextField txtpay;
    // End of variables declaration//GEN-END:variables
}
