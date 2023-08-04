package com.ellies.gymsaround

import com.ellies.gymsaround.gyms.domain.Gym

object DummyGymsList {

    fun getDummyGymsList() = arrayListOf(
        Gym(0, "n0", "p0", false),
        Gym(1, "n1", "p1", false),
        Gym(2, "n2", "p2", false),
        Gym(3, "n3", "p3", false),
        Gym(4, "n4", "p4", false),
    )
}