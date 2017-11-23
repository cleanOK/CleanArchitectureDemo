package com.dmytrod.cademo.screens;

import java.util.List;

import rx.Observable;

/**
 * Created by Dmytro Denysenko on 11/22/17.
 */

public interface PetcubeRepository {
    Observable<List<Petcube>> getPetcubes(long userId);
}
