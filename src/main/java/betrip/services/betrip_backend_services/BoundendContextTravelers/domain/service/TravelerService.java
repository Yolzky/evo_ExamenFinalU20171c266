package betrip.services.betrip_backend_services.BoundendContextTravelers.domain.service;

import betrip.services.betrip_backend_services.BoundendContextTravelers.domain.model.entity.Traveler;
import betrip.services.betrip_backend_services.BoundendContextTravelers.resource.AuthenticateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TravelerService  {
    List<Traveler>getAll();
    Page<Traveler> getAll(Pageable pageable);
    Traveler getById(Long travelerId);
    Traveler create(Traveler traveler);
    Traveler update(Long travelerId,Traveler request);
    ResponseEntity<?> delete(Long travelerId);
    Traveler authenticate (AuthenticateRequest request);
    boolean isTravelerDangerous(Long travelerId);
}
