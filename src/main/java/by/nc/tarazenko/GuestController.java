package by.nc.tarazenko;

import by.nc.tarazenko.dtos.GuestDTO;
import by.nc.tarazenko.entity.Guest;
import by.nc.tarazenko.service.GuestService;
import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.apache.log4j.Logger;

@RestController
@RequestMapping("guests")
public class GuestController {
    Logger logger = Logger.getLogger(GuestController.class);
    @Autowired
    private GuestService guestService;

    @RequestMapping(method = RequestMethod.GET)
    public List<GuestDTO> getAll(){
        return guestService.getAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public  void  save(@RequestBody String body){
        GuestDTO guest = new Gson().fromJson(body, GuestDTO.class);
        logger.info(guest.toString());
        logger.info(body);
        guestService.create(guest);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public GuestDTO getByid(@PathVariable int id){
        return guestService.getById(id);
    }
}
