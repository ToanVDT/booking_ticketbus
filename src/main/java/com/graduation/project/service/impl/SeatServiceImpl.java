package com.graduation.project.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.graduation.project.common.ConstraintMSG;
import com.graduation.project.common.Utility;
import com.graduation.project.dto.SeatDTO;
import com.graduation.project.entity.Schedule;
import com.graduation.project.entity.Seat;
import com.graduation.project.entity.Ticket;
import com.graduation.project.payload.response.APIResponse;
import com.graduation.project.payload.response.SeatEmptyResponse;
import com.graduation.project.payload.response.SeatResponseForCustomer;
import com.graduation.project.repository.ScheduleRepository;
import com.graduation.project.repository.SeatRepository;
import com.graduation.project.repository.TicketRepository;
import com.graduation.project.service.SeatService;

@Service
public class SeatServiceImpl implements SeatService{
	
	@Autowired
	private SeatRepository seatRepository;
	
	@Autowired
	private ScheduleRepository scheduleRepository;
	
	@Autowired
	private TicketRepository ticketRepository;

	@Override
	public APIResponse getSeatInShuttle(Integer scheduleId) {
		APIResponse response = new APIResponse();
		Schedule schedule = scheduleRepository.findById(scheduleId).orElse(null);
		List<SeatResponseForCustomer> responses = seatRepository.findSeatsBySchedule(scheduleId);
		List<List<SeatResponseForCustomer>> result = new ArrayList<>();
		if(schedule.getBus().getType().getType().equals("PHONG")) {
			result = Utility.getSeatWithTypeBus(responses,4,6);
		}
		else {
			result = Utility.getSeatWithTypeBus(responses,6,6);
		}
		response.setData(result);
		response.setMessage(ConstraintMSG.GET_DATA_MSG);
		response.setSuccess(true);
		return response;
	}

	@Override
	public List<SeatDTO> getSeatWithScheduleId(Integer scheduleId) {
		Schedule schedule = scheduleRepository.findById(scheduleId).orElse(null);
		List<Seat> seats = seatRepository.findBySchedule(schedule);
		List<SeatDTO> dtos = new ArrayList<>();
		SeatDTO dto = null;
		for(Seat seat : seats) {
			dto = new SeatDTO();
			dto.setPrice(seat.getPrice());
			dto.setEatingFee(seat.getEatingFee());
			dto.setId(seat.getId());
			dto.setSeatName(seat.getName());
			dto.setStatusTicket(seat.getStatus().getStatus());
			if(seat.getTickets().isEmpty()) {
				dto.setCustomerName("");
				dto.setCustomerPhone("");
			}
			else {
				Ticket ticket = ticketRepository.findBySeat(seat.getId());
				dto.setCustomerName(ticket.getOrder().getUser().getLastName()+' '+ticket.getOrder().getUser().getFirstName());
				dto.setCustomerPhone(ticket.getOrder().getUser().getPhoneNumber());
			}
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public SeatEmptyResponse getSeatEmpty(LocalDate dateStart,Integer scheduleId) {
		SeatEmptyResponse seatEmpty = seatRepository.findSeatEmpty(dateStart,scheduleId);
		return seatEmpty;
	}
}
