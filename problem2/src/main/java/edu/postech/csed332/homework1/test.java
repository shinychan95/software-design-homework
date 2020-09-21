package edu.postech.csed332.homework1;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class test {
    public static void main(String args[]){
        Map<String, String> test = new HashMap<>();

        for (String str : test.values()){
            System.out.println(str);
        }

        System.out.println(test.size());
    }
}
