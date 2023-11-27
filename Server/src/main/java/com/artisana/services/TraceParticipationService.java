package com.artisana.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.artisana.models.Product;
import com.artisana.models.TraceParticipation;
import com.artisana.repositories.TraceParticipationRepository;

@Service
public class TraceParticipationService {

	@Autowired
	private TraceParticipationRepository tracesParticipationRepository;

	public void tasjil(Long product, Long loggedUser, Double amount) {
		tracesParticipationRepository.sajel(product, loggedUser, amount);
	}

	public void save(TraceParticipation p) {
		tracesParticipationRepository.save(p);
	}

	public TraceParticipation findOne(Product p) {
		Optional<TraceParticipation> OptParticipationt = tracesParticipationRepository.findByProduct(p);
		if (OptParticipationt.isPresent()) {
			return OptParticipationt.get();

		} else
			return null;
	}

	public Double getNumberOfParticipation(Long id) {

		Double sum = tracesParticipationRepository.getNumberOfParticipation(id);
		return sum != null ? sum : null;
	}

	public TraceParticipation lastParticipation(Long id) {
		TraceParticipation OptParticipationt = tracesParticipationRepository.lastParticipation(id);
		if (OptParticipationt != null) {
			return OptParticipationt;
		} else
			return null;
	}
	
	public List<TraceParticipation> getPreviousParticipations(Long id)
	{
		return tracesParticipationRepository.getPreviousParticipations(id) != null 
				? tracesParticipationRepository.getPreviousParticipations(id)
				: null;
	}
	
	public void delete(Long id)
	{
		tracesParticipationRepository.deleteById(id);
	}
	
	public void deleteByProduct(Product p)
	{
		tracesParticipationRepository.deleteByProduct(p);
	}

	public void deleteAllByProduct(Long id) {
		tracesParticipationRepository.deleteAllTheProducts(id);
		
	}
}
