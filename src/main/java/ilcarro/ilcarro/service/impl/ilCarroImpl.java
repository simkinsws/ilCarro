package ilcarro.ilcarro.service.impl;
import ilcarro.ilcarro.api.ilCarroReturnCode;
import ilcarro.ilcarro.dto.Comment;
import ilcarro.ilcarro.dto.bookingDto.BookedCarDto;
import ilcarro.ilcarro.dto.bookingDto.BookedPeriodDto;
import ilcarro.ilcarro.dto.carDto.CarRequestDto;
import ilcarro.ilcarro.dto.carDto.CarResponseDto;
import ilcarro.ilcarro.dto.carDto.CarResponseOwnerDto;
import ilcarro.ilcarro.dto.carDto.OwnerDto;
import ilcarro.ilcarro.dto.commentDto.CommentRequestDto;
import ilcarro.ilcarro.dto.userDto.UserRequestDto;
import ilcarro.ilcarro.dto.userDto.UserResponseDto;
import ilcarro.ilcarro.entities.*;
import ilcarro.ilcarro.exceptions.errors.CarNotFoundException;
import ilcarro.ilcarro.exceptions.errors.UserNotFoundException;
import ilcarro.ilcarro.repository.ilCarroRepository;
import ilcarro.ilcarro.service.ilCarroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;


@Service
public class ilCarroImpl implements ilCarroService {

    @Autowired
    private ilCarroRepository ilCarroRepository;

    @Override
    @Transactional
    public UserResponseDto registerUser(UserRequestDto userRequestDto) {
        if(ilCarroRepository.existsById(userRequestDto.getEmail())) {
            return null;
        }
        UserMongo userMongo = new UserMongo(userRequestDto.getEmail(), userRequestDto.getFirstName()
                , userRequestDto.getSecondName(), userRequestDto.getPhone(), userRequestDto.getPhotoUrl(),
                new Date(), new ArrayList<Comment>(), new ArrayList<CarMongo>(),
                new ArrayList<BookedCarMongo>(), new ArrayList<BookedCarMongo>());
        ilCarroRepository.save(userMongo);

        return new UserResponseDto(userMongo.getFirstName(),userMongo.getSecondName(),
                userMongo.getRegistrationDate()
        ,new ArrayList<Comment>(), new ArrayList<CarResponseDto>(),
                new ArrayList<BookedCarDto>(), new ArrayList<BookedCarDto>());
    }

    @Override
    @Transactional
    public UserResponseDto updateUser(UserRequestDto userRequestDto) {
        UserMongo userMongo = ilCarroRepository.findById(userRequestDto.getEmail()).orElse(null);

        if(userMongo == null){
            throw new UserNotFoundException("User Not Found.");
        }
        userMongo.setFirstName(userRequestDto.getFirstName());
        userMongo.setSecondName(userRequestDto.getSecondName());
        userMongo.setPhotoUrl(userRequestDto.getPhotoUrl());
        userMongo.setPhone(userRequestDto.getPhone());
        ilCarroRepository.save(userMongo);
        return toUserDto(userMongo);
    }


    @Override
    @Transactional
    public ilCarroReturnCode deleteUser(String email) {
        UserMongo user = ilCarroRepository.findById(email).orElse(null);
        ilCarroRepository.delete(user);
        return ilCarroReturnCode.OK;
    }

    @Override
    @Transactional
    public CarResponseOwnerDto addCar(CarRequestDto carRequestDto, String email) {
        UserMongo userMongo = ilCarroRepository.findById(email).orElseThrow(()
                        -> new UserNotFoundException("User with email " + email + " is not found"));
        CarMongo carMongo = new CarMongo(carRequestDto.getSerialNumber(), carRequestDto.getMake(), carRequestDto.getModel(),
                carRequestDto.getYear(),carRequestDto.getEngine(),carRequestDto.getFuel(),carRequestDto.getGear(),
                carRequestDto.getWheelsDrive(),carRequestDto.getDoors(),carRequestDto.getSeats(),carRequestDto.getFuelConsumption()
        ,carRequestDto.getFeatures(),carRequestDto.getCarClass(),carRequestDto.getPricePerDay(),carRequestDto.getDistance()
        ,carRequestDto.getAbout(),carRequestDto.getPickUpPlace(),carRequestDto.getImageUrl(),new ArrayList<OwnerBookedPeriod>(),false);

        boolean isEmpty = userMongo.getOwnCars().stream().filter(c-> c.getSerialNumber()
                .equalsIgnoreCase(carMongo.getSerialNumber())).collect(Collectors.toList()).isEmpty();
        if(!isEmpty){
            return null;
        }

        userMongo.getOwnCars().add(carMongo);
        ilCarroRepository.save(userMongo);

        return toCarResponseOwnerDto(userMongo.getFirstName(),userMongo.getSecondName(),
                userMongo.getRegistrationDate(),carMongo);
    }

