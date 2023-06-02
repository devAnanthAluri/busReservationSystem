# busReservationSystem

This is a Java application that simulates a bus reservation system. It provides a graphical user interface (GUI) for users to reserve seats, cancel reservations, display seat availability, view routes, print tickets, and view all seats on a bus.

## Features

- Reserve Seat: Users can enter the bus number, seat number, and passenger name to reserve a seat. If the seat is available, it will be marked as reserved.
- Cancel Seat: Users can cancel a previously reserved seat by entering the bus number, seat number, and passenger name. The seat will be marked as available again.
- Display Seat: Users can view the seat arrangement of a specific bus. Reserved seats are shown in red, and available seats are shown in green.
- View Routes: Users can view the available bus routes and their details, such as start time, travel time, and bus type.
- Print Ticket: Users can print a ticket for a reserved seat by entering the bus number and seat number. The ticket includes details such as the passenger's name, bus number, and seat number.
- View All Seats: Users can view the seat arrangement of all buses in the system.

## How to Use

1. Compile and run the `LoginPage` class to start the application.
2. The login window will appear. Enter a username and password to log in (default username: "q", default password: "q").
3. After successful login, the main window of the bus reservation system will open.
4. Use the provided buttons and input fields to perform various actions such as reserving seats, canceling reservations, displaying seat availability, and more.
5. Close the application by clicking the "Exit" button or closing the window. The data will be automatically saved for future use.

## Installation and Usage
1. Clone the repository or download the source code.
2. Compile the Java source files using any Java IDE or command-line compiler.
3. Run the `BusReservationSystem` class to start the application.
4. Follow the on-screen instructions to use the system and navigate through the different functionalities.

## Data Persistence
The application uses serialization to save and load the data. The seat availability and passenger names are stored in the `data.ser` file. When the application starts, it loads the data from the file, and when the application is closed, it saves the updated data to the file.

## Dependencies
The Bus Reservation System does not have any external dependencies. It uses standard Java libraries and the Swing library for the graphical user interface.
