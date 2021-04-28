package studio.eyesthetics.sbdelivery.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import studio.eyesthetics.sbdelivery.data.database.entities.SuggestionEntity

@Dao
interface SuggestionsDao : BaseDao<SuggestionEntity> {
    @Query("SELECT * FROM suggestions")
    fun getSuggestions(): LiveData<List<SuggestionEntity>>

    @Delete
    fun deleteSuggestion(suggestion: SuggestionEntity)
}