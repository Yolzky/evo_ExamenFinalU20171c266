package unit_tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import betrip.services.betrip_backend_services.BoundendContextTravelers.domain.model.entity.Traveler;
import betrip.services.betrip_backend_services.BoundendContextTravelers.domain.persistence.TravelerRepository;
import betrip.services.betrip_backend_services.BoundendContextTravelers.domain.service.TravelerService;

@ExtendWith(SpringExtension.class)
public class TravelerServiceImplTests {

	@MockBean
	private TravelerService travelerService;

	@MockBean
	private TravelerRepository travelerRepository;

	@Test
	@DisplayName("when calification of traveler is lower than 3 Then Return Post")
	public void whenCalificationOfTravelerIsLowerThan3ThenReturnPost() {
		// ARRANGE
		Long travelerId = 1L;
		int puntuacionTest = 2;
		Traveler traveler = new Traveler();
		traveler.setId(travelerId);
		traveler.setPuntuacion(puntuacionTest);
		boolean foundDangerTraveler = travelerService.isTravelerDangerous(travelerId);
		assertThat(foundDangerTraveler).isFalse();
	}
}