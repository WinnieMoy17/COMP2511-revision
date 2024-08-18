package unsw.friends;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SocialNetwork<P extends Comparable<P>> {
  private List<Person<P>> people = new ArrayList<>();
  private List<NetworkIterator<P>> iterators = new ArrayList<>();

  public void addPersonToNetwork(P id) {
    people.add(new Person<>(id));
    notifyIterators();
  }

  public void follow(P person1, P person2) {
    Person<P> p1 = getPerson(person1);
    Person<P> p2 = getPerson(person2);

    if (p1 != null && p2 != null) {
      p1.followPerson(p2);
      notifyIterators();
    }
  }

  // maintain encapsulation
  public List<Person<P>> getPeople() {
    return people;
  }

  public Person<P> getPerson(P person) {
    return people.stream().filter(p -> p.getId().equals(person)).findFirst().get();
  }

  public NetworkIterator<P> getIterator(Comparator<Person<P>> comparator) {
    NetworkIterator<P> iterator = new NetworkIterator<>(this, comparator);
    iterators.add(iterator);
    return iterator;
  }

  public Comparator<Person<P>> getComparator(String orderBy) {
    Comparator<Person<P>> comparator = Comparator.comparing(p -> p.getId());
    if (orderBy.equals("popularity")) {
      comparator = Comparator.comparing(p -> -p.getPopularity(people));
    } else if (orderBy.equals("friends")) {
      comparator = Comparator.comparing(p -> -p.getNumFriends());
    }
    return comparator;
  }

  private void notifyIterators() {
    for (NetworkIterator<P> iterator : iterators) {
      iterator.notifyNetworkChanges();
    }
  }
}
