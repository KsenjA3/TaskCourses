package org.example.lesson1_MyLinkedList;


public interface MyList <T> {

     void setFirst (T element);
     void setLast (T element);
     void set (Integer index, T element);

     T removeAtIndex (Integer index);
     Integer remove (T element);
     T removeFirst ();
     T removeLast ();

     T get (Integer index);
     T getFirst () throws NoSuchFieldException;
     T getLast () throws NoSuchFieldException;

     Integer indexOf(T element);
     Integer lastIndexOf(T element);
     Integer size();

}
