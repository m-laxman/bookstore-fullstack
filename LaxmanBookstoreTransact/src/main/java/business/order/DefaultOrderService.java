package business.order;

import api.ApiException;
import business.BookstoreDbException;
import business.JdbcUtils;
import business.book.Book;
import business.book.BookDao;
import business.book.BookForm;
import business.cart.ShoppingCart;
import business.cart.ShoppingCartItem;
import business.customer.Customer;
import business.customer.CustomerDaoJdbc;
import business.customer.CustomerForm;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class DefaultOrderService implements OrderService {

    private BookDao bookDao;
    private OrderDao orderDao;
    private LineItemDaoJdbc lineItemDao;
    private CustomerDaoJdbc customerDao;

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public void setLineItemDao(LineItemDaoJdbc lineItemDao) {
        this.lineItemDao = lineItemDao;
    }

    public void setCustomerDao(CustomerDaoJdbc customerDao) {
        this.customerDao = customerDao;
    }


    @Override
    public OrderDetails getOrderDetails(long orderId) {
        Order order = orderDao.findByOrderId(orderId);
        Customer customer = customerDao.findByCustomerId(order.customerId());
        List<LineItem> lineItems = lineItemDao.findByOrderId(orderId);
        List<Book> books = lineItems
                .stream()
                .map(lineItem -> bookDao.findByBookId(lineItem.bookId()))
                .toList();
        return new OrderDetails(order, customer, lineItems, books);
    }

    private Date getCardExpirationDate(String monthString, String yearString) {

        int expiryMonth = Integer.parseInt(monthString);
        int expiryYear = Integer.parseInt(yearString);
        YearMonth expiryYearMonth = YearMonth.of(expiryYear, expiryMonth);
        LocalDate lastDayOfMonth = expiryYearMonth.atEndOfMonth();
        return java.sql.Date.valueOf(lastDayOfMonth);
    }

    private long performPlaceOrderTransaction(
            String name, String address, String phone,
            String email, String ccNumber, Date date,
            ShoppingCart cart, Connection connection) {
        try {
            connection.setAutoCommit(false);
            long customerId = customerDao.create(
                    connection, name, address, phone, email,
                    ccNumber, date);
            long customerOrderId = orderDao.create(
                    connection,
                    cart.getComputedSubtotal() + cart.getSurcharge(),
                    generateConfirmationNumber(), customerId);
            for (ShoppingCartItem item : cart.getItems()) {
                lineItemDao.create(connection, customerOrderId,
                        item.getBookId(), item.getQuantity());
            }
            connection.commit();
            return customerOrderId;
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new BookstoreDbException("Failed to roll back transaction", e1);
            }
            return 0;
        }
    }

    private int generateConfirmationNumber() {
        return ThreadLocalRandom.current().nextInt(999999999);
    }

    @Override
    public long placeOrder(CustomerForm customerForm, ShoppingCart cart) {

        validateCustomer(customerForm);
        validateCart(cart);

        // NOTE: MORE CODE PROVIDED NEXT PROJECT
        try (Connection connection = JdbcUtils.getConnection()) {
            Date ccExpDate = getCardExpirationDate(
                    customerForm.getCcExpiryMonth(),
                    customerForm.getCcExpiryYear());
            return performPlaceOrderTransaction(
                    customerForm.getName(),
                    customerForm.getAddress(),
                    customerForm.getPhone(),
                    customerForm.getEmail(),
                    customerForm.getCcNumber(),
                    ccExpDate, cart, connection);
        } catch (SQLException e) {
            throw new BookstoreDbException("Error during close connection for customer order", e);
        }
    }


    private void validateCustomer(CustomerForm customerForm) {

        String name = customerForm.getName();

        if (name == null || name.isEmpty()) {
            throw new ApiException.ValidationFailure("Missing name field");
        } else if (name.length() < 4 || name.length() > 45) {
            throw new ApiException.ValidationFailure("Invalid name field");
        }

        // TODO: Validation checks for address, phone, email, ccNumber
        String address = customerForm.getAddress();
        if (address == null || address.isEmpty()) {
            throw new ApiException.ValidationFailure("Missing address field");
        } else if (address.length() < 4 || address.length() > 45) {
            throw new ApiException.ValidationFailure("Invalid address field");
        }

        String phone = customerForm.getPhone();
        if (phone == null || phone.isEmpty()) {
            throw new ApiException.ValidationFailure("Missing phone field");
        } else {
            String digits = phone.replaceAll("\\D", "");
            if (digits.length() != 10) {
                throw new ApiException.ValidationFailure("Invalid phone field");
            }
        }

        String email = customerForm.getEmail();
        if (email == null || email.isEmpty()) {
            throw new ApiException.ValidationFailure("Missing email field");
        } else {
            if (email.contains(" ") || !email.contains("@") || email.endsWith(".")) {
                throw new ApiException.ValidationFailure("Invalid email field");
            }
        }

        String ccNumber = customerForm.getCcNumber();
        if (ccNumber == null || ccNumber.isEmpty()) {
            throw new ApiException.ValidationFailure("Missing ccNumber field");
        } else {
            String digits = ccNumber.replaceAll("\\D", "");
            if (!(digits.length() >= 14 && digits.length() <= 16)) {
                throw new ApiException.ValidationFailure("Invalid ccNumber field");
            }
        }

        if (expiryDateIsInvalid(customerForm.getCcExpiryMonth(), customerForm.getCcExpiryYear())) {
            throw new ApiException.ValidationFailure("Please enter a valid expiration date");
        }
    }

    private boolean expiryDateIsInvalid(String ccExpiryMonth, String ccExpiryYear) {

        if (ccExpiryMonth == null || ccExpiryYear == null || ccExpiryMonth.isEmpty() || ccExpiryYear.isEmpty()) {
            return true;
        }
        // TODO: return true when the provided month/year is before the current month/yeaR
        // HINT: Use Integer.parseInt and the YearMonth class
        int expiryMonth = Integer.parseInt(ccExpiryMonth);
        int expiryYear = Integer.parseInt(ccExpiryYear);

        YearMonth currentYearMonth = YearMonth.now();
        YearMonth expiryYearMonth = YearMonth.of(expiryYear, expiryMonth);

        return expiryYearMonth.isBefore(currentYearMonth);
    }

    private void validateCart(ShoppingCart cart) {

        if (cart.getItems().size() <= 0) {
            throw new ApiException.ValidationFailure("Cart is empty");
        }

        cart.getItems().forEach(item -> {
            if (item.getQuantity() < 1 || item.getQuantity() > 99) {
                throw new ApiException.ValidationFailure("Invalid Quantity");
            }
            Book databaseBook = bookDao.findByBookId(item.getBookId());
            BookForm bookForm = item.getBookForm();
            // TODO: complete the required validations
            if (databaseBook.price() != bookForm.getPrice()) {
                throw new ApiException.ValidationFailure("Invalid Price");
            }

            if (databaseBook.categoryId() != bookForm.getCategoryId()) {
                throw new ApiException.ValidationFailure("Invalid Category");
            }
        });
    }
}
