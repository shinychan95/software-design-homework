package edu.postech.csed332.homework6;

import edu.postech.csed332.homework6.events.Event;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.awt.*;

public class CellUI extends JTextField implements Observer {

    /**
     * create a cell UI
     *
     * @param cell a cell model
     */
    CellUI(Cell cell) {
        cell.addObserver(this);
        initCellUI(cell);

        if (cell.getNumber().isEmpty()) {
            //TODO: whenever the content is changed, cell.setNumber() or cell.unsetNumber() is accordingly invoked.
            // You may use an action listener, a key listener, a document listener, etc.
            this.getDocument().addDocumentListener(new DocumentListener() {
                public void changedUpdate(DocumentEvent e) {
                    System.out.println("Change Event");
                }
                public void removeUpdate(DocumentEvent e) {
                    System.out.println("Remove Event");
                    try {
                        int length = e.getDocument().getLength();
                        System.out.println(e.getDocument().getText(0, length));
                    } catch (BadLocationException badLocationException) {
                        badLocationException.printStackTrace();
                    }
                }
                public void insertUpdate(DocumentEvent e) {
                    System.out.println("Insert Event");
                    try {
                        int length = e.getDocument().getLength();
                        System.out.println(e.getDocument().getText(0, length));
                    } catch (BadLocationException badLocationException) {
                        badLocationException.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     * Mark this cell UI as active
     */
    public void setActivate() {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setEditable(true);
    }

    /**
     * Mark this cell UI as inactive
     */
    public void setDeactivate() {
        setBorder(BorderFactory.createLineBorder(Color.RED));
        setEditable(false);
    }

    /**
     * Whenever a cell is changed, this function is called. EnabledEvent and DisabledEvent are handled here.
     * If the underlying cell is enabled, mark this cell UI as active. If the underlying cell is disabled, mark
     * this cell UI as inactive.
     *
     * @param caller the subject
     * @param arg    an argument passed to caller
     */
    @Override
    public void update(Subject caller, Event arg) {
        //TODO: implement this
        System.out.println("CellUI Update");
    }

    /**
     * Initialize this cell UI
     *
     * @param cell a cell model
     */
    private void initCellUI(Cell cell) {
        setFont(new Font("Times", Font.BOLD, 30));
        setHorizontalAlignment(JTextField.CENTER);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        if (cell.getType() == Cell.Type.EVEN)
            setBackground(Color.LIGHT_GRAY);

        if (cell.getNumber().isPresent()) {
            setForeground(Color.BLUE);
            setText(cell.getNumber().get().toString());
            setEditable(false);
        }
    }
}
