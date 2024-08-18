package unsw.friends;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class NetworkIterator<P extends Comparable<P>> implements Iterator<P> {

    private Iterator<Person<P>> iter;
    private List<Person<P>> sortedMembers;
    private Comparator<Person<P>> comparator;
    private SocialNetwork<P> network;

    public NetworkIterator(SocialNetwork<P> network, Comparator<Person<P>> comparator) {
        this.comparator = comparator;
        this.network = network;
        updateCache();
    }

    public void updateCache() {
        sortedMembers = new ArrayList<>(network.getPeople());
        sortedMembers.sort(comparator);
        this.iter = sortedMembers.iterator();
    }

    @Override
    public boolean hasNext() {
        return iter.hasNext();
    }

    @Override
    public P next() {
        updateCache();
        return iter.next().getId();
    }

    public void notifyNetworkChanges() {
        updateCache();
    }

}
