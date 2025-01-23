package org.example.lesson_1;

import java.util.NoSuchElementException;

public class MyLinkedList <T>  implements MyList<T> {

    private Node<T> root;
    private int size;

    public MyLinkedList() {
        root = new Node<>();
        root.setValue(null);
        root.setNext(root);
        root.setPrevious(root);
        size = 0;
    }

    @Override
    public void setFirst(T element) {
        Node<T> node=new Node<>();
        node.setValue(element);
        Node<T> nextRoot=root.getNext();

        node.setPrevious(root);
        node.setNext(nextRoot);
        root.setNext(node);
        nextRoot.setPrevious(node);
        size++;
    }

    @Override
    public void setLast(T element) {
        Node<T> node=new Node<>();
        node.setValue(element);
        Node<T> beforeRoot=root.getPrevious();

        node.setPrevious(beforeRoot);
        node.setNext(root);
        beforeRoot.setNext(node);
        root.setPrevious(node);

        size++;
    }

    @Override
    public T removeFirst() {
        if (size==0) return null;

        Node<T> firstNode=root.getNext();
        Node<T> secondNode=firstNode.getNext();

        root.setNext(secondNode);
        secondNode.setPrevious(root);
        firstNode.setNext(null);
        firstNode.setPrevious(null);

        size--;
        return firstNode.getValue();
    }

    @Override
    public T removeLast() {
        if (size==0) return null;

        Node<T> lastNode=root.getPrevious();
        Node<T> beforeLastNode=lastNode.getPrevious();

        root.setPrevious(beforeLastNode);
        beforeLastNode.setNext(root);

        lastNode.setNext(null);
        lastNode.setPrevious(null);

        size--;
        return lastNode.getValue();
    }


    @Override
    public T getFirst() throws NoSuchFieldException {
        if (size!=0)
            return (T) root.getNext().getValue();
        else throw new NoSuchFieldException("List is empty.");
    }

    @Override
    public T getLast() throws NoSuchFieldException {
        if (size!=0)
            return (T) root.getPrevious().getValue();
        else throw new NoSuchFieldException("List is empty.");
    }

    @Override
    public T get(Integer index) {
        if (index>size) throw new IllegalStateException("Index out of broad list size.");

        Node<T> node=root;
        for (int i = 1; i <=index ; i++) {
            node=node.getNext();
        }
        return node.getValue();
    }

    @Override
    public void set(Integer index, T element) {
        if (index>size+1) throw new IllegalStateException("Index out of broad list size.");
        else if (index==size+1) this.setLast(element);
        else {
            Node<T> nodePriv=root;
            for (int i = 1; i <index ; i++) {
                nodePriv=nodePriv.getNext();
            }
            Node<T> nodeNext=nodePriv.getNext();

            Node<T> node=new Node<>();
            node.setValue(element);
            node.setNext(nodeNext);
            node.setPrevious(nodePriv);

            nodePriv.setNext(node);
            nodeNext.setPrevious(node);
            size++;
        }
    }


    @Override
    public T removeAtIndex (Integer index) {
        if (index>size) throw new NoSuchElementException("Index out of broad list size.");
        else {
            Node<T> nodePriv=root;
            for (int i = 1; i <index ; i++) {
                nodePriv=nodePriv.getNext();
            }
            Node<T> nodeDel=nodePriv.getNext();
            Node<T> nodeNext=nodeDel .getNext();

            nodeDel.setNext(null);
            nodeDel.setPrevious(null);
            nodePriv.setNext(nodeNext);
            nodeNext.setPrevious(nodePriv);
            size--;
            return nodeDel.getValue();
        }

    }

    @Override
    public Integer remove(T element) {
        int count=0;

        Node<T> node=root;
        for (int i = 1; i <=size ; i++) {
            node=node.getNext();
            T val=node.getValue();
            if (val.equals(element)){
                Node<T> nodePriv=node.getPrevious();
                Node<T> nodeNext=node.getNext();
                node.setNext(null);
                node.setPrevious(null);
                nodePriv.setNext(nodeNext);
                nodeNext.setPrevious(nodePriv);

                node=nodePriv;
                count++;
            }
        }
        size=size-count;
        return count;
    }


    @Override
    public Integer indexOf(T element) {
        Node<T> node=root;
        for (int i = 1; i <=size ; i++) {
            node=node.getNext();
            T val=node.getValue();
            if (val.equals(element)){
               return i;
            }
        }
        return  null;
    }

    @Override
    public Integer lastIndexOf(T element) {
        Node<T> node=root;
        for (int i = size; i >0 ; i--) {
            node=node.getPrevious();
            T val=node.getValue();
            if (val.equals(element)){
                return i;
            }
        }
        return  null;

    }


    @Override
    public Integer size() {
        return size;
    }

    @Override
    public String toString() {
        StringBuffer sb=new StringBuffer("MyLinkedList{");

        Node<T> node=root;

        for (int i = 1; i <=size; i++) {
            if (i!=1) sb.append(",");
            sb.append(i+"-[");
            Node<T> nextNode=node.getNext();
            sb.append(nextNode.getValue().toString());
            sb.append("]");
            node=nextNode;
        }
        sb.append("}");
        return  sb.toString()   ;
    }


}
