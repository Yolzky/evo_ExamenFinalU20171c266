package betrip.services.betrip_backend_services.BoundenContextTravelEvents.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import betrip.services.betrip_backend_services.BoundenContextTravelEvents.domain.model.entity.TravelEvent;
import betrip.services.betrip_backend_services.BoundenContextTravelEvents.domain.persistence.TravelEventRepository;
import betrip.services.betrip_backend_services.BoundenContextTravelEvents.domain.service.TravelEventService;
import betrip.services.betrip_backend_services.BoundendContextTravelers.domain.model.entity.Traveler;
import betrip.services.betrip_backend_services.BoundendContextTravelers.domain.persistence.TravelerRepository;
import betrip.services.betrip_backend_services.BoundendContextTravelers.domain.service.TravelerService;
import betrip.services.betrip_backend_services.shared.exception.ResourceNotFoundException;
import betrip.services.betrip_backend_services.shared.exception.ResourceValidationException;

@Service
@Transactional(readOnly = true)
public class TravelEventServiceImpl implements TravelEventService {
	private static final String ENTITY = "Travel-Events";
	private final TravelEventRepository travelEventsRepository;
	private final TravelerRepository travelerRepository;
	private final TravelerService travelerService;
	// validator de tipo JAVAX
	private final Validator validator;

	public TravelEventServiceImpl(TravelEventRepository travelEventsRepository, TravelerRepository travelerRepository,
			TravelerService travelerService, Validator validator) {
		this.travelEventsRepository = travelEventsRepository;
		this.travelerRepository = travelerRepository;
		this.travelerService = travelerService;
		this.validator = validator;
	}

	@Override
	public TravelEvent getById(Long postId) {
		return travelEventsRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException(ENTITY, postId));
	}

	@Override
	public List<TravelEvent> getAll() {
		return travelEventsRepository.findAll();
	}

	@Override
	public List<TravelEvent> getAllByTravelerId(Long travelerId) {
		return travelEventsRepository.findByTravelerId(travelerId);
	}

	@Override
	public Page<TravelEvent> getAllByTravelerId(Long travelerId, Pageable pageable) {
		return travelEventsRepository.findByTravelerId(travelerId, pageable);
	}

	public boolean isDangerousHour(Integer horaActual) {
		Integer hora1 = 20;
		Integer hora2 = 24;
		Integer hora3 = 4;
		if ((horaActual >= hora1 || horaActual < hora3)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	@Transactional
	public TravelEvent create(Long travelerId, TravelEvent request) {
		Set<Traveler> passengers = new HashSet<>();
		Set<ConstraintViolation<TravelEvent>> violations = validator.validate(request);
		if (!violations.isEmpty()) {
			throw new ResourceValidationException(ENTITY, violations);
		}

		// Traveler creatorTraveler=travelerRepository.getById(travelerId);
		Integer horadesalida = Integer.parseInt(request.getDeparture_time());
		Integer hora1 = 20;
		Integer hora2 = 24;
		Integer hora3 = 4;

		if (travelerService.isTravelerDangerous(travelerId) && isDangerousHour(horadesalida)) {
			throw new ResourceValidationException(ENTITY, "User is considered dangerous");
		}

		return travelerRepository.findById(travelerId).map(travelEvent -> {
			request.setTraveler(travelEvent);
			request.setPassengers(passengers);
			return travelEventsRepository.save(request);
		}).orElseThrow(() -> new ResourceNotFoundException("Post", travelerId));
	}

	@Override
	@Transactional
	public TravelEvent update(Long travelerId, Long travelEventId, TravelEvent request) {
		Set<ConstraintViolation<TravelEvent>> violations = validator.validate(request);
		// Set<Traveler> travelers =
		// request.getPassengers().stream().collect(Collectors.toSet());
		if (!violations.isEmpty()) {
			throw new ResourceValidationException(ENTITY, violations);
		}
		if (!travelerRepository.existsById(travelerId))
			throw new ResourceNotFoundException("Post", travelerId);
		return travelEventsRepository.findById(travelEventId)
				.map(comment -> travelEventsRepository.save(comment.withDriverId(request.getDriverId())
						.withDestiny(request.getDestiny()).withDestinyUrl(request.getDestinyUrl())
						.withSeating(request.getSeating()).withStarting_point(request.getStarting_point())
						.withDeparture_time(request.getDeparture_time()).withDeparture_date(request.getDeparture_date())
						.withCost(request.getCost()).withType(request.getType()).withPlate(request.getPlate())
						.withTravelerProfilePhotofUrl(request.getTravelerProfilePhotofUrl())
						.withPassengers(request.getPassengers())))
				.orElseThrow(() -> new ResourceNotFoundException(ENTITY, travelerId));
	}

	@Override
	@Transactional
	public ResponseEntity<?> delete(Long travelerId, Long travelEventId) {
		return travelEventsRepository.findByIdAndTravelerId(travelerId, travelEventId).map(comment -> {
			travelEventsRepository.delete(comment);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException(ENTITY, travelEventId));

	}
}
