package ru.myitschool.work.ui.profile

import kotlinx.serialization.Serializable
import ru.myitschool.work.ui.qr.scan.QrScanFragment


@Serializable
object ProfileDestination {

    fun newInstance(): ProfileFragment {
        return ProfileFragment()
    }
}