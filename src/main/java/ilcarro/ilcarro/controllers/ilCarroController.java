package ilcarro.ilcarro.controllers;

import ilcarro.ilcarro.api.ilCarroReturnCode;
import ilcarro.ilcarro.dto.Comment;
import ilcarro.ilcarro.dto.carDto.CarRequestDto;
import ilcarro.ilcarro.dto.carDto.CarResponseDto;
import ilcarro.ilcarro.dto.carDto.CarResponseOwnerDto;
import ilcarro.ilcarro.dto.commentDto.CommentRequestDto;
import ilcarro.ilcarro.dto.userDto.UserRequestDto;
import ilcarro.ilcarro.dto.userDto.UserResponseDto;
import ilcarro.ilcarro.service.ilCarroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class ilCarroController {

    @Autowired
    private ilCarroService ilCarroService;

    @PostMapping("/registration")
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody UserRequestDto userRequestDto) {
        return new ResponseEntity<>(ilCarroService.registerUser(userRequestDto),HttpStatus.OK);
    }

    @PutMapping("/user")
    public ResponseEntity<UserResponseDto> updateUser(@RequestBody UserRequestDto userRequestDto){
        return new ResponseEntity<>(ilCarroService.updateUser(userRequestDto),HttpStatus.OK);
    }

    @DeleteMapping("/user/{email}")
    public ilCarroReturnCode deleteUser(@PathVariable("email") String email) {
            ilCarroService.deleteUser(email);
            return ilCarroReturnCode.OK;
    }

    @PostMapping("/car/{email}")
    public CarResponseOwnerDto addCar(@RequestBody CarRequestDto carRequestDto, @PathVariable("email") String email) {
        return ilCarroService.addCar(carRequestDto, email);
    }

    @GetMapping("/user/cars/")
        public List<CarResponseDto> getOwnerCarsByEmail(@RequestParam String email){
            return ilCarroService.getOwnerCarsById(email);
        }

    @GetMapping("/user/cars/car/") // owner get car by id
    public CarResponseDto getOwnerCarById(@RequestParam String serialNumber) {
            return ilCarroService.getOwnerCarById("nir.simkin@gmail.com",serialNumber);
    }

    @PutMapping("/car/update/")
    public CarResponseOwnerDto updateCar(@RequestBody CarRequestDto carRequestDto) {
        return ilCarroService.updateCar(carRequestDto,"nur");
    }

    @GetMapping("/car/{serialNumber}")
    public CarResponseOwnerDto getCar(@PathVariable String serialNumber) {
        return ilCarroService.getCar(serialNumber);
    }

    @DeleteMapping("/car/delete/")
    public ilCarroReturnCode deleteCar(@RequestParam String serialNumber) {
        return ilCarroService.deleteCar("nur",serialNumber);
    }

    @GetMapping("/comments")
    public List<Comment> getComments() {
        return ilCarroService.getComments();
    }

    @PostMapping("/comment?serialNumber={serialNumber}")
    public ilCarroReturnCode addComment(@RequestBody CommentRequestDto commentRequest,
                                        @PathVariable("serialNumber") String serialNumber) {
        return ilCarroService.addComment(commentRequest, serialNumber, "nir.simkin@gmail.com");
    }

}
