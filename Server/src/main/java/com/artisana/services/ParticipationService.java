package com.artisana.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.artisana.models.Participation;
import com.artisana.models.Product;
import com.artisana.repositories.ParticipationRepository;

@Service
public class ParticipationService {

	@Autowired
	private ParticipationRepository participationRepository;

	public void tasjil(Long product, Long loggedUser, Double amount) {
		participationRepository.sajel(product, loggedUser, amount);
	}

	public void save(Participation p) {
		participationRepository.save(p);
	}

	public Participation findOne(Product p) {
		Optional<Participation> OptParticipationt = participationRepository.findByProduct(p);
		if (OptParticipationt.isPresent()) {
			return OptParticipationt.get();

		} else
			return null;
	}

	public Double getNumberOfParticipation(Long id) {

		Double sum = participationRepository.getNumberOfParticipation(id);
		return sum != null ? sum : null;
	}

	public Participation lastParticipation(Long id) {
		Participation OptParticipationt = participationRepository.lastParticipation(id);
		if (OptParticipationt != null) {
			return OptParticipationt;
		} else
			return null;
	}
	
	public List<Participation> getPreviousParticipations(Long id)
	{
		return participationRepository.getPreviousParticipations(id);
	}
	
	public void delete(Long id)
	{
		participationRepository.deleteById(id);
	}
	
	public Participation findById(Long id)
	{
		Optional<Participation> OptParticipationt = participationRepository.findById(id);
		if (OptParticipationt != null) {
			return OptParticipationt.get();
		} else
			return null;
	}
	public Participation findByProduct(Product p)
	{
		Optional<Participation> OptParticipationt = participationRepository.findByProduct(p);
		if (OptParticipationt != null) {
			return OptParticipationt.get();
		} else
			return null;
	}
}
