package edu.postech.csed332.homework6;

import edu.postech.csed332.homework6.events.DisabledEvent;
import edu.postech.csed332.homework6.events.EnabledEvent;
import edu.postech.csed332.homework6.events.SetNumberEvent;
import edu.postech.csed332.homework6.events.UnsetNumberEvent;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * A cell that has a number and a set of possibilities. Even cells can contain only even numbers, and odd cells can
 * contain only odd numbers. A cell may have a number of observers, and notifies events to the observers.
 */
public class Cell extends Subject {
    enum Type {EVEN, ODD}

    //TODO: add private member variables for Board
    private Type type;
    private Integer number;
    private ArrayList<Integer> possibility;
    private ArrayList<Group> groups;

    /**
     * Creates an empty cell with a given type. Initially, no number is assigned.
     *
     * @param type EVEN or ODD
     */
    public Cell(@NotNull Type type) {
        //TODO: implement this
        this.type = type;
        this.number = null;
        this.possibility = new ArrayList<>();
        this.groups = new ArrayList<>();

        if (type == Type.EVEN) {
            possibility.addAll(Arrays.asList(2, 4, 6, 8));
        } else {
            possibility.addAll(Arrays.asList(1, 3, 5, 7, 9));
        }
    }

    /**
     * Returns the type of this cell.
     *
     * @return the type
     */
    @NotNull
    public Type getType() {
        //TODO: implement this
        return type;
    }

    /**
     * Returns the number of this cell.
     *
     * @return the number; Optional.empty() if no number assigned
     */
    @NotNull
    public Optional<Integer> getNumber() {
        //TODO: implement this
        if (number != null) return Optional.of(number);
        return Optional.empty();
    }

    /**
     * Sets a number of this cell and notifies a SetNumberEvent, provided that the cell has previously no number
     * and the given number to be set is in the set of possibilities.
     *
     * 셀이 비어있어야 && possibility에 있는 숫자여야 넣을 수 있음. (이 2가지만 검사함)
     * removePossibility 함수 호출
     * 숫자를 넣고 SetNumberEvent로 observer(그룹, UI) 들을 update
     *
     * @param number the number
     */
    public void setNumber(int number) {
        //TODO: implement this
        // 1. cell.getNumber.isEmpty && number in cell.possibility인지 확인
        if (this.getNumber().isEmpty() && this.containsPossibility(number)) {
            // 2. set number
            this.number = number;
            // 3. notify observers
            this.notifyObservers(new SetNumberEvent(number));
        }
    }

    /**
     * Removes the number of this cell and notifies an UnsetNumberEvent, provided that the cell has a number.
     *
     * 셀에 숫자가 있어야 지울 수 있음 (이것만 검사함)
     * addPossibility 함수 호출
     * 숫자를 지우고, UnsetNumberEvent로 observer(그룹, UI)들을 update
     *
     */
    public void unsetNumber() {
        //TODO: implement this
        // 1. cell.getNumber이 not empty인지 확인
        if (this.getNumber().isPresent()) {
            // 2. unset
            int tempNum = this.number;
            this.number = null;
            // 3. notify observers
            this.notifyObservers(new UnsetNumberEvent(tempNum));
        }
    }

    /**
     * Adds a group for this cell. This methods should also call addObserver(group).
     *
     * @param group
     */
    public void addGroup(@NotNull Group group) {
        //TODO: implement this

        // cell의 observer 리스트에 group을 추가
        this.addObserver(group);

        // cell의 group 리스트에 group을 추가
        groups.add(group);
    }

    /**
     * Returns true if a given number is in the set of possibilities
     *
     * @param n a number
     * @return true if n is in the set of possibilities
     */
    @NotNull
    public Boolean containsPossibility(int n) {
        //TODO: implement this
        return possibility.contains(n);
    }

    /**
     * Returns true if the cell has no possibility
     *
     * @return true if the set of possibilities is empty
     */
    @NotNull
    public Boolean emptyPossibility() {
        //TODO: implement this
        return possibility.isEmpty();
    }

    /**
     * Adds the possibility of a given number, if possible, and notifies an EnabledEvent if the set of possibilities,
     * previously empty, becomes non-empty. Even (resp., odd) cells have only even (resp., odd) possibilities. Also,
     * if this number is already used by another cell in the same group with this cell, the number cannot be added to
     * the set of possibilities.
     *
     * 1. cell.possibility에 number를 넣는다 if (type 맞음 && not in group.cell.number)
     * 2. cell.possibility가 empty -> non-empty가 될 때에만 EnabledEvent notify
     *
     * @param number the number
     */
    public void addPossibility(int number) {
        //TODO: implement this
        Type numberType = number % 2 == 0 ? Type.EVEN : Type.ODD;
        if (this.getType() == numberType && !this.containsPossibility(number)) {
            this.possibility.add(number);
        }
        if (this.possibility.size() == 1) {
            this.notifyObservers(new EnabledEvent());
        }
    }

    /**
     * Removes the possibility of a given number. Notifies a DisabledEvent if the set of possibilities becomes empty.
     * Note that even (resp., odd) cells have only even (resp., odd) possibilities.
     *
     * 1. cell.possibility에서 number을 제거
     * 2. cell.possibility가 non-empty -> empty가 될 때에만 DisabledEvent notify
     *
     * @param number the number
     */
    public void removePossibility(int number) {
        //TODO: implement this
        if (this.containsPossibility(number)) {
            this.possibility.remove((Object) number);
            if (this.possibility.size() == 0) {
                this.notifyObservers(new DisabledEvent());
            }
        }
    }
}
