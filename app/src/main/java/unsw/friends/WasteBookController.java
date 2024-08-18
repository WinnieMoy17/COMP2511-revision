package unsw.friends;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class WasteBookController<P extends Comparable<P>> {

    private SocialNetwork<P> network = new SocialNetwork<>();

    /**
     * Adds a new member with the given name to the network.
     */
    public void addPersonToNetwork(P name) {
        network.addPersonToNetwork(name);
    }

    /**
     * @preconditions person1 and person2 already exist in the social media network.
     *                person1 follows person2 in the social media network.
     */
    public void follow(P person1, P person2) {
        network.follow(person1, person2);
    }

    public int getPopularity(P person) {
        Person<P> p = network.getPerson(person);
        return p.getPopularity(network.getPeople());
    }

    public int getFriends(P person) {
        Person<P> p = network.getPerson(person);
        return p.getNumFriends();
    }

    /**
     * Returns an iterator to the network (each member)
     * ordered by the given parameter.
     */
    public NetworkIterator<P> getIterator(String orderBy) {
        return network.getIterator(network.getComparator(orderBy));
    }

    public void switchIteratorComparisonMethod(NetworkIterator<P> iter, String orderBy) {
        // TODO Part d)
    }
}