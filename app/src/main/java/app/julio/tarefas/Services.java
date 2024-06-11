package app.julio.tarefas;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Services {

    @POST("create")
    Call<Void> createTask(@Body TaskModel task);

    @DELETE("delete")
    Call<Void> deleteTask(@Query("id_task") String id_task);

    @GET("tasks")
    Call<List<TaskModel>> getTask();


}
