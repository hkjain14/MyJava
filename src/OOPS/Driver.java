package OOPS;

import java.util.ArrayList;
import java.util.List;

public class Driver {
    public static void main(String[] args) {
//        Parent p = new Parent();
//        System.out.println("=========");
//
//        Parent p2 = new Child();
//        System.out.println("=========");
//
//        Child c = new Child();
//        System.out.println("=========");
//
//        Singleton s = Singleton.getInstance();
//        Singleton s2 = Singleton.getInstance();
//        System.out.println(s.equals(s2));
//        System.out.println(s.hashCode() == s2.hashCode());

        Parent p1 = new Parent();
        Parent p2 = new Parent();
        List<Parent> pList = new ArrayList<>();
        pList.add(p1);
        pList.add(p2);



        List<Child> childList = new ArrayList<>();


        ParentInterface p = new Child();
        Child c = new Child();
        System.out.println(p.getClass().getTypeName());
    }
}
