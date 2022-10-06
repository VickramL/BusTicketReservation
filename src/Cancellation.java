import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Cancellation {
    private int bookingId;
    private String pickUp;
    private String destination;
     int pickUpNumber;
     int destinationNumber;
    Cancellation(){
        Scanner input = new Scanner(System.in);
        System.out.print("Enter Your booking Id to Cancel:");
        this.bookingId = input.nextInt();
    }
    public boolean isIdAvailable(ArrayList<Booking> bookings,ArrayList<Bus> buses,String[] route){

        boolean flag = false;
        for(Booking booking:bookings){
            if(booking.getBookingId() == bookingId){
                pickUp =  booking.getPickUpPoint();
                destination = booking.getDestination();

                List<String> routeList = Arrays.asList(route);
                pickUpNumber = routeList.indexOf(pickUp);
                destinationNumber = routeList.indexOf(destination);
                int seatNo = bookingId%1000;
                int busNo = bookingId/1000;
                for(Bus bus:buses){
                    if(bus.getBusNumber() == busNo){
                        for(Bus.Seat seat: bus.getSeat()){
                            if(seat.getSeatNumber() == seatNo){
                                seat.setAvailability(pickUpNumber, destinationNumber, "cancel");
                                bookings.remove(booking);
                            }
                        }
                    }
                }
                flag = true;
                break;
            }
        }

        return flag;
    }
}
