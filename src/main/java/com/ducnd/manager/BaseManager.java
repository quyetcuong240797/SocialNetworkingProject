package com.ducnd.manager;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by Lap trinh on 3/28/2018.
 */
@Component
public class BaseManager {
    @Autowired
    protected DSLContext dslContext;


    public DSLContext getDslContext(){
        return dslContext;
    }

}
