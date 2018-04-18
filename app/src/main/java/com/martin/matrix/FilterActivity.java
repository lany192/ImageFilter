package com.martin.matrix;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
        setContentView(R.layout.activity_color_filter);

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
    }

    private void inItFilters() {
        filters.add(new FilterInfo("黑白", ColorFilter.colormatrix_heibai));
        filters.add(new FilterInfo("怀旧", ColorFilter.colormatrix_huajiu));
        filters.add(new FilterInfo("哥特", ColorFilter.colormatrix_gete));
        filters.add(new FilterInfo("淡雅", ColorFilter.colormatrix_danya));
        filters.add(new FilterInfo("蓝调", ColorFilter.colormatrix_landiao));
        filters.add(new FilterInfo("光晕", ColorFilter.colormatrix_guangyun));
        filters.add(new FilterInfo("梦幻", ColorFilter.colormatrix_menghuan));
        filters.add(new FilterInfo("酒红", ColorFilter.colormatrix_jiuhong));
        filters.add(new FilterInfo("胶片", ColorFilter.colormatrix_fanse));
        filters.add(new FilterInfo("湖光掠影", ColorFilter.colormatrix_huguang));
        filters.add(new FilterInfo("褐片", ColorFilter.colormatrix_hepian));
        filters.add(new FilterInfo("复古", ColorFilter.colormatrix_fugu));
        filters.add(new FilterInfo("泛黄", ColorFilter.colormatrix_huan_huang));
        filters.add(new FilterInfo("传统", ColorFilter.colormatrix_chuan_tong));
        filters.add(new FilterInfo("胶片2", ColorFilter.colormatrix_jiao_pian));
        filters.add(new FilterInfo("锐色", ColorFilter.colormatrix_ruise));
        filters.add(new FilterInfo("清宁", ColorFilter.colormatrix_qingning));
        filters.add(new FilterInfo("浪漫", ColorFilter.colormatrix_langman));
        filters.add(new FilterInfo("夜色", ColorFilter.colormatrix_yese));
    }
}
