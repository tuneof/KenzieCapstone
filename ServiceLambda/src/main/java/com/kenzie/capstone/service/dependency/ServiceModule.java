package com.kenzie.capstone.service.dependency;

import com.kenzie.capstone.service.HireService;
import com.kenzie.capstone.service.dao.HireDao;

import dagger.Module;
import dagger.Provides;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Module(
    includes = DaoModule.class
)
public class ServiceModule {

    @Singleton
    @Provides
    @Inject
    public HireService provideHireService(@Named("HireDao") HireDao hireDao) {
        return new HireService(hireDao);
    }
}

