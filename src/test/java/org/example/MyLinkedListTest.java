package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

class MyLinkedListTest {

    MyList<Integer> list;

    @BeforeEach
    void setUp() {
        list = new MyLinkedList<>();
        list.setLast(11);
        list.setLast(22);
        list.setLast(33);
        list.setLast(11);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void setFirst() {
        list.setFirst(44);
        assertEquals("MyLinkedList{1-[44],2-[11],3-[22],4-[33],5-[11]}", list.toString());
    }

    @Test
    void setLast() {
        list.setLast(44);
        assertEquals("MyLinkedList{1-[11],2-[22],3-[33],4-[11],5-[44]}", list.toString());
    }

    @Test
    void removeFirst() {
        list.removeFirst();
        assertEquals("MyLinkedList{1-[22],2-[33],3-[11]}", list.toString());
    }

    @Test
    void removeLast() {
        list.removeLast();
        assertEquals("MyLinkedList{1-[11],2-[22],3-[33]}", list.toString());
    }

    @Test
    void removeFirst_empty() {
        list.removeFirst();
        list.removeFirst();
        list.removeFirst();
        list.removeFirst();
        assertEquals(null, list.removeFirst());
    }

    @Test
    void removeLast_empty() {
        list.removeLast();
        list.removeLast();
        list.removeLast();
        list.removeLast();
        assertEquals(null, list.removeLast());
    }

    @Test
    void getFirst() throws NoSuchFieldException {
        assertEquals(11, list.getFirst());
    }

    @Test
    void getLast() throws NoSuchFieldException {
        assertEquals(11, list.getLast());
    }

    @Test
    void getFirst_empty() throws NoSuchFieldException {
        MyLinkedList<Integer> empty = new MyLinkedList<>();
        Throwable ex = assertThrows(NoSuchFieldException.class, () -> empty.getFirst());
        assertEquals("List is empty.", ex.getMessage());
    }

    @Test
    void getLast_empty() throws NoSuchFieldException {
        MyLinkedList<Integer> empty = new MyLinkedList<>();
        Throwable ex = assertThrows(NoSuchFieldException.class, () -> empty.getLast());
        assertEquals("List is empty.", ex.getMessage());
    }

    @ParameterizedTest(name = "get value by index {arguments}")
    @CsvSource(value = {"1,11", "2,22", "3,33", "1,11"})
    void get(Integer index, Integer expect) {
        assertEquals(expect, list.get(index));
    }

    @Test
    void get_value_outBroadSize() throws NoSuchFieldException {
        Throwable ex = assertThrows(IllegalStateException.class, () -> list.get(5));
        assertEquals("Index out of broad list size.", ex.getMessage());
    }

    @Test
    void set_middle() {
        list.set(2, 44);
        assertEquals("MyLinkedList{1-[11],2-[44],3-[22],4-[33],5-[11]}", list.toString());
    }

    @Test
    void set_first() {
        list.set(1, 44);
        assertEquals("MyLinkedList{1-[44],2-[11],3-[22],4-[33],5-[11]}", list.toString());
    }

    @Test
    void set_last() {
        list.set(5, 44);
        assertEquals("MyLinkedList{1-[11],2-[22],3-[33],4-[11],5-[44]}", list.toString());
    }

    @Test
    void set_first_toEmpty() {
        MyLinkedList<Integer> empty = new MyLinkedList<>();
        empty.set(1, 44);
        assertEquals("MyLinkedList{1-[44]}", empty.toString());
    }

    @Test
    void set_7_toEmpty() {
        MyLinkedList<Integer> empty = new MyLinkedList<>();
        Throwable ex = assertThrows(IllegalStateException.class, () -> empty.set(7, 77));
        assertEquals("Index out of broad list size.", ex.getMessage());
    }

    @Test
    void set_index_outOfBoard() {
        Throwable ex = assertThrows(IllegalStateException.class, () -> list.set(6, 44));
        assertEquals("Index out of broad list size.", ex.getMessage());

    }

    @ParameterizedTest(name = "remove by index {index}")
    @CsvSource(delimiter = ';', value = {
            "1;11;MyLinkedList{1-[22],2-[33],3-[11]}",
            "2;22;MyLinkedList{1-[11],2-[33],3-[11]}",
            "3;33;MyLinkedList{1-[11],2-[22],3-[11]}",
            "4;11;MyLinkedList{1-[11],2-[22],3-[33]}"})
    void removeAtIndex(Integer index, Integer expectVal,  String expect) {
        assertEquals(expectVal, list.removeAtIndex(index));
        assertEquals(expect, list.toString());
    }

    @Test
    void removeAtIndex_index_outOfBoard() {
        Throwable ex = assertThrows(NoSuchElementException.class, () -> list.removeAtIndex(6));
        assertEquals("Index out of broad list size.", ex.getMessage());

    }

    @Test
    void remove () {
        assertEquals(2, list.remove(11));
        assertEquals("MyLinkedList{1-[22],2-[33]}", list.toString());
    }



    @ParameterizedTest(name = "find index by value {arguments}")
    @CsvSource(value = {"1,11", "2,22", "3,33"})
    void indexOf(Integer expect, Integer value ) {
        assertEquals(expect, list.indexOf(value));
    }

    @ParameterizedTest(name = "find index by value {arguments}")
    @CsvSource(value = {"4,11", "2,22", "3,33"})
    void lastIndexOf(Integer expect, Integer value ) {
        assertEquals(expect, list.lastIndexOf(value));
    }

    @Test
    void size() throws NoSuchFieldException {
        assertEquals(4, list.size());
    }
}