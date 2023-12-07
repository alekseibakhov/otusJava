package homework;

import java.util.Deque;
import java.util.LinkedList;

public class CustomerReverseOrder {
    final Deque<Customer> list = new LinkedList<>();

    public void add(Customer customer) {
        list.add(customer);
    }

    public Customer take() {
        return list.removeLast();
    }
}