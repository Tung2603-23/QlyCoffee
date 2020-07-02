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
import java.awt.HeadlessException;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
//import server.ConnectionSQL;

/**
 *
 * @author HUYNH
 */
public class History extends javax.swing.JFrame {

    /**
     * Creates new form History
     */
    Vector vec;
    DefaultTableModel dtm;
    SimpleDateFormat s = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy");
    SimpleDateFormat s2 = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat n = new DecimalFormat("#,###");

    public History() throws UnknownHostException {
        initComponents();
        btnprint.setEnabled(false);
        this.setLocationRelativeTo(null);
        ImageIcon img = new ImageIcon("Image//history-icon-68319.png");
        this.setIconImage(img.getImage());
        dtm = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int Row, int Column) {
                return false;
            }
        };
        jTable1.setModel(dtm);
        dtm.setColumnIdentifiers(new Object[]{"Mã đơn hàng", "Mã sản phẩm", "Số lượng(Ly)", "Đơn giá(VNĐ)", "Thời gian", "Ngày", "TK Nhân viên", "Thành tiền(VNĐ)"});
        Mongo mongo = new Mongo();
        DB db = mongo.getDB("QlyCofee");
        DBCollection employees = db.getCollection("Employee");
        DBCursor cursor = employees.find(new BasicDBObject().append("", "").append("username", 1));
        List searchResults = employees.distinct("username");
        int u = searchResults.size();
        vec = new Vector();

        int i = 0;

        while (i < u) {
            vec.add(searchResults.get(i));
//                        System.out.println(searchResults.get(i));
            i++;
        }
