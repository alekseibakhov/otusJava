package homework;

import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class CustomerService {
    private final NavigableMap<Customer, String> map = new TreeMap<>(Comparator.comparingLong(Customer::getScores));

    public Map.Entry<Customer, String> getSmallest() {
        Map.Entry<Customer, String> entry = map.firstEntry();

        if (entry != null) {
            return getCopy(entry);
        }

        return null;
//        return map.firstEntry();
    }


    public Map.Entry<Customer, String> getNext(Customer customer) {
        Map.Entry<Customer, String> entry = map.higherEntry(customer);

        if (entry != null) {
            return getCopy(entry);
        }

        return null;
//        return map.higherEntry(customer);
    }

    public void add(Customer customer, String data) {
        map.put(customer, data);
    }

    private static Map.Entry<Customer, String> getCopy(Map.Entry<Customer, String> entry) {
        return Map.entry(
                new Customer(
                        entry.getKey().getId(),
                        entry.getKey().getName(),
                        entry.getKey().getScores()),
                entry.getValue());
    }
}