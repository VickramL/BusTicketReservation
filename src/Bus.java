import java.util.ArrayList;

public class Bus {
    private int busNumber;
    private int busCapacity;
    private boolean busType;
    private String[] busRoute;
    private String busTiming;
    ArrayList<Seat> seats = new ArrayList<>();


    Bus(int busNumber,int busCapacity,boolean busType,String[] busRoute,String busTiming){
        this.busNumber = busNumber;
        this.busCapacity = busCapacity;
        this.busType = busType;
        this.busRoute = busRoute;
        this.busTiming = busTiming;
        for(int iterations = 0; iterations<busCapacity; iterations++){
            seats.add(new Seat(iterations+1, busRoute.length) );
        }
    }


    /*
     * class that stores the seat availability and it's number
     */
     class Seat{
        int seatNumber;
        int [] availability;
        Seat(int seatNumber, int totalStops){
            this.seatNumber = seatNumber;
            availability = new int[totalStops];
        }
        Seat(int pickup, int destination,String cancel){
            setAvailability(pickup,destination,cancel);
        }


        // getter for availability
        public int[] getAvailability(){
            return availability;
        }

        // getter for seatNumber
        public int getSeatNumber(){
            return seatNumber;
        }

        /*
         * input ==> {
         *  pickup point as int
         *  destination point as int
         *  request type as String (reserve, cancel)
         *
         */
        void setAvailability(int pickup, int destination, String requestType){
            System.out.println(pickup + " " + destination + " " + requestType + " " + seatNumber);
            if(requestType.equals("reserve")){
                for(int iteration=pickup; iteration<destination; iteration++){
                    availability[iteration] = 1;
                }
            }else{
                for(int iteration=pickup; iteration<destination; iteration++){
                    availability[iteration] = 0;
                }
            }
        }
    }

    //getter for the seats
    public ArrayList<Seat> getSeat(){
        return seats;
    }

    public int getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(int busNumber) {
        this.busNumber = busNumber;
    }

    public int getBusCapacity() {
        return busCapacity;
    }

    public void setBusCapacity(int busCapacity) {
        this.busCapacity = busCapacity;
    }

    public boolean isBusType() {
        return busType;
    }

    public void setBusType(boolean busType) {
        this.busType = busType;
    }

    public String[] getBusRoute() {
        return busRoute;
    }

    public void setBusRoute(String[] busRoute) {
        this.busRoute = busRoute;
    }

    public String getBusTiming() {
        return busTiming;
    }

    public void setBusTiming(String busTiming) {
        this.busTiming = busTiming;
    }
    public void displayBusInformation(){
        System.out.println("Bus Number : "+busNumber+", Seats : "+busCapacity+((busType)?" AC":" Non-AC")
                +" Timing : "+busTiming+" Route "+ busRoute);
    }
}