//        while (cursor.hasNext()) {
//            System.out.println(cursor.next());
////    vec.add(cursor.next().get("username"));
//        }
        JTextField t = (JTextField) jComboBox1.getEditor().getEditorComponent();
        t.setText("");
        t.addKeyListener(new ComboListener(vec, jComboBox1));
        LoadTable();
        Total();
    }

    public void Total() {
        int total = 0;
        int line = jTable1.getRowCount();
        for (int i = 0; i < line; i++) {
            String thanhtien = (String) jTable1.getValueAt(i, 7);
            total += Integer.parseInt(thanhtien.replaceAll(",", ""));
        }
        jLabel4.setText(n.format(total) + "VNĐ");
    }

    public void LoadTable() throws UnknownHostException {
        dtm.getDataVector().removeAllElements();
        try {
            Mongo mongo = new Mongo();
            DB db = mongo.getDB("QlyCofee");
            DBCollection history = db.getCollection("History");
//            DBCursor cursor = history.find();

            long cc = history.count();
            for (int i = 0; i < cc; i++) {
//                               DBCursor cursor = history.find(new BasicDBObject().append("STT", cc));
                DBCursor cursor = history.find();

                DBObject ht = (DBObject) history.findOne(new BasicDBObject().append("STT", i + 1));
//                DBObject ht = (DBObject) history.findOne(new BasicDBObject().append("STT", 1));

                if (cursor.hasNext()) {
                    vec = new Vector();
                    vec.add(ht.get("IDHĐ"));
                    vec.add(ht.get("IDProduct"));
                    vec.add(ht.get("Quantity"));
                    vec.add(n.format(ht.get("Price")));
                    vec.add(ht.get("TimeHĐ"));
                    vec.add(ht.get("DateHĐ"));
                    vec.add(ht.get("username"));
                    int quantity = (int) ht.get("Quantity");
                    int price = (int) ht.get("Price");
                    int Total = quantity * price;
                    vec.add((n.format(Total)));
                    dtm.addRow(vec);
                }
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(rootPane, "Không thể kết nối đến máy chủ.");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnprint = new javax.swing.JButton();
        txtdate = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Lịch sử bán hàng");

        jLabel1.setText("TK Nhân Viên:");

        jLabel2.setText("Ngày lập đơn hàng:");

        jComboBox1.setEditable(true);

        jButton1.setText("Tìm kiếm");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Làm mới");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jLabel3.setText("Tổng số tiền:");

        jLabel4.setText("0 VNĐ");

        btnprint.setText("Print");
        btnprint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnprintActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtdate, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1)
                        .addGap(64, 64, 64)
                        .addComponent(jButton2)
                        .addGap(0, 2, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(348, 348, 348)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnprint)
                .addGap(104, 104, 104))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(txtdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(btnprint))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String name = (String) jComboBox1.getSelectedItem();
        String date = txtdate.getText();

        if (jComboBox1.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(rootPane, "Tài khoản nhân viên không được để trống.");
            btnprint.setEnabled(false);
        } else if (txtdate.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Chưa nhập ngày.");
        } else {
            try {
                btnprint.setEnabled(true);
                Mongo mongo = new Mongo();
                DB db = mongo.getDB("QlyCofee");
                DBCollection search = db.getCollection("History");
                DBCursor cursor = search.find(new BasicDBObject().append("DateHĐ", date).append("username", name));
//                DBCursor cursor2 = search.find(new BasicDBObject().append("DateHĐ", date).append("username", name));

                if (!cursor.hasNext()) {
                    JOptionPane.showMessageDialog(rootPane, "Nhân viên '" + name + "' chưa bán được sản phẩm nào trong ngày " + date + ".");
                    jButton2ActionPerformed(evt);
                } else {
                    btnprint.setEnabled(true);
                    dtm.getDataVector().removeAllElements();

//                    long cc = cursor.count();
//                    long ccc = cursor.count();
//                    System.out.println(ccc);
                    long u = (long) cursor.next().get("STT");
                    int o = cursor.count();
                    for (long i = u; i < u + o; i++) {

                        DBObject ht = (DBObject) search.findOne(new BasicDBObject().append("DateHĐ", date).append("username", name).append("STT", i));
//                               DBCursor cursor = history.find(new BasicDBObject().append("STT", cc));
//                        DBCursor cursor = history.find();
//                        DBObject ht = (DBObject) search.findOne(new BasicDBObject().append("STT", i + 1));
                        if (cursor.hasNext()) {
                            vec = new Vector();
                            vec.add(ht.get("IDHĐ"));
                            vec.add(ht.get("IDProduct"));
                            vec.add(ht.get("Quantity"));
                            vec.add(n.format(ht.get("Price")));
                            vec.add(ht.get("TimeHĐ"));
                            vec.add(ht.get("DateHĐ"));
                            vec.add(ht.get("username"));
                            int quantity = (int) ht.get("Quantity");
                            int price = (int) ht.get("Price");
                            int Total = quantity * price;
                            vec.add((n.format(Total)));
                            dtm.addRow(vec);
                        }
//                        u++;
                    }
                }
            } catch (HeadlessException | NumberFormatException e) {
                JOptionPane.showMessageDialog(rootPane, "Không thể kết nối đến máy chủ.");
            } catch (UnknownHostException ex) {
                Logger.getLogger(History.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Total();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        jComboBox1.setSelectedIndex(-1);
        try {
            LoadTable();
        } catch (UnknownHostException ex) {
            Logger.getLogger(History.class.getName()).log(Level.SEVERE, null, ex);
        }
        Total();
        btnprint.setEnabled(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnprintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnprintActionPerformed
        // TODO add your handling code here:
        //Viết vào file History.txt

        try {
            Date now = new Date();
            try (Writer b = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("History.txt"), "UTF8"))) {
                b.write("\t\t\t\tTHE HUYNH COFFEE\r\n\r\n");
                b.write("\t\tĐịa chỉ: Tổ 4, P.Cự Khối, Q.Long Biên, Hà Nội\r\n");
                b.write("\t\tSĐT: 0983923430\r\n");
                b.write("\t\tThời gian: " + s.format(now) + "\r\n\r\n");
                if (jComboBox1.getSelectedIndex() == -1) {
                    b.write("\t\t\tBẢNG THỐNG KÊ BÁN HÀNG NGÀY " + s2.format(now) + "\r\n\r\n");
                } else {
                    b.write("\t\t\tBẢNG THỐNG KÊ BÁN HÀNG NGÀY " + txtdate.getText().trim() + "\r\n\r\n");
                }
                try {
                    Mongo mongo = new Mongo();
                    DB db = mongo.getDB("QlyCofee");
                    DBCollection employee = db.getCollection("Employee");
                    DBCursor cursor = employee.find(new BasicDBObject().append("username", jComboBox1.getSelectedItem()));
                    if (cursor.hasNext()) {
                        b.write("\tTài khoản: " + jComboBox1.getSelectedItem() + "\r\n");
                        b.write("\tTên nhân viên: " + cursor.next().get("NameEmp") + "\r\n\r\n");
                    } else {
                        b.write("\tToàn bộ nhân viên " + "\r\n\r\n");
                    }
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(rootPane, "Không thế kết nối tới máy chủ.");
                }
                b.write("\t-------------------------------------------------------------------------------\r\n");
                b.write("\tMã HĐ\tMã SP\tSố lượng(ly)\tĐơn giá         Thời gian\tThành tiền(VNĐ)\r\n");
                b.write("\t-------------------------------------------------------------------------------\r\n");
                int line = jTable1.getRowCount();
                for (int i = 0; i < line; i++) {
                    String n1 = (String) jTable1.getValueAt(i, 0);
                    String n2 = (String) jTable1.getValueAt(i, 1);
                    int n3 = (int) jTable1.getValueAt(i, 2);
                    String n4 = (String) jTable1.getValueAt(i, 3);
                    String n5 = (String) jTable1.getValueAt(i, 4);
                    String n6 = (String) jTable1.getValueAt(i, 7);
                    b.write("\t" + n1 + "\t" + n2 + "\t" + n3 + "\t\t" + n4 + "          " + n5 + "\t" + n6 + "\r\n");
                }
                b.write("\t-------------------------------------------------------------------------------\r\n");
                b.write("\tTổng tiền: " + jLabel4.getText());
            }
        } catch (HeadlessException | IOException e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
        //Mở file txt
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec("notepad History.txt");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
        jButton2ActionPerformed(evt);
    }//GEN-LAST:event_btnprintActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnprint;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtdate;
    // End of variables declaration//GEN-END:variables
}
