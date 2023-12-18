package com.graduation.project.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.graduation.project.common.ConstraintMSG;
import com.graduation.project.entity.Brand;
import com.graduation.project.entity.Bus;
import com.graduation.project.entity.Schedule;
import com.graduation.project.entity.Seat;
import com.graduation.project.entity.Shuttle;
import com.graduation.project.entity.Status;
import com.graduation.project.payload.request.DataCreateScheduleRequest;
import com.graduation.project.payload.request.ScheduleRequest;
import com.graduation.project.payload.response.APIResponse;
import com.graduation.project.payload.response.ScheduleResponse;
import com.graduation.project.payload.response.ScheduleResponseForDropDown;
import com.graduation.project.repository.BrandRepository;
import com.graduation.project.repository.BusRepository;
import com.graduation.project.repository.ScheduleRepository;
import com.graduation.project.repository.SeatRepository;
import com.graduation.project.repository.ShuttleRepository;
import com.graduation.project.repository.StatusRepository;
import com.graduation.project.service.ScheduleService;

@Service
public class ScheduleServiceImpl implements ScheduleService {

	@Autowired
	private ScheduleRepository scheduleRepository;

	@Autowired
	private ShuttleRepository shuttleRepository;

	@Autowired
	private BusRepository busRepository;

	@Autowired
	private SeatRepository seatRepository;

	@Autowired
	private BrandRepository brandRepository;

	@Autowired
	private StatusRepository statusRepository;

	@Override
	public APIResponse createSchedule(DataCreateScheduleRequest request) {
		APIResponse response = new APIResponse();
		try {
			createItemSchedule(request, request.getListDateStart(),request.getShuttleId());
			if(!request.getListDateStartReturn().isEmpty()) {
				createItemSchedule(request, request.getListDateStartReturn(),request.getShuttleReturnId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	public void createItemSchedule(DataCreateScheduleRequest request,List<LocalDate> dates,Integer shuttleId) {
		Schedule schedule = null;
		for(LocalDate date : dates) {
			schedule = new Schedule();
			Bus bus = busRepository.findById(request.getBusId()).orElse(null);
			schedule.setBus(bus);
			Shuttle shuttle = shuttleRepository.findById(shuttleId).orElse(null);
			schedule.setShuttle(shuttle);
			schedule.setDateStart(date);
			scheduleRepository.save(schedule);
			createTicketInSchedule(bus, schedule, request.getPrice(), request.getEatingFee());
		}
	}

	public void deleteListTicket(Integer scheduleId) {
		seatRepository.deleteAllTicketWithScheduleId(scheduleId);
	}
	public void createTicketInSchedule(Bus bus, Schedule schedule, Double priceTicket, Double eatingFee) {
		for (int i = 0; i < bus.getSeats(); i++) {
			Seat seat = new Seat();
			seat.setSchedule(schedule);
			Status status = statusRepository.findById(ConstraintMSG.STATUS_INITIALIZED).orElse(null);
			seat.setStatus(status);
			seat.setPrice(priceTicket);
			seat.setEatingFee(eatingFee);
			if (bus.getType().getType().equals("PHONG")) {
				if (i < bus.getSeats() / 2) {
					seat.setName("A" + (i + 1));
				} else {
					seat.setName("B" + (i - bus.getSeats() / 2 + 1));
				}
			} else {
				if (i < bus.getSeats() / 3) {
					seat.setName("A" + (i + 1));
				} else if (i >= bus.getSeats() / 3 && i < 2 * bus.getSeats() / 3) {
					seat.setName("B" + (i - bus.getSeats() / 3 + 1));
				} else {
					seat.setName("C" + (i - 2 * bus.getSeats() / 3 + 1));
				}
			}
			seatRepository.save(seat);
		}
	}

	@Override
	public APIResponse updateSchedule(ScheduleRequest request) {
		APIResponse response = new APIResponse();
		try {
			Schedule schedule = scheduleRepository.findById(request.getId()).orElse(null);
			Bus bus = busRepository.findById(request.getBusId()).orElse(null);
			if (!schedule.getBus().equals(bus)) {
				schedule.setBus(bus);
				deleteListTicket(request.getId());
				createTicketInSchedule(bus,schedule,request.getPrice(),request.getEatingFee());
			}
			scheduleRepository.save(schedule);
			List<Seat> seats = seatRepository.findBySchedule(schedule);
			for (Seat seat : seats) {
				seat.setPrice(request.getPrice());
				seat.setEatingFee(request.getEatingFee());
				seatRepository.save(seat);
			}
			response.setData(schedule);
			response.setMessage(ConstraintMSG.UPDATE_DATA_MSG);
			response.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public APIResponse getSchedules(Integer routeId) {
		APIResponse response = new APIResponse();
		List<ScheduleResponse> scheduleResponses = scheduleRepository.findScheduleInRoute(routeId);
		response.setData(scheduleResponses);
		response.setMessage(ConstraintMSG.GET_DATA_MSG);
		response.setSuccess(true);
		return response;
	}

	@Override
	public List<ScheduleResponseForDropDown> getScheduleByTravelDate(LocalDate dateStart, Integer userId) {
		Brand brand = brandRepository.findByUserId(userId);
		List<ScheduleResponseForDropDown> list = scheduleRepository.findScheduleByTravelDate(dateStart, brand.getId());
		return list;
	}

}
