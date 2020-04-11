package com.space.service;

import com.space.model.Ship;

import java.util.List;
import java.util.Map;

public interface ShipService {

    /**
     * Create new ship
     * @param ship - ship for creation
     */
    void create(Ship ship);

    /**
     * Return the list of all ships
     * @return ships list
     */
    List<Ship> readAll();
    List<Ship> readAll(Map<String, String> allParams);

    /**
     * Return ship by ID
     * @param id - ship ID
     * @return - ship object with given ID
     */
    Ship read(long id);

    boolean isExistByID(long id);

    /**
     * Return ships count
     * @return - ships count
     */
    long count();

    /**
     * Updates the ship with the given ID,
     * in accordance with the transferred client
     * @param ship - the ship according to which you need to update data
     * @param id - id of the ship whose update you want
     * @return - true if the data has been updated, otherwise false
     */
    boolean update(Ship ship, long id);

    /**
     * Deletes the ship with the given ID
     * @param id - id of the ship to be deleted
     * @return - true if the ship was deleted, otherwise false
     */
    boolean delete(long id);

}
