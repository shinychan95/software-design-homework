package edu.postech.csed332.homework1;

import java.util.ArrayList;
import java.util.List;

public class Member {
    String name;
    List<Integer> accountList = new ArrayList<>();

    public Member(String name, Integer account){
        this.name = name;
        this.accountList.add(account);
    }

    public void addAccount(Integer account){
        this.accountList.add(account);
    }

    public List<Integer> getAccountList() {
        return accountList;
    }
}
