package Models;

public class OrdersReport {

    private String customerName;
    private int totalOrders;

    public OrdersReport() {
    }

    public OrdersReport(String customerName, int totalOrders) {
        this.customerName = customerName;
        this.totalOrders = totalOrders;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
    }
}