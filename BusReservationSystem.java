import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class BusReservationSystem implements ActionListener {
    int bno;
    int sno;
    private JFrame frame;
    private JButton reserveSeatButton, cancelSeatButton, displaySeatButton, exitButton, routesButton, printTicketButton,
            viewAllSeatsButton;
    private JLabel busNumberLabel, seatNumberLabel, passengerNameLabel;
    public JTextField busNumberField, seatNumberField, passengerNameField;
    private int[][] seats;
    private String[][] passengerNames;

    BusReservationSystem() {
        bno = 100;
        sno = 35;
        seats = new int[bno][sno];
        passengerNames = new String[bno][sno];
    }

    public static void main(String[] args) {
        BusReservationSystem gui = new BusReservationSystem();
        gui.loadData();
        gui.createAndShowGUI();
    }

    public void createAndShowGUI() {
        frame = new JFrame("Bus Reservation System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveData();
                frame.dispose();
            }
        });
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = createInputPanel();
        JPanel buttonPanel = createButtonPanel();

        frame.add(inputPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        busNumberLabel = createLabel("Enter Bus Number:");
        busNumberField = createTextField(10);
        seatNumberLabel = createLabel("Enter Seat Number:");
        seatNumberField = createTextField(10);
        passengerNameLabel = createLabel("Enter Passenger Name:");
        passengerNameField = createTextField(20);

        addToPanel(inputPanel, busNumberLabel, gbc, 0, 0);
        addToPanel(inputPanel, busNumberField, gbc, 1, 0);
        addToPanel(inputPanel, seatNumberLabel, gbc, 0, 1);
        addToPanel(inputPanel, seatNumberField, gbc, 1, 1);
        addToPanel(inputPanel, passengerNameLabel, gbc, 0, 2);
        addToPanel(inputPanel, passengerNameField, gbc, 1, 2);

        return inputPanel;
    }

    private void saveData() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data.ser"));
            oos.writeObject(seats);
            oos.writeObject(passengerNames);
            oos.close();
            JOptionPane.showMessageDialog(frame, "Data saved successfully");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error occurred while saving data");
            e.printStackTrace();
        }
    }

    public void loadData() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data.ser"));
            seats = (int[][]) ois.readObject();
            passengerNames = (String[][]) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(frame, "Error occurred while loading data");
            e.printStackTrace();
        }
    }

    public JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);

        reserveSeatButton = createButton("Reserve Seat");
        cancelSeatButton = createButton("Cancel Seat");
        displaySeatButton = createButton("Display Seat");
        exitButton = createButton("Exit");
        routesButton = createButton("View Routes");
        printTicketButton = createButton("Print Ticket");
        viewAllSeatsButton = createButton("View All Seats");

        reserveSeatButton.addActionListener(this);
        cancelSeatButton.addActionListener(this);
        displaySeatButton.addActionListener(this);
        exitButton.addActionListener(this);
        routesButton.addActionListener(this);
        printTicketButton.addActionListener(this);
        viewAllSeatsButton.addActionListener(this);

        buttonPanel.add(reserveSeatButton);
        buttonPanel.add(cancelSeatButton);
        buttonPanel.add(displaySeatButton);
        buttonPanel.add(exitButton);
        buttonPanel.add(routesButton);
        buttonPanel.add(printTicketButton);
        buttonPanel.add(viewAllSeatsButton);

        return buttonPanel;
    }

    private void addToPanel(JPanel panel, Component component, GridBagConstraints gbc, int x, int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        panel.add(component, gbc);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        return label;
    }

    private JTextField createTextField(int columns) {
        JTextField textField = new JTextField(columns);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        return textField;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == reserveSeatButton) {
            reserveSeat();
        } else if (e.getSource() == cancelSeatButton) {
            cancelSeat();
        } else if (e.getSource() == displaySeatButton) {
            displaySeat();
        } else if (e.getSource() == exitButton) {
            saveData();
            exit();
        } else if (e.getSource() == routesButton) {
            displayRoutes();
        } else if (e.getSource() == printTicketButton) {
            printTicket();
        } else if (e.getSource() == viewAllSeatsButton) {
            viewAllSeats();
        }
    }

    private void reserveSeat() {
        int busNumber = Integer.parseInt(busNumberField.getText());
        int seatNumber = Integer.parseInt(seatNumberField.getText());
        String passengerName = passengerNameField.getText();
        if (busNumber < 1 || busNumber > bno || seatNumber < 1 || seatNumber > sno) {
            JOptionPane.showMessageDialog(frame, "Invalid bus number or seat number");
        } else if (seats[busNumber - 1][seatNumber - 1] == 1) {
            JOptionPane.showMessageDialog(frame, "Seat already reserved");
        } else {
            seats[busNumber - 1][seatNumber - 1] = 1;
            passengerNames[busNumber - 1][seatNumber - 1] = passengerName;
            JOptionPane.showMessageDialog(frame, "Seat reserved for " + passengerName);
        }
    }

    private void cancelSeat() {
        int busNumber = Integer.parseInt(busNumberField.getText());
        int seatNumber = Integer.parseInt(seatNumberField.getText());
        String passengerName = passengerNameField.getText();
        if (busNumber < 1 || busNumber > bno || seatNumber < 1 || seatNumber > sno) {
            JOptionPane.showMessageDialog(frame, "Invalid bus number or seat number");
        } else if (seats[busNumber - 1][seatNumber - 1] == 0) {
            JOptionPane.showMessageDialog(frame, "Seat not reserved");
        } else if (!passengerName.equals(getPassengerName(busNumber, seatNumber))) {
            JOptionPane.showMessageDialog(frame, "Passenger name does not match reservation");
        } else {
            seats[busNumber - 1][seatNumber - 1] = 0;
            passengerNames[busNumber - 1][seatNumber - 1] = null;
            passengerNameField.setText("");
            JOptionPane.showMessageDialog(frame, "Reservation canceled for " + passengerName);
        }
    }

    private void displaySeat() {
        int busNumber = Integer.parseInt(busNumberField.getText());
        if (busNumber < 1 || busNumber > bno) {
            JOptionPane.showMessageDialog(frame, "Invalid bus number");
        } else {
            JPanel busPanel = new JPanel(new GridLayout(6, 5, 2, 2));
            busPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            busPanel.add(new JLabel("Bus " + busNumber, SwingConstants.CENTER));
            for (int i = 0; i < sno; i++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(30, 30));
                if (seats[busNumber - 1][i] == 1) {
                    button.setBackground(Color.RED);
                    String passengerName = getPassengerName(busNumber, i + 1);
                    button.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JOptionPane.showMessageDialog(frame, "Passenger name: " + passengerName);
                        }
                    });
                } else {
                    button.setBackground(Color.GREEN);
                }
                busPanel.add(button);
            }
            JOptionPane.showMessageDialog(frame, busPanel);
        }
    }

    private void exit() {
        System.exit(0);
    }

    private void displayRoutes() {
        String[] routes = {
                "<html><font color='Blue'>Bus1(Driver Name: Venkat Reddy, Mobile number: 6300800800): Vijayawada - Hyderabad(274.3KM's || Start time: 05:00PM 4h:35m  Travel Time || Bus Type: Tata Marcoplo AC Bus)</font></html>",
                "<html><font color='Olive'>Bus2(Driver Name: Sai Kumar, Mobile number: 6300900900): Vijayawada - Chennai(383KM's || Start time: 09:30AM 10h:35m  Travel Time || Bus Type: Ashok Leyland Lynx AC Bus)</font></html>",
                "<html><font color='Orange'>Bus3(Driver Name: Lakshman Goud, Mobile number: 6300700700): Vijayawada - Bangalore(640KM's || Start time:08:30AM 12h:02m  Travel Time || Bus Type: Mahindra Comfio AC Bus)</font></html>",
                "<html><font color='Red'>Bus4(Driver Name: Rajesh Naidu, Mobile number: 6300500500): Vijayawada - Kharagpur(1400Km's || Start time:01:30PM 29h:15m Travel Time || Bus Type: Eicher Skyline AC Bus)</font></html>",
                "<html><font color='Brown'>Bus5(Driver Name: Ravi Shankar, Mobile number: 6300400400): Vijayawada - Pune(754Km's || Start time:09:30PM 14h:30m Travel Time || Bus Type: Force Traveller Monobus 4020 AC BUS)</font></html>",
                "<html><font color='Green'>Bus6(Driver Name: Srinivas Yadav, Mobile number: 6300200200): Vijayawada - Kolkata(1258Km's || Start time:10:30AM 22h:45m Travel Time || Bus Type:BharatBenz 914 AC BUS)</font></html>",
                "<html><font color='Gray'>Bus7(Driver Name: Ramesh Babu, Mobile number: 6300100100): Vijayawada - Delhi(1392Km's || Start time:08:00AM 41h:00m Travel Time || Bus Type:Volvo B7R AC Bus)</font></html>",
                "<html><font color='Purple'>Bus8(Driver Name: Anjan Gupta, Mobile number: 6300300300): Vijayawada - Ahmedabad(1400Km's || Start time:01:30PM 29h:15m Travel Time || Bus Type:Eicher Skyline AC Bus)</font></html>",
                "<html><font color='Orange'>Bus9(Driver Name: Nandan Nalla, Mobile number: 6300600600): Vijayawada - Jaipur(1400Km's || Start time:01:30PM 29h:15m Travel Time || Bus Type:Eicher Skyline AC Bus)</font></html>",
                "<html><font color='Maroon'>Bus10(Driver Name: Prasad Kulkarni, Mobile number: 6300350350): Vijayawada - Surat(1400Km's || Start time:01:30PM 29h:15m Travel Time || Bus Type:Eicher Skyline AC Bus)</font></html>",
        };
        JOptionPane.showMessageDialog(frame, String.join("\n", routes));
    }

    private void printTicket() {
        int busNumber = Integer.parseInt(busNumberField.getText());
        int seatNumber = Integer.parseInt(seatNumberField.getText());
        // String passengerName = passengerNameField.getText();
        if (busNumber < 1 || busNumber > bno || seatNumber < 1 || seatNumber > sno) {
            JOptionPane.showMessageDialog(frame,
                    "<html><font color='red'>Invalid bus number or seat number</font></html>");
        } else if (seats[busNumber - 1][seatNumber - 1] == 0) {
            JOptionPane.showMessageDialog(frame, "<html><font color='red'>Seat not reserved</font></html>");
        } else {

            // Generate and print the ticket
            String ticket = "Bus Number: " + busNumber + "\nSeat Number: " + seatNumber
                    + "\nPassenger Name: " + passengerNames[busNumber - 1][seatNumber - 1];
            JTextArea textArea = new JTextArea(ticket);
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            JOptionPane.showMessageDialog(frame, scrollPane, "Print Ticket", JOptionPane.INFORMATION_MESSAGE);
            JOptionPane.showMessageDialog(frame,
                    "<html><font color='green'>Happy Journey! &#128522;&#128522;</font></html>");
        }
    }

    private void viewAllSeats() {
        // Display seat availability logic
        JPanel allBusesPanel = new JPanel();
        allBusesPanel.setLayout(new BoxLayout(allBusesPanel, BoxLayout.Y_AXIS));

        for (int busNumber = 1; busNumber <= bno; busNumber++) {
            JPanel busPanel = new JPanel(new GridLayout(6, 5, 5, 5));
            busPanel.setBorder(BorderFactory.createTitledBorder("Bus " + busNumber));

            for (int seatNumber = 1; seatNumber <= sno; seatNumber++) {
                JButton seatButton = new JButton(Integer.toString(seatNumber));
                seatButton.setPreferredSize(new Dimension(50, 50));

                int seatAvailability = seats[busNumber - 1][seatNumber - 1];

                if (seatAvailability == 0) {
                    seatButton.setEnabled(true);
                } else {
                    String passengerName = passengerNames[busNumber - 1][seatNumber - 1];
                    seatButton.setEnabled(false);
                    seatButton.setToolTipText(passengerName);
                }

                String s = "" + passengerNames[busNumber - 1][seatNumber - 1] + "";
                seatButton.setToolTipText(s);

                busPanel.add(seatButton);
            }

            allBusesPanel.add(busPanel);
        }

        JScrollPane scrollPane = new JScrollPane(allBusesPanel);
        scrollPane.setPreferredSize(new Dimension(400, 500));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Adjust the unit increment
        scrollPane.getVerticalScrollBar().setBlockIncrement(64); // Adjust the block increment

        JOptionPane.showMessageDialog(frame, scrollPane, "Seat Availability", JOptionPane.PLAIN_MESSAGE);
    }

    private String getPassengerName(int busNumber, int seatNumber) {
        for (int i = 0; i < sno; i++) {
            if (seats[busNumber - 1][i] == 1 && i + 1 == seatNumber) {
                return passengerNames[busNumber - 1][seatNumber - 1];
            }
        }
        return "";
    }
}
