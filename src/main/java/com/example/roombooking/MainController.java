package com.example.roombooking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class MainController {

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/signup")
    public ResponseEntity<?> addCustomer(@RequestBody SignupRequest signupRequest) {

        String email = signupRequest.getEmail();

        if (customerRepository.existsByEmail(email)) {
            return ResponseEntity.badRequest().body(new ReturnFormat("Forbidden, Account already exists "));
        }

        Customer appUser = new Customer();
        appUser.setName(signupRequest.getName());
        appUser.setEmail(signupRequest.getEmail());
        appUser.setPassword(signupRequest.getPassword());
        customerRepository.save(appUser);
        return ResponseEntity.ok("Account Creation Successful");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Optional<Customer> optionalCustomer = customerRepository.findByEmail(email);

        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            if (customer.getPassword().equals(password)) {
                return ResponseEntity.ok("Login Successful");
            } else {
                return ResponseEntity.badRequest().body(new ReturnFormat("Username/Password Incorrect"));
            }
        } else {
            return ResponseEntity.badRequest().body(new ReturnFormat("User does not exist"));
        }
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserDetails(@RequestParam("userID") Integer userId) {
        Optional<Customer> optionalCustomer = customerRepository.findById(userId);

        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            CustomerDetails customerDetails = new CustomerDetails(customer.getId(), customer.getName(), customer.getEmail());
            return ResponseEntity.ok(customerDetails);
        } else {
            return ResponseEntity.badRequest().body(new ReturnFormat("User does not exist"));
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<CustomerDetails>> getAllUsers() {
        List<Customer> customers = (List<Customer>) customerRepository.findAll();
        if (customers.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            List<CustomerDetails> userDetailsList = customers.stream()
                    .map(customer -> new CustomerDetails(customer.getId(), customer.getEmail(), customer.getName()))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(userDetailsList);
        }
    }

    @Autowired
    private RoomRepository roomRepository;

    @PostMapping("/rooms")
    public ResponseEntity<?> addRoom(@RequestBody AddRoom addroom) {

        String roomName = addroom.getRoomName();
        int roomCapacity = addroom.getRoomCapacity();

        if (roomRepository.existsByRoomName(roomName)) {
            return ResponseEntity.ok(new ReturnFormat("Room already exists"));
        }

        if (roomCapacity < 1) {
            return ResponseEntity.ok(new ReturnFormat("Invalid capacity"));
        }

        Room room = new Room();
        room.setCapacity(roomCapacity);
        room.setRoomName(roomName);
        roomRepository.save(room);
        return ResponseEntity.ok("Room created successfully");

    }

    @GetMapping("/rooms")
    public ResponseEntity<List<RoomDetails>> getAllRooms(@RequestParam(value = "capacity", required = false) Integer capacity) {
        List<Room> rooms;

        if (capacity != null) {
            rooms = roomRepository.findByCapacityGreaterThanEqual(capacity);
        } else {
            rooms = (List<Room>) roomRepository.findAll();
        }

        List<RoomDetails> roomDetailsList = rooms.stream()
                .map(room -> new RoomDetails(room.getRoomId(), room.getRoomName(), room.getCapacity(), room.getBookings()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(roomDetailsList);
    }


    @Autowired
    private BookedRepository bookedRepository;

//    @Autowired
//    private UserDTORepository userDTORepository;


    @PostMapping("/book")
    public ResponseEntity<?> bookRoom(@RequestBody Booking booking) {
        Customer user = customerRepository.findById(booking.getUserID()).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ReturnFormat("User does not exist"));
        }

        Room room = roomRepository.findByRoomId(booking.getRoomID());

        if (room != null) {
            List<Booked> existingBookings = room.getBookings();
            boolean isConflict = false;

            for (Booked existingBooking : existingBookings) {
                LocalDate existingBookingDate = existingBooking.getDateOfBooking();
                LocalTime existingBookingTimeFrom = LocalTime.parse(existingBooking.getTimeFrom());
                LocalTime existingBookingTimeTo = LocalTime.parse(existingBooking.getTimeTo());

                LocalDate newBookingDate = booking.getDateOfBooking();
                LocalTime newBookingTimeFrom = LocalTime.parse(booking.getTimeFrom());
                LocalTime newBookingTimeTo = LocalTime.parse(booking.getTimeTo());

                if (existingBookingDate.isEqual(newBookingDate) &&
                        ((newBookingTimeFrom.isBefore(existingBookingTimeFrom) && newBookingTimeTo.isAfter(existingBookingTimeFrom)) ||
                                (newBookingTimeFrom.isBefore(existingBookingTimeTo) && newBookingTimeTo.isAfter(existingBookingTimeTo)) ||
                                (newBookingTimeFrom.equals(existingBookingTimeFrom) || newBookingTimeTo.equals(existingBookingTimeTo)))) {
                    isConflict = true;
                    break;
                }
            }

            if (isConflict) {
                return ResponseEntity.ok(new ReturnFormat("Room unavailable"));
            } else {
                // Find the existing UserDTO by userId
//                UserDTO existingUserDTO = userDTORepository.findByUserId(booking.getUserID());

                // Create a new Booked instance
                Booked newBooking = new Booked();
                UserDisplay userDisplay = new UserDisplay();
                newBooking.setDateOfBooking(booking.getDateOfBooking());
                newBooking.setTimeFrom(booking.getTimeFrom());
                newBooking.setTimeTo(booking.getTimeTo());
                newBooking.setPurpose(booking.getPurpose());
                userDisplay.setUserId(booking.getUserID());
                newBooking.setUser(userDisplay);

                // Associate the existing UserDTO with the new Booked instance
//                newBooking.setUser(existingUserDTO); // Add the new Booked instance to the UserDTO

                // Associate the new Booked instance with the Room instance
                newBooking.setBookedRoom(room);

                // Save the new Booked instance
                newBooking = bookedRepository.save(newBooking);

                // Add the new booking to the room's bookings list
                room.getBookings().add(newBooking);

                roomRepository.save(room); // Save room with booking details

                return ResponseEntity.ok("Room booked successfully");
            }
        } else {
            return ResponseEntity.ok(new ReturnFormat("Room does not exist"));
        }
    }




    @PatchMapping("/rooms")
    public ResponseEntity<?> editRoom(@RequestBody RoomEditRequest roomEditRequest) {
        int roomId = roomEditRequest.getRoomId();
        String roomName = roomEditRequest.getRoomName();
        int roomCapacity = roomEditRequest.getRoomCapacity();

        // Check if room exists
        Room existingRoom = roomRepository.findById(roomId).orElse(null);
        if (existingRoom == null) {
            return ResponseEntity.ok(new ReturnFormat("Room does not exist"));
        }

        // Check if room name is already used
        Room roomWithSameName = roomRepository.findByRoomName(roomName);
        if (roomWithSameName != null && roomWithSameName.getRoomId() != roomId) {
            return ResponseEntity.ok(new ReturnFormat("Room with given name already exists"));
        }

        // Check if room capacity is valid
        if (roomCapacity <= 0) {
            return ResponseEntity.ok(new ReturnFormat("Invalid capacity"));
        }

        // Update room details
        existingRoom.setRoomName(roomName);
        existingRoom.setCapacity(roomCapacity);
        roomRepository.save(existingRoom);

        return ResponseEntity.ok("Room edited successfully");
    }

    @DeleteMapping("rooms/{roomId}")
    public ResponseEntity<String> deleteRoom(@PathVariable int roomId) {
        if (roomRepository.existsById(roomId)) {
            roomRepository.deleteById(roomId);
            return ResponseEntity.ok("Room deleted successfully");
        } else {
            return ResponseEntity.badRequest().body("Room does not exist");
        }
    }


}
