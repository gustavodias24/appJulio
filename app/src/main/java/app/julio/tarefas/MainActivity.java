package app.julio.tarefas;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import app.julio.tarefas.databinding.ActivityMainBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding mainBinding;
    Services sv;
    RecyclerView rv;
    List<TaskModel> lista = new ArrayList<>();

    Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        sv = RetrofitUitls.createServices(
                RetrofitUitls.createRetrofit()
        );

        rv  = mainBinding.rv;
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setHasFixedSize(true);
        adapter = new Adapter(lista, this);
        rv.setAdapter(adapter);

        atualizarList();

        mainBinding.addTask.setOnClickListener(v ->
            sv.createTask(new TaskModel(mainBinding.edtTask.getText().toString())).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    atualizarList();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable throwable) {

                }
            })
        );

    }

    public void atualizarList(){
        sv.getTask().enqueue(new Callback<List<TaskModel>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<List<TaskModel>> call, Response<List<TaskModel>> response) {
                lista.clear();
                if (response.isSuccessful()){
                    lista.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<TaskModel>> call, Throwable throwable) {

            }
        });
    }
}