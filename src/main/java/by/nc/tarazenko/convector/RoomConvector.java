package by.nc.tarazenko.convector;

import by.nc.tarazenko.dtos.RoomDTO;
import by.nc.tarazenko.entity.Feature;
import by.nc.tarazenko.entity.Room;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RoomConvector implements Convector<RoomDTO, Room> {

    @Override
    public RoomDTO toDTO(Room room) {
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setId(room.getId());
        roomDTO.setFeatures(room.getFeatures());
        roomDTO.setNumber(room.getNumber());
        double cost = 0;
        if (room.getFeatures() != null) {
            for (Feature feature : room.getFeatures()) {
                cost += feature.getCost();
            }
        }
        roomDTO.setCost(cost);
        return roomDTO;
    }

    @Override
    public Room fromDTO(RoomDTO roomDTO) {
        Room room = new Room();
        room.setId(roomDTO.getId());
        room.setNumber(roomDTO.getNumber());
        room.setFeatures(roomDTO.getFeatures());
        return room;
    }
}
