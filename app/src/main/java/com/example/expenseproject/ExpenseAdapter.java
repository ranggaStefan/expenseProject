package com.example.expenseproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.MyViewHolder>{
    private Context context;
    private List<ExpenseTable> expenseTableList;
    private ClickEvent clickevent;

    public ExpenseAdapter(Context context, ClickEvent clickevent) {
        this.context = context;
        expenseTableList = new ArrayList<>();
        this.clickevent = clickevent;
    }

    public void add (ExpenseTable expenseTable){
        expenseTableList.add(expenseTable);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ExpenseTable expenseTable = expenseTableList.get(position);
        holder.date.setText(expenseTable.getExpenseDate().toString());
        holder.category.setText(expenseTable.getExpenseCategory());
        holder.desc.setText(expenseTable.getExpenseDescription());
        holder.amount.setText("- Rp" + String.format("%,d",expenseTable.getExpenseAmount()));

        if (expenseTable.getExpenseCategory().equals("Transportasi"))
            holder.icon.setImageResource(R.drawable.transport);
        else if (expenseTable.getExpenseCategory().equals("Makanan")) {
            holder.icon.setImageResource(R.drawable.food);
        }else if (expenseTable.getExpenseCategory().equals("Shopping")) {
            holder.icon.setImageResource(R.drawable.shopping);
        }else if (expenseTable.getExpenseCategory().equals("Tagihan")) {
            holder.icon.setImageResource(R.drawable.file_invoice_solid);
        }
        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickevent.OnClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return expenseTableList.size();
    }

    public int getId(int pos) {
        return expenseTableList.get(pos).getId();
    }

    public void delete(int pos){
        expenseTableList.remove(pos);
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView date, category, desc, amount;
        ImageView icon, deleteItem;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.itemDate);
            category = itemView.findViewById(R.id.itemCategory);
            desc = itemView.findViewById(R.id.itemDesc);
            amount = itemView.findViewById(R.id.itemAmount);
            icon = itemView.findViewById(R.id.itemIcon);
            deleteItem = itemView.findViewById(R.id.deleteItem);
        }
    }
}
