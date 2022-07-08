package integration_tests;

import static org.junit.Assert.assertEquals;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import betrip.services.betrip_backend_services.BetripBackendServicesApplication;
import betrip.services.betrip_backend_services.BoundenContextTravelEvents.domain.model.entity.TravelEvent;
import betrip.services.betrip_backend_services.BoundenContextTravelEvents.domain.service.TravelEventService;
import betrip.services.betrip_backend_services.BoundendContextTravelers.domain.model.entity.Traveler;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BetripBackendServicesApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class TravelEventControlerIntegrationTests{
	
	@MockBean
	TravelEventService travelEventService;	
	
	@Test
	public void TestTIN01() throws JSONException{
		
		//Instancia Traveler con PUNTUACION 2	
		Long IdTraveler =1L;
		int puntuacionTraveler=2;
		Traveler traveler = new Traveler();
		traveler.setId(IdTraveler);
		traveler.setPuntuacion(puntuacionTraveler);
		
					
		//Instancia de TravelEvent
		Long numberSeatsTest = 6L;
		TravelEvent travelEvent = new TravelEvent();
		Long travelEventId = 4L;
		travelEvent.setId(travelEventId);
		travelEvent.setSeating(numberSeatsTest);
		
		try {
			TravelEvent foundTravelEvent=(travelEventService.create(IdTraveler, travelEvent));
		}catch(Exception e) {
			assertEquals("User is considered dangerous",e.getMessage());
		}
	}
}