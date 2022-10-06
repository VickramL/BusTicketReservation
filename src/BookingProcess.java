import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class BookingProcess {
    static String[] route = {"chennai","kanchipuram","vellore","dharmapuri","salem","erode","coimbatore"};
    private final static Scanner input = new Scanner(System.in);
    static ArrayList<Bus> busses = new ArrayList<>();
    static ArrayList<Booking> bookings = new ArrayList<>();
    public static void main(String[] args) {


        busses.add(new Bus(1,4,true,route,"09:00AM"));
        busses.add(new Bus(2,4,true,route,"01:00PM"));
        busses.add(new Bus(3,4,true,route,"05:00PM"));
        busses.add(new Bus(4,4,true,route,"09:00PM"));
        String passengerName;
        String pickup ="";
        String destination="";
        int busNumber, seat;
        int userInput =0;
        while(true){



                System.out.println("1. Booking.");
                System.out.println("2. Cancellation.");
                System.out.println("3. Available Seats.");
                System.out.println("4. Booking List.");
                System.out.println("5. Exit.\n\n");

                if(input.hasNextInt() && ( 5 >= (userInput = input.nextInt()))){
                }else{
                    System.out.println("invalid input");
                    continue;
                }

            List<String> routeList = Arrays.asList(route);
            if(userInput == 1 || userInput == 3) {
                System.out.print("Pickup Point : ");
                pickup = input.next();
                System.out.print("Destination : ");
                destination = input.next();


                if(!(routeList.contains(pickup) && routeList.contains(destination)) ||
                        (routeList.indexOf(pickup)>=routeList.indexOf(destination))
                                &&routeList.indexOf(pickup)>=routeList.indexOf(destination)){
                    System.out.println("Invalid pickup Point or Destination!..");
                    continue;
                }
            }

            switch (userInput){
                case 1: {

                    printAvailableSeats(pickup, destination);
                    System.out.print("\nEnter the bus number : ");
                    busNumber = input.nextInt();
                    System.out.print("\nEnter the seat number : ");
                    seat = input.nextInt();
                    if (Booking.isAvailable(busses, pickup, destination, seat, busNumber)) {
                        System.out.print("\nPassenger Name : ");
                        input.nextLine();
                        passengerName = input.nextLine();
                        int pickupIndex = routeList.indexOf(pickup);
                        int dropIndex = routeList.indexOf(destination);
                        int payment = (dropIndex-pickupIndex)*50;
                        System.out.println("\nEnter the Payment Rs: "+payment);
                        System.out.println("\n1. Pay\n2. Cancel\n");
                        if(input.nextInt() ==2){
                            System.err.println("\t\t**** Payment cancelled ****");
                            break;
                        }

                        Booking.setIdGenerator(busNumber, seat);
                        Booking booking = new Booking(passengerName,busNumber,pickup,destination,"04-10-2022",
                                busses, seat);
                        booking.setBookingId(Booking.getIdGenerator());
                        bookings.add(booking);
                        System.out.println("\t\t****** your Booking Is successful ******\nYour Booking Id is "+booking.getBookingId());
                    } else
                        System.err.println("Seat is NotAvailable... Try Another seat or Bus:");
                    break;
                } case 2: {
                    Cancellation cancellation = new Cancellation();
                    if (cancellation.isIdAvailable(bookings,busses,route)) {
                        System.out.println("Your Ticket is Cancelled...");
                        System.out.println("Your Payment Amount will be Refunded Through your Bank Account Shortly");
                    }
                    else
                        System.err.println("Sorry !, Invalid BookingId");
                    break;
                } case 3: {
                        printAvailableSeats(pickup, destination);
                        break;
                }
                case 4:{
                    bookingList(bookings);
                    break;
                }
                case 5:
                    System.out.println("Thank You!");
                    System.exit(0);
            }
        }

    }

    // method to print the seat availability
    public static void printAvailableSeats(String pickup, String destination){
        ArrayList<AvailableBus> busList = Booking.getAvailableBuses(busses, pickup, destination);
        System.out.println("Available Bus list : ");
        System.out.println("------------------------------------------------");
        for(AvailableBus availableBus: busList){
            System.out.print("Bus Number ==> " + availableBus.getBusNumber());
            System.out.print("  Total Available " + availableBus.getTotalSeatAvailable());
            System.out.print("  Time ==> " + availableBus.getBusTiming());
            System.out.println("\nAvailable seatNumbers ==> " + availableBus.getAvailableSeats());
            System.out.println();
        }
        System.out.println("\n\n");
    }

    public static void bookingList(ArrayList<Booking> bookings){
        System.out.printf("%-15s| %-15s| %-10s| %-12s| %-15s| %-10s|\n","Name","Booking Id","From","To","Seat Number",
                "Bus Number");
        System.out.println("_____________________________________________________________________________________");
        for(Booking booking:bookings){
//            System.out.println("Name :"+ booking.getPassengerName()+" Booking Id "+booking.getBookingId()+
//                    " From : "+booking.getPickUpPoint()+" To : "+booking.getDestination()+
//                    " Seat Number "+ booking.getSeatNumber());

            System.out.printf("%-15s| %-15d| %-10s| %-12s| %-15d| %-10d|\n" ,booking.getPassengerName(),booking.getBookingId(),
                    booking.getPickUpPoint(),booking.getDestination(), booking.getSeatNumber(),booking.getBusNumber());
        }
        System.out.println("_____________________________________________________________________________________");
    }

}
