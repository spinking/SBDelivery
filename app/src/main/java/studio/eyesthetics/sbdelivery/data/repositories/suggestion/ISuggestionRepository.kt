package studio.eyesthetics.sbdelivery.data.repositories.suggestion

import androidx.lifecycle.LiveData
import studio.eyesthetics.sbdelivery.data.database.entities.SuggestionEntity

interface ISuggestionRepository {
    fun insertSuggestion(suggestion: SuggestionEntity)
    fun getSuggestions(): LiveData<List<SuggestionEntity>>
    fun deleteSuggestion(suggestion: SuggestionEntity)
}