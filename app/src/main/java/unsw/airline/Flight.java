package unsw.airline;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Flight {
  private String name;
  private int firstSeats;
  private int businessSeats;
  private int economySeats;

  private LocalDateTime departure;
  private LocalDateTime arrival;

  private List<Passenger> passengers = new ArrayList<>();
}
