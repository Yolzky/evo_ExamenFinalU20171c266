package betrip.services.betrip_backend_services.BoundendContextDriverRoutes.resource;

import betrip.services.betrip_backend_services.BoundendContextTravelers.domain.model.entity.Traveler;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public class DriverRouteResource {

    private Long id;
    private  Long driverId;
    private String destiny;
    private  Long seating;
    private String starting_point;
    private String departure_time;
    private String departure_date;
    private  Long cost;

    private Set<Traveler> passengers;
}
