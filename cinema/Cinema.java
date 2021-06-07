package cinema;

import java.util.Scanner;

public class Cinema {
    private enum Messages {
        PROMPT_NUM_ROWS("Enter the number of rows:"),
        PROMPT_NUM_SEATS_PER_ROW("Enter the number of seats in each row:"),
        PROMPT_ENTER_ROW_NUM("Enter a row number:"),
        PROMPT_ENTER_SEAT_NUM("Enter a seat number in that row:"),
        TICKET_PRICE("Ticket price: $%s"),
        OPTIONS("1. Show the seats\n" +
                "2. Buy a ticket\n" +
                "3. Statistics\n" +
                "0. Exit"),
        SEAT_TAKEN("That ticket has already been purchased!"),
        INVALID_INPUT("Wrong input!");

        private final String text;

        Messages(String text) {
            this.text = text;
        }

        public void print(String... strings) {
            System.out.println(String.format(text, (Object[]) strings));
        }
    }

    public static void main(String[] args) {
        run();
    }

    private static void run() {
        int option;
        int rowNumber;
        int seatNumber;
        final int EXIT_OPTION = 0;
        Scanner scanner = new Scanner(System.in);

        // Get input and create Seating Chart
        int numberRows = getInput(scanner, Messages.PROMPT_NUM_ROWS);
        int seatsPerRow = getInput(scanner, Messages.PROMPT_NUM_SEATS_PER_ROW);
        SeatingChart seatingChart = new SeatingChart(numberRows, seatsPerRow);

        // Application Loop
        do {
            option = getInput(scanner, Messages.OPTIONS);
            switch(option) {
                case 1:
                    seatingChart.printSeatingChart();
                    break;
                case 2:
                    do {
                        rowNumber = getInput(scanner, Messages.PROMPT_ENTER_ROW_NUM);
                        seatNumber = getInput(scanner, Messages.PROMPT_ENTER_SEAT_NUM);
                    } while (!inputIsValid(seatingChart, rowNumber, seatNumber));
                    makeReservation(seatingChart, rowNumber, seatNumber);
                    break;
                case 3:
                    seatingChart.printStatistics();
                    break;
            }
        } while(option != EXIT_OPTION);
    }

    private static int getInput(Scanner scanner, Messages message) {
        message.print();
        return scanner.nextInt();
    }

    private static boolean inputIsValid(SeatingChart seatingChart, int rowNumber, int seatNumber) {
        if (!seatLocationIsValid(seatingChart, rowNumber, seatNumber)) {
            Messages.INVALID_INPUT.print();
            return false;
        } else if (!seatingChart.seatIsAvailable(rowNumber, seatNumber)) {
            Messages.SEAT_TAKEN.print();
            return false;
        }
        return true;
    }

    private static boolean seatLocationIsValid(SeatingChart seatingChart, int rowNumber, int seatNumber) {
        boolean rowNumberIsValid = rowNumber >= 0 && rowNumber <= seatingChart.getNumberRows();
        boolean seatNumberIsValid = seatNumber >= 0 && seatNumber <= seatingChart.getSeatsPerRow();
        return rowNumberIsValid && seatNumberIsValid;
    }

    private static void makeReservation(SeatingChart seatingChart, int rowNumber, int seatNumber) {
        Messages.TICKET_PRICE.print(Integer.toString(seatingChart.getSeatPrice(rowNumber, seatNumber)));
        seatingChart.reserveSeat(rowNumber, seatNumber);
    }
}