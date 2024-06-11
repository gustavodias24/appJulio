package app.julio.tarefas;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    List<TaskModel> lista;
    Activity a;

    public Adapter(List<TaskModel> lista, Activity a) {
        this.lista = lista;
        this.a = a;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_task, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.textInfo.setText(lista.get(position).getName());
        holder.btnDelete.setOnClickListener(v -> {
            RetrofitUitls.createServices(
                    RetrofitUitls.createRetrofit()
            ).deleteTask(
                    lista.get(position).get_id()
            ).enqueue(new Callback<Void>() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    lista.remove(position);
                    notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable throwable) {

                }
            });

        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textInfo;
        Button btnDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textInfo = itemView.findViewById(R.id.textInfo);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
