package by.nc.tarazenko;

import by.nc.tarazenko.dtos.GuestDTO;
import by.nc.tarazenko.entity.Guest;
import by.nc.tarazenko.service.GuestService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//import org.apache.log4j.Logger;

@RestController
public class GuestController {
    Logger logger = LoggerFactory.getLogger(GuestController.class);
    @Autowired
    private GuestService guestService;

    @RequestMapping(value = "/guests", method = RequestMethod.GET)
    public List<Guest> getAll(){
        return guestService.getAll();
    }

/*    @RequestMapping(value = "/guest/{id}", method = RequestMethod.GET)
    public Optional<GuestDTO> findById(@RequestParam("id") Optional<Integer> id){
        System.out.println(id.get());
        return guestService.getById(id.get());
    }*/

    @RequestMapping(value = "/guest", method = RequestMethod.POST)
    public  void  save(@RequestBody String body){
        GuestDTO guest = new Gson().fromJson(body, GuestDTO.class);
        logger.info(guest.toString());
        logger.info(body);
        guestService.create(guest);
    }
}
