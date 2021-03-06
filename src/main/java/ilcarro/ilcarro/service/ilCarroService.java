package ilcarro.ilcarro.service;

import ilcarro.ilcarro.api.ilCarroReturnCode;
import ilcarro.ilcarro.dto.Comment;
import ilcarro.ilcarro.dto.bookingDto.BookedPeriodDto;
import ilcarro.ilcarro.dto.carDto.CarRequestDto;
import ilcarro.ilcarro.dto.carDto.CarResponseDto;
import ilcarro.ilcarro.dto.carDto.CarResponseOwnerDto;
import ilcarro.ilcarro.dto.commentDto.CommentRequestDto;
import ilcarro.ilcarro.dto.userDto.UserRequestDto;
import ilcarro.ilcarro.dto.userDto.UserResponseDto;
import ilcarro.ilcarro.entities.CarMongo;

import java.util.List;


public interface ilCarroService {
    UserResponseDto registerUser(UserRequestDto userRequestDto);
    UserResponseDto updateUser(UserRequestDto userRequestDto);
    ilCarroReturnCode deleteUser(String email);
    CarResponseOwnerDto addCar(CarRequestDto carRequestDto, String email);
    ilCarroReturnCode addComment(CommentRequestDto commentRequest, String serialNumber, String email);
    CarResponseDto getOwnerCarById(String email, String serialNumber);
    List<CarResponseDto> getOwnerCarsById(String email);
    CarResponseOwnerDto updateCar(CarRequestDto carRequestDto, String ownerEmail);
    List<BookedPeriodDto> getOwnerCarBookedPeriods(String email, String serialNumber);
    CarResponseOwnerDto getCar(String serialNumber);
    UserResponseDto getUser(String email);
    ilCarroReturnCode deleteCar(String email, String serialNumber);
    List<Comment> getComments();
    Comment getComment();
}
