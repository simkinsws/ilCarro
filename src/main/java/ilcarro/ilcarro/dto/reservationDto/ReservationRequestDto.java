package ilcarro.ilcarro.dto.reservationDto;

import ilcarro.ilcarro.dto.Renter;

import java.util.Date;

public class ReservationRequestDto {
    private Date startDate;
    private Date endDate;
    private Renter personWhoBooked;

    public ReservationRequestDto(Date startDate, Date endDate, Renter personWhoBooked) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.personWhoBooked = personWhoBooked;
    }

    public ReservationRequestDto() {
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

    public Renter getPersonWhoBooked() {
        return personWhoBooked;
    }

    public void setPersonWhoBooked(Renter personWhoBooked) {
        this.personWhoBooked = personWhoBooked;
    }
}
