package planning.Interface;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

@SuppressWarnings("serial")
public class MyListCellRenderer extends JLabel implements ListCellRenderer {

    public MyListCellRenderer() {
        setOpaque(true);
    }

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        // Assumes the stuff in the list has a pretty toString
        setText(value.toString());
              
        if (isSelected) {
            setBackground(Color.gray);
            setForeground(Color.white);
        } else {        	  
        	if (index % 2 == 0){ 
        		setBackground(Color.lightGray);
        		setForeground(Color.black);
        	}
            else{ 
            	setBackground(Color.white);
            	setForeground(Color.black);
            }
        }
        return this;
    }
}
