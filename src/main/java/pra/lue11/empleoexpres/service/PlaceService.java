package pra.lue11.empleoexpres.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pra.lue11.empleoexpres.model.Place;
import pra.lue11.empleoexpres.repository.PlaceRepository;

import java.util.List;

/**
 * @author luE11 on 21/07/23
 */
@Service
@AllArgsConstructor
public class PlaceService {

    private PlaceRepository placeRepository;

    public Place findPlaceById(int id){
        return placeRepository.findById(id)
                .orElse(null);
    }

    public List<Place> getAllPlaces(){
        return placeRepository.findAll();
    }

    public Place getPlaceById(Integer placeId) {
        return placeId!=null ? placeRepository.findById(placeId).orElse(null) : null;
    }
}
