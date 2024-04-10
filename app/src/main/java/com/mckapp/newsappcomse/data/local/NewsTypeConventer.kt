package com.mckapp.newsappcomse.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.mckapp.newsappcomse.domain.model.Source

@ProvidedTypeConverter
class NewsTypeConventer {

    @TypeConverter
    fun sourceToString(source: Source) : String{
        return "${source.id},${source.name}"
    }

    //String ifade , den böl dedik split list döndürdüğü için ifadeleri alabiliriz.
    @TypeConverter
    fun stringToSource(source: String) : Source{
        return source.split(",").let { sourceArray->
            Source(sourceArray[0],sourceArray[1])
        }
    }
}