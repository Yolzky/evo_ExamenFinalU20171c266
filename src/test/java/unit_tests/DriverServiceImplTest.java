package unit_tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import betrip.services.betrip_backend_services.BoundendContextDrivers.domain.model.entity.Driver;
import betrip.services.betrip_backend_services.BoundendContextDrivers.domain.persistence.DriverRepository;
import betrip.services.betrip_backend_services.BoundendContextDrivers.domain.service.DriverService;

@ExtendWith(SpringExtension.class)
public class DriverServiceImplTest{
	
	@MockBean
	private DriverService driverService;
	
	@MockBean
	private DriverRepository driverRepository;
	
	@Test
	@DisplayName("When Driver calification is lower than 3 Then Return Post")
	public void WhenDriverCalificationIsLowerThan3ThenReturnPost() {
		Long id=1L;
		int puntuacionTest = 2;
		int puntuacionMaxima=3;
		Driver driver=new Driver();
		driver.setId(id);
		driver.setPuntuacion(puntuacionTest);
		when(driverRepository.findById(id)).thenReturn(Optional.of(driver));
		//ACT
		assertThat(driver.getPuntuacion()).isLessThanOrEqualTo(puntuacionMaxima);
	}	
	
}