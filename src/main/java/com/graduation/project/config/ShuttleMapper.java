package com.graduation.project.config;

import org.springframework.beans.factory.annotation.Autowired;

import com.graduation.project.dto.ShuttleDTO;
import com.graduation.project.entity.Seat;
import com.graduation.project.entity.Shuttle;
import com.graduation.project.repository.SeatRepository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShuttleMapper {

	@Autowired 
	private SeatRepository seatRepository;
	
	public ShuttleDTO toDTO(Shuttle shuttle ) {
		ShuttleDTO dto = new ShuttleDTO();
		dto.setBusName(shuttle.getBus().getName());
		dto.setEndTime(shuttle.getEndTime());
		dto.setStartTime(shuttle.getStartTime());
		dto.setSeats(shuttle.getBus().getSeats());
		Seat seat = seatRepository.findSeatByShuttleId(shuttle.getId());
		dto.setPrice(seat.getPrice());
		return dto;
	}
}
