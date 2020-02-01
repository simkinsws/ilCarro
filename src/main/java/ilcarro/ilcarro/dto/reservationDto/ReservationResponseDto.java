package ilcarro.ilcarro.dto.reservationDto;

import java.util.Date;

public class ReservationResponseDto {
    private String orderNumber;
    private float amount;
    private Date bookingDate;

    public ReservationResponseDto(String orderNumber, float amount, Date bookingDate) {
        this.orderNumber = orderNumber;
        this.amount = amount;
        this.bookingDate = bookingDate;
    }

    public ReservationResponseDto() {
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
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