    @Override
    public CarResponseDto getOwnerCarById(String email, String serialNumber) {
        UserMongo userMongo = ilCarroRepository.findCarByOwnerEmailAndSerialNumber(email, serialNumber);
//        boolean rightCar = userMongo.getOwnCars();
        if(userMongo == null) {
            throw new UserNotFoundException("User With Email : " + email + " Not Found");
        }
        if(userMongo.getOwnCars().get(0)==null){
            throw new CarNotFoundException("No Car Found.");
        }
            return toCarResponseDto(userMongo.getOwnCars().get(0));
    }

    @Override
    public List<CarResponseDto> getOwnerCarsById(String email) {
        UserMongo userMongo = ilCarroRepository.findCarsByOwnerEmail(email);
            if(userMongo == null) {
                throw new UserNotFoundException("User not Found with this email :" + email);
            }
        if(userMongo.getOwnCars().isEmpty()) {
            throw new CarNotFoundException("No Cars Found");
        }
        return toCarResponseDtoList(userMongo.getOwnCars());
    }

    @Override
    @Transactional
    public CarResponseOwnerDto updateCar(CarRequestDto carRequestDto, String email) {
        UserMongo userMongo = ilCarroRepository.findById(email).orElse(null);
        UserMongo carToUpdate = ilCarroRepository.findCarByOwnerEmailAndSerialNumber(email, carRequestDto.getSerialNumber());

        List<CarMongo> cars = carToUpdate.getOwnCars().stream()
                .filter(car-> car.getSerialNumber().equals(carRequestDto.getSerialNumber()))
                .sorted().collect(Collectors.toList());
        CarMongo car = cars.get(0);

        if(carToUpdate  == null) {
            throw new CarNotFoundException("Error occurred.");
        }
        car.setCarClass(carRequestDto.getCarClass());
//            carToUpdate.getOwnCars().get(0).setMake(carRequestDto.getMake());
//            carToUpdate.getOwnCars().get(0).setModel(carRequestDto.getModel());
//            carToUpdate.getOwnCars().get(0).setYear(carRequestDto.getYear());
//            carToUpdate.getOwnCars().get(0).setEngine(carRequestDto.getEngine());
//            carToUpdate.getOwnCars().get(0).setFuel(carRequestDto.getFuel());
//            carToUpdate.getOwnCars().get(0).setGear(carRequestDto.getGear());
//            carToUpdate.getOwnCars().get(0).setWheelsDrive(carRequestDto.getWheelsDrive());
//            carToUpdate.getOwnCars().get(0).setDoors(carRequestDto.getDoors());
//            carToUpdate.getOwnCars().get(0).setSeats(carRequestDto.getSeats());
//            carToUpdate.getOwnCars().get(0).setFuelConsumption(carRequestDto.getFuelConsumption());
//            carToUpdate.getOwnCars().get(0).setFeatures(carRequestDto.getFeatures());
            carToUpdate.getOwnCars().get(0).setCarClass(carRequestDto.getCarClass());
//            carToUpdate.getOwnCars().get(0).setPricePerDay(carRequestDto.getPricePerDay());
//            carToUpdate.getOwnCars().get(0).setDistance(carRequestDto.getDistance());
//            carToUpdate.getOwnCars().get(0).setAbout(carRequestDto.getAbout());
//            carToUpdate.getOwnCars().get(0).setPickUpPlace(carRequestDto.getPickUpPlace());
//            carToUpdate.getOwnCars().get(0).setImageUrl(carRequestDto.getImageUrl());

//        ilCarroRepository.save(userMongo);

        ilCarroRepository.save(carToUpdate);

        return toCarResponseOwnerDto(userMongo.getFirstName(),userMongo.getSecondName(),userMongo.getRegistrationDate()
        ,car);
    }

