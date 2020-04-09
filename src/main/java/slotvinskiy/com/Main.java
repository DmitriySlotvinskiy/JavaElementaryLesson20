package slotvinskiy.com;

public class Main {

    public static final String FILE_NAME = "src/main/resources/save";

    public static void main(String[] args) {

        Storage storage = new FileStorage(FILE_NAME);
//        storage.removeAll();
        storage.addUser(new User("Linn", 23));
        storage.addUser(new User("Mike", 45));
        storage.addUser(new User("Don", 29));
        System.out.println(storage.getAllUsers());
        System.out.println(storage.getUser(2));
        storage.removeUserByName("Linn");
        System.out.println(storage.getAllUsers());
        System.out.println(storage);



    }
}
