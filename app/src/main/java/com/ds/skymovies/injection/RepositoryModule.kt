package com.ds.skymovies.injection

import com.ds.skymovies.repository.INetworkRepository
import com.ds.skymovies.repository.NetworkRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<INetworkRepository> {
        NetworkRepository(get())
    }
}
