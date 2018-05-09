package com.ascend.wangfeng.wifimanage.delegates.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.latte.ui.recycler.BaseDecoration;
import com.ascend.wangfeng.wifimanage.MainApp;
import com.ascend.wangfeng.wifimanage.R;
import com.ascend.wangfeng.wifimanage.bean.Person;
import com.ascend.wangfeng.wifimanage.bean.vo.PersonVo;
import com.ascend.wangfeng.wifimanage.greendao.PersonDao;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.yokeyword.fragmentation.ISupportFragment;

/**
 * Created by fengye on 2018/5/9.
 * email 1040441325@qq.com
 * 人员列表,提供新增功能
 */

public class PersonListDelegate extends LatteDelegate {
    @BindView(R.id.ic_back)
    IconTextView mIcBack;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.ll_add)
    LinearLayout mLlAdd;
    @BindView(R.id.rv_people)
    RecyclerView mRvPeople;
    private List<PersonVo> mPeopleVo;


    @Override
    public Object setLayout() {
        return R.layout.delegate_person_list;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        mToolbarTitle.setText("成员列表");
        mIcBack.setVisibility(View.VISIBLE);
        mIcBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop();
            }
        });
        mLlAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 进入成员信息编辑页面
                start(PersonDetailDelegate.newInstance(null));
            }
        });

    }

    @Override
    public void onResume() {
        //initList();
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        initList();
    }

    private void initList() {
        PersonDao dao = ((MainApp) getActivity().getApplication()).getDaoSession().getPersonDao();
        List<Person> people = dao.loadAll();
        mPeopleVo = new ArrayList<>();
        for (int i = 0; i < people.size(); i++) {
            mPeopleVo.add(PersonVo.get(people.get(i)));
        }
        PersonAdapter adapter = new PersonAdapter(mPeopleVo);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        mRvPeople.setLayoutManager(manager);
        mRvPeople.setAdapter(adapter);
        mRvPeople.addItemDecoration(BaseDecoration.create(getResources()
                .getColor(android.R.color.darker_gray), 1));
        if (mPeopleVo.size()>0){
        Bundle bundle = new Bundle();
        bundle.putSerializable("person",mPeopleVo.get(0));
        setFragmentResult(1,bundle);}
    }



    public static ISupportFragment newInstance() {
        return new PersonListDelegate();
    }
}
