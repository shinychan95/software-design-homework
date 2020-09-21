package edu.postech.csed332.homework1;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class test {
    public static void main(String args[]){
        Map<String, String> test = new HashMap<>();

        for (String str : test.values()){
            System.out.println(str);
        }

        System.out.println(test.size());

        int[] a = new int[5];
        System.out.println(a[0]);

        Set<String> t = new HashSet<>();
        t.add("kim");
        t.add("kim");
        if (t.isEmpty()){
            System.out.println("empty");
        }
        for (String str : t){
            System.out.println(str);
        }
    }
}
