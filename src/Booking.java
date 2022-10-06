
import java.util.*;

public class Booking {
    static Scanner input = new Scanner(System.in);
    private String passengerName;
    private int busNumber;
    private int seatNumber;

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    private String dateInput;
    private int bookingId;
    private String pickUpPoint;
    private String destination;

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public int getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(int busNumber) {
        this.busNumber = busNumber;
    }

    public void setPickUpPoint(String pickUpPoint) {
        this.pickUpPoint = pickUpPoint;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    private static int idGenerator;

    public static int getIdGenerator() {
        return idGenerator;
    }

    public static void setIdGenerator(int busNumber, int seatNumber) {
        Booking.idGenerator = busNumber * 1000 + seatNumber;

    }

    public static Scanner getInput() {
        return input;
    }

    public String getPickUpPoint() {
        return pickUpPoint;
    }

    public String getDestination() {
        return destination;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    Booking(String passengerName, int busNumber, String pickUpPoint, String destination, String dateInput,
            ArrayList<Bus> busses, int seatNumber)  {

        this.passengerName = passengerName;
        this.busNumber = busNumber;
        this.pickUpPoint = pickUpPoint;
        this.destination = destination;
        this.dateInput = dateInput;
        this.seatNumber = seatNumber;
        Bus bus = busses.get(busNumber-1);
        ArrayList<Bus.Seat> seats = bus.getSeat();


        seats.get(seatNumber-1).setAvailability(Arrays.asList(BookingProcess.route).indexOf(pickUpPoint),
                Arrays.asList(BookingProcess.route).indexOf(destination), "reserve") ;
    }


    public static ArrayList<AvailableBus> getAvailableBuses(ArrayList<Bus> busses, String pickup, String destination){
        ArrayList<AvailableBus> availableBus = new ArrayList<>();

        // iterating through the bus list for availability
        for(Bus bus: busses){
            // converting the String array into arraylist
            List<String> route = Arrays.asList(bus.getBusRoute());



            // getting the index of the pickup and destination points
            int pickUpNum = route.indexOf(pickup), destinationNum = route.indexOf(destination);

            AvailableBus availBus = new AvailableBus(bus.getBusNumber(), bus.getBusTiming());

            // iterating through the bus seats to check the availability
            for(Bus.Seat seat: bus.getSeat()){
                boolean avail = true;

                int [] locationAvail = seat.getAvailability();
                // iterating through the stopping and check for the availability
                for(int index=pickUpNum; index<destinationNum-1; index++){
                    if(locationAvail[index] == 1) {
                        avail = false;
                        break;
                    }
                }

                // if seats available add seat to the arraylist
                if(avail){
                    availBus.addSeat(seat.getSeatNumber());
                }
            }

            // if bus available adds bus to the arraylist
            if (availBus.getTotalSeatAvailable() > 0){
                availableBus.add(availBus);
            }
        }
        return availableBus;
    }

    // method to check the availability of the buses
    public static boolean isAvailable(ArrayList<Bus> busses, String pickUpPoint, String destination,
                                      int seat, int busNumber){
        ArrayList<AvailableBus> availableBuses = getAvailableBuses(busses, pickUpPoint, destination);

        if(availableBuses.size() > 0){
            for(AvailableBus bus : availableBuses){
                if(bus.getBusNumber() == busNumber ){
                    return bus.getAvailableSeats().contains(seat);
                }
            }
        }
        return false;
    }
}


// class for storing the available buses
class AvailableBus{
    int busNumber;
    String busTiming;
    int totalSeatAvailable=0;
    ArrayList<Integer> availableSeats = new ArrayList<>();
    AvailableBus(int busNumber, String busTiming){
        this.busNumber = busNumber;
        this.busTiming = busTiming;
    }

    public String getBusTiming() {
        return busTiming;
    }

    public void addSeat(int seatNumber){
        availableSeats.add(seatNumber);
        totalSeatAvailable++;
    }

    public int getBusNumber() {
        return busNumber;
    }

    public int getTotalSeatAvailable() {
        return totalSeatAvailable;
    }

    public ArrayList<Integer> getAvailableSeats() {
        return availableSeats;
    }
}