    @Override
    public List<BookedPeriodDto> getOwnerCarBookedPeriods(String email, String serialNumber) {
        UserMongo userBookedPeriods = ilCarroRepository.findCarBookedPeriodsByOwnerEmailAndSerialNumber(email,serialNumber);
        return toBookedPeriodDtoList(userBookedPeriods.getOwnCars().get(0).getBookedPeriod());
    }

    @Override
    public CarResponseOwnerDto getCar(String serialNumber) {
        UserMongo userCar = ilCarroRepository.findCarBySerialNumber(serialNumber).findFirst().orElse(null);

        List<CarMongo> cars = userCar.getOwnCars().stream().filter(car-> car.getSerialNumber().equals(serialNumber))
                .sorted().collect(Collectors.toList());

        CarMongo car = cars.get(0);


        return toCarResponseOwnerDto(userCar.getFirstName(),userCar.getSecondName(),userCar.getRegistrationDate(),
               car);
    }

    @Override
    public UserResponseDto getUser(String email) {
        return toUserDto(ilCarroRepository.findById(email).orElseThrow(() ->
                new UserNotFoundException("User with email : "+email + " Not Found")));
    }

    @Override
    public ilCarroReturnCode deleteCar(String email, String serialNumber) {
        return ilCarroReturnCode.OK;
    }

    @Override
    public List<Comment> getComments() {
        List<Comment> comments = ilCarroRepository.findAll().stream()
                .map(u -> u.getComments()).flatMap(Collection::stream).collect(Collectors.toList());
        return comments;
    }

    @Override
    public Comment getComment() {
        return ilCarroRepository.findBy().findAny().orElse(null).getComments().get(0);
    }

    @Override
    @Transactional
    public ilCarroReturnCode addComment(CommentRequestDto commentRequest, String serialNumber, String email) {
        UserMongo userCar = ilCarroRepository.findCarBySerialNumber(serialNumber).findAny().orElse(null);
        UserMongo userMongo = ilCarroRepository.findById(email).orElse(null);

//        List<Comment> comments = ilCarroRepository.findAllBy().findAny().get().getComments();


//        comments.add(new Comment(userMongo.getFirstName(), userMongo.getSecondName(),userMongo.getPhotoUrl()
//        ,new Date(), commentRequest.getPost()));
        userCar.getComments().add(new Comment(userMongo.getFirstName(), userMongo.getSecondName(),userMongo.getPhotoUrl()
                ,new Date(), commentRequest.getPost()));

        ilCarroRepository.save(userCar);
        return ilCarroReturnCode.OK;
    }

    private UserResponseDto toUserDto(UserMongo userMongo) {
        return new UserResponseDto(userMongo.getFirstName(),userMongo.getSecondName(),userMongo.getRegistrationDate()
                ,userMongo.getComments(),
                toCarResponseDtoList(userMongo.getOwnCars()),
                toBookedCarDtoList(userMongo.getBookedCars()),
                toBookedCarDtoList(userMongo.getHistory()));
    }

    private List<BookedCarDto> toBookedCarDtoList(List<BookedCarMongo> bookedCars) {
        return bookedCars.stream().map(this::toBookedCarDto).collect(Collectors.toList());
    }

    private BookedCarDto toBookedCarDto(BookedCarMongo bookedCarMongo) {
        return new BookedCarDto(bookedCarMongo.getSerialNumber(),
                toBookedPeriodDto(bookedCarMongo.getBookedPeriod()));
    }

    private List<BookedPeriodDto> toBookedPeriodDtoList(List<OwnerBookedPeriod> ownerBookedPeriod) {
        if(ownerBookedPeriod == null) {
            return new ArrayList<>();
        }
        return ownerBookedPeriod.stream().map(this::toBookedPeriod).collect(Collectors.toList());
    }

