package homework;

import java.util.LinkedList;
import java.util.List;

public class CustomerReverseOrder {
    final List<Customer> list = new LinkedList<>();

    public void add(Customer customer) {
        list.add(customer);
    }

    public Customer take() {
        return list.removeLast();
    }
}