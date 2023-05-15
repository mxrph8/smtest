package com.example.godplesedothis;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ArrayList<University> filteredList = new ArrayList<>();


    private List<University> universities;

    private static final MutableLiveData<Integer> seekBarValue;
    private static final MutableLiveData<Boolean> cb_fm;
    private static final MutableLiveData<Boolean> cb_se;
    private static final MutableLiveData<Boolean> cb_xb;

    static {
        seekBarValue = new MutableLiveData<>();
        seekBarValue.postValue(400);
        cb_fm = new MutableLiveData<>();
        cb_se = new MutableLiveData<>();
        cb_xb = new MutableLiveData<>();
    }
    public static MutableLiveData<Integer> getSeekBarValue(){
        return seekBarValue;
    }
    public static MutableLiveData<Boolean> getCb_fm(){
        return cb_fm;
    }
    public static MutableLiveData<Boolean> getCb_se(){
        return cb_se;
    }
    public static MutableLiveData<Boolean> getCb_xb(){
        return cb_xb;
    }




    FragmentCategory fragmentCategory = new FragmentCategory();

    int CLickSchet = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.ListView);

        arrayAdapterStart();
        initLiveData();


        Button categorybt = (Button) findViewById(R.id.categorybt);
        categorybt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CLickSchet += 1;
                setNewFragment(fragmentCategory);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDescriptionDialog(position);
            }
        });

    }



    public void initLiveData(){



        seekBarValue.observe(this,
                new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        System.out.println("123");
//                        for (University university : universities) {
//                            if (university.getPoints() <= getSeekBarValue().getValue()) {
//                                filteredList.add(university);
////                                listView.setAdapter(adapter);
//                            }
//                        }
                        FilterByPoints();
                    }
                }
                );

        cb_fm.observe(this,
                new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if (getCb_fm().getValue() == true){
                            System.out.println("11");
                            filterByFaculty("Физ-Мат");
                        }
                        else {
                            System.out.println("111");
                            removeByFaculty("Физ-Мат");}
                    }
                });
        cb_se.observe(this,
                new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        System.out.println("2");
                        if (getCb_se().getValue() == true){
                            filterByFaculty("Соц-Эконом");
                        }
                        else removeByFaculty("Соц-Эконом");
                    }
                });
        cb_xb.observe(this,
                new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        System.out.println("3");
                        if (getCb_xb().getValue() == true){
                            filterByFaculty("Хим-Био");
                        }
                        else removeByFaculty("Хим-Био");
                    }
                });
    }
    public void FilterByPoints(){
        ListView listView = findViewById(R.id.ListView);



        for (University university : universities) {
            if (university.getPoints() <= getSeekBarValue().getValue()) {
                filteredList.add(university);
                ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.vuzitem, R.id.vuzname, filteredList);
                listView.setAdapter(adapter);

            }
        }
    }

//    public void onCheckboxClicked(View view) {
//
//        CheckBox checkboxfm = findViewById(R.id.cbfm);
//        CheckBox checkboxse = findViewById(R.id.cbse);
//        CheckBox checkboxxb = findViewById(R.id.cbxb);
//
//        ListView listView = findViewById(R.id.ListView);
//
//        CheckBox checkBox = (CheckBox) view;
//
//        boolean checked = checkBox.isChecked();
//
//        switch (view.getId()) {
//            case R.id.cbfm:
//                if (checked) {
//                    filterByFaculty("Физ-Мат");
//                } else {
//                    removeByFaculty("Физ-Мат");
//                }
//                break;
//            case R.id.cbse:
//                if (checked) {
//                    filterByFaculty("Соц-Эконом");
//                } else {
//                    removeByFaculty("Соц-Эконом");
//                }
//                break;
//            case R.id.cbxb:
//                if (checked) {
//                    filterByFaculty("Хим-Био");
//                } else {
//                    removeByFaculty("Хим-Био");
//                }
//            default:
//                if (checkboxfm.isChecked() != true && checkboxse.isChecked() != true && checkboxxb.isChecked() != true) {
//                    ArrayAdapter<University> adapter = new ArrayAdapter<>(this,
//                            R.layout.vuzitem, R.id.vuzname, universities);
//                    listView.setAdapter(adapter);
//                }
//        }
//    }

    private void setNewFragment(Fragment fragment) {
        if (CLickSchet % 2 != 0) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_layout, fragment);
            ft.show(fragment);
            ft.commit();
        } else {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.hide(fragment);
            ft.commit();
        }

    }


    public void arrayAdapterStart() {
        ListView listView = findViewById(R.id.ListView);
        universities = new ArrayList<>();
        universities.add(new University(1, "Университет Синергия", "Хим-Био", 230, "dadadaff"));
        universities.add(new University(2, "Национальный исследовательский университет «МЭИ» ", "Физ-Мат", 142, "dadadaff"));
        universities.add(new University(3, "Национальный исследовательский университет Высшая школа экономики", "Соц-Эконом", 200, "dadadaff"));
        universities.add(new University(4, "Московский политехнический университет", "Хим-Био", 130, "dadadaff"));
        universities.add(new University(5, "Санкт-Петербургский национальный исследовательский Академический университет имени Ж.И. Алферова Российской академии наук", "Физ-Мат", 160, "dadadaff"));
        universities.add(new University(6, "Финансовый университет при Правительстве РФ", "Соц-Эконом", 172, "dadadaff"));
        universities.add(new University(7, "Московский институт психоанализа", "Хим-Био", 109, "dadadaff"));
        universities.add(new University(8, "Московский политехнический университет ", "Физ-Мат", 132, "dadadaff"));
        universities.add(new University(9, "Южный федеральный университет", "Соц-Эконом", 123, "dadadaff"));
        universities.add(new University(10, "Московский государственный областной университет", "Хим-Био", 140, "dadadaff"));
        universities.add(new University(11, "Национальный исследовательский ядерный университет «МИФИ»", "Физ-Мат", 158, "dadadaff"));
        universities.add(new University(12, "Сибирский федеральный университет", "Соц-Эконом", 112, "dadadaff"));
        universities.add(new University(13, "Уральский государственный лесотехнический университет", "Физ-Мат", 130, "dadadaff"));
        universities.add(new University(14, "Уральский ГАУ", "Физ-Мат", 132, "dadadaff"));
        universities.add(new University(15, "Уральский государственный горный университет", "Физ-Мат", 137, "dadadaff"));
        universities.add(new University(16, "Уральский институт Государственной противопожарной службы МЧС России", "Физ-Мат", 111, "dadadaff"));


        ArrayAdapter<University> adapter = new ArrayAdapter<>(this,
                R.layout.vuzitem, R.id.vuzname, universities);
        listView.setAdapter(adapter);
    }

    public void removeByFaculty(String faculty) {
        ListView listView = findViewById(R.id.ListView);

        for (University university : universities) {
            if (university.getFaculty().equals(faculty)) {
                filteredList.remove(university);
            }
        }

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.vuzitem, R.id.vuzname, filteredList);
        listView.setAdapter(adapter);
    }

    public void filterByFaculty(String faculty) {

        ListView listView = findViewById(R.id.ListView);

        for (University university : universities) {
            if (university.getFaculty().equals(faculty)) {
                filteredList.add(university);
            }
        }
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.vuzitem, R.id.vuzname, filteredList);
        listView.setAdapter(adapter);
    }

    private void showDescriptionDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(universities.get(position).getName())
                .setMessage(universities.get(position).getDescription())
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    }


