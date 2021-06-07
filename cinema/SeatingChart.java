package cinema;

public class SeatingChart {
    private final int numberRows;
    private final int seatsPerRow;
    private static Seat[][] seats;
    private static int totalIncome;
    private static final int VIP_ROOM_MAX_CAPACITY = 60;
    private static final int VIP_DIVISOR = 2;
    private final boolean[][] reservedSeats;

    public SeatingChart(int numberRows, int seatsPerRow) {
        this.numberRows = numberRows;
        this.seatsPerRow = seatsPerRow;
        reservedSeats = new boolean[numberRows][seatsPerRow];
        getSeats();
        calculateTotalIncome();
    }

    public int getNumberRows() {
        return numberRows;
    }

    public int getSeatsPerRow() {
        return seatsPerRow;
    }

    public static int getTotalIncome() {
        return totalIncome;
    }

    public int getSeatPrice(int row, int seatNumber) {
        return seats[row - 1][seatNumber - 1].getPrice();
    }

    public void printSeatingChart() {
        StringBuilder header = new StringBuilder(" ");
        for (int i = 0; i < seatsPerRow; i++) {
            header.append(String.format(" %d", i + 1));
        }
        System.out.println("Cinema:");
        System.out.println(header);
        for (int i = 0; i < seats.length; i++) {
            System.out.print(i + 1);
            for (int j = 0; j < seats[i].length; j++) {
                System.out.print(String.format(" %s", seats[i][j].getStatus()));
            }
            System.out.println();
        }
    }

    public void reserveSeat(int row, int seatNumber) {
        if (!seatIsReserved(row, seatNumber)) {
            reservedSeats[row - 1][seatNumber - 1] = true;
            seats[row - 1][seatNumber - 1].reserveSeat();
        }
    }

    public boolean seatIsAvailable(int row, int seatNumber) {
        return seats[row - 1][seatNumber - 1].isAvailable();
    }

    public void printStatistics() {
        double[] statistics = getStatistics();
        System.out.println(String.format("Number of purchased tickets: %d", (int) statistics[0]));
        System.out.println(String.format("Percentage: %.2f%%", statistics[0] / totalSeats() * 100));
        System.out.println(String.format("Current income: $%d", (int) statistics[1]));
        System.out.println(String.format("Total income: $%d", getTotalIncome()));
    }

    // Private methods

    private double[] getStatistics() {
        double countOfReservedSeats = 0.0;
        double currentIncome = 0.0;
        for (int i = 0; i < reservedSeats.length; i++) {
            for (int j = 0; j < reservedSeats[i].length; j++) {
                if (reservedSeats[i][j]) {
                    countOfReservedSeats++;
                    currentIncome += seats[i][j].getPrice();
                }
            }
        }
        return new double[] {countOfReservedSeats, currentIncome};
    }

    private boolean seatIsReserved(int row, int seatNumber) {
        return reservedSeats[row - 1][seatNumber - 1];
    }

    private void getSeats() {
        seats = new Seat[numberRows][seatsPerRow];
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                seats[i][j] = getSeat(i, j);
            }
        }
    }

    private Seat getSeat(int rowIndex, int seatNumberIndex) {
        SeatCategory category = isInVipSection(rowIndex) ? SeatCategory.VIP : SeatCategory.GENERAL;
        SeatStatus status = seatIsReserved(rowIndex + 1, seatNumberIndex + 1) ? SeatStatus.B : SeatStatus.S;
        Seat seat = new Seat(rowIndex, seatNumberIndex, category, status);
        return seat;
    }

    private boolean isVipRoom() {
        return totalSeats() <= VIP_ROOM_MAX_CAPACITY;
    }

    private boolean isInVipSection(int rowIndex) {
        return isVipRoom() || rowIndex < numberRows / VIP_DIVISOR;
    }

    private int totalSeats() {
        return numberRows * seatsPerRow;
    }

    private int totalVipSeats() {
        if (totalSeats() <= VIP_ROOM_MAX_CAPACITY) {
            return totalSeats();
        } else {
            return numberRows / VIP_DIVISOR * seatsPerRow;
        }
    }

    private int totalGeneralAdmissionSeats() {
        return totalSeats() - totalVipSeats();
    }

    private void calculateTotalIncome() {
        int vipIncome = totalVipSeats() * SeatCategory.VIP.price;
        int generalAdmissionIncome = totalGeneralAdmissionSeats() * SeatCategory.GENERAL.price;
        totalIncome = vipIncome + generalAdmissionIncome;
    }

}
