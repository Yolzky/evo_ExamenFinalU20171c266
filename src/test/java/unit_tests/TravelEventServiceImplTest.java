package unit_tests;

import static org.assertj.core.api.Assertions.catchThrowable;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import betrip.services.betrip_backend_services.BoundenContextTravelEvents.domain.model.entity.TravelEvent;
import betrip.services.betrip_backend_services.BoundenContextTravelEvents.domain.persistence.TravelEventRepository;
import betrip.services.betrip_backend_services.BoundenContextTravelEvents.domain.service.TravelEventService;
import betrip.services.betrip_backend_services.BoundendContextDrivers.domain.model.entity.Driver;
import betrip.services.betrip_backend_services.BoundendContextDrivers.domain.service.DriverService;
import betrip.services.betrip_backend_services.BoundendContextTravelers.domain.model.entity.Traveler;
import betrip.services.betrip_backend_services.BoundendContextTravelers.domain.service.TravelerService;

@ExtendWith(SpringExtension.class)
public class TravelEventServiceImplTest{
	
	@MockBean
	private TravelEventService travelEventService;
	
	@MockBean
	private TravelEventRepository travelEventRepository;
	
	@MockBean
	private DriverService driverService;
	
	@MockBean
	private TravelerService travelerService;
	
	@Test
	@DisplayName("When travel event is running there must be a free seat for a new passenger to reserve it")
	public void  WhenTravelEventIsRunningThereMustBeAFreeSeatForANewPassengerToReserveIt () {
		//Arrange
		//Instancia Driver Test
		Long idDriver=1L;
		Long numberSeatsTest = 4L;
		int puntuacionTest =5;
		Driver driver = new Driver();
		driver.setId(idDriver);
		driver.setNumber_seats(numberSeatsTest);
		driver.setPuntuacion(puntuacionTest);
		//Instancia Traveler1		
		Long IdTraveler1 =2L;
		int puntuacionTraveler1=5;
		Traveler traveler1 = new Traveler();
		traveler1.setId(IdTraveler1);
		traveler1.setPuntuacion(puntuacionTraveler1);
		//Instancia Traveler2	
		Long IdTraveler2 =3L;
		int puntuacionTraveler2=5;
		Traveler traveler2 = new Traveler();
		traveler1.setId(IdTraveler2);
		traveler1.setPuntuacion(puntuacionTraveler2);
		//Instancia de TravelEvent
		
		TravelEvent travelEvent = new TravelEvent();
		Long travelEventId = 4L;
		travelEvent.setId(travelEventId);
		travelEvent.setDriverId(idDriver);
		travelEvent.setSeating(numberSeatsTest);
		//Act
		//Creando Travel Event
		travelEventService.create(IdTraveler1, travelEvent);
		//Añadiendo 2do pasajero
		travelEventService.update(IdTraveler2, travelEvent.getId(), travelEvent);
		
		Throwable exception = catchThrowable(() -> {
			TravelEvent foundTravelEvent=travelEventService.getById(travelEventId);
        });
		//Assert
		AssertionsForClassTypes.assertThat(exception).isEqualTo(null);
		
	}
}