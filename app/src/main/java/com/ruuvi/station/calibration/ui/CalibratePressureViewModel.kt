package com.ruuvi.station.calibration.ui

import androidx.lifecycle.ViewModel
import com.ruuvi.station.calibration.domain.CalibrationInteractor
import com.ruuvi.station.calibration.model.CalibrationInfo
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CalibratePressureViewModel (
    val sensorId: String,
    val calibrationInteractor: CalibrationInteractor
) : ViewModel(), ICalibrationViewModel {
    override val calibrationInfoFlow: Flow<CalibrationInfo>
        get() = flow {
            while (true) {
                val data = calibrationInteractor.getPressureCalibrationInfo(sensorId)
                if (data != null) emit(data)
                delay(500)
            }
        }.flowOn(Dispatchers.IO)

    override fun getUnit(): String = calibrationInteractor.getPressureUnit()

    override fun calibrateTo(targetValue: Double) {
        calibrationInteractor.calibratePressure(sensorId, targetValue)
    }

    override fun clearCalibration() {
        calibrationInteractor.clearPressureCalibration(sensorId)
    }

    override fun getStringForValue(value: Double): String = calibrationInteractor.getPressureString(value)
}