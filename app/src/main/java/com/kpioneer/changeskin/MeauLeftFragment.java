package com.kpioneer.changeskin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.kpioneer.changeskin.skin.SkinManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author xionhgu
 * @version [版本号，2017/1/5]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */

public class MeauLeftFragment extends Fragment {

    @BindView(R.id.one)
    ImageView one;
    @BindView(R.id.rl_innerchange01)
    RelativeLayout mRedView;
    @BindView(R.id.two)
    ImageView two;
    @BindView(R.id.rl_innerchange02)
    RelativeLayout mGreenView;
    @BindView(R.id.three)
    ImageView three;
    @BindView(R.id.rl_innerchange03)
    RelativeLayout rlInnerchange03;
    @BindView(R.id.four)
    ImageView four;
    @BindView(R.id.rl_innerchange04)
    RelativeLayout rlInnerchange04;
    @BindView(R.id.five)
    ImageView five;
    @BindView(R.id.rl_innerchange05)
    RelativeLayout rlInnerchange05;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_menu, container, false);
       // ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

    }

    @OnClick({R.id.rl_innerchange01, R.id.rl_innerchange02, R.id.rl_innerchange03, R.id.rl_innerchange04, R.id.rl_innerchange05})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_innerchange01:
                SkinManager.getsInstance().changeSkin("red");

                Toast.makeText(getActivity(),"9989898989",Toast.LENGTH_LONG).show();

                break;
            case R.id.rl_innerchange02:
                SkinManager.getsInstance().changeSkin("green");

                Toast.makeText(getActivity(),"9989898989",Toast.LENGTH_LONG).show();

                break;
            case R.id.rl_innerchange03:
                break;
            case R.id.rl_innerchange04:
                break;
            case R.id.rl_innerchange05:
                break;
        }
    }
}
