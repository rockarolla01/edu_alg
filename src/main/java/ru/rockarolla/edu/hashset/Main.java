package ru.rockarolla.edu.hashset;

/**
 * Created by davydov on 05-Sep-16.
 */
public class Main {
    public static void main(String[] args) {
        Set<String> set = new SetImpl<>();
        set.add("aaa");
        set.add("bbb");
        System.out.println(set.add("aaA"));

        System.out.println("1: " + set);
        System.out.println(set.add("aaA"));
        System.out.println("2: " + set);

        System.out.println("cont1: "+set.contains("aaa"));
        System.out.println("cont2: "+set.contains("a"));
        set.remove("aaa");
        System.out.println("3: " + set);
        set.remove("aaa");

        System.out.println("4: "+set);
    }
}
