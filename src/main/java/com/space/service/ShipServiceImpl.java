package com.space.service;

import com.space.ShipHelper;
import com.space.model.Ship;
import com.space.repository.ShipsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

/*
Аннотация @Service говорит спрингу, что данный класс является сервисом.
Это специальный тип классов, в котором реализуется некоторая бизнес логика приложения.
Впоследствии, благодаря этой аннотации Spring будет предоставлять нам экземпляр данного класса в местах, где это,
нужно с помощью Dependency Injection.

https://howtodoinjava.com/hibernate/hibernate-jpa-2-persistence-annotations-tutorial/

@Column(name="FNAME",length=100,nullable=false)
private String  firstName;
*/

@Service
public class ShipServiceImpl implements ShipService {

    @Autowired
    private ShipsRepository shipsRepository;

    // create
    @Override
    public long create(Map<String, String> body) {
        ShipHelper.printMessage("DEBUG: ShipServiceImpl CREATE");
        Ship ship = ShipHelper.createNewShip(body); // get new ship object | it's not in DB yet
        shipsRepository.save(ship);                 // save new ship into DB
        long id = ship.getId();                     // id will be created automatically after saved to DB via repository
        ShipHelper.printMessage("DEBUG: new ship ENTITY created in DB:");
        ShipHelper.printMessage(ship.toString());
        return id;
    }

    // get all ships without params
    @Override
    public List<Ship> readAll() {
        List<Ship> ships = new ArrayList<>();
        ShipHelper.printMessage("DEBUG: ShipServiceImpl SHOW ALL (empty)");
        ships = shipsRepository.findAll();
        return ships;
    }

    @Override
    // get all ships with params
    // Example: Parameters are [name=www, shipType=MERCHANT, after=-62126972453848, isUsed=true, pageNumber=0, pageSize=3, order=ID]
    public List<Ship> readAll(Map<String, String> allParams) {
        ShipHelper.printMessage("DEBUG: ShipServiceImpl SHOW ALL (with params)");
        // get all ships
        List<Ship> ships = shipsRepository.findAll();
        // create correct ships list from full
        ships = ShipHelper.getShipsOnPage(ships, allParams);
        return ships;
    }

    @Override
    public Ship read(long id) {
        // not use getOne(), because it returns a reference and will be Serialisation error | not an Object.
        return shipsRepository.findById(id).get();
    }

    @Override
    public boolean isIdValid(long id) {
        return ShipHelper.isLong(String.valueOf(id));
    }

    @Override
    public boolean isBodyValid(Map<String, String> body) {
        return ShipHelper.areParamsValid(body);
    }

    @Override
    public boolean isExistByID(long id) {
        return shipsRepository.existsById(id);
    }

    @Override
    public int count(Map<String, String> allParams) {
        // get all ships
        List<Ship> ships = shipsRepository.findAll();
        // create correct ships list from full
        int count = ShipHelper.getShipsCount(ships, allParams);
        return count;
    }

    @Override
    public boolean update(long id, Map<String, String> body) {
        // get original ship
        Ship ship = shipsRepository.findById(id).get();
        // update parameters
        ship = ShipHelper.getUpdatedShip(ship, body);
        // save updated ship
        shipsRepository.save(ship);
        // return result
        return true;
    }

    @Override
    public boolean delete(long id) {
        if (shipsRepository.existsById(id)) {
            shipsRepository.deleteById(id);
            ShipHelper.printMessage(String.format("DEBUG: Ship with ID %d deleted successfully.", id));
            return true;
        }
        return false;
    }
}
