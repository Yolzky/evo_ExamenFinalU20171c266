package betrip.services.betrip_backend_services.BoundendContextDriverRoutes.service;

import betrip.services.betrip_backend_services.BoundenContextTravelEvents.domain.model.entity.TravelEvent;
import betrip.services.betrip_backend_services.BoundendContextDriverRoutes.domain.model.entity.DriverRoute;
import betrip.services.betrip_backend_services.BoundendContextDriverRoutes.domain.persistence.DriverRouteRepository;
import betrip.services.betrip_backend_services.BoundendContextDriverRoutes.domain.service.DriverRouteService;
import betrip.services.betrip_backend_services.BoundendContextDrivers.domain.persistence.DriverRepository;
import betrip.services.betrip_backend_services.BoundendContextTravelers.domain.model.entity.Traveler;
import betrip.services.betrip_backend_services.shared.exception.ResourceNotFoundException;
import betrip.services.betrip_backend_services.shared.exception.ResourceValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class DriverRouteServiceImpl implements DriverRouteService {
    private static final  String ENTITY="Driver-Routes";
    private final DriverRouteRepository driverRoutesRepository;
    private final DriverRepository driverRepository;
    //validator de tipo JAVAX
    private final Validator validator;

    public DriverRouteServiceImpl(DriverRouteRepository driverRouteRepository, DriverRepository driverRepository, Validator validator) {
        this.driverRoutesRepository = driverRouteRepository;
        this.driverRepository = driverRepository;
        this.validator = validator;
    }

    @Override
    public List<DriverRoute> getAll() {
        return driverRoutesRepository.findAll();
    }

    @Override
    public List<DriverRoute> getAllByDriverId(Long driverId) {
        return  driverRoutesRepository.findByDriverId(driverId);
    }
    @Override
    public DriverRoute getById(Long postId) {
        return driverRoutesRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException(ENTITY, postId));
    }

    @Override
    public Page<DriverRoute> getAllByDriverId(Long driverId, Pageable pageable) {
        return driverRoutesRepository.findByDriverId(driverId, pageable);
    }




    @Override
    @Transactional
    public DriverRoute create(Long driverId, DriverRoute request) {
        Set<Traveler>passengers=new HashSet<>();
        Set<ConstraintViolation<DriverRoute>> violations=validator.validate(request);
        if(!violations.isEmpty()){
            throw new ResourceValidationException(ENTITY,violations);
        }

        return driverRepository.findById(driverId).map(driverRoute -> {
            request.setDriver(driverRoute);
            request.setPassengers(passengers);
            return driverRoutesRepository.save(request);
        }).orElseThrow(()-> new ResourceNotFoundException("Driver",driverId));
    }

    @Override
    @Transactional
    public DriverRoute update(Long driverId, Long driverRouteId, DriverRoute request) {
        Set<ConstraintViolation<DriverRoute>>violations=validator.validate(request);
        if(!violations.isEmpty()){
            throw new ResourceValidationException(ENTITY,violations);
        }
        if (!driverRepository.existsById(driverId))
            throw new ResourceNotFoundException("Post",driverId);
        return driverRoutesRepository.findById(driverRouteId).map(comment ->
                        driverRoutesRepository.save(comment
                                .withDestiny(request.getDestiny())
                                .withSeating(request.getSeating())
                                .withStarting_point(request.getStarting_point())
                                .withDeparture_time(request.getDeparture_time())
                                .withDeparture_date(request.getDeparture_date())
                                .withCost(request.getCost())
                                .withPassengers(request.getPassengers())
                        ))
                .orElseThrow(()->new ResourceNotFoundException(ENTITY,driverId));
    }

    @Override
    @Transactional
    public ResponseEntity<?> delete(Long driverId, Long driverRouteId) {
        return driverRoutesRepository.findByIdAndDriverId(driverId,driverRouteId).map(comment ->
        {
            driverRoutesRepository.delete(comment);
            return ResponseEntity.ok().build();
        }).orElseThrow(()->new ResourceNotFoundException(ENTITY,driverRouteId));

    }
}
