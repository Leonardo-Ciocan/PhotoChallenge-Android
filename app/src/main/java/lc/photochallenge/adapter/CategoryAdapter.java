package lc.photochallenge.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import lc.photochallenge.R;
import lc.photochallenge.SquareView;
import lc.photochallenge.models.Category;

public class CategoryAdapter  extends ArrayAdapter<Category> {
    public CategoryAdapter(Context context , ArrayList<Category> books){
        super(context , R.layout.category_item , books);
    }

    @Bind(R.id.name)
    TextView name;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
           // LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           // convertView = inflater.inflate( R.layout.book_tile , null);
           convertView = new SquareView(getContext() , R.layout.category_item);
        }

        ButterKnife.bind(this, convertView);

        name.setText(getItem(position).getName());

        return convertView;
    }
}
