package com.example.mad

import android.app.Application
import android.database.sqlite.SQLiteException
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mad.Database.DbHelperArticles
import com.example.mad.Database.DbHelperCrop
import com.example.mad.Database.DbHelperProduct
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONException
import java.io.IOException
import java.time.Instant

data class FarmLocation(
    val name: String,
    val latitude: Double,
    val longitude: Double
) {
    companion object {
        val supported = listOf(
            FarmLocation("Colombo", 6.9271, 79.8612),
            FarmLocation("Kandy", 7.2906, 80.6337),
            FarmLocation("Galle", 6.0535, 80.2210),
            FarmLocation("Jaffna", 9.6615, 80.0255),
            FarmLocation("Anuradhapura", 8.3114, 80.4037),
            FarmLocation("Badulla", 6.9934, 81.0550)
        )
    }
}

data class CropItem(
    val name: String,
    val region: String,
    val price: String
)

data class ProductItem(
    val name: String,
    val region: String,
    val price: String
)

data class LearningItem(
    val title: String,
    val date: String,
    val description: String
)

data class FarmSnapshot(
    val crops: List<CropItem> = emptyList(),
    val products: List<ProductItem> = emptyList(),
    val articles: List<LearningItem> = emptyList(),
    val news: List<LearningItem> = emptyList()
)

data class CurrentWeather(
    val temperature: Double,
    val feelsLike: Double,
    val humidity: Int,
    val precipitation: Double,
    val windSpeed: Double,
    val code: Int
)

sealed interface WeatherState {
    data object Loading : WeatherState
    data class Ready(val weather: CurrentWeather, val updatedAt: Instant) : WeatherState
    data class Failed(val message: String) : WeatherState
}

data class DashboardState(
    val location: FarmLocation = FarmLocation.supported.first(),
    val snapshot: FarmSnapshot = FarmSnapshot(),
    val isLoadingFarm: Boolean = true,
    val farmError: String? = null,
    val weather: WeatherState = WeatherState.Loading
)

class DashboardViewModel(application: Application) : AndroidViewModel(application) {

    private val preferences = application.getSharedPreferences(PREFERENCES_NAME, 0)
    private val repository = FarmRepository(application)
    private val weatherRepository = WeatherRepository()
    private val savedLocation = FarmLocation.supported.firstOrNull {
        it.name == preferences.getString(LOCATION_KEY, null)
    } ?: FarmLocation.supported.first()

    private val _state = MutableStateFlow(DashboardState(location = savedLocation))
    val state = _state.asStateFlow()

    private var farmJob: Job? = null
    private var weatherJob: Job? = null

    fun refresh() {
        farmJob?.cancel()
        farmJob = viewModelScope.launch {
            _state.update { it.copy(isLoadingFarm = true, farmError = null) }
            try {
                val snapshot = withContext(Dispatchers.IO) { repository.loadSnapshot() }
                _state.update { it.copy(snapshot = snapshot, isLoadingFarm = false) }
            } catch (exception: SQLiteException) {
                _state.update {
                    it.copy(
                        isLoadingFarm = false,
                        farmError = "Your saved farm data could not be loaded. Please try again."
                    )
                }
            } catch (exception: CancellationException) {
                throw exception
            }
        }
        refreshWeather(_state.value.location)
    }

    fun refreshWeather(location: FarmLocation = _state.value.location) {
        weatherJob?.cancel()
        weatherJob = viewModelScope.launch {
            _state.update { it.copy(weather = WeatherState.Loading) }
            try {
                val weather = withContext(Dispatchers.IO) {
                    weatherRepository.fetchCurrent(location)
                }
                _state.update {
                    it.copy(weather = WeatherState.Ready(weather, Instant.now()))
                }
            } catch (exception: IOException) {
                _state.update {
                    it.copy(
                        weather = WeatherState.Failed(
                            "Live weather is unavailable. Check your connection and try again."
                        )
                    )
                }
            } catch (exception: JSONException) {
                _state.update {
                    it.copy(
                        weather = WeatherState.Failed(
                            "The weather service returned an unreadable response. Try again shortly."
                        )
                    )
                }
            } catch (exception: CancellationException) {
                throw exception
            }
        }
    }

    fun selectLocation(location: FarmLocation) {
        preferences.edit().putString(LOCATION_KEY, location.name).apply()
        _state.update { it.copy(location = location) }
        refreshWeather(location)
    }

    companion object {
        private const val PREFERENCES_NAME = "agri_preferences"
        private const val LOCATION_KEY = "farm_location"
    }
}

private class FarmRepository(private val application: Application) {

    fun loadSnapshot(): FarmSnapshot {
        val crops = DbHelperCrop(application).getAllCrops().map {
            CropItem(it.cropName, it.cropRegion, it.cropPrice)
        }
        val products = DbHelperProduct(application).getAllCrops().map {
            ProductItem(it.productName, it.productRegion, it.productPrice)
        }
        val database = DbHelperArticles(application)
        val articles = database.getAllArtcles().map {
            LearningItem(it.article_Title, it.article_Date, it.article_description)
        }
        val news = database.getAllNews().map {
            LearningItem(it.news_Title, it.news_Date, it.news_description)
        }
        return FarmSnapshot(crops, products, articles, news)
    }
}
