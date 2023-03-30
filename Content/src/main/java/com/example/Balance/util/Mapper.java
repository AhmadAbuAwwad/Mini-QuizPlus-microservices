package com.example.Balance.util;


import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Mapper {

    private MapperFactory factory =
            new DefaultMapperFactory.Builder().build();


    /**
     * @param dest
     * @param object
     * @param <T>
     * @return
     */
    public <T> T map(Class dest, Object object) {
        if (object == null) {
            return null;
        }
        MapperFacade mapper = factory.getMapperFacade();
        return (T) mapper.map(object, dest);
    }

    /**
     * @param dest
     * @param objects
     * @param <T>
     * @return
     */
    public <T> List<T> mapAsList(Class dest, List<Object> objects) {
        if (objects == null) {
            return null;
        }
        MapperFacade mapper = factory.getMapperFacade();
        return (List<T>) mapper.mapAsList(objects, dest);
    }
}
