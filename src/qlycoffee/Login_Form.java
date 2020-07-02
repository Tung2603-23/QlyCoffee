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
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author HUYNH
 */
public class Login_Form extends javax.swing.JFrame {

    /**
     * Creates new form Login_Form
     */
    public Login_Form() throws IOException {
        initComponents();
        jpassword.setEchoChar('\u25cf');
        this.setLocationRelativeTo(null);
        ImageIcon img = new ImageIcon("Image//Login.png");
        this.setIconImage(img.getImage());
        SetImage s = new SetImage();   //Chỉnh tỉ lệ image icon để set vào label        
        //set icon cho label
        s.setImageLabel(user, "Image//user.png");
        s.setImageLabel(password, "Image//pass.png");
        s.setImageLabel(role, "Image//role.png");

//        jPanel = new javax.swing.JPanel() {
//            ImageIcon icon = new ImageIcon("image//back_Login.png");
//
//            public void paintComponent(Graphics g) {
//                Dimension d = getSize();
//                g.drawImage(icon.getImage(), 0, 0, d.width, d.height, null);
//                setOpaque(false);
//                super.paintComponent(g);
//            }
//        };

    }

    public void OK() throws UnknownHostException //Phương thức đăng nhập
    {
        while (true) {
            if (txtuser.getText().trim().equals("")) //Nếu ô tài khoản rỗng
            {
                JOptionPane.showMessageDialog(rootPane, "Tên tài khoản không được trống. ");
                txtuser.requestFocusInWindow();
                return;
            } else if (jpassword.getText().trim().equals("")) //Nếu ô mật khẩu rỗng
            {
                JOptionPane.showMessageDialog(rootPane, "Mật khẩu không được trống. ");
                jpassword.requestFocusInWindow();
                return;
            } else if (cbrole.getSelectedIndex() == 0) //nếu chọn người đăng nhập là nhân viên
            {
                try {
                    Mongo mongo = new Mongo();
                    DB db = mongo.getDB("QlyCofee");
                    DBCollection employees = db.getCollection("Employee");
                    DBCursor cursor = employees.find(new BasicDBObject().append("username", txtuser.getText()).append("password", jpassword.getText()));
                    if (cursor.hasNext()) {
                        new Bill_Form((String) cursor.next().get("NameEmp")).setVisible(true);
//                          System.out.println((String) cursor.next().get("NameEmp"));
                        this.setVisible(false);
//                        String x=(String) cursor.next().get("NameEmp");
//                        JOptionPane.showMessageDialog(rootPane, (String) cursor.next().get("NameEmp"));
                        JOptionPane.showMessageDialog(rootPane, "Đăng nhập thành cmn công");

                        return;

                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Sai tên đăng nhập hoặc mật khẩu.");
                        txtuser.requestFocusInWindow();
                        return;

                    }
                } catch (HeadlessException e) {
                    JOptionPane.showMessageDialog(rootPane, "Không thể kết nối đến máy chủ.");
                    return;
                }
            } else if (cbrole.getSelectedIndex() == 1) { //nếu chọn admin
                try {
                    Mongo mongo = new Mongo();
                    DB db = mongo.getDB("QlyCofee");
                    DBCollection employees = db.getCollection("Administrator");
                    DBCursor cursor = employees.find(new BasicDBObject().append("Username", txtuser.getText()).append("Password", jpassword.getText()));
                    if (cursor.hasNext()) {
                        new AdminForm();
                        this.setVisible(false);
                        JOptionPane.showMessageDialog(rootPane, "Đăng nhập thành công.");
                        return;

                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Sai tên đăng nhập hoặc mật khẩu.");
                        txtuser.requestFocusInWindow();
                        return;
                    }
                } catch (HeadlessException e) {
                    JOptionPane.showMessageDialog(rootPane, "Không thể kết nối đến máy chủ.");
                    return;
                }
            }
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

        user = new javax.swing.JLabel();
        password = new javax.swing.JLabel();
        txtuser = new javax.swing.JTextField();
        role = new javax.swing.JLabel();
        cbrole = new javax.swing.JComboBox<>();
        version = new javax.swing.JLabel();
        btnLogin = new javax.swing.JButton();
        jpassword = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Đăng Nhập");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        user.setText("Tài Khoản:");

        password.setText("Mật Khẩu:");

        txtuser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtuserKeyPressed(evt);
            }
        });

        role.setText("Quyền truy cập:");

        cbrole.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nhân Viên", "Quản lý" }));
        cbrole.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbroleKeyPressed(evt);
            }
        });

        version.setText("Phiên Bản: 1.0");

        btnLogin.setText("Login");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        jpassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jpasswordKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(role)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                        .addComponent(cbrole, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(user)
                            .addComponent(password))
                        .addGap(54, 54, 54)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtuser, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                            .addComponent(jpassword))))
                .addGap(22, 22, 22))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(342, 342, 342)
                        .addComponent(version))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(324, 324, 324)
                        .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(342, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtuser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(user))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(password)
                    .addComponent(jpassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(role)
                    .addComponent(cbrole, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(58, 58, 58)
                .addComponent(btnLogin, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
                .addGap(116, 116, 116)
                .addComponent(version)
                .addGap(31, 31, 31))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        try {
            // TODO add your handling code here:
            OK();
        } //        catch (ex) {
        //            Logger.getLogger(Login_Form.class.getName()).log(Level.SEVERE, null, ex);
        //        }
        catch (UnknownHostException ex) {
            Logger.getLogger(Login_Form.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnLoginActionPerformed

    private void txtuserKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtuserKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            jpassword.requestFocusInWindow();
        }
    }//GEN-LAST:event_txtuserKeyPressed

    private void jpasswordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jpasswordKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnLogin.doClick();
        }
    }//GEN-LAST:event_jpasswordKeyPressed

    private void cbroleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbroleKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnLogin.doClick();
        }
    }//GEN-LAST:event_cbroleKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JComboBox<String> cbrole;
    private javax.swing.JPasswordField jpassword;
    private javax.swing.JLabel password;
    private javax.swing.JLabel role;
    private javax.swing.JTextField txtuser;
    private javax.swing.JLabel user;
    private javax.swing.JLabel version;
    // End of variables declaration//GEN-END:variables
}
