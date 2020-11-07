package edu.postech.csed332.homework6;

import edu.postech.csed332.homework6.events.Event;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class CellUI extends JTextField implements Observer {

    /**
     * create a cell UI
     *
     * @param cell a cell model
     */
    CellUI(Cell cell) {
        cell.addObserver(this);
        initCellUI(cell);

        // CellUI를 초기화하면서 observer를 같이 붙여놓는 것.
        // 감지 결과에 따라 cell.setNumber, cell.unsetNumber 메소드 호출
        if (cell.getNumber().isEmpty()) {
            //TODO: whenever the content is changed, cell.setNumber() or cell.unsetNumber() is accordingly invoked.
            // You may use an action listener, a key listener, a document listener, etc.
            // 부정입력(홀짝 오류, 길이 오류, 이미 값 있음)에 대해서는 마지막에 고려하기 (여기 listener에다가 enable, disable코드 추가하면 될듯)
            this.getDocument().addDocumentListener(new DocumentListener() {
                // 안 쓰는 update
                public void changedUpdate(DocumentEvent e) {
                    System.out.println("Change Event");
                }
                // 지워짐을 감지 -> cell.unsetNumber 함수 호출
                public void removeUpdate(DocumentEvent e) {
                    // remove에서 발생할 수 있는 exception은 뭐가 있지?
                    try {
                        int length = e.getDocument().getLength();
                        System.out.println(e.getDocument().getText(0, length));
                        cell.unsetNumber();
                    } catch (BadLocationException badLocationException) {
                        badLocationException.printStackTrace();
                    }
                }

                // 타이핑을 감지 -> cell.setNumber 함수 호출
                public void insertUpdate(DocumentEvent e) {
                    // 부정 입력 감지. 1~9가 아닌 경우 throw
                    try {
                        int length = e.getDocument().getLength();
                        System.out.println(e.getDocument().getText(0, length));
                        cell.setNumber(Integer.parseInt(e.getDocument().getText(0, 1)));
                    } catch (BadLocationException badLocationException) {
                        badLocationException.printStackTrace();
                    }
                }
            });

/*            this.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent ke) {
                    Character inputValue = ke.getKeyChar();
                }
            });*/
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
     * Event의 종류에 따라
     * 1. Set, Unset number 이벤트
     *   - 따로 할 일 없음. cellUI가 update되어야하는 순간은 activate, deactivate될 때뿐.
     * 2. Enabled, Disabled 이벤트
     *   - arg 메시지에 따라서 caller.setActivate || caller.setDeactivate
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
