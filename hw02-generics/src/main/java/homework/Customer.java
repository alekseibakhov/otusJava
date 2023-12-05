package homework;

//@SuppressWarnings({"java:S1135"}) // при выполнении ДЗ эту аннотацию надо удалить
public class Customer {
    private final long id;
    private String name;
    private long scores;

    public Customer(long id, String name, long scores) {
        this.id = id;
        this.name = name;
        this.scores = scores;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getScores() {
        return scores;
    }

    public void setScores(long scores) {
        this.scores = scores;
    }

    @Override
    public String toString() {
        return "Customer{" + "id=" + id + ", name='" + name + '\'' + ", scores=" + scores + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        return id == customer.id;
//        if (scores != customer.scores) return false;
//        return name != null ? name.equals(customer.name) : customer.name == null;
    }

    @Override
    public int hashCode() {
        //        result = 31 * result + (name != null ? name.hashCode() : 0);
//        result = 31 * result + (int) (scores ^ (scores >>> 32));
        return (int) (id ^ (id >>> 32));
    }

}