    private BookedPeriodDto toBookedPeriod(OwnerBookedPeriod ownerBookedPeriod){
        return new BookedPeriodDto(ownerBookedPeriod.getOrderId(),ownerBookedPeriod.getStartDate()
                ,ownerBookedPeriod.getEndDate(), ownerBookedPeriod.isPaid(),ownerBookedPeriod.getAmount(),
                ownerBookedPeriod.getBookingDate(),ownerBookedPeriod.getPersonWhoBooked());
    }

    private BookedPeriodDto toBookedPeriodDto(RenterBookedPeriod bookedPeriod) {
        return new BookedPeriodDto(bookedPeriod.getOrderId(),bookedPeriod.getStartDate(),bookedPeriod.getEndDate(),
                bookedPeriod.isPaid(),bookedPeriod.getAmount(),bookedPeriod.getBookingDate());
    }


    private List<CarResponseDto> toCarResponseDtoList(List<CarMongo> carsMongo) {
        return carsMongo.stream().map(this::toCarResponseDto).collect(Collectors.toList());
    }

    private CarResponseDto toCarResponseDto(CarMongo carMongo){
        return  new CarResponseDto(carMongo.getSerialNumber(),carMongo.getMake(),carMongo.getModel(),
                carMongo.getYear(),carMongo.getEngine(),carMongo.getFuel(),carMongo.getGear(),carMongo.getWheelsDrive(),
                carMongo.getDoors(),carMongo.getSeats(),carMongo.getFuelConsumption(),carMongo.getFeatures(),carMongo.getCarClass(),
                carMongo.getPricePerDay(),carMongo.getDistance(),carMongo.getAbout(),carMongo.getPickUpPlace(),carMongo.getImageUrl(),
                toBookedPeriodDtoList(carMongo.getBookedPeriod()));
    }

    private CarResponseOwnerDto toCarResponseOwnerDto(String firstName, String secondName, Date registrationDate, CarMongo carMongo){
        return  new CarResponseOwnerDto(carMongo.getSerialNumber(),carMongo.getMake(),carMongo.getModel(),
                carMongo.getYear(),carMongo.getEngine(),carMongo.getFuel(),carMongo.getGear(),carMongo.getWheelsDrive(),
                carMongo.getDoors(),carMongo.getSeats(),carMongo.getFuelConsumption(),carMongo.getFeatures()
                ,carMongo.getCarClass(), carMongo.getPricePerDay(),
                carMongo.getDistance(),carMongo.getAbout(),carMongo.getPickUpPlace(),carMongo.getImageUrl()
                ,toOwnerDto(firstName, secondName, registrationDate), toBookedPeriodDtoList(carMongo.getBookedPeriod()));
    }

    private OwnerDto toOwnerDto(String firstName, String secondName, Date registrationDate) {
        OwnerDto ownerDto = new OwnerDto();
        ownerDto.setFirstName(firstName);
        ownerDto.setSecondName(secondName);
        ownerDto.setRegistrationDate(registrationDate);
        return ownerDto;
    }

//    private CarResponseDto toCarResponseOwnerDto(OwnerDto ownerDto, CarMongo carMongo) {
//        return new CarResponseDto(carMongo.getSerialNumber(),carMongo.getMake(),carMongo.getModel(),
//                carMongo.getYear(),carMongo.getEngine(),carMongo.getFuel(),carMongo.getGear(),carMongo.getWheelsDrive(),
//                carMongo.getDoors(),carMongo.getSeats(),carMongo.getFuelConsumption(),carMongo.getFeatures(),carMongo.getCarClass(),
//                carMongo.getPricePerDay(),carMongo.getDistance(),carMongo.getAbout(),carMongo.getPickUpPlace(),carMongo.getImageUrl(),
//                new OwnerDto(ownerDto.getFirstName(),ownerDto.getSecondName(),ownerDto.getRegistrationDate()),toBookedPeriodDtoList(carMongo.getBookedPeriod()));
//    }

    private List<BookedPeriodDto> toBookedPeriodRenterList(List<RenterBookedPeriod> bookedPeriods) {
        return bookedPeriods.stream().map(this::toBookedPeriodDto).collect(Collectors.toList());
    }
}
