package uz.pdp.apphotel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.apphotel.entity.Hotel;
import uz.pdp.apphotel.repository.HotelRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hotel")
public class HotelController {

    @Autowired
    HotelRepository hotelRepository;

    @GetMapping
    public List<Hotel> getHotels(){
        return hotelRepository.findAll();
    }

    @PostMapping
    public String addHotel(@RequestBody Hotel hotel){
        Hotel hotel1 = new Hotel(null,hotel.getName());
        hotelRepository.save(hotel1);
        return "Hotel added";
    }

    @PutMapping("/{id}")
    public String editHotel(@PathVariable Integer id, @RequestBody Hotel newHotel){
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        if (!optionalHotel.isPresent()) return "Hotel not found";
        Hotel hotel = optionalHotel.get();
        hotel.setName(newHotel.getName());
        hotelRepository.save(hotel);
        return "Hotel edited";
    }
    @DeleteMapping("/{id}")
    public String deleteHotel(@PathVariable Integer id){
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        if (!optionalHotel.isPresent()) return "Hotel not found";
        hotelRepository.delete(optionalHotel.get());
        return "Hotel deleted";
    }
}
