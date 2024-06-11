package app.julio.tarefas;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUitls {


    public static Retrofit createRetrofit(){
        return new Retrofit.Builder().baseUrl("https://api-julio.vercel.app/").addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static Services createServices(Retrofit retrofit){
        return retrofit.create(Services.class);
    }


}
