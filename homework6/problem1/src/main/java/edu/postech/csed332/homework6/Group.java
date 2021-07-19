package edu.postech.csed332.homework6;

import edu.postech.csed332.homework6.events.Event;
import edu.postech.csed332.homework6.events.SetNumberEvent;
import edu.postech.csed332.homework6.events.UnsetNumberEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Set;

/**
 * A group that observes a set of cells, and maintains the invariant: if one of the members has a particular value,
 * none of its other members can have the value as a possibility.
 */
public class Group implements Observer {
    //TODO: add private member variables for Board
    private ArrayList<Cell> group;

    /**
     * Creates an empty group.
     */
    Group() {
        //TODO: implement this
        this.group = new ArrayList<>();
    }

    /**
     * Adds a cell to this group. Use cell.addGroup to register this group.
     *
     * @param cell a cell to be added
     */
    void addCell(Cell cell) {
        //TODO: implement this
        group.add(cell);
    }

    /**
     * Returns true if a given cell is belong to this group
     *
     * @param cell a cell
     * @return true if this group contains cell
     */
    @NotNull
    Boolean contains(@NotNull Cell cell) {
        //TODO: implement this
        return group.contains(cell);
    }

    /**
     * Returns true if a given number is available in the group
     *
     * @param number a number
     * @return true if no cell in the group has a given number.
     */
    @NotNull
    public Boolean isAvailable(int number) {
        //TODO: implement this
        for (Cell c : group)
            if (c.getNumber().get() == number) return false;
        return true;
    }

    /**
     * 어떤 cell이 변경되면 group이 update를 해야한다. 왜?
     * 그 셀(caller)과 연결된 groups(row, col, square) 안의 cell.possibility가 변화하기 때문
     * 1. 어떤 cell에 숫자를 입력했을 때 (SetNumberEvent)
     *   - caller.groups를 순회하면서 그 안의 group.cell.possibility에서 숫자를 제거
     * 2. 어떤 cell에서 숫자를 지웠을 때 (UnsetNumberEvent)
     *   - caller.groups를 순회하면서 그 안의 group.cell.possibility에 숫자를 추가
     *
     * Whenever a cell is changed, this function is called. Two kinds of events, SetNumberEvent and UnsetNumberEvent,
     * should be handled here.
     *
     * @param caller the subject
     * @param arg    an argument
     */
    @Override
    public void update(Subject caller, Event arg) {
        //TODO: implement this
        if (arg instanceof SetNumberEvent) {
            for (Cell c : this.group) {
                c.removePossibility(((SetNumberEvent) arg).getNumber());
            }
        } else if (arg instanceof UnsetNumberEvent) {
            for (Cell c : this.group) {
                c.addPossibility(((UnsetNumberEvent) arg).getNumber());
            }
        }
    }
}
