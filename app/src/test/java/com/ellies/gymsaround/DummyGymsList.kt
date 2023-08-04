package com.ellies.gymsaround

import com.ellies.gymsaround.gyms.data.remote.RemoteGym
import com.ellies.gymsaround.gyms.domain.Gym

object DummyGymsList {

    fun getDummyGymsList() = arrayListOf(
        RemoteGym(0, "n0", "p0", false),
        RemoteGym(1, "n1", "p1", false),
        RemoteGym(2, "n2", "p2", false),
        RemoteGym(3, "n3", "p3", false),
        RemoteGym(4, "n4", "p4", false),
    )

    fun getDomainDummyGymsList() = buildList {
        add(Gym(0, "n0", "p0", false))
        add(Gym(1, "n1", "p1", false))
        add(Gym(2, "n2", "p2", false))
        add(Gym(3, "n3", "p3", false))
        add(Gym(4, "n4", "p4", false))
    }
}