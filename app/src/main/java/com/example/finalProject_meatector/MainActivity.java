package com.example.finalProject_meatector;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.example.finalProject_meatector.ml.Daging;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private ImageView imgView;
    private Button select, predict, amblFoto, save;
    private TextView tv,tv2;
    private Bitmap img;
    private String currentImagePath,Predicttext;



    private static final int REQUEST_CODE_PERMISSIONS = 1;
    private static final int REQUEST_CODE_CAPTURE_IMAGE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgView = (ImageView) findViewById(R.id.imageView);
        tv = (TextView) findViewById(R.id.textView);
        tv2 = (TextView) findViewById(R.id.textView2);
        select = (Button) findViewById(R.id.btnSelect);
        predict = (Button) findViewById(R.id.btnPredict);
        amblFoto = (Button) findViewById(R.id.btnCapture);
        save = (Button) findViewById(R.id.btnSave);

        tv2.setVisibility(View.INVISIBLE);
        save.setVisibility(View.INVISIBLE);


        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.history:
                        Intent intent = new Intent(MainActivity.this, History.class);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.home:
                        break;
                    case R.id.tips:
                        Intent intent2 = new Intent(MainActivity.this, Tips.class);
                        startActivity(intent2);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.help:
                        Intent intent3 = new Intent(MainActivity.this, Help.class);
                        startActivity(intent3);
                        overridePendingTransition(0,0);
                        break;
                }
                return false;
            }
        });

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,100);
            }
        });

        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(img == null){
                    Toast.makeText(MainActivity.this, "Failed Predict data!", Toast.LENGTH_SHORT).show();
                }

                else{

                    img = Bitmap.createScaledBitmap(img, 224, 224, true);

                    try {
                        Daging model = Daging.newInstance(getApplicationContext());


                        TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);


                        TensorImage tensorImage = new TensorImage(DataType.FLOAT32);
                        tensorImage.load(img);
                        ByteBuffer byteBuffer = tensorImage.getBuffer();
                        inputFeature0.loadBuffer(byteBuffer);


                        Daging.Outputs outputs = model.process(inputFeature0);
                        TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                        // Releases model resources if no longer used.
                        model.close();

                        float sapi = outputFeature0.getFloatArray()[0];
                        float kambing = outputFeature0.getFloatArray()[1];
                        float babi = outputFeature0.getFloatArray()[2];

                        DecimalFormat df = new DecimalFormat("#.##");

                        sapi = sapi*100;
                        kambing = kambing*100;
                        babi = babi*100;


                        if (sapi > kambing
                                && sapi > babi) {
                            tv.setText("Daging Sapi \n" + df.format(sapi) + "%");
                        } else if (kambing > sapi
                                && kambing > babi) {
                            tv.setText("Daging Kambing \n" + df.format(kambing) + "%");
                        } else if (babi > sapi
                                && babi > kambing) {
                            tv.setText("Daging Babi \n"+ df.format(babi) + "%");
                        }

                        Predicttext = tv.getText().toString();
                        tv2.setVisibility(View.VISIBLE);
                        save.setVisibility(View.VISIBLE);

                    } catch (IOException e) {
                        // TODO Handle the exception
                    }

                }

            }
        });

        amblFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(getApplicationContext(),
                                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                                    Manifest.permission.CAMERA,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                            }, REQUEST_CODE_PERMISSIONS
                    );

                } else {
                        dispactCaptureImageIntent();
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getImage = currentImagePath;
                Date date = Calendar.getInstance().getTime();
                String getDate = date.toString();
                String getPredict = Predicttext;

                DatabaseHelper db = new DatabaseHelper(MainActivity.this);
                long execution = db.insertData(getImage,getPredict,getDate);

                if(execution == - 1){
                    Toast.makeText(MainActivity.this, "Failed save data!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Save data successful!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void dispactCaptureImageIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            File imageFile = null;
            try {
                imageFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (imageFile != null) {
                Uri imageUri = FileProvider.getUriForFile(
                        this,
                        "com.example.finalProject_meatector.fileprovider",
                        imageFile
                );


                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, REQUEST_CODE_CAPTURE_IMAGE);
            }
        }
    }

    private File createImageFile() throws IOException {
        String fileName = "Image_"
                + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());

        File directory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(fileName,".jpeg", directory);

        currentImagePath = imageFile.getAbsolutePath();
        return  imageFile;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,  int[] grantResults) {
        if (requestCode == 50){
            if (grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                dispactCaptureImageIntent();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
            imgView.setImageURI(data.getData());
            Uri uri = data.getData();

            try {
                img = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                Bitmap bitmapimg = ((BitmapDrawable) imgView.getDrawable()).getBitmap();
                String fileName = "Image_"
                        + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date())+".jpeg";
                File file = bitmapToFile(this,bitmapimg,fileName);
                currentImagePath = file.getAbsolutePath();

                tv2.setVisibility(View.INVISIBLE);
                save.setVisibility(View.INVISIBLE);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == REQUEST_CODE_CAPTURE_IMAGE) {
            try {

                imgView.setImageBitmap(getScale(imgView));
                img =((BitmapDrawable) imgView.getDrawable()).getBitmap();

                tv2.setVisibility(View.INVISIBLE);
                save.setVisibility(View.INVISIBLE);

            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
    }

    private Bitmap getScale (ImageView imageView) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        int scaleFactor = Math.min(
                options.outWidth / imageView.getWidth(),
                options.outHeight / imageView.getHeight()
        );

        options.inJustDecodeBounds = false;
        options.inSampleSize = scaleFactor;

        return BitmapFactory.decodeFile(currentImagePath,options);
    }

    public File bitmapToFile(Context context, Bitmap bitmap, String fileNameToSave) {

        File file = null;
        try {
            File directory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            String path = directory.getAbsolutePath();
            file = new File(path + File.separator + fileNameToSave);
            file.createNewFile();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 0 , bos);
            byte[] bitmapdata = bos.toByteArray();


            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
            return file;
        }catch (Exception e){
            e.printStackTrace();
            return file;
        }
    }


}