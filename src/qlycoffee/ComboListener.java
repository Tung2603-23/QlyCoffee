package qlycoffee;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ComboListener extends KeyAdapter
{
    Vector vec;
    JComboBox cbproduct;
    public ComboListener(Vector vector,JComboBox cbproduct)
    {
        vec=vector;
       this.cbproduct=cbproduct;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        JTextField t1=(JTextField) e.getSource();
        String s=t1.getText();
        getList(s);
        DefaultComboBoxModel dcm=new DefaultComboBoxModel(getList(s));
        cbproduct.setModel(dcm);
        cbproduct.setSelectedIndex(-1);
        JTextField t2=(JTextField) cbproduct.getEditor().getEditorComponent();
        t2.setText(s);
        cbproduct.showPopup();     
    }
    public Vector getList(String text)
    {
        Vector v=new Vector();
        for(int i=0;i<vec.size();i++)
        {
            if(vec.get(i).toString().startsWith(text))
            {
                v.add(vec.get(i).toString());
            }
        }
        return v;
    }  
}