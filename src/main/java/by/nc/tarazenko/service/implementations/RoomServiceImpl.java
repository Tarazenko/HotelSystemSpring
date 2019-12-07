package by.nc.tarazenko.service.implementations;

import by.nc.tarazenko.convector.RoomConvector;
import by.nc.tarazenko.dtos.RoomDTO;
import by.nc.tarazenko.entity.Room;
import by.nc.tarazenko.repository.RoomRepositoy;
import by.nc.tarazenko.service.RoomService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {
    private Logger logger = Logger.getLogger(RoomService.class);

    @Autowired
    RoomRepositoy roomRepositoy;

    private RoomConvector roomConvector = new RoomConvector();

    @Override
    public RoomDTO getById(int id) {
        Room room = roomRepositoy.findById(id).get();
        logger.debug(room.getFeatures());
        return roomConvector.toDTO(room);
    }

    @Override
    public List<RoomDTO> getAll() {
        List<Room> rooms = roomRepositoy.findAll();
        List<RoomDTO> roomDTOs = new ArrayList<>();
        for (Room room : rooms) {
            roomDTOs.add(roomConvector.toDTO(room));
        }
        return roomDTOs;
    }

    //todo return created
    @Override
    public void create(RoomDTO roomDTO) {
        Room room = roomConvector.fromDTO(roomDTO);
        roomRepositoy.saveAndFlush(room);
    }

    //todo return what was update
    @Override
    public boolean update(RoomDTO roomDTO) {
        Room room = roomConvector.fromDTO(roomDTO);
        boolean ok = true;
        if (roomRepositoy.findById(room.getId()).isPresent()) {
            roomRepositoy.saveAndFlush(room);
        } else {
            ok = false;
        }
        return ok;
    }

    @Override
    public boolean deleteById(int id) {
        boolean ok = true;
        if (roomRepositoy.findById(id).isPresent()) {
            roomRepositoy.deleteById(id);
        } else {
            ok = false;
        }
        return ok;
    }
}
