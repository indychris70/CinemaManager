package cinema;

public enum SeatCategory {
    VIP(10),
    GENERAL(8);

    public int price;

    SeatCategory(int price) {
        this.price = price;
    }
}
