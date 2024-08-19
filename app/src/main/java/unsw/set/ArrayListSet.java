package unsw.set;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArrayListSet<E> implements Set<E> {

  private ArrayList<E> elements;

  public ArrayListSet() {
    elements = new ArrayList<>();
  }

  @Override
  public Iterator<E> iterator() {
    return elements.iterator();
  }

  @Override
  public void add(E e) {
    if (!contains(e)) {
      elements.add(e);
    }
  }

  @Override
  public void remove(E e) {
    elements.remove(e);
  }

  @Override
  public boolean contains(Object e) {
    return elements.contains(e);
  }

  @Override
  public int size() {
    return elements.size();
  }

  @Override
  public boolean subsetOf(Set<?> other) {
    for (E element : elements) {
      if (!other.contains(element)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public Set<E> union(Set<? extends E> other) {
    ArrayListSet<E> unionSet = new ArrayListSet<>();
    unionSet.elements.addAll(elements);
    for (E element : other) {
      if (!unionSet.contains(element)) {
        unionSet.add(element);
      }
    }
    return unionSet;
  }

  @Override
  public Set<E> intersection(Set<? extends E> other) {
    ArrayListSet<E> interSet = new ArrayListSet<>();
    for (E e : elements) {
      if (other.contains(e)) {
        interSet.add(e);
      }
    }
    return interSet;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (obj.getClass().equals(this))
      return false;

    ArrayListSet<?> otherSet = (ArrayListSet<?>) obj;
    if (size() != otherSet.size())
      return false;

    return subsetOf(otherSet) && otherSet.subsetOf(this);
  }

}
