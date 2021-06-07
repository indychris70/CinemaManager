package cinema;

public class Seat {
    private int rowIndex;
    private int seatNumberIndex;
    private SeatStatus status;
    private SeatCategory category;

    public Seat(int rowIndex, int seatNumberIndex, SeatCategory category, SeatStatus status) {
        this.rowIndex = rowIndex;
        this.seatNumberIndex = seatNumberIndex;
        this.category = category;
        this.status = SeatStatus.S;
    }

    public SeatStatus getStatus() {
        return status;
    }

    public boolean isAvailable() {
        return status.equals(SeatStatus.S);
    }

    public int getPrice() {
        return category.price;
    }

    public void reserveSeat() {
        this.status = SeatStatus.B;
    }
}
