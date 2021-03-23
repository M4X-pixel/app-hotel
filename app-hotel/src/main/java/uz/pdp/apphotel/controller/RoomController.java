package uz.pdp.apphotel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.pdp.apphotel.entity.Hotel;
import uz.pdp.apphotel.entity.Room;
import uz.pdp.apphotel.model.RoomDto;
import uz.pdp.apphotel.repository.HotelRepository;
import uz.pdp.apphotel.repository.RoomRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    HotelRepository hotelRepository;

    @GetMapping("/byHotelId/{hotelId}")
    public Page<Room> getRoomPagesByHotelId(@PathVariable Integer hotelId, @RequestParam int page){
        Pageable pageable = PageRequest.of(page, 12);
       return roomRepository.findAllByHotelId(hotelId,pageable);
    }

    @GetMapping
    public List<Room> getRooms(){
        return roomRepository.findAll();
    }

    @PostMapping
    public String addRoom(@RequestBody RoomDto roomDto){
        Optional<Hotel> optionalHotel = hotelRepository.findById(roomDto.getHotelId());
        if (!optionalHotel.isPresent()) return "hotel not found";
        Room room = new Room();
        room.setFloor(room.getFloor());
        room.setNumber(room.getNumber());
        room.setSize(roomDto.getSize());
        room.setHotel(optionalHotel.get());
        roomRepository.save(room);
        return "room saved";
    }

    @PutMapping("/{id}")
    public String editRoom(@PathVariable Integer id, @RequestBody RoomDto roomDto){
        Optional<Hotel> optionalHotel = hotelRepository.findById(roomDto.getHotelId());
        if (!optionalHotel.isPresent()) return "hotel not found";
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (!optionalRoom.isPresent()) return "Room not found";
        Room room = optionalRoom.get();
        room.setFloor(room.getFloor());
        room.setNumber(room.getNumber());
        room.setSize(roomDto.getSize());
        room.setHotel(optionalHotel.get());
        roomRepository.save(room);
        return "room edited";
    }
    @DeleteMapping("/{id}")
    public String deleteRoom(@PathVariable Integer id){
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (!optionalRoom.isPresent()) return "Room not found";
        roomRepository.delete(optionalRoom.get());
        return "Room deleted";
    }

}
