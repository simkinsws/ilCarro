package ilcarro.ilcarro.repository;

import ilcarro.ilcarro.dto.CarGetter;
import ilcarro.ilcarro.entities.CarMongo;
import ilcarro.ilcarro.entities.UserMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Stream;

@Repository
public interface ilCarroRepository extends MongoRepository<UserMongo, String> {

//    UserMongo findUserMongoByOwnCarsSerialNumber(String serialNumber);

    @Query(value = "{'ownCars.serialNumber':?0}", fields = "{'ownCars': 1, _id: 0}")
        Stream<UserMongo> findCarBySerialNumber(String serialNumber);

    @Query(value = "{'email': ?0}", fields = "{'ownCars': 1, _id: 0}")
    UserMongo findCarsByOwnerEmail(String email);

    @Query(value = "{$and:[{'email':?0} , {'ownCars.serialNumber':?1}]}",fields = "{'ownCars' : 1, _id : 0}")
    UserMongo findCarByOwnerEmailAndSerialNumber(String email, String serialNumber);

    @Query(value = "{$and:[{'email':?0} , {'ownCars.serialNumber':?1}]}",fields = "{'ownCars.bookedPeriod' : 1, _id : 0}")
    UserMongo findCarBookedPeriodsByOwnerEmailAndSerialNumber(String email, String serialNumber);

    @Query(fields = "{'comments': 1 , _id : 0}")
    List<UserMongo> findAllBy();

    @Query(fields = "{'comments': 1, _id : 0}")
    Stream<UserMongo> findBy();


}
