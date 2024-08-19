package unsw.airline;

import java.util.List;

public class Passenger {
  private String name;
  private List<Schedule> schedules; // 1 to many composition relationship
}
