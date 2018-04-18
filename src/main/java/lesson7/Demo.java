package lesson7;

public class Demo {

    public static void main(String[] args) {
        RoomDAO roomDAO = new RoomDAO();
        Hotel h1 = new Hotel();
        h1.setCity("Kiev");
        h1.setCountry("Ukraine");
        h1.setName("Hilton");
        h1.setStreet("none");
        Room r1 = new Room();
        r1.setNumberOfGuests(2);
        r1.setBreakfastIncluded(1);
        r1.setPetsAllowed(0);
        r1.setHotel(h1);

        roomDAO.save(r1);
        roomDAO.delete(47);
        System.out.println("DONE");
        System.exit(0);
    }
}
