package com.example.findingfalcone.gamenetwork

import com.example.findingfalcone.gamedata.*
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

class GameNetwork {

    companion object{
        @Volatile private var gameNetworkInstance: IGameNetwork? = null
        private const val baseUrl = "https://ﬁndfalcone.herokuapp.com"
        fun getNetworkInstance(): IGameNetwork {
            synchronized(GameNetwork::class) {
                return gameNetworkInstance ?: run {
                    val retroFitService = Retrofit.Builder().baseUrl(baseUrl).addCallAdapterFactory(
                        RxJava2CallAdapterFactory.create()
                    )
                    retroFitService.build().create(IGameNetwork::class.java) as IGameNetwork
                }
            }
        }
    }

    fun getAllPlanets(): Observable<List<PlanetsItem>>{
        return getNetworkInstance().getAvailablePlanets().concatMap { responseBody ->
            Observable.create { planetsEmitter ->
                responseBody.checkResponse(ListSerializer(PlanetsItem.serializer()),planetsEmitter)
            }
        }
    }

    fun getAllVehicles(): Observable<List<VehiclesItem>>{
        return getNetworkInstance().getAvailablePlanets().concatMap { responseBody ->
            Observable.create { vehiclesEmitter ->
                responseBody.checkResponse(ListSerializer(VehiclesItem.serializer()),vehiclesEmitter)
            }
        }
    }

    fun findAlFalcone(searchBody: SearchBody): Observable<GameResponse>{
        return getNetworkInstance().getAccessToken().concatMap { responseBody ->
            Observable.create<Token> { tokenEmitter ->
               responseBody.checkResponse(Token.serializer(),tokenEmitter)
            }.concatMap {
                it.token?.let { tokenValue ->
                    searchBody.token = tokenValue
                }
                val requestJsonBody = Json.encodeToString(SearchBody.serializer(),searchBody)
                val requestBody = RequestBody.create(MediaType.parse("application/json"),requestJsonBody)
                getNetworkInstance().findAlFalcone(requestBody).concatMap {
                    Observable.create { gameResponseEmitter ->
                        responseBody.checkResponse(GameResponse.serializer(),gameResponseEmitter)
                    }
                }
            }
        }
    }



    private fun <T: Any> String.getModelData(deSerializer: KSerializer<T>, emitter : ObservableEmitter<T>){
        try{
           val data = Json.decodeFromString(deSerializer,this)
            emitter.onNext(data)
        }catch (e: Exception){
            emitter.onError(e)
        }
    }

    private fun<T: Any> Response<ResponseBody>.checkResponse(deSerializer: KSerializer<T>, emitter : ObservableEmitter<T>){
        if(this.isSuccessful){
            val responseData = this.body()?.string()
            responseData?.getModelData(deSerializer,emitter)
        }else{
           emitter.onError(Exception("Network Error"))
        }
    }


}