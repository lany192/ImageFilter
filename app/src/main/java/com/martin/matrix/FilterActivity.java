package com.martin.matrix;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.caimuhao.rxpicker.RxPicker;
import com.caimuhao.rxpicker.bean.ImageItem;
import com.martin.matrix.entity.FilterInfo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

public class FilterActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FiltersAdapter filtersAdapter;
    private List<FilterInfo> filters = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        recyclerView = findViewById(R.id.mRecyclerView);
        inItFilters();
        filtersAdapter = new FiltersAdapter(getLayoutInflater(), filters);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(filtersAdapter);
        findViewById(R.id.my_text_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxPicker.of().single(true)
                        .start(FilterActivity.this)
                        .subscribe(new Consumer<List<ImageItem>>() {
                            @Override
                            public void accept(@NonNull List<ImageItem> imageItems) {
                                filtersAdapter.setImagePath(imageItems.get(0).getPath());
                            }
                        });
            }
        });
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void inItFilters() {
        filters.add(new FilterInfo("黑白", ColorFilter.HEIBAI));
        filters.add(new FilterInfo("怀旧", ColorFilter.HUAJIU));
        filters.add(new FilterInfo("哥特", ColorFilter.GETE));
        filters.add(new FilterInfo("淡雅", ColorFilter.DANYA));
        filters.add(new FilterInfo("蓝调", ColorFilter.LANDIAO));
        filters.add(new FilterInfo("光晕", ColorFilter.GUANGYUN));
        filters.add(new FilterInfo("梦幻", ColorFilter.MENGHUAN));
        filters.add(new FilterInfo("酒红", ColorFilter.JIUHONG));
        filters.add(new FilterInfo("胶片", ColorFilter.JIAOPIAN1));
        filters.add(new FilterInfo("湖光掠影", ColorFilter.HUGUANG));
        filters.add(new FilterInfo("褐片", ColorFilter.HEPIAN));
        filters.add(new FilterInfo("复古", ColorFilter.FUGU));
        filters.add(new FilterInfo("泛黄", ColorFilter.FANHUANG));
        filters.add(new FilterInfo("传统", ColorFilter.CHUANTONG));
        filters.add(new FilterInfo("胶片2", ColorFilter.JIAOPIAN2));
        filters.add(new FilterInfo("锐色", ColorFilter.RUISE));
        filters.add(new FilterInfo("清宁", ColorFilter.QINGNING));
        filters.add(new FilterInfo("浪漫", ColorFilter.LANGMAN));
        filters.add(new FilterInfo("夜色", ColorFilter.YESE));
    }
}
