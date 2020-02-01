package ilcarro.ilcarro.entities;

import ilcarro.ilcarro.dto.Renter;

import java.util.Date;

public class OwnerBookedPeriod {
    private long orderId;
    private Date startDate;
    private Date endDate;
    private boolean paid;
    private float amount;
    private Date bookingDate;
    private Renter personWhoBooked;

    public OwnerBookedPeriod(long orderId, Date startDate, Date endDate, boolean paid, float amount, Date bookingDate, Renter personWhoBooked) {
        this.orderId = orderId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.paid = paid;
        this.amount = amount;
        this.bookingDate = bookingDate;
        this.personWhoBooked = personWhoBooked;
    }

    public OwnerBookedPeriod() {
    }

    public long getOrderId() {
        return orderId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Renter getPersonWhoBooked() {
        return personWhoBooked;
    }

}
