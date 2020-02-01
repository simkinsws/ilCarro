package ilcarro.ilcarro.dto.bookingDto;

import ilcarro.ilcarro.dto.Renter;

import java.util.Date;

public class BookedPeriodDto {
    private long order_id;
    private Date startDate;
    private Date endDate;
    private boolean paid;
    private float amount;
    private Date bookingDate;
    private Renter personWhoBooked;

    public BookedPeriodDto(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public BookedPeriodDto(long order_id, Date startDate, Date endDate, boolean paid, float amount, Date bookingDate) {
        this.order_id = order_id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.paid = paid;
        this.amount = amount;
        this.bookingDate = bookingDate;
    }

    public BookedPeriodDto(long order_id,
                           Date startDate,
                           Date endDate,
                           boolean paid,
                           float amount,
                           Date bookingDate,
                           Renter personWhoBooked) {
        this.order_id = order_id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.paid = paid;
        this.amount = amount;
        this.bookingDate = bookingDate;
        this.personWhoBooked = personWhoBooked;
    }

    public BookedPeriodDto() {
    }

    public Renter getPersonWhoBooked() {
        return personWhoBooked;
    }

    public void setPersonWhoBooked(Renter personWhoBooked) {
        this.personWhoBooked = personWhoBooked;
    }

    public long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(long order_id) {
        this.order_id = order_id;
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
}
