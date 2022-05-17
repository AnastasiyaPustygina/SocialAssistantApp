package com.example.mainproject.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mainproject.OpenHelper;
import com.example.mainproject.R;
import com.example.mainproject.domain.Person;
import com.example.mainproject.rest.AppApiVolley;

public class RegFragment extends Fragment {

    private String data;
    private String name;
    private int age;

    private String dateOfBirth;
    private String city;
    private String password1;
    private String password2;
    private AppCompatButton btOfTel;
    private AppCompatButton btOfEmail;
    private EditText edTelOrEmail;
    private AppCompatButton bt_reg_fr_reg;
    private TextView checking, tv_data;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.reg_fragment, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        /*OpenHelper openHelper = new OpenHelper(getContext(), "OpenHelder", null, OpenHelper.VERSION);
        openHelper.deleteAllChat();
        openHelper.deleteAllMessage();
        openHelper.deleteAllOrganization();
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_channel);
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.image_for_checking);
        Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_channel);

        openHelper.insertOrg(new Organization( "yellowBackground", "orphan home", bitmap1,
                "Desc", "Address", "Needs", "Link"));
        openHelper.insertOrg(new Organization( "blueBackground", "orphan home", bitmap2,
                "SecondDesc", "SecondAddress", "SecondNeeds",
                "SecondLink"));
        openHelper.insertOrg(new Organization( "Three", "orphan home", bitmap3,
                "Desc3", "Address3", "Needs3",
                "Link3"));*/
        edTelOrEmail = getActivity().findViewById(R.id.ed_reg_data);
        checking = getActivity().findViewById(R.id.checking);
        tv_data = getActivity().findViewById(R.id.tv_reg_data);
        EditText edName = getActivity().findViewById(R.id.ed_reg_name);
        EditText edAge = getActivity().findViewById(R.id.ed_reg_age);

        EditText edData = getActivity().findViewById(R.id.ed_reg_data);
        bt_reg_fr_reg = getActivity().findViewById(R.id.bt_reg_fr_reg);
        EditText edBateOfBirth = getActivity().findViewById(R.id.ed_reg_dateOfBirth);
        EditText edCity = getActivity().findViewById(R.id.ed_reg_city);
        EditText edPass1 = getActivity().findViewById(R.id.ed_reg_pass1);
        EditText edPass2 = getActivity().findViewById(R.id.ed_reg_pass2);
        btOfTel = (getActivity().findViewById(R.id.bt_reg_telephone));
        btOfEmail = (getActivity().findViewById(R.id.bt_reg_email));
        btOfTel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btOfTel.setBackgroundResource(R.drawable.dark_circle_button);
                btOfTel.setTextColor(getResources().getColor(R.color.white));
                btOfEmail.setBackgroundResource(R.drawable.light_circle_button);
                btOfEmail.setTextColor(getResources().getColor(R.color.purple_700));
                tv_data.setText("Номер телефона");
                edTelOrEmail.setHint(Html.fromHtml("<small>"
                        + getString(R.string.ed_tel) + "<small>"));
            }
        });
        btOfEmail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                btOfEmail.setBackgroundResource(R.drawable.dark_circle_button);
                btOfEmail.setTextColor(getResources().getColor(R.color.white));
                btOfTel.setBackgroundResource(R.drawable.light_circle_button);
                btOfTel.setTextColor(getResources().getColor(R.color.purple_700));
                tv_data.setText("Адрес электронной почты");
                edTelOrEmail.setHint(Html.fromHtml("<small>"
                        + getString(R.string.ed_email) + "<small>"));
            }
        });
        bt_reg_fr_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = edName.getText().toString();
                try {
                    age = Integer.parseInt(edAge.getText().toString());
                } catch (Exception e) {
                }
                if (edData.getText().
                        toString().contains("@")) data = edTelOrEmail.getText().
                        toString();
                else data = edTelOrEmail.getText().
                        toString();
                dateOfBirth = edBateOfBirth.getText().toString();
                city = edCity.getText().toString();
                password1 = edPass1.getText().toString();
                password2 = edPass2.getText().toString();
                if (name.isEmpty()
                        || age == 0
                        || data.isEmpty()
                        || dateOfBirth.isEmpty()
                       || city.isEmpty()
                        || password1.isEmpty()
                        || password2.isEmpty()) {
                   checking.setText("Не все поля заполнены");
                } else {
                    if (!password1.equals(password2)) {
                        checking.setText("Пароли не совпадают");
                    } else {
                        OpenHelper openHelper = new OpenHelper(getContext(),
                                "OpenHelder",
                                null, OpenHelper.VERSION
                        );
                        Bitmap bitmap = BitmapFactory.decodeResource(
                                getResources(), R.drawable.image_for_checking);
                        new AppApiVolley(getContext()).addPerson
                                (new Person(data, name, bitmap, age, dateOfBirth, city, password1));
                        bt_reg_fr_reg.setOnClickListener((view1) -> {
                            NavHostFragment.
                                    findNavController(RegFragment.this).navigate(
                                    R.id.action_regFragment_to_signInFragment);
                        });
                        bt_reg_fr_reg.performClick();
                    }
                }
            }
        });
    }
}