package OOPS;

public class Driver {
    public static void main(String[] args) {
        Parent p = new Parent();
        System.out.println("=========");

        Parent p2 = new Child();
        System.out.println("=========");

        Child c = new Child();
        System.out.println("=========");

        Singleton s = Singleton.getInstance();
        Singleton s2 = Singleton.getInstance();
        System.out.println(s.equals(s2));
        System.out.println(s.hashCode() == s2.hashCode());
    }
